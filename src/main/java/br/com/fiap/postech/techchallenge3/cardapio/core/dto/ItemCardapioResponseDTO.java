package br.com.fiap.postech.techchallenge3.cardapio.core.dto;

import br.com.fiap.postech.techchallenge3.cardapio.core.domain.CategoriaItemCardapio;
import br.com.fiap.postech.techchallenge3.cardapio.core.domain.ItemCardapio;

import java.math.BigDecimal;

public record ItemCardapioResponseDTO(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        CategoriaItemCardapio categoria,
        Long restauranteId,
        String fotoPath
) {

    public static ItemCardapioResponseDTO from(ItemCardapio item) {
        return new ItemCardapioResponseDTO(
                item.getId(),
                item.getNome(),
                item.getDescricao(),
                item.getPreco(),
                item.getCategoria(),
                item.getRestauranteId(),
                item.getFotoPath()
        );
    }
}
