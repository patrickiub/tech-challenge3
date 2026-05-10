package br.com.fiap.postech.techchallenge3.restaurante.core.usecase;

import br.com.fiap.postech.techchallenge3.restaurante.core.domain.Restaurante;
import br.com.fiap.postech.techchallenge3.restaurante.core.gateway.RestauranteGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CriarRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private CriarRestauranteUseCase useCase;

    @Test
    void deveCriarRestauranteComSucesso() {
        Restaurante entrada = new Restaurante(null, "Restaurante Bom", "Italiana", "Rua A, 1", "11h-22h", 1L);
        Restaurante salvo = new Restaurante(1L, "Restaurante Bom", "Italiana", "Rua A, 1", "11h-22h", 1L);
        when(restauranteGateway.salvar(entrada)).thenReturn(salvo);

        Restaurante resultado = useCase.executar(entrada);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNome()).isEqualTo("Restaurante Bom");
        assertThat(resultado.getTipoCozinha()).isEqualTo("Italiana");
        verify(restauranteGateway).salvar(entrada);
    }
}
