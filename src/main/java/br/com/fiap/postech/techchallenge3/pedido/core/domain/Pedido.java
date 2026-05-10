package br.com.fiap.postech.techchallenge3.pedido.core.domain;

import java.math.BigDecimal;
import java.util.List;

import br.com.fiap.postech.techchallenge3.pedido.core.exception.PedidoTransicaoEstadoException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    private Long id;
    private Long clienteId;
    private Long restauranteId;
    private List<ItemPedido> itens;
    private BigDecimal valorTotal;
    private StatusPedido status;

    public Pedido(Long clienteId, Long restauranteId, List<ItemPedido> itens) {
        this.clienteId = clienteId;
        this.restauranteId = restauranteId;
        this.itens = itens;
        this.valorTotal = calcularTotal();
        this.status = StatusPedido.AGUARDANDO_CONFIRMACAO;
    }

    private BigDecimal calcularTotal() {
        return itens.stream()
                .map(item -> item.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void confirmar() {
        if (!StatusPedido.AGUARDANDO_CONFIRMACAO.equals(this.status)){
            throw new PedidoTransicaoEstadoException(this.status.toString(), StatusPedido.AGUARDANDO_CONFIRMACAO.toString());
        }

        this.status = StatusPedido.CONFIRMADO;
    }
}

