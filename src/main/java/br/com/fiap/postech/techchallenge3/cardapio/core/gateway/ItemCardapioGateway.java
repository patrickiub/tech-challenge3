package br.com.fiap.postech.techchallenge3.cardapio.core.gateway;

import br.com.fiap.postech.techchallenge3.cardapio.core.domain.ItemCardapio;

import java.util.List;
import java.util.Optional;

public interface ItemCardapioGateway {
    ItemCardapio salvar(ItemCardapio itemCardapio);
    Optional<ItemCardapio> buscarPorId(Long id);
    List<ItemCardapio> listarTodos();
    List<ItemCardapio> listarPorRestaurante(Long restauranteId);
    void deletar(Long id);
    boolean existePorId(Long id);
}
