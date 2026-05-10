package br.com.fiap.postech.techchallenge3.cardapio.core.usecase;

import br.com.fiap.postech.techchallenge3.cardapio.core.domain.CategoriaItemCardapio;
import br.com.fiap.postech.techchallenge3.cardapio.core.domain.ItemCardapio;
import br.com.fiap.postech.techchallenge3.cardapio.core.gateway.ItemCardapioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarItensCardapioPorRestauranteUseCaseTest {

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @InjectMocks
    private ListarItensCardapioPorRestauranteUseCase useCase;

    @Test
    void deveListarItensPorRestaurante() {
        Long restauranteId = 1L;
        List<ItemCardapio> itens = List.of(
                new ItemCardapio(1L, "Coxinha", "Frango", new BigDecimal("8.50"), CategoriaItemCardapio.ENTRADA, restauranteId, null),
                new ItemCardapio(2L, "Feijoada", "Completa", new BigDecimal("45.90"), CategoriaItemCardapio.PRATO_PRINCIPAL, restauranteId, null)
        );
        when(itemCardapioGateway.listarPorRestaurante(restauranteId)).thenReturn(itens);

        List<ItemCardapio> resultado = useCase.executar(restauranteId);

        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(item -> item.getRestauranteId().equals(restauranteId));
        assertThat(resultado).extracting(ItemCardapio::getNome).containsExactly("Coxinha", "Feijoada");
    }

    @Test
    void deveRetornarListaVaziaQuandoRestauranteSemItens() {
        Long restauranteId = 99L;
        when(itemCardapioGateway.listarPorRestaurante(restauranteId)).thenReturn(List.of());

        List<ItemCardapio> resultado = useCase.executar(restauranteId);

        assertThat(resultado).isEmpty();
    }
}
