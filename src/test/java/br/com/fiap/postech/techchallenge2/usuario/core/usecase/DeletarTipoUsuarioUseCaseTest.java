package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.TipoUsuarioGateway;
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
class DeletarTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private DeletarTipoUsuarioUseCase useCase;

    @Test
    void deveDeletarTipoUsuarioComSucesso() {
        Long id = 1L;
        when(tipoUsuarioGateway.existePorId(id)).thenReturn(true);

        assertThatCode(() -> useCase.executar(id)).doesNotThrowAnyException();

        verify(tipoUsuarioGateway).deletar(id);
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoEncontrado() {
        Long id = 99L;
        when(tipoUsuarioGateway.existePorId(id)).thenReturn(false);

        assertThatThrownBy(() -> useCase.executar(id))
                .isInstanceOf(TipoUsuarioNaoEncontradoException.class)
                .hasMessageContaining("99");

        verify(tipoUsuarioGateway, never()).deletar(any());
    }
}
