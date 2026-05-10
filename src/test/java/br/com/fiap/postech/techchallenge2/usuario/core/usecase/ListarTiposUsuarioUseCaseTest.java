package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.TipoUsuarioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarTiposUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private ListarTiposUsuarioUseCase useCase;

    @Test
    void deveListarTodosTiposUsuario() {
        List<TipoUsuario> tipos = List.of(
                new TipoUsuario(1L, "Dono de Restaurante"),
                new TipoUsuario(2L, "Cliente")
        );
        when(tipoUsuarioGateway.listarTodos()).thenReturn(tipos);

        List<TipoUsuario> resultado = useCase.executar();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(TipoUsuario::getNome)
                .containsExactly("Dono de Restaurante", "Cliente");
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaTipos() {
        when(tipoUsuarioGateway.listarTodos()).thenReturn(List.of());

        List<TipoUsuario> resultado = useCase.executar();

        assertThat(resultado).isEmpty();
    }
}
