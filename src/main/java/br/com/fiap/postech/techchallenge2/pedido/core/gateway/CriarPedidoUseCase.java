package br.com.fiap.postech.techchallenge2.pedido.core.gateway;

import br.com.fiap.postech.techchallenge2.pedido.core.domain.Pedido;
import br.com.fiap.postech.techchallenge2.pedido.core.usecase.PedidoGateway;

public class CriarPedidoUseCase {

    private final PedidoGateway pedidoGateway;

    public CriarPedidoUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido executar(Pedido pedido){
        return pedidoGateway.salvar(pedido);
    }


}
