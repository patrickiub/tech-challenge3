package br.com.fiap.postech.techchallenge3.cardapio.infra.gateway;

import br.com.fiap.postech.techchallenge3.cardapio.core.domain.ItemCardapio;
import br.com.fiap.postech.techchallenge3.cardapio.core.gateway.ItemCardapioGateway;
import br.com.fiap.postech.techchallenge3.cardapio.infra.gateway.db.entity.ItemCardapioEntity;
import br.com.fiap.postech.techchallenge3.cardapio.infra.gateway.db.repository.ItemCardapioRepository;
import br.com.fiap.postech.techchallenge3.restaurante.infra.gateway.db.repository.RestauranteRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ItemCardapioGatewayImpl implements ItemCardapioGateway {

    private final ItemCardapioRepository repository;
    private final RestauranteRepository restauranteRepository;

    public ItemCardapioGatewayImpl(ItemCardapioRepository repository, RestauranteRepository restauranteRepository) {
        this.repository = repository;
        this.restauranteRepository = restauranteRepository;
    }

    private ItemCardapioEntity toEntity(ItemCardapio item) {
        var restaurante = restauranteRepository.getReferenceById(item.getRestauranteId());
        return new ItemCardapioEntity(
                item.getId(),
                item.getNome(),
                item.getDescricao(),
                item.getPreco(),
                item.getCategoria(),
                restaurante,
                item.getFotoPath()
        );
    }

    private ItemCardapio toDomain(ItemCardapioEntity entity) {
        return new ItemCardapio(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getPreco(),
                entity.getCategoria(),
                entity.getRestaurante().getId(),
                entity.getFotoPath()
        );
    }

    @Override
    public ItemCardapio salvar(ItemCardapio itemCardapio) {
        return toDomain(repository.save(toEntity(itemCardapio)));
    }

    @Override
    public Optional<ItemCardapio> buscarPorId(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<ItemCardapio> listarTodos() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemCardapio> listarPorRestaurante(Long restauranteId) {
        return repository.findByRestauranteId(restauranteId).stream()
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
