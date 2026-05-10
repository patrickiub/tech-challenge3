package br.com.fiap.postech.techchallenge3.cardapio.core.exception;

public class ItemCardapioNaoEncontradoException extends RuntimeException {
    public ItemCardapioNaoEncontradoException(Long id) {
        super("Item de cardápio não encontrado com id: " + id);
    }
}
