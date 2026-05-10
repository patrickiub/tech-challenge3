package br.com.fiap.postech.techchallenge3.usuario.core.gateway;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioGateway {
    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorEmail(String email);
    List<Usuario> listarTodos();
    void deletar(Long id);
    boolean existePorId(Long id);
}
