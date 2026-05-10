package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.Usuario;
import br.com.fiap.postech.techchallenge3.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.TipoUsuarioGateway;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.UsuarioGateway;
import org.springframework.stereotype.Service;

@Service
public class CriarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;
    private final TipoUsuarioGateway tipoUsuarioGateway;

    public CriarUsuarioUseCase(UsuarioGateway usuarioGateway, TipoUsuarioGateway tipoUsuarioGateway) {
        this.usuarioGateway = usuarioGateway;
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public Usuario executar(Usuario usuario) {
        Long tipoId = usuario.getTipoUsuario().getId();
        tipoUsuarioGateway.buscarPorId(tipoId)
                .orElseThrow(() -> new TipoUsuarioNaoEncontradoException(tipoId));
        return usuarioGateway.salvar(usuario);
    }
}
