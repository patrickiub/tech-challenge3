package br.com.fiap.postech.techchallenge3.cardapio.core.usecase;

import br.com.fiap.postech.techchallenge3.cardapio.core.exception.ItemCardapioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.cardapio.core.gateway.ItemCardapioGateway;
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
class DeletarItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @InjectMocks
    private DeletarItemCardapioUseCase useCase;

    @Test
    void deveDeletarItemComSucesso() {
        Long id = 1L;
        when(itemCardapioGateway.existePorId(id)).thenReturn(true);

        assertThatCode(() -> useCase.executar(id)).doesNotThrowAnyException();

        verify(itemCardapioGateway).deletar(id);
    }

    @Test
    void deveLancarExcecaoQuandoItemNaoEncontrado() {
        Long id = 99L;
        when(itemCardapioGateway.existePorId(id)).thenReturn(false);

        assertThatThrownBy(() -> useCase.executar(id))
                .isInstanceOf(ItemCardapioNaoEncontradoException.class)
                .hasMessageContaining("99");

        verify(itemCardapioGateway, never()).deletar(any());
    }
}
