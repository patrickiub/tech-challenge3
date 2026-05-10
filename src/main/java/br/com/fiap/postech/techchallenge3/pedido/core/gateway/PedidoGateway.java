package br.com.fiap.postech.techchallenge3.pedido.core.gateway;


import java.util.List;
import java.util.Optional;

import br.com.fiap.postech.techchallenge3.pedido.core.domain.Pedido;
import br.com.fiap.postech.techchallenge3.pedido.core.dto.CriarPedidoRequestDTO;

public interface PedidoGateway {

    Pedido salvar(CriarPedidoRequestDTO dto);

    Optional<Pedido> consultarPedido(Long pedidoId);

    List<Pedido> consultarPedidosCliente(Long clienteId);

    Pedido atualizarStatus(Pedido pedido);

}
