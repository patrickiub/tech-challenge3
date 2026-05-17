package com.fiap.pagamento.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequisicaoProcpagDTO {
    private Long valor;

    @JsonProperty("pagamento_id")
    private String pagamentoId;

    @JsonProperty("cliente_id")
    private String clienteId;
}
