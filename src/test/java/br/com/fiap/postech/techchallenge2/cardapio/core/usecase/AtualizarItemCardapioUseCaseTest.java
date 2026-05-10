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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @InjectMocks
    private AtualizarItemCardapioUseCase useCase;

    @Test
    void deveAtualizarItemCardapioComSucesso() {
        Long id = 1L;
        ItemCardapio entrada = new ItemCardapio(null, "Coxinha Especial", "Coxinha premium", new BigDecimal("10.00"), CategoriaItemCardapio.ENTRADA, 1L, null);
        ItemCardapio atualizado = new ItemCardapio(id, "Coxinha Especial", "Coxinha premium", new BigDecimal("10.00"), CategoriaItemCardapio.ENTRADA, 1L, null);
        when(itemCardapioGateway.existePorId(id)).thenReturn(true);
        when(itemCardapioGateway.salvar(entrada)).thenReturn(atualizado);

        ItemCardapio resultado = useCase.executar(id, entrada);

        assertThat(resultado.getId()).isEqualTo(id);
        assertThat(resultado.getNome()).isEqualTo("Coxinha Especial");
        assertThat(entrada.getId()).isEqualTo(id);
        verify(itemCardapioGateway).existePorId(id);
        verify(itemCardapioGateway).salvar(entrada);
    }

    @Test
    void deveLancarExcecaoQuandoItemNaoEncontrado() {
        Long id = 99L;
        when(itemCardapioGateway.existePorId(id)).thenReturn(false);

        assertThatThrownBy(() -> useCase.executar(id, new ItemCardapio()))
                .isInstanceOf(ItemCardapioNaoEncontradoException.class)
                .hasMessageContaining("99");

        verify(itemCardapioGateway, never()).salvar(any());
    }
}
