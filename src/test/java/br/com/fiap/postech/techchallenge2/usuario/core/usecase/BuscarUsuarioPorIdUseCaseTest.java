package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;
import br.com.fiap.postech.techchallenge3.usuario.core.domain.Usuario;
import br.com.fiap.postech.techchallenge3.usuario.core.exception.UsuarioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.UsuarioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarUsuarioPorIdUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private BuscarUsuarioPorIdUseCase useCase;

    @Test
    void deveBuscarUsuarioPorIdComSucesso() {
        Long id = 1L;
        TipoUsuario tipo = new TipoUsuario(1L, "Cliente");
        Usuario usuario = new Usuario(id, "Maria", "maria@email.com", "senha123", tipo);
        when(usuarioGateway.buscarPorId(id)).thenReturn(Optional.of(usuario));

        Usuario resultado = useCase.executar(id);

        assertThat(resultado.getId()).isEqualTo(id);
        assertThat(resultado.getNome()).isEqualTo("Maria");
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        Long id = 99L;
        when(usuarioGateway.buscarPorId(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.executar(id))
                .isInstanceOf(UsuarioNaoEncontradoException.class)
                .hasMessageContaining("99");
    }
}
