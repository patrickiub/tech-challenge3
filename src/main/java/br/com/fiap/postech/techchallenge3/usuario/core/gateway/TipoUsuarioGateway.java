package br.com.fiap.postech.techchallenge3.usuario.core.gateway;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;

import java.util.List;
import java.util.Optional;

public interface TipoUsuarioGateway {
    TipoUsuario salvar(TipoUsuario tipoUsuario);
    Optional<TipoUsuario> buscarPorId(Long id);
    List<TipoUsuario> listarTodos();
    void deletar(Long id);
    boolean existePorId(Long id);
}
