package br.com.fiap.postech.techchallenge2.pedido.core.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {    
    private Long id;
    private Long itemCardapioId;
    private String nome;
    private int quantidade;
    private BigDecimal preco;
}
