package br.com.fiap.postech.techchallenge3.restaurante.core.usecase;

import br.com.fiap.postech.techchallenge3.restaurante.core.domain.Restaurante;
import br.com.fiap.postech.techchallenge3.restaurante.core.gateway.RestauranteGateway;
import org.springframework.stereotype.Service;

@Service
public class CriarRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;

    public CriarRestauranteUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public Restaurante executar(Restaurante restaurante) {
        return restauranteGateway.salvar(restaurante);
    }
}
