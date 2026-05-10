package br.com.fiap.postech.techchallenge3.pedido.core.exception;

public class PedidoNaoEncontradoException extends RuntimeException {

    public PedidoNaoEncontradoException(Long id) {
        super("Pedido de id " + id + " nÃ£o encontrado");
    }
}

