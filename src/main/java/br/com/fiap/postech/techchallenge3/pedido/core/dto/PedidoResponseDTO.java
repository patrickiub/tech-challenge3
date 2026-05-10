package br.com.fiap.postech.techchallenge3.pedido.core.dto;

import java.math.BigDecimal;
import java.util.List;

import br.com.fiap.postech.techchallenge3.pedido.core.domain.Pedido;
import br.com.fiap.postech.techchallenge3.pedido.core.domain.StatusPedido;

public record PedidoResponseDTO(
        Long id,
        Long clienteId,
        Long restauranteId,
        BigDecimal valorTotal,
        StatusPedido status,
        List<ItemPedidoResponseDTO> itens
) {
    public static PedidoResponseDTO from(Pedido pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getClienteId(),
                pedido.getRestauranteId(),
                pedido.getValorTotal(),
                pedido.getStatus(),
                pedido.getItens().stream()
                        .map(ItemPedidoResponseDTO::from)
                        .toList()
        );
    }
}
