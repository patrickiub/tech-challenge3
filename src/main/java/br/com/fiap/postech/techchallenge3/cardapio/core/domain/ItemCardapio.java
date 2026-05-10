package br.com.fiap.postech.techchallenge3.cardapio.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCardapio {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private CategoriaItemCardapio categoria;
    private Long restauranteId;
    private String fotoPath;
}
