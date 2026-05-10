package br.com.fiap.postech.techchallenge3.restaurante.core.usecase;

import br.com.fiap.postech.techchallenge3.restaurante.core.domain.Restaurante;
import br.com.fiap.postech.techchallenge3.restaurante.core.exception.RestauranteNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.restaurante.core.gateway.RestauranteGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarRestaurantePorIdUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private BuscarRestaurantePorIdUseCase useCase;

    @Test
    void deveBuscarRestaurantePorIdComSucesso() {
        Long id = 1L;
        Restaurante restaurante = new Restaurante(id, "Restaurante Bom", "Italiana", "Rua A, 1", "11h-22h", 1L);
        when(restauranteGateway.buscarPorId(id)).thenReturn(Optional.of(restaurante));

        Restaurante resultado = useCase.executar(id);

        assertThat(resultado.getId()).isEqualTo(id);
        assertThat(resultado.getNome()).isEqualTo("Restaurante Bom");
        assertThat(resultado.getDonoRestauranteId()).isEqualTo(1L);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        Long id = 99L;
        when(restauranteGateway.buscarPorId(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.executar(id))
                .isInstanceOf(RestauranteNaoEncontradoException.class)
                .hasMessageContaining("99");
    }
}
