package br.com.fiap.postech.techchallenge3.usuario.infra.controller;

import br.com.fiap.postech.techchallenge3.cardapio.infra.gateway.db.repository.ItemCardapioRepository;
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

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerIT {

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

    private Long tipoUsuarioId;

    @BeforeEach
    void setUp() {
        itemCardapioRepository.deleteAll();
        restauranteRepository.deleteAll();
        usuarioRepository.deleteAll();
        tipoUsuarioRepository.deleteAll();

        var tipo = tipoUsuarioRepository.save(new TipoUsuarioEntity(null, "Cliente"));
        tipoUsuarioId = tipo.getId();
    }

    @Test
    void deveCriarUsuario() throws Exception {
        var request = Map.of(
                "nome", "João Silva",
                "email", "joao@teste.com",
                "senha", "senha123",
                "tipoUsuarioId", tipoUsuarioId
        );

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@teste.com"))
                .andExpect(jsonPath("$.tipoUsuario.id").value(tipoUsuarioId));
    }

    @Test
    void deveBuscarUsuarioPorId() throws Exception {
        var tipo = tipoUsuarioRepository.findById(tipoUsuarioId).orElseThrow();
        var usuario = usuarioRepository.save(new UsuarioEntity(null, "Maria", "maria@teste.com", "senha123", tipo));

        mockMvc.perform(get("/api/v1/usuarios/{id}", usuario.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuario.getId()))
                .andExpect(jsonPath("$.nome").value("Maria"))
                .andExpect(jsonPath("$.email").value("maria@teste.com"));
    }

    @Test
    void deveListarTodosUsuarios() throws Exception {
        var tipo = tipoUsuarioRepository.findById(tipoUsuarioId).orElseThrow();
        usuarioRepository.save(new UsuarioEntity(null, "User1", "user1@teste.com", "senha123", tipo));
        usuarioRepository.save(new UsuarioEntity(null, "User2", "user2@teste.com", "senha456", tipo));

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deveAtualizarUsuario() throws Exception {
        var tipo = tipoUsuarioRepository.findById(tipoUsuarioId).orElseThrow();
        var usuario = usuarioRepository.save(new UsuarioEntity(null, "Antigo", "antigo@teste.com", "senha123", tipo));

        var request = Map.of(
                "nome", "Novo Nome",
                "email", "novo@teste.com",
                "senha", "novaSenha",
                "tipoUsuarioId", tipoUsuarioId
        );

        mockMvc.perform(put("/api/v1/usuarios/{id}", usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Novo Nome"))
                .andExpect(jsonPath("$.email").value("novo@teste.com"));
    }

    @Test
    void deveDeletarUsuario() throws Exception {
        var tipo = tipoUsuarioRepository.findById(tipoUsuarioId).orElseThrow();
        var usuario = usuarioRepository.save(new UsuarioEntity(null, "ParaDeletar", "deletar@teste.com", "senha123", tipo));

        mockMvc.perform(delete("/api/v1/usuarios/{id}", usuario.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/usuarios/{id}", usuario.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400QuandoEmailInvalido() throws Exception {
        var request = Map.of(
                "nome", "Teste",
                "email", "email-invalido",
                "senha", "senha123",
                "tipoUsuarioId", tipoUsuarioId
        );

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar400QuandoNomeVazio() throws Exception {
        var request = Map.of(
                "nome", "",
                "email", "teste@teste.com",
                "senha", "senha123",
                "tipoUsuarioId", tipoUsuarioId
        );

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar404QuandoUsuarioNaoEncontrado() throws Exception {
        mockMvc.perform(get("/api/v1/usuarios/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveListarVazioQuandoNaoHaUsuarios() throws Exception {
        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
