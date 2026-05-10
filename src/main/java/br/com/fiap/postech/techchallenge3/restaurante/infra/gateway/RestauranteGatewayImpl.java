package br.com.fiap.postech.techchallenge3.restaurante.infra.gateway;

import br.com.fiap.postech.techchallenge3.restaurante.core.domain.Restaurante;
import br.com.fiap.postech.techchallenge3.restaurante.core.gateway.RestauranteGateway;
import br.com.fiap.postech.techchallenge3.restaurante.infra.gateway.db.entity.RestauranteEntity;
import br.com.fiap.postech.techchallenge3.restaurante.infra.gateway.db.repository.RestauranteRepository;
import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RestauranteGatewayImpl implements RestauranteGateway {

    private final RestauranteRepository repository;
    private final UsuarioRepository usuarioRepository;

    public RestauranteGatewayImpl(RestauranteRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    private RestauranteEntity toEntity(Restaurante restaurante) {
        var dono = usuarioRepository.getReferenceById(restaurante.getDonoRestauranteId());
        return new RestauranteEntity(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getTipoCozinha(),
                restaurante.getEndereco(),
                restaurante.getHorarioFuncionamento(),
                dono
        );
    }

    private Restaurante toDomain(RestauranteEntity entity) {
        return new Restaurante(
                entity.getId(),
                entity.getNome(),
                entity.getTipoCozinha(),
                entity.getEndereco(),
                entity.getHorarioFuncionamento(),
                entity.getDonoRestaurante().getId()
        );
    }

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        return toDomain(repository.save(toEntity(restaurante)));
    }

    @Override
    public Optional<Restaurante> buscarPorId(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Restaurante> listarTodos() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Restaurante> listarPorDonoRestaurante(Long donoRestauranteId) {
        return repository.findByDonoRestauranteId(donoRestauranteId).stream()
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
