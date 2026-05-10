package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;
import br.com.fiap.postech.techchallenge3.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.TipoUsuarioGateway;
import org.springframework.stereotype.Service;

@Service
public class AtualizarTipoUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public AtualizarTipoUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public TipoUsuario executar(Long id, TipoUsuario tipoUsuario) {
        if (!tipoUsuarioGateway.existePorId(id)) {
            throw new TipoUsuarioNaoEncontradoException(id);
        }
        tipoUsuario.setId(id);
        return tipoUsuarioGateway.salvar(tipoUsuario);
    }
}
