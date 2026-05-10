package br.com.fiap.postech.techchallenge2.pedido.core.dto;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        Long id,
        Long itemCardapioId,
        String nome,
        int quantidade,
        BigDecimal preco
) {
    public static ItemPedidoResponseDTO from(br.com.fiap.postech.techchallenge2.pedido.core.domain.ItemPedido item) {
        return new ItemPedidoResponseDTO(
                item.getId(),
                item.getItemCardapioId(),
                item.getNome(),
                item.getQuantidade(),
                item.getPreco()
        );
    }
}
