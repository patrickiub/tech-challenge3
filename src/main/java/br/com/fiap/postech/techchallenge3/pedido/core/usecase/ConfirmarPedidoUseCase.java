package br.com.fiap.postech.techchallenge3.pedido.core.usecase;

import org.springframework.stereotype.Service;

import br.com.fiap.postech.techchallenge3.pedido.core.domain.Pedido;
import br.com.fiap.postech.techchallenge3.pedido.core.exception.PedidoNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.pedido.core.exception.PedidoNaoPertenceClienteException;
import br.com.fiap.postech.techchallenge3.pedido.core.gateway.PedidoGateway;

@Service
public class ConfirmarPedidoUseCase {

    private final PedidoGateway pedidoGateway;

    public ConfirmarPedidoUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido executar(Long pedidoId, Long clienteId) {

        Pedido pedido = pedidoGateway.consultarPedido(pedidoId)
            .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));

        if (!pedido.getClienteId().equals(clienteId)) {
            throw new PedidoNaoPertenceClienteException(pedidoId, clienteId);
        }    

        pedido.confirmar(); // regra de negÃ³cio encapsulada no domÃ­nio
        return pedidoGateway.atualizarStatus(pedido); // gateway recebe objeto de domÃ­nio
    }

}
