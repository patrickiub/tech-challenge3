package br.com.fiap.postech.techchallenge3.pedido.core.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoRequestDTO(
        @NotNull(message = "Item de cardÃ¡pio Ã© obrigatÃ³rio")
        Long itemCardapioId,

        @Positive(message = "Quantidade deve ser positiva")
        int quantidade
) {
}
