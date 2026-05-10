package br.com.fiap.postech.techchallenge3.cardapio.core.usecase;

import br.com.fiap.postech.techchallenge3.cardapio.core.domain.ItemCardapio;
import br.com.fiap.postech.techchallenge3.cardapio.core.gateway.ItemCardapioGateway;
import org.springframework.stereotype.Service;

@Service
public class CriarItemCardapioUseCase {

    private final ItemCardapioGateway itemCardapioGateway;

    public CriarItemCardapioUseCase(ItemCardapioGateway itemCardapioGateway) {
        this.itemCardapioGateway = itemCardapioGateway;
    }

    public ItemCardapio executar(ItemCardapio itemCardapio) {
        return itemCardapioGateway.salvar(itemCardapio);
    }
}
