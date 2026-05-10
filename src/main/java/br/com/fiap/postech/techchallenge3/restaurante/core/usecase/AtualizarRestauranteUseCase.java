package br.com.fiap.postech.techchallenge3.restaurante.core.usecase;

import br.com.fiap.postech.techchallenge3.restaurante.core.domain.Restaurante;
import br.com.fiap.postech.techchallenge3.restaurante.core.exception.RestauranteNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.restaurante.core.gateway.RestauranteGateway;
import org.springframework.stereotype.Service;

@Service
public class AtualizarRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;

    public AtualizarRestauranteUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public Restaurante executar(Long id, Restaurante restaurante) {
        if (!restauranteGateway.existePorId(id)) {
            throw new RestauranteNaoEncontradoException(id);
        }
        restaurante.setId(id);
        return restauranteGateway.salvar(restaurante);
    }
}
