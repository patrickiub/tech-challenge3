package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;
import br.com.fiap.postech.techchallenge3.usuario.core.domain.Usuario;
import br.com.fiap.postech.techchallenge3.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.exception.UsuarioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.TipoUsuarioGateway;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.UsuarioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarUsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private AtualizarUsuarioUseCase useCase;

    @Test
    void deveAtualizarUsuarioComSucesso() {
        Long id = 1L;
        TipoUsuario tipo = new TipoUsuario(1L, "Cliente");
        Usuario entrada = new Usuario(null, "Maria Atualizada", "maria@email.com", "senha123", tipo);
        Usuario atualizado = new Usuario(id, "Maria Atualizada", "maria@email.com", "senha123", tipo);
        when(usuarioGateway.existePorId(id)).thenReturn(true);
        when(tipoUsuarioGateway.buscarPorId(1L)).thenReturn(Optional.of(tipo));
        when(usuarioGateway.salvar(entrada)).thenReturn(atualizado);

        Usuario resultado = useCase.executar(id, entrada);

        assertThat(resultado.getId()).isEqualTo(id);
        assertThat(resultado.getNome()).isEqualTo("Maria Atualizada");
        assertThat(entrada.getId()).isEqualTo(id);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        Long id = 99L;
        TipoUsuario tipo = new TipoUsuario(1L, "Cliente");
        Usuario usuario = new Usuario(null, "Maria", "maria@email.com", "senha123", tipo);
        when(usuarioGateway.existePorId(id)).thenReturn(false);

        assertThatThrownBy(() -> useCase.executar(id, usuario))
                .isInstanceOf(UsuarioNaoEncontradoException.class)
                .hasMessageContaining("99");

        verify(usuarioGateway, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoEncontrado() {
        Long id = 1L;
        TipoUsuario tipo = new TipoUsuario(99L, null);
        Usuario usuario = new Usuario(null, "Maria", "maria@email.com", "senha123", tipo);
        when(usuarioGateway.existePorId(id)).thenReturn(true);
        when(tipoUsuarioGateway.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.executar(id, usuario))
                .isInstanceOf(TipoUsuarioNaoEncontradoException.class)
                .hasMessageContaining("99");

        verify(usuarioGateway, never()).salvar(any());
    }
}
