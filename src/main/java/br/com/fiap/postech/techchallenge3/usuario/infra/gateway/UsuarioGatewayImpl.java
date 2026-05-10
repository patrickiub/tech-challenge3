package br.com.fiap.postech.techchallenge3.usuario.infra.gateway;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;
import br.com.fiap.postech.techchallenge3.usuario.core.domain.Usuario;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.UsuarioGateway;
import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.entity.TipoUsuarioEntity;
import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.entity.UsuarioEntity;
import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.repository.TipoUsuarioRepository;
import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UsuarioGatewayImpl implements UsuarioGateway {

    private final UsuarioRepository repository;
    private final TipoUsuarioRepository tipoUsuarioRepository;

    public UsuarioGatewayImpl(UsuarioRepository repository, TipoUsuarioRepository tipoUsuarioRepository) {
        this.repository = repository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    private UsuarioEntity toEntity(Usuario usuario) {
        TipoUsuarioEntity tipoEntity = tipoUsuarioRepository.getReferenceById(usuario.getTipoUsuario().getId());
        return new UsuarioEntity(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha(), tipoEntity);
    }

    private Usuario toDomain(UsuarioEntity entity) {
        TipoUsuario tipo = new TipoUsuario(entity.getTipoUsuario().getId(), entity.getTipoUsuario().getNome());
        return new Usuario(entity.getId(), entity.getNome(), entity.getEmail(), entity.getSenha(), tipo);
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return toDomain(repository.save(toEntity(usuario)));
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Usuario> listarTodos() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existePorId(Long id) {
        return repository.existsById(id);
    }
}
