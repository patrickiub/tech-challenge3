package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.Usuario;
import br.com.fiap.postech.techchallenge3.usuario.core.exception.UsuarioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.UsuarioGateway;
import org.springframework.stereotype.Service;

@Service
public class BuscarUsuarioPorIdUseCase {

    private final UsuarioGateway usuarioGateway;

    public BuscarUsuarioPorIdUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public Usuario executar(Long id) {
        return usuarioGateway.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }
}
