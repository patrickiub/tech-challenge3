package br.com.fiap.postech.techchallenge3.usuario.infra.controller;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;
import br.com.fiap.postech.techchallenge3.usuario.core.domain.Usuario;
import br.com.fiap.postech.techchallenge3.usuario.core.dto.UsuarioRequestDTO;
import br.com.fiap.postech.techchallenge3.usuario.core.dto.UsuarioResponseDTO;
import br.com.fiap.postech.techchallenge3.usuario.core.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuario", description = "Gerenciamento de usuários do sistema. Cada usuário possui um tipo que define seu papel (Dono de Restaurante, Cliente, etc.).")
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ListarUsuariosUseCase listarUsuariosUseCase;
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private final DeletarUsuarioUseCase deletarUsuarioUseCase;

    public UsuarioController(
            CriarUsuarioUseCase criarUsuarioUseCase,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            ListarUsuariosUseCase listarUsuariosUseCase,
            AtualizarUsuarioUseCase atualizarUsuarioUseCase,
            DeletarUsuarioUseCase deletarUsuarioUseCase) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
        this.listarUsuariosUseCase = listarUsuariosUseCase;
        this.atualizarUsuarioUseCase = atualizarUsuarioUseCase;
        this.deletarUsuarioUseCase = deletarUsuarioUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar usuário", description = "Cadastra um novo usuário no sistema vinculado a um tipo de usuário existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    })
    public UsuarioResponseDTO criar(@Valid @RequestBody UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario(null, dto.nome(), dto.email(), dto.senha(),
                new TipoUsuario(dto.tipoUsuarioId(), null));
        return UsuarioResponseDTO.from(criarUsuarioUseCase.executar(usuario));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados completos de um usuário a partir do seu identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public UsuarioResponseDTO buscarPorId(@Parameter(description = "ID do usuário", required = true) @PathVariable Long id) {
        return UsuarioResponseDTO.from(buscarUsuarioPorIdUseCase.executar(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna a lista completa de usuários cadastrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public List<UsuarioResponseDTO> listarTodos() {
        return listarUsuariosUseCase.executar().stream()
                .map(UsuarioResponseDTO::from)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente, incluindo nome, e-mail, senha e tipo de usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public UsuarioResponseDTO atualizar(@Parameter(description = "ID do usuário", required = true) @PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario(id, dto.nome(), dto.email(), dto.senha(),
                new TipoUsuario(dto.tipoUsuarioId(), null));
        return UsuarioResponseDTO.from(atualizarUsuarioUseCase.executar(id, usuario));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar usuário", description = "Remove permanentemente um usuário do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public void deletar(@Parameter(description = "ID do usuário", required = true) @PathVariable Long id) {
        deletarUsuarioUseCase.executar(id);
    }
}
