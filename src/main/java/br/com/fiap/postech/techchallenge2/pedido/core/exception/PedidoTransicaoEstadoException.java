package br.com.fiap.postech.techchallenge2.pedido.core.exception;

public class PedidoTransicaoEstadoException extends RuntimeException {

    public PedidoTransicaoEstadoException(String statusAtual, String novoStatus) {
        super("Pedido não pode ser alterado do status: " + statusAtual + " para o status: " + novoStatus);
    }
}

