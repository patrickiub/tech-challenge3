package br.com.fiap.postech.techchallenge3.restaurante.core.usecase;

import br.com.fiap.postech.techchallenge3.restaurante.core.domain.Restaurante;
import br.com.fiap.postech.techchallenge3.restaurante.core.exception.RestauranteNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.restaurante.core.gateway.RestauranteGateway;
import org.springframework.stereotype.Service;

@Service
public class BuscarRestaurantePorIdUseCase {

    private final RestauranteGateway restauranteGateway;

    public BuscarRestaurantePorIdUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public Restaurante executar(Long id) {
        return restauranteGateway.buscarPorId(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }
}
