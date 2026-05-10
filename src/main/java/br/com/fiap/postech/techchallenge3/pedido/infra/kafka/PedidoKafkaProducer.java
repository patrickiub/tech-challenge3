package br.com.fiap.postech.techchallenge3.pedido.infra.kafka;

import br.com.fiap.postech.techchallenge3.pedido.core.dto.PedidoCriadoEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PedidoKafkaProducer {

    private static final String TOPIC = "pedido.criado";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PedidoKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicarPedidoCriado(PedidoCriadoEvent event) {
        kafkaTemplate.send(TOPIC, event.getPedidoId(), event);
    }
}
