package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.TipoUsuarioGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarTiposUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public ListarTiposUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public List<TipoUsuario> executar() {
        return tipoUsuarioGateway.listarTodos();
    }
}
