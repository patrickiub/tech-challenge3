package br.com.fiap.postech.techchallenge3.pedido.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoAprovadoEvent {
    private String pedidoId;
    private String clienteId;
    private BigDecimal valorTotal;
}
