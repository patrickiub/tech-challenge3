package br.com.fiap.postech.techchallenge3.pedido.core.dto;

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
public class PedidoCriadoEvent {
    private String pedidoId;
    private String clienteId;
    private String restauranteId;
    private BigDecimal valorTotal;
    private List<ItemPedidoResponseDTO> itens;
}
