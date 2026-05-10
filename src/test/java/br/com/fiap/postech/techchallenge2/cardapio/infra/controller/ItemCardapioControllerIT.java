package br.com.fiap.postech.techchallenge3.cardapio.infra.controller;

import br.com.fiap.postech.techchallenge3.cardapio.core.domain.CategoriaItemCardapio;
import br.com.fiap.postech.techchallenge3.cardapio.infra.gateway.db.entity.ItemCardapioEntity;
import br.com.fiap.postech.techchallenge3.cardapio.infra.gateway.db.repository.ItemCardapioRepository;
import br.com.fiap.postech.techchallenge3.restaurante.infra.gateway.db.entity.RestauranteEntity;
import br.com.fiap.postech.techchallenge3.restaurante.infra.gateway.db.repository.RestauranteRepository;
import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.entity.TipoUsuarioEntity;
import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.entity.UsuarioEntity;
import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.repository.TipoUsuarioRepository;
import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemCardapioControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    private Long restauranteId;

    @BeforeEach
    void setUp() {
        itemCardapioRepository.deleteAll();
        restauranteRepository.deleteAll();
        usuarioRepository.deleteAll();
        tipoUsuarioRepository.deleteAll();

        var tipo = tipoUsuarioRepository.save(new TipoUsuarioEntity(null, "Proprietario"));
        var usuario = usuarioRepository.save(new UsuarioEntity(null, "Chef", "chef@restaurante.com", "senha123", tipo));
        var restaurante = restauranteRepository.save(
                new RestauranteEntity(null, "Restaurante Principal", "Brasileira", "Av. Central, 1", "11h-22h", usuario)
        );
        restauranteId = restaurante.getId();
    }

    @Test
    void deveCriarItemCardapio() throws Exception {
        var request = Map.of(
                "nome", "Frango Grelhado",
                "descricao", "Frango grelhado com legumes",
                "preco", "29.90",
                "categoria", "PRATO_PRINCIPAL",
                "restauranteId", restauranteId
        );

        mockMvc.perform(post("/api/v1/itens-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Frango Grelhado"))
                .andExpect(jsonPath("$.categoria").value("PRATO_PRINCIPAL"))
                .andExpect(jsonPath("$.restauranteId").value(restauranteId));
    }

    @Test
    void deveBuscarItemCardapioPorId() throws Exception {
        var restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
        var item = itemCardapioRepository.save(new ItemCardapioEntity(
                null, "Suco de Laranja", "Suco natural", new BigDecimal("8.00"), CategoriaItemCardapio.BEBIDA, restaurante, null
        ));

        mockMvc.perform(get("/api/v1/itens-cardapio/{id}", item.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.nome").value("Suco de Laranja"))
                .andExpect(jsonPath("$.categoria").value("BEBIDA"));
    }

    @Test
    void deveListarTodosItensCardapio() throws Exception {
        var restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
        itemCardapioRepository.save(new ItemCardapioEntity(null, "Pizza", "Pizza margherita", new BigDecimal("35.00"), CategoriaItemCardapio.PRATO_PRINCIPAL, restaurante, null));
        itemCardapioRepository.save(new ItemCardapioEntity(null, "Sorvete", "Sorvete de chocolate", new BigDecimal("12.00"), CategoriaItemCardapio.SOBREMESA, restaurante, null));

        mockMvc.perform(get("/api/v1/itens-cardapio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deveListarItensPorRestaurante() throws Exception {
        var restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
        itemCardapioRepository.save(new ItemCardapioEntity(null, "Pão de Alho", "Entrada especial", new BigDecimal("10.00"), CategoriaItemCardapio.ENTRADA, restaurante, null));
        itemCardapioRepository.save(new ItemCardapioEntity(null, "Água Mineral", "500ml", new BigDecimal("5.00"), CategoriaItemCardapio.BEBIDA, restaurante, null));

        mockMvc.perform(get("/api/v1/itens-cardapio/restaurante/{restauranteId}", restauranteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deveAtualizarItemCardapio() throws Exception {
        var restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
        var item = itemCardapioRepository.save(new ItemCardapioEntity(
                null, "Nome Antigo", "Desc antiga", new BigDecimal("20.00"), CategoriaItemCardapio.LANCHE, restaurante, null
        ));

        var request = Map.of(
                "nome", "Nome Novo",
                "descricao", "Desc nova",
                "preco", "25.00",
                "categoria", "LANCHE",
                "restauranteId", restauranteId
        );

        mockMvc.perform(put("/api/v1/itens-cardapio/{id}", item.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nome Novo"))
                .andExpect(jsonPath("$.preco").value(25.00));
    }

    @Test
    void deveDeletarItemCardapio() throws Exception {
        var restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
        var item = itemCardapioRepository.save(new ItemCardapioEntity(
                null, "Item Para Deletar", "Desc", new BigDecimal("15.00"), CategoriaItemCardapio.SOBREMESA, restaurante, null
        ));

        mockMvc.perform(delete("/api/v1/itens-cardapio/{id}", item.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/itens-cardapio/{id}", item.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400QuandoNomeVazio() throws Exception {
        var request = Map.of(
                "nome", "",
                "preco", "10.00",
                "categoria", "BEBIDA",
                "restauranteId", restauranteId
        );

        mockMvc.perform(post("/api/v1/itens-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar400QuandoPrecoNegativo() throws Exception {
        var request = Map.of(
                "nome", "Item Teste",
                "preco", "-5.00",
                "categoria", "BEBIDA",
                "restauranteId", restauranteId
        );

        mockMvc.perform(post("/api/v1/itens-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar404QuandoItemNaoEncontrado() throws Exception {
        mockMvc.perform(get("/api/v1/itens-cardapio/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar404AoAtualizarItemInexistente() throws Exception {
        var request = Map.of(
                "nome", "Qualquer",
                "preco", "10.00",
                "categoria", "BEBIDA",
                "restauranteId", restauranteId
        );

        mockMvc.perform(put("/api/v1/itens-cardapio/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveListarVazioQuandoNaoHaItens() throws Exception {
        mockMvc.perform(get("/api/v1/itens-cardapio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void deveListarVazioQuandoRestauranteSemItens() throws Exception {
        mockMvc.perform(get("/api/v1/itens-cardapio/restaurante/{restauranteId}", restauranteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
