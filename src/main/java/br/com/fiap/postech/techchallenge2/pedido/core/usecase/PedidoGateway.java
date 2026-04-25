package br.com.fiap.postech.techchallenge2.pedido.core.usecase;

import br.com.fiap.postech.techchallenge2.pedido.core.domain.Pedido;

public interface PedidoGateway {

    Pedido salvar(Pedido pedido);

}
