package br.com.fiap.postech.techchallenge3.pedido.core.usecase;

import br.com.fiap.postech.techchallenge3.pedido.core.domain.Pedido;
import br.com.fiap.postech.techchallenge3.pedido.core.dto.CriarPedidoRequestDTO;
import br.com.fiap.postech.techchallenge3.pedido.core.dto.ItemPedidoResponseDTO;
import br.com.fiap.postech.techchallenge3.pedido.core.dto.PedidoCriadoEvent;
import br.com.fiap.postech.techchallenge3.pedido.core.gateway.PedidoGateway;
import br.com.fiap.postech.techchallenge3.pedido.infra.kafka.PedidoKafkaProducer;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CriarPedidoUseCase {

    private final PedidoGateway pedidoGateway;
    private final PedidoKafkaProducer kafkaProducer;

    public CriarPedidoUseCase(PedidoGateway pedidoGateway, PedidoKafkaProducer kafkaProducer) {
        this.pedidoGateway = pedidoGateway;
        this.kafkaProducer = kafkaProducer;
    }

    public Pedido executar(CriarPedidoRequestDTO dto) {
        Pedido pedido = pedidoGateway.salvar(dto);

        PedidoCriadoEvent event = new PedidoCriadoEvent(
                String.valueOf(pedido.getId()),
                String.valueOf(pedido.getClienteId()),
                String.valueOf(pedido.getRestauranteId()),
                pedido.getValorTotal(),
                pedido.getItens().stream().map(ItemPedidoResponseDTO::from).collect(Collectors.toList())
        );
        kafkaProducer.publicarPedidoCriado(event);

        return pedido;
    }
}
