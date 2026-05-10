package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.Role;
import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;
import br.com.fiap.postech.techchallenge3.usuario.core.domain.Usuario;
import br.com.fiap.postech.techchallenge3.usuario.core.exception.TipoUsuarioNaoEncontradoException;
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
class CriarUsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private CriarUsuarioUseCase useCase;

    @Test
    void deveCriarUsuarioComSucesso() {
        TipoUsuario tipo = new TipoUsuario(1L, "Cliente");
        Usuario entrada = new Usuario(null, "Maria", "maria@email.com", "senha123", tipo, Role.ROLE_CLIENTE);
        Usuario salvo = new Usuario(1L, "Maria", "maria@email.com", "senha123", tipo, Role.ROLE_CLIENTE);
        when(tipoUsuarioGateway.buscarPorId(1L)).thenReturn(Optional.of(tipo));
        when(usuarioGateway.salvar(entrada)).thenReturn(salvo);

        Usuario resultado = useCase.executar(entrada);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNome()).isEqualTo("Maria");
        verify(tipoUsuarioGateway).buscarPorId(1L);
        verify(usuarioGateway).salvar(entrada);
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoEncontrado() {
        TipoUsuario tipo = new TipoUsuario(99L, null);
        Usuario usuario = new Usuario(null, "Maria", "maria@email.com", "senha123", tipo, Role.ROLE_CLIENTE);
        when(tipoUsuarioGateway.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.executar(usuario))
                .isInstanceOf(TipoUsuarioNaoEncontradoException.class)
                .hasMessageContaining("99");

        verify(usuarioGateway, never()).salvar(any());
    }
}
