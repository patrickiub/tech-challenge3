package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.exception.UsuarioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.UsuarioGateway;
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
class DeletarUsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private DeletarUsuarioUseCase useCase;

    @Test
    void deveDeletarUsuarioComSucesso() {
        Long id = 1L;
        when(usuarioGateway.existePorId(id)).thenReturn(true);

        assertThatCode(() -> useCase.executar(id)).doesNotThrowAnyException();

        verify(usuarioGateway).deletar(id);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        Long id = 99L;
        when(usuarioGateway.existePorId(id)).thenReturn(false);

        assertThatThrownBy(() -> useCase.executar(id))
                .isInstanceOf(UsuarioNaoEncontradoException.class)
                .hasMessageContaining("99");

        verify(usuarioGateway, never()).deletar(any());
    }
}
