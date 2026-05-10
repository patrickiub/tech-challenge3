package br.com.fiap.postech.techchallenge3.pedido.core.exception;

public class PedidoTransicaoEstadoException extends RuntimeException {

    public PedidoTransicaoEstadoException(String statusAtual, String novoStatus) {
        super("Pedido nÃ£o pode ser alterado do status: " + statusAtual + " para o status: " + novoStatus);
    }
}

