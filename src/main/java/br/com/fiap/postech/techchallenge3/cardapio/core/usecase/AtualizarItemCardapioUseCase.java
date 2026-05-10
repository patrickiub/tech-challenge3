package br.com.fiap.postech.techchallenge3.cardapio.core.usecase;

import br.com.fiap.postech.techchallenge3.cardapio.core.domain.ItemCardapio;
import br.com.fiap.postech.techchallenge3.cardapio.core.exception.ItemCardapioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.cardapio.core.gateway.ItemCardapioGateway;
import org.springframework.stereotype.Service;

@Service
public class AtualizarItemCardapioUseCase {

    private final ItemCardapioGateway itemCardapioGateway;

    public AtualizarItemCardapioUseCase(ItemCardapioGateway itemCardapioGateway) {
        this.itemCardapioGateway = itemCardapioGateway;
    }

    public ItemCardapio executar(Long id, ItemCardapio itemCardapio) {
        if (!itemCardapioGateway.existePorId(id)) {
            throw new ItemCardapioNaoEncontradoException(id);
        }
        itemCardapio.setId(id);
        return itemCardapioGateway.salvar(itemCardapio);
    }
}
