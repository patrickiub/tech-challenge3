package br.com.fiap.postech.techchallenge3.pedido.core.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.postech.techchallenge3.pedido.core.domain.Pedido;
import br.com.fiap.postech.techchallenge3.pedido.core.gateway.PedidoGateway;

@Service
public class ConsultarPedidosClienteUseCase {

    private final PedidoGateway pedidoGateway;

    public ConsultarPedidosClienteUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public List<Pedido> executar(Long clienteId){
        return pedidoGateway.consultarPedidosCliente(clienteId);
    }
}
