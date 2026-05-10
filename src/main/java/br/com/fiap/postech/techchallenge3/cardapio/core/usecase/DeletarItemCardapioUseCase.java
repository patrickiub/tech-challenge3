package br.com.fiap.postech.techchallenge3.cardapio.core.usecase;

import br.com.fiap.postech.techchallenge3.cardapio.core.exception.ItemCardapioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.cardapio.core.gateway.ItemCardapioGateway;
import org.springframework.stereotype.Service;

@Service
public class DeletarItemCardapioUseCase {

    private final ItemCardapioGateway itemCardapioGateway;

    public DeletarItemCardapioUseCase(ItemCardapioGateway itemCardapioGateway) {
        this.itemCardapioGateway = itemCardapioGateway;
    }

    public void executar(Long id) {
        if (!itemCardapioGateway.existePorId(id)) {
            throw new ItemCardapioNaoEncontradoException(id);
        }
        itemCardapioGateway.deletar(id);
    }
}
