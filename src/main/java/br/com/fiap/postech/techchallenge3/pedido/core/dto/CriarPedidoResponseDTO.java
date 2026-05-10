package br.com.fiap.postech.techchallenge3.pedido.core.dto;

import java.math.BigDecimal;

import br.com.fiap.postech.techchallenge3.pedido.core.domain.Pedido;

public record CriarPedidoResponseDTO(
        Long id,
        BigDecimal valorTotal
) {
    public static CriarPedidoResponseDTO from(Pedido pedido) {
        return new CriarPedidoResponseDTO(
                pedido.getId(),
                pedido.getValorTotal()
        );
    }
}
