package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;
import br.com.fiap.postech.techchallenge3.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.TipoUsuarioGateway;
import org.springframework.stereotype.Service;

@Service
public class BuscarTipoUsuarioPorIdUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public BuscarTipoUsuarioPorIdUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public TipoUsuario executar(Long id) {
        return tipoUsuarioGateway.buscarPorId(id)
                .orElseThrow(() -> new TipoUsuarioNaoEncontradoException(id));
    }
}
