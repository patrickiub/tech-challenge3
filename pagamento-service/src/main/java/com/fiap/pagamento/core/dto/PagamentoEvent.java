package com.fiap.pagamento.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoEvent {
    private String pedidoId;
    private String clienteId;
    private String restauranteId;
    private BigDecimal valorTotal;
    private List<Object> itens;
}
