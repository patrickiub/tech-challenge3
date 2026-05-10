package br.com.fiap.postech.techchallenge3.usuario.infra.controller;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;
import br.com.fiap.postech.techchallenge3.usuario.core.dto.TipoUsuarioRequestDTO;
import br.com.fiap.postech.techchallenge3.usuario.core.dto.TipoUsuarioResponseDTO;
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
@RequestMapping("/api/v1/tipos-usuario")
@Tag(name = "TipoUsuario", description = "Gerenciamento de tipos de usuário. Define as categorias atribuídas aos usuários do sistema (ex.: Dono de Restaurante, Cliente).")
public class TipoUsuarioController {

    private final CriarTipoUsuarioUseCase criarTipoUsuarioUseCase;
    private final BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;
    private final ListarTiposUsuarioUseCase listarTiposUsuarioUseCase;
    private final AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase;
    private final DeletarTipoUsuarioUseCase deletarTipoUsuarioUseCase;

    public TipoUsuarioController(
            CriarTipoUsuarioUseCase criarTipoUsuarioUseCase,
            BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase,
            ListarTiposUsuarioUseCase listarTiposUsuarioUseCase,
            AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase,
            DeletarTipoUsuarioUseCase deletarTipoUsuarioUseCase) {
        this.criarTipoUsuarioUseCase = criarTipoUsuarioUseCase;
        this.buscarTipoUsuarioPorIdUseCase = buscarTipoUsuarioPorIdUseCase;
        this.listarTiposUsuarioUseCase = listarTiposUsuarioUseCase;
        this.atualizarTipoUsuarioUseCase = atualizarTipoUsuarioUseCase;
        this.deletarTipoUsuarioUseCase = deletarTipoUsuarioUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar tipo de usuário", description = "Cadastra um novo tipo de usuário no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tipo de usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    })
    public TipoUsuarioResponseDTO criar(@Valid @RequestBody TipoUsuarioRequestDTO dto) {
        TipoUsuario tipoUsuario = new TipoUsuario(null, dto.nome());
        return TipoUsuarioResponseDTO.from(criarTipoUsuarioUseCase.executar(tipoUsuario));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de usuário por ID", description = "Retorna um tipo de usuário específico a partir do seu identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")
    })
    public TipoUsuarioResponseDTO buscarPorId(@Parameter(description = "ID do tipo de usuário", required = true) @PathVariable Long id) {
        return TipoUsuarioResponseDTO.from(buscarTipoUsuarioPorIdUseCase.executar(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os tipos de usuário", description = "Retorna a lista completa de tipos de usuário cadastrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public List<TipoUsuarioResponseDTO> listarTodos() {
        return listarTiposUsuarioUseCase.executar().stream()
                .map(TipoUsuarioResponseDTO::from)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tipo de usuário", description = "Atualiza os dados de um tipo de usuário existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")
    })
    public TipoUsuarioResponseDTO atualizar(@Parameter(description = "ID do tipo de usuário", required = true) @PathVariable Long id, @Valid @RequestBody TipoUsuarioRequestDTO dto) {
        TipoUsuario tipoUsuario = new TipoUsuario(id, dto.nome());
        return TipoUsuarioResponseDTO.from(atualizarTipoUsuarioUseCase.executar(id, tipoUsuario));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar tipo de usuário", description = "Remove permanentemente um tipo de usuário do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tipo de usuário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")
    })
    public void deletar(@Parameter(description = "ID do tipo de usuário", required = true) @PathVariable Long id) {
        deletarTipoUsuarioUseCase.executar(id);
    }
}
