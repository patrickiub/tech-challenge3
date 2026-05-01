package br.com.fiap.postech.techchallenge2.pedido.core.exception;

public class PedidoNaoEncontradoException extends RuntimeException {

    public PedidoNaoEncontradoException(Long id) {
        super("Pedido de id " + id + " não encontrado");
    }
}

