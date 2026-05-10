package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;
import br.com.fiap.postech.techchallenge3.usuario.core.domain.Usuario;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.UsuarioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarUsuariosUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private ListarUsuariosUseCase useCase;

    @Test
    void deveListarTodosUsuarios() {
        TipoUsuario tipo = new TipoUsuario(1L, "Cliente");
        List<Usuario> usuarios = List.of(
                new Usuario(1L, "Maria", "maria@email.com", "senha123", tipo),
                new Usuario(2L, "João", "joao@email.com", "senha456", tipo)
        );
        when(usuarioGateway.listarTodos()).thenReturn(usuarios);

        List<Usuario> resultado = useCase.executar();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(Usuario::getNome).containsExactly("Maria", "João");
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaUsuarios() {
        when(usuarioGateway.listarTodos()).thenReturn(List.of());

        List<Usuario> resultado = useCase.executar();

        assertThat(resultado).isEmpty();
    }
}
