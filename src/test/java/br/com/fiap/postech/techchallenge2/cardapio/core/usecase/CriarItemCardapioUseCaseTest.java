package br.com.fiap.postech.techchallenge3.cardapio.core.usecase;

import br.com.fiap.postech.techchallenge3.cardapio.core.domain.CategoriaItemCardapio;
import br.com.fiap.postech.techchallenge3.cardapio.core.domain.ItemCardapio;
import br.com.fiap.postech.techchallenge3.cardapio.core.gateway.ItemCardapioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CriarItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @InjectMocks
    private CriarItemCardapioUseCase useCase;

    @Test
    void deveCriarItemCardapioComSucesso() {
        ItemCardapio entrada = new ItemCardapio(null, "Coxinha", "Coxinha de frango", new BigDecimal("8.50"), CategoriaItemCardapio.ENTRADA, 1L, null);
        ItemCardapio salvo = new ItemCardapio(1L, "Coxinha", "Coxinha de frango", new BigDecimal("8.50"), CategoriaItemCardapio.ENTRADA, 1L, null);
        when(itemCardapioGateway.salvar(entrada)).thenReturn(salvo);

        ItemCardapio resultado = useCase.executar(entrada);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNome()).isEqualTo("Coxinha");
        assertThat(resultado.getCategoria()).isEqualTo(CategoriaItemCardapio.ENTRADA);
        assertThat(resultado.getPreco()).isEqualByComparingTo("8.50");
        verify(itemCardapioGateway).salvar(entrada);
    }
}
