package br.com.fiap.postech.techchallenge3.restaurante.core.usecase;

import br.com.fiap.postech.techchallenge3.restaurante.core.domain.Restaurante;
import br.com.fiap.postech.techchallenge3.restaurante.core.gateway.RestauranteGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarRestaurantesUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private ListarRestaurantesUseCase useCase;

    @Test
    void deveListarTodosRestaurantes() {
        List<Restaurante> restaurantes = List.of(
                new Restaurante(1L, "Restaurante A", "Italiana", "Rua A", "11h-22h", 1L),
                new Restaurante(2L, "Restaurante B", "Japonesa", "Rua B", "12h-23h", 2L)
        );
        when(restauranteGateway.listarTodos()).thenReturn(restaurantes);

        List<Restaurante> resultado = useCase.executar();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(Restaurante::getNome)
                .containsExactly("Restaurante A", "Restaurante B");
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaRestaurantes() {
        when(restauranteGateway.listarTodos()).thenReturn(List.of());

        List<Restaurante> resultado = useCase.executar();

        assertThat(resultado).isEmpty();
    }
}
