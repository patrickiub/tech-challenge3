package br.com.fiap.postech.techchallenge3.restaurante.core.usecase;

import br.com.fiap.postech.techchallenge3.restaurante.core.domain.Restaurante;
import br.com.fiap.postech.techchallenge3.restaurante.core.exception.RestauranteNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.restaurante.core.gateway.RestauranteGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private AtualizarRestauranteUseCase useCase;

    @Test
    void deveAtualizarRestauranteComSucesso() {
        Long id = 1L;
        Restaurante entrada = new Restaurante(null, "Restaurante Novo", "Japonesa", "Rua B, 2", "12h-23h", 1L);
        Restaurante atualizado = new Restaurante(id, "Restaurante Novo", "Japonesa", "Rua B, 2", "12h-23h", 1L);
        when(restauranteGateway.existePorId(id)).thenReturn(true);
        when(restauranteGateway.salvar(entrada)).thenReturn(atualizado);

        Restaurante resultado = useCase.executar(id, entrada);

        assertThat(resultado.getId()).isEqualTo(id);
        assertThat(resultado.getNome()).isEqualTo("Restaurante Novo");
        assertThat(entrada.getId()).isEqualTo(id);
        verify(restauranteGateway).existePorId(id);
        verify(restauranteGateway).salvar(entrada);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        Long id = 99L;
        when(restauranteGateway.existePorId(id)).thenReturn(false);

        assertThatThrownBy(() -> useCase.executar(id, new Restaurante()))
                .isInstanceOf(RestauranteNaoEncontradoException.class)
                .hasMessageContaining("99");

        verify(restauranteGateway, never()).salvar(any());
    }
}
