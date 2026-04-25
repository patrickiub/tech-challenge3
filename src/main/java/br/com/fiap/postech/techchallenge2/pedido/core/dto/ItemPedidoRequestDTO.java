package br.com.fiap.postech.techchallenge2.pedido.core.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoRequestDTO(
        @NotNull(message = "Item de cardápio é obrigatório")
        Long itemCardapioId,

        @Positive(message = "Quantidade deve ser positiva")
        int quantidade
) {
}
