package br.com.fiap.postech.techchallenge3.usuario.core.usecase;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.TipoUsuarioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CriarTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private CriarTipoUsuarioUseCase useCase;

    @Test
    void deveCriarTipoUsuarioComSucesso() {
        TipoUsuario entrada = new TipoUsuario(null, "Dono de Restaurante");
        TipoUsuario salvo = new TipoUsuario(1L, "Dono de Restaurante");
        when(tipoUsuarioGateway.salvar(entrada)).thenReturn(salvo);

        TipoUsuario resultado = useCase.executar(entrada);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNome()).isEqualTo("Dono de Restaurante");
        verify(tipoUsuarioGateway).salvar(entrada);
    }
}
