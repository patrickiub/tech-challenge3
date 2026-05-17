package br.com.fiap.postech.techchallenge3.pedido.infra.kafka;

import br.com.fiap.postech.techchallenge3.pedido.core.domain.Pedido;
import br.com.fiap.postech.techchallenge3.pedido.core.domain.StatusPedido;
import br.com.fiap.postech.techchallenge3.pedido.core.dto.PagamentoAprovadoEvent;
import br.com.fiap.postech.techchallenge3.pedido.core.dto.PagamentoPendenteEvent;
import br.com.fiap.postech.techchallenge3.pedido.core.gateway.PedidoGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PagamentoKafkaConsumer {

    private final PedidoGateway pedidoGateway;

    public PagamentoKafkaConsumer(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    @KafkaListener(topics = "pagamento.aprovado", groupId = "pedido-service")
    public void consumirAprovado(PagamentoAprovadoEvent event) {
        log.info("Evento pagamento.aprovado recebido: pedidoId={}", event.getPedidoId());
        Long pedidoId = Long.parseLong(event.getPedidoId());
        Pedido pedido = pedidoGateway.consultarPedido(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + pedidoId));
        pedido.setStatus(StatusPedido.PAGO);
        pedidoGateway.atualizarStatus(pedido);
        log.info("Pedido {} atualizado para PAGO", pedidoId);
    }

    @KafkaListener(topics = "pagamento.pendente", groupId = "pedido-service")
    public void consumirPendente(PagamentoPendenteEvent event) {
        log.info("Evento pagamento.pendente recebido: pedidoId={}, tentativas={}", event.getPedidoId(), event.getTentativas());
        Long pedidoId = Long.parseLong(event.getPedidoId());
        Pedido pedido = pedidoGateway.consultarPedido(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + pedidoId));
        pedido.setStatus(StatusPedido.PENDENTE_PAGAMENTO);
        pedidoGateway.atualizarStatus(pedido);
        log.info("Pedido {} atualizado para PENDENTE_PAGAMENTO", pedidoId);
    }
}
