package com.fiap.pagamento.core.usecase;

import com.fiap.pagamento.core.domain.Pagamento;
import com.fiap.pagamento.core.domain.StatusPagamento;
import com.fiap.pagamento.core.dto.PagamentoAprovadoEvent;
import com.fiap.pagamento.core.dto.PagamentoEvent;
import com.fiap.pagamento.core.dto.PagamentoPendenteEvent;
import com.fiap.pagamento.core.dto.RequisicaoProcpagDTO;
import com.fiap.pagamento.core.gateway.PagamentoGateway;
import com.fiap.pagamento.infra.client.ProcpagClient;
import com.fiap.pagamento.infra.kafka.producer.PagamentoKafkaProducer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ProcessarPagamentoUseCase {

    private final ProcpagClient procpagClient;
    private final PagamentoGateway pagamentoGateway;
    private final PagamentoKafkaProducer kafkaProducer;

    public ProcessarPagamentoUseCase(ProcpagClient procpagClient, PagamentoGateway pagamentoGateway,
                                     PagamentoKafkaProducer kafkaProducer) {
        this.procpagClient = procpagClient;
        this.pagamentoGateway = pagamentoGateway;
        this.kafkaProducer = kafkaProducer;
    }

    @CircuitBreaker(name = "procpag", fallbackMethod = "fallbackPagamento")
    @Retry(name = "procpag")
    public void processar(PagamentoEvent event) {
        log.info("Chamando procpag para pedidoId={}, valor={}", event.getPedidoId(), event.getValorTotal());
        RequisicaoProcpagDTO requisicao = new RequisicaoProcpagDTO(
                event.getValorTotal().longValue(), event.getPedidoId(), event.getClienteId());

        boolean aprovado = procpagClient.processar(requisicao);

        if (!aprovado) {
            throw new RuntimeException("Pagamento recusado pelo procpag para pedido: " + event.getPedidoId());
        }

        log.info("Pagamento aprovado para pedidoId={}", event.getPedidoId());
        LocalDateTime agora = LocalDateTime.now();
        Pagamento pagamento = new Pagamento(null, event.getPedidoId(), event.getClienteId(),
                event.getValorTotal(), StatusPagamento.APROVADO, agora);
        pagamentoGateway.salvar(pagamento);

        kafkaProducer.publicarAprovado(new PagamentoAprovadoEvent(
                event.getPedidoId(), event.getClienteId(), event.getValorTotal(), agora));
    }

    public void fallbackPagamento(PagamentoEvent event, Throwable t) {
        log.error("Fallback ativado para pedidoId={}: {}", event.getPedidoId(), t.getMessage(), t);
        salvarPendente(event);
    }

    private void salvarPendente(PagamentoEvent event) {
        LocalDateTime agora = LocalDateTime.now();
        Pagamento pagamento = new Pagamento(null, event.getPedidoId(), event.getClienteId(),
                event.getValorTotal(), StatusPagamento.PENDENTE, agora);
        pagamentoGateway.salvar(pagamento);

        kafkaProducer.publicarPendente(new PagamentoPendenteEvent(
                event.getPedidoId(), event.getClienteId(), event.getValorTotal(), 1, agora));
    }
}
