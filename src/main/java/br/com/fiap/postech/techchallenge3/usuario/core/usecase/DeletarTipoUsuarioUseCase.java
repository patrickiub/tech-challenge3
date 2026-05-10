package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.TipoUsuarioGateway;
import org.springframework.stereotype.Service;

@Service
public class DeletarTipoUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public DeletarTipoUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public void executar(Long id) {
        if (!tipoUsuarioGateway.existePorId(id)) {
            throw new TipoUsuarioNaoEncontradoException(id);
        }
        tipoUsuarioGateway.deletar(id);
    }
}
