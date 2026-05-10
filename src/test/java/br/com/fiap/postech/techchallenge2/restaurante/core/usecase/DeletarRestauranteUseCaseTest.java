package br.com.fiap.postech.techchallenge3.restaurante.core.usecase;

import br.com.fiap.postech.techchallenge3.restaurante.core.exception.RestauranteNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.restaurante.core.gateway.RestauranteGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private DeletarRestauranteUseCase useCase;

    @Test
    void deveDeletarRestauranteComSucesso() {
        Long id = 1L;
        when(restauranteGateway.existePorId(id)).thenReturn(true);

        assertThatCode(() -> useCase.executar(id)).doesNotThrowAnyException();

        verify(restauranteGateway).deletar(id);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        Long id = 99L;
        when(restauranteGateway.existePorId(id)).thenReturn(false);

        assertThatThrownBy(() -> useCase.executar(id))
                .isInstanceOf(RestauranteNaoEncontradoException.class)
                .hasMessageContaining("99");

        verify(restauranteGateway, never()).deletar(any());
    }
}
