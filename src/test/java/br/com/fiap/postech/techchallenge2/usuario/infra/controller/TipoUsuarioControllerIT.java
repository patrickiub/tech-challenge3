package br.com.fiap.postech.techchallenge3.usuario.infra.controller;

import br.com.fiap.postech.techchallenge3.cardapio.infra.gateway.db.repository.ItemCardapioRepository;
import br.com.fiap.postech.techchallenge3.restaurante.infra.gateway.db.repository.RestauranteRepository;
import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.entity.TipoUsuarioEntity;
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
class TipoUsuarioControllerIT {

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

    @BeforeEach
    void setUp() {
        itemCardapioRepository.deleteAll();
        restauranteRepository.deleteAll();
        usuarioRepository.deleteAll();
        tipoUsuarioRepository.deleteAll();
    }

    @Test
    void deveCriarTipoUsuario() throws Exception {
        var request = Map.of("nome", "Administrador");

        mockMvc.perform(post("/api/v1/tipos-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Administrador"));
    }

    @Test
    void deveBuscarTipoUsuarioPorId() throws Exception {
        var entity = tipoUsuarioRepository.save(new TipoUsuarioEntity(null, "Cliente"));

        mockMvc.perform(get("/api/v1/tipos-usuario/{id}", entity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId()))
                .andExpect(jsonPath("$.nome").value("Cliente"));
    }

    @Test
    void deveListarTodosTiposUsuario() throws Exception {
        tipoUsuarioRepository.save(new TipoUsuarioEntity(null, "Admin"));
        tipoUsuarioRepository.save(new TipoUsuarioEntity(null, "Cliente"));

        mockMvc.perform(get("/api/v1/tipos-usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deveAtualizarTipoUsuario() throws Exception {
        var entity = tipoUsuarioRepository.save(new TipoUsuarioEntity(null, "Antigo"));
        var request = Map.of("nome", "Novo");

        mockMvc.perform(put("/api/v1/tipos-usuario/{id}", entity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId()))
                .andExpect(jsonPath("$.nome").value("Novo"));
    }

    @Test
    void deveDeletarTipoUsuario() throws Exception {
        var entity = tipoUsuarioRepository.save(new TipoUsuarioEntity(null, "ParaDeletar"));

        mockMvc.perform(delete("/api/v1/tipos-usuario/{id}", entity.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/tipos-usuario/{id}", entity.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400QuandoNomeEmBranco() throws Exception {
        var request = Map.of("nome", "");

        mockMvc.perform(post("/api/v1/tipos-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar404QuandoTipoUsuarioNaoEncontrado() throws Exception {
        mockMvc.perform(get("/api/v1/tipos-usuario/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar404AoAtualizarTipoInexistente() throws Exception {
        var request = Map.of("nome", "Qualquer");

        mockMvc.perform(put("/api/v1/tipos-usuario/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveListarVazioQuandoNaoHaTipos() throws Exception {
        mockMvc.perform(get("/api/v1/tipos-usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
