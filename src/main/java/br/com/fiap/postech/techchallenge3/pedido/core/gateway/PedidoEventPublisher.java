package br.com.fiap.postech.techchallenge3.pedido.core.gateway;

import br.com.fiap.postech.techchallenge3.pedido.core.dto.PedidoCriadoEvent;

public interface PedidoEventPublisher {
    void publicarPedidoCriado(PedidoCriadoEvent event);
}
