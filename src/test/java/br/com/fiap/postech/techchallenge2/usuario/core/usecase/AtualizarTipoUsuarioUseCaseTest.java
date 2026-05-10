package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;
import br.com.fiap.postech.techchallenge3.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.TipoUsuarioGateway;
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
class AtualizarTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private AtualizarTipoUsuarioUseCase useCase;

    @Test
    void deveAtualizarTipoUsuarioComSucesso() {
        Long id = 1L;
        TipoUsuario entrada = new TipoUsuario(null, "Cliente");
        TipoUsuario atualizado = new TipoUsuario(id, "Cliente");
        when(tipoUsuarioGateway.existePorId(id)).thenReturn(true);
        when(tipoUsuarioGateway.salvar(entrada)).thenReturn(atualizado);

        TipoUsuario resultado = useCase.executar(id, entrada);

        assertThat(resultado.getId()).isEqualTo(id);
        assertThat(resultado.getNome()).isEqualTo("Cliente");
        assertThat(entrada.getId()).isEqualTo(id);
        verify(tipoUsuarioGateway).existePorId(id);
        verify(tipoUsuarioGateway).salvar(entrada);
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoEncontrado() {
        Long id = 99L;
        when(tipoUsuarioGateway.existePorId(id)).thenReturn(false);

        assertThatThrownBy(() -> useCase.executar(id, new TipoUsuario()))
                .isInstanceOf(TipoUsuarioNaoEncontradoException.class)
                .hasMessageContaining("99");

        verify(tipoUsuarioGateway, never()).salvar(any());
    }
}
