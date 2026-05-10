package com.fiap.pagamento.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoAprovadoEvent {
    private String pedidoId;
    private String clienteId;
    private BigDecimal valor;
    private LocalDateTime dataPagamento;
}
