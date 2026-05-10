package com.fiap.pagamento.core.domain;

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
public class Pagamento {
    private Long id;
    private String pedidoId;
    private String clienteId;
    private BigDecimal valor;
    private StatusPagamento status;
    private LocalDateTime dataCriacao;
}
