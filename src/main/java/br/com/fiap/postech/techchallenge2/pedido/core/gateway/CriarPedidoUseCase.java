package br.com.fiap.postech.techchallenge2.pedido.core.gateway;

import org.springframework.stereotype.Service;

import br.com.fiap.postech.techchallenge2.pedido.core.domain.Pedido;
import br.com.fiap.postech.techchallenge2.pedido.core.dto.PedidoRequestDTO;
import br.com.fiap.postech.techchallenge2.pedido.core.usecase.PedidoGateway;

@Service
public class CriarPedidoUseCase {

    private final PedidoGateway pedidoGateway;

    public CriarPedidoUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido executar(PedidoRequestDTO dto){
        return pedidoGateway.salvar(dto);
    }
}
