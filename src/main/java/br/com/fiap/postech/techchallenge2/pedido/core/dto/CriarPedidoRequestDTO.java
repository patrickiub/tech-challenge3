package br.com.fiap.postech.techchallenge2.pedido.core.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record CriarPedidoRequestDTO(

    @NotNull(message = "Cliente é obrigatório")
    Long clienteId,

    @NotNull(message = "Restaurante é obrigatório")
    Long restauranteId,

    @NotNull(message = "Itens são obrigatórios")
    List<ItemPedidoRequestDTO> itens

) {    
}
