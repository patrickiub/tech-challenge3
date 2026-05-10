package br.com.fiap.postech.techchallenge3.pedido.core.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record CriarPedidoRequestDTO(

    @NotNull(message = "Cliente Ã© obrigatÃ³rio")
    Long clienteId,

    @NotNull(message = "Restaurante Ã© obrigatÃ³rio")
    Long restauranteId,

    @NotNull(message = "Itens sÃ£o obrigatÃ³rios")
    List<ItemPedidoRequestDTO> itens

) {    
}
