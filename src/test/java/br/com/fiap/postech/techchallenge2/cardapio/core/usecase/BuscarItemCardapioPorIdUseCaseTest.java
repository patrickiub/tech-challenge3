package br.com.fiap.postech.techchallenge3.cardapio.core.usecase;

import br.com.fiap.postech.techchallenge3.cardapio.core.domain.CategoriaItemCardapio;
import br.com.fiap.postech.techchallenge3.cardapio.core.domain.ItemCardapio;
import br.com.fiap.postech.techchallenge3.cardapio.core.exception.ItemCardapioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.cardapio.core.gateway.ItemCardapioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarItemCardapioPorIdUseCaseTest {

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @InjectMocks
    private BuscarItemCardapioPorIdUseCase useCase;

    @Test
    void deveBuscarItemPorIdComSucesso() {
        Long id = 1L;
        ItemCardapio item = new ItemCardapio(id, "Coxinha", "Coxinha de frango", new BigDecimal("8.50"), CategoriaItemCardapio.ENTRADA, 1L, null);
        when(itemCardapioGateway.buscarPorId(id)).thenReturn(Optional.of(item));

        ItemCardapio resultado = useCase.executar(id);

        assertThat(resultado.getId()).isEqualTo(id);
        assertThat(resultado.getNome()).isEqualTo("Coxinha");
        assertThat(resultado.getRestauranteId()).isEqualTo(1L);
    }

    @Test
    void deveLancarExcecaoQuandoItemNaoEncontrado() {
        Long id = 99L;
        when(itemCardapioGateway.buscarPorId(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.executar(id))
                .isInstanceOf(ItemCardapioNaoEncontradoException.class)
                .hasMessageContaining("99");
    }
}
