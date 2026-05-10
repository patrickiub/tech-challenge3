package br.com.fiap.postech.techchallenge3.pedido.core.usecase;

import org.springframework.stereotype.Service;

import br.com.fiap.postech.techchallenge3.pedido.core.domain.Pedido;
import br.com.fiap.postech.techchallenge3.pedido.core.exception.PedidoNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.pedido.core.gateway.PedidoGateway;

@Service
public class ConsultarPedidoUseCase {

    private final PedidoGateway pedidoGateway;

    public ConsultarPedidoUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido executar(Long id) {
        return pedidoGateway.consultarPedido(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

}
