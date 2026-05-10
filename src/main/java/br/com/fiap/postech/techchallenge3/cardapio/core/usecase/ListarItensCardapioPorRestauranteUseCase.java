package br.com.fiap.postech.techchallenge3.cardapio.core.usecase;

import br.com.fiap.postech.techchallenge3.cardapio.core.domain.ItemCardapio;
import br.com.fiap.postech.techchallenge3.cardapio.core.gateway.ItemCardapioGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarItensCardapioPorRestauranteUseCase {

    private final ItemCardapioGateway itemCardapioGateway;

    public ListarItensCardapioPorRestauranteUseCase(ItemCardapioGateway itemCardapioGateway) {
        this.itemCardapioGateway = itemCardapioGateway;
    }

    public List<ItemCardapio> executar(Long restauranteId) {
        return itemCardapioGateway.listarPorRestaurante(restauranteId);
    }
}
