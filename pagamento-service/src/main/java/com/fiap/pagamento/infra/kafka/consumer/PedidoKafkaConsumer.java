package com.fiap.pagamento.infra.kafka.consumer;

import com.fiap.pagamento.core.dto.PagamentoEvent;
import com.fiap.pagamento.core.usecase.ProcessarPagamentoUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PedidoKafkaConsumer {

    private final ProcessarPagamentoUseCase processarPagamentoUseCase;

    public PedidoKafkaConsumer(ProcessarPagamentoUseCase processarPagamentoUseCase) {
        this.processarPagamentoUseCase = processarPagamentoUseCase;
    }

    @KafkaListener(topics = "pedido.criado", groupId = "${spring.kafka.consumer.group-id}")
    public void consumir(PagamentoEvent event) {
        log.info("Evento pedido.criado recebido: pedidoId={}, valorTotal={}", event.getPedidoId(), event.getValorTotal());
        processarPagamentoUseCase.processar(event);
    }
}
