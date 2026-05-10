package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.exception.UsuarioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.UsuarioGateway;
import org.springframework.stereotype.Service;

@Service
public class DeletarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public DeletarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public void executar(Long id) {
        if (!usuarioGateway.existePorId(id)) {
            throw new UsuarioNaoEncontradoException(id);
        }
        usuarioGateway.deletar(id);
    }
}
