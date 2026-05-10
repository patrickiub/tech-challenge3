package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.Usuario;
import br.com.fiap.postech.techchallenge3.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.exception.UsuarioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.TipoUsuarioGateway;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.UsuarioGateway;
import org.springframework.stereotype.Service;

@Service
public class AtualizarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;
    private final TipoUsuarioGateway tipoUsuarioGateway;

    public AtualizarUsuarioUseCase(UsuarioGateway usuarioGateway, TipoUsuarioGateway tipoUsuarioGateway) {
        this.usuarioGateway = usuarioGateway;
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public Usuario executar(Long id, Usuario usuario) {
        if (!usuarioGateway.existePorId(id)) {
            throw new UsuarioNaoEncontradoException(id);
        }
        Long tipoId = usuario.getTipoUsuario().getId();
        tipoUsuarioGateway.buscarPorId(tipoId)
                .orElseThrow(() -> new TipoUsuarioNaoEncontradoException(tipoId));
        usuario.setId(id);
        return usuarioGateway.salvar(usuario);
    }
}
