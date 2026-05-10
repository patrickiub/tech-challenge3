package br.com.fiap.postech.techchallenge3.restaurante.core.usecase;

import br.com.fiap.postech.techchallenge3.restaurante.core.exception.RestauranteNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.restaurante.core.gateway.RestauranteGateway;
import org.springframework.stereotype.Service;

@Service
public class DeletarRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;

    public DeletarRestauranteUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public void executar(Long id) {
        if (!restauranteGateway.existePorId(id)) {
            throw new RestauranteNaoEncontradoException(id);
        }
        restauranteGateway.deletar(id);
    }
}
