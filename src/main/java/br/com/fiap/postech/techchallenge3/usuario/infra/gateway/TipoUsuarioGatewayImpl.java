package br.com.fiap.postech.techchallenge3.usuario.infra.gateway;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.TipoUsuarioGateway;
import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.entity.TipoUsuarioEntity;
import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.repository.TipoUsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TipoUsuarioGatewayImpl implements TipoUsuarioGateway {

    private final TipoUsuarioRepository repository;

    public TipoUsuarioGatewayImpl(TipoUsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public TipoUsuario salvar(TipoUsuario tipoUsuario) {
        TipoUsuarioEntity entity = new TipoUsuarioEntity(tipoUsuario.getId(), tipoUsuario.getNome());
        TipoUsuarioEntity saved = repository.save(entity);
        return new TipoUsuario(saved.getId(), saved.getNome());
    }

    @Override
    public Optional<TipoUsuario> buscarPorId(Long id) {
        return repository.findById(id)
                .map(e -> new TipoUsuario(e.getId(), e.getNome()));
    }

    @Override
    public List<TipoUsuario> listarTodos() {
        return repository.findAll().stream()
                .map(e -> new TipoUsuario(e.getId(), e.getNome()))
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
