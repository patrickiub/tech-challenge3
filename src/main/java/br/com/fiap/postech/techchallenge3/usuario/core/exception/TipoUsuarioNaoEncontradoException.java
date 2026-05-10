package br.com.fiap.postech.techchallenge3.usuario.core.exception;

public class TipoUsuarioNaoEncontradoException extends RuntimeException {
    public TipoUsuarioNaoEncontradoException(Long id) {
        super("TipoUsuario não encontrado com id: " + id);
    }
}
