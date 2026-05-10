package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;
import br.com.fiap.postech.techchallenge3.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.TipoUsuarioGateway;
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
class BuscarTipoUsuarioPorIdUseCaseTest {

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private BuscarTipoUsuarioPorIdUseCase useCase;

    @Test
    void deveBuscarTipoUsuarioPorIdComSucesso() {
        Long id = 1L;
        TipoUsuario tipoUsuario = new TipoUsuario(id, "Dono de Restaurante");
        when(tipoUsuarioGateway.buscarPorId(id)).thenReturn(Optional.of(tipoUsuario));

        TipoUsuario resultado = useCase.executar(id);

        assertThat(resultado.getId()).isEqualTo(id);
        assertThat(resultado.getNome()).isEqualTo("Dono de Restaurante");
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoEncontrado() {
        Long id = 99L;
        when(tipoUsuarioGateway.buscarPorId(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.executar(id))
                .isInstanceOf(TipoUsuarioNaoEncontradoException.class)
                .hasMessageContaining("99");
    }
}
