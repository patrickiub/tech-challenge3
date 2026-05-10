package br.com.fiap.postech.techchallenge3.restaurante.core.usecase;

import br.com.fiap.postech.techchallenge3.restaurante.core.domain.Restaurante;
import br.com.fiap.postech.techchallenge3.restaurante.core.gateway.RestauranteGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarRestaurantesUseCase {

    private final RestauranteGateway restauranteGateway;

    public ListarRestaurantesUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public List<Restaurante> executar() {
        return restauranteGateway.listarTodos();
    }
}
