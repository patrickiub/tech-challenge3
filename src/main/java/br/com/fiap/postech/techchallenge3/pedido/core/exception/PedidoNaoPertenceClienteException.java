package br.com.fiap.postech.techchallenge3.pedido.core.exception;

public class PedidoNaoPertenceClienteException extends RuntimeException {

    public PedidoNaoPertenceClienteException(Long pedidoId, Long clienteId) {
        super("O pedido: " + pedidoId + " nÃ£o pertence ao cliente: " + clienteId);
    }
}

