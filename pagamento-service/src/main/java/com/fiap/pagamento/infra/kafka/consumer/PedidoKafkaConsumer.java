package com.fiap.pagamento.infra.kafka.consumer;

import com.fiap.pagamento.core.dto.PagamentoEvent;
import com.fiap.pagamento.core.usecase.ProcessarPagamentoUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoKafkaConsumer {

    private final ProcessarPagamentoUseCase processarPagamentoUseCase;

    public PedidoKafkaConsumer(ProcessarPagamentoUseCase processarPagamentoUseCase) {
        this.processarPagamentoUseCase = processarPagamentoUseCase;
    }

    @KafkaListener(topics = "pedido.criado", groupId = "${spring.kafka.consumer.group-id}")
    public void consumir(PagamentoEvent event) {
        processarPagamentoUseCase.processar(event);
    }
}
