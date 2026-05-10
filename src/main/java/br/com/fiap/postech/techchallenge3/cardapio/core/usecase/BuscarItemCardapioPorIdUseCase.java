package br.com.fiap.postech.techchallenge3.cardapio.core.usecase;

import br.com.fiap.postech.techchallenge3.cardapio.core.domain.ItemCardapio;
import br.com.fiap.postech.techchallenge3.cardapio.core.exception.ItemCardapioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.cardapio.core.gateway.ItemCardapioGateway;
import org.springframework.stereotype.Service;

@Service
public class BuscarItemCardapioPorIdUseCase {

    private final ItemCardapioGateway itemCardapioGateway;

    public BuscarItemCardapioPorIdUseCase(ItemCardapioGateway itemCardapioGateway) {
        this.itemCardapioGateway = itemCardapioGateway;
    }

    public ItemCardapio executar(Long id) {
        return itemCardapioGateway.buscarPorId(id)
                .orElseThrow(() -> new ItemCardapioNaoEncontradoException(id));
    }
}
