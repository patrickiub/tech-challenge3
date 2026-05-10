package com.fiap.pagamento.infra.kafka.producer;

import com.fiap.pagamento.core.dto.PagamentoAprovadoEvent;
import com.fiap.pagamento.core.dto.PagamentoPendenteEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PagamentoKafkaProducer {

    private static final String TOPIC_APROVADO = "pagamento.aprovado";
    private static final String TOPIC_PENDENTE = "pagamento.pendente";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PagamentoKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicarAprovado(PagamentoAprovadoEvent event) {
        kafkaTemplate.send(TOPIC_APROVADO, event.getPedidoId(), event);
    }

    public void publicarPendente(PagamentoPendenteEvent event) {
        kafkaTemplate.send(TOPIC_PENDENTE, event.getPedidoId(), event);
    }
}
