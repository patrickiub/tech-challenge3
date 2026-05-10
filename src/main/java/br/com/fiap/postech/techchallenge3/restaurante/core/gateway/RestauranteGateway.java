package br.com.fiap.postech.techchallenge3.restaurante.core.gateway;

import br.com.fiap.postech.techchallenge3.restaurante.core.domain.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteGateway {
    Restaurante salvar(Restaurante restaurante);
    Optional<Restaurante> buscarPorId(Long id);
    List<Restaurante> listarTodos();
    List<Restaurante> listarPorDonoRestaurante(Long donoRestauranteId);
    void deletar(Long id);
    boolean existePorId(Long id);
}
