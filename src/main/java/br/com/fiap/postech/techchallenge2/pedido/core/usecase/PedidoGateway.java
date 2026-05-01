package br.com.fiap.postech.techchallenge2.pedido.core.usecase;

import br.com.fiap.postech.techchallenge2.pedido.core.domain.Pedido;
import br.com.fiap.postech.techchallenge2.pedido.core.dto.PedidoRequestDTO;

public interface PedidoGateway {

    Pedido salvar(PedidoRequestDTO dto);

}
