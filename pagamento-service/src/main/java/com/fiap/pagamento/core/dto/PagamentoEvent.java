package com.fiap.pagamento.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoEvent {
    private String pedidoId;
    private String clienteId;
    private BigDecimal valor;
}
