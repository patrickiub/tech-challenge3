package br.com.fiap.postech.techchallenge2.pedido.core.exception;

public class PedidoNaoPertenceClienteException extends RuntimeException {

    public PedidoNaoPertenceClienteException(Long pedidoId, Long clienteId) {
        super("O pedido: " + pedidoId + " não pertence ao cliente: " + clienteId);
    }
}

