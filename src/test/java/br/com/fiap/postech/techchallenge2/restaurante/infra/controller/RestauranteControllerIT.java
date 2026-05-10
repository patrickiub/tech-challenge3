package br.com.fiap.postech.techchallenge3.restaurante.infra.controller;

import br.com.fiap.postech.techchallenge3.cardapio.infra.gateway.db.repository.ItemCardapioRepository;
import br.com.fiap.postech.techchallenge3.restaurante.infra.gateway.db.entity.RestauranteEntity;
import br.com.fiap.postech.techchallenge3.restaurante.infra.gateway.db.repository.RestauranteRepository;
import br.com.fiap.postech.techchallenge3.usuario.core.domain.Role;
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

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RestauranteControllerIT {

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

    private Long usuarioId;

    @BeforeEach
    void setUp() {
        itemCardapioRepository.deleteAll();
        restauranteRepository.deleteAll();
        usuarioRepository.deleteAll();
        tipoUsuarioRepository.deleteAll();

        var tipo = tipoUsuarioRepository.save(new TipoUsuarioEntity(null, "Dono"));
        var usuario = usuarioRepository.save(new UsuarioEntity(null, "Dono do Restaurante", "dono@restaurante.com", "senha123", tipo, Role.ROLE_CLIENTE));
        usuarioId = usuario.getId();
    }

    @Test
    void deveCriarRestaurante() throws Exception {
        var request = Map.of(
                "nome", "Pizzaria Bella",
                "tipoCozinha", "Italiana",
                "endereco", "Rua das Pizzas, 100",
                "horarioFuncionamento", "18h-23h",
                "donoRestauranteId", usuarioId
        );

        mockMvc.perform(post("/api/v1/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Pizzaria Bella"))
                .andExpect(jsonPath("$.tipoCozinha").value("Italiana"))
                .andExpect(jsonPath("$.donoRestauranteId").value(usuarioId));
    }

    @Test
    void deveBuscarRestaurantePorId() throws Exception {
        var usuario = usuarioRepository.findById(usuarioId).orElseThrow();
        var restaurante = restauranteRepository.save(
                new RestauranteEntity(null, "Sushi Bar", "Japonesa", "Av. Japão, 50", "12h-22h", usuario)
        );

        mockMvc.perform(get("/api/v1/restaurantes/{id}", restaurante.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(restaurante.getId()))
                .andExpect(jsonPath("$.nome").value("Sushi Bar"))
                .andExpect(jsonPath("$.tipoCozinha").value("Japonesa"));
    }

    @Test
    void deveListarTodosRestaurantes() throws Exception {
        var usuario = usuarioRepository.findById(usuarioId).orElseThrow();
        restauranteRepository.save(new RestauranteEntity(null, "Restaurante 1", "Brasileira", "Rua A, 1", "11h-22h", usuario));
        restauranteRepository.save(new RestauranteEntity(null, "Restaurante 2", "Italiana", "Rua B, 2", "18h-23h", usuario));

        mockMvc.perform(get("/api/v1/restaurantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deveAtualizarRestaurante() throws Exception {
        var usuario = usuarioRepository.findById(usuarioId).orElseThrow();
        var restaurante = restauranteRepository.save(
                new RestauranteEntity(null, "Nome Antigo", "Francesa", "Rua C, 3", "10h-21h", usuario)
        );

        var request = Map.of(
                "nome", "Nome Novo",
                "tipoCozinha", "Francesa Atualizada",
                "endereco", "Rua D, 4",
                "horarioFuncionamento", "11h-22h",
                "donoRestauranteId", usuarioId
        );

        mockMvc.perform(put("/api/v1/restaurantes/{id}", restaurante.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nome Novo"))
                .andExpect(jsonPath("$.tipoCozinha").value("Francesa Atualizada"));
    }

    @Test
    void deveDeletarRestaurante() throws Exception {
        var usuario = usuarioRepository.findById(usuarioId).orElseThrow();
        var restaurante = restauranteRepository.save(
                new RestauranteEntity(null, "Para Deletar", "Variada", "Rua E, 5", "09h-18h", usuario)
        );

        mockMvc.perform(delete("/api/v1/restaurantes/{id}", restaurante.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/restaurantes/{id}", restaurante.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400QuandoNomeVazio() throws Exception {
        var request = Map.of(
                "nome", "",
                "tipoCozinha", "Italiana",
                "endereco", "Rua Teste, 1",
                "donoRestauranteId", usuarioId
        );

        mockMvc.perform(post("/api/v1/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar404QuandoRestauranteNaoEncontrado() throws Exception {
        mockMvc.perform(get("/api/v1/restaurantes/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar404AoAtualizarRestauranteInexistente() throws Exception {
        var request = Map.of(
                "nome", "Qualquer",
                "tipoCozinha", "Variada",
                "endereco", "Rua X, 0",
                "donoRestauranteId", usuarioId
        );

        mockMvc.perform(put("/api/v1/restaurantes/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveListarVazioQuandoNaoHaRestaurantes() throws Exception {
        mockMvc.perform(get("/api/v1/restaurantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
