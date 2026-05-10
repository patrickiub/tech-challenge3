package br.com.fiap.postech.techchallenge3.restaurante.infra.controller;

import br.com.fiap.postech.techchallenge3.restaurante.core.domain.Restaurante;
import br.com.fiap.postech.techchallenge3.restaurante.core.dto.RestauranteRequestDTO;
import br.com.fiap.postech.techchallenge3.restaurante.core.dto.RestauranteResponseDTO;
import br.com.fiap.postech.techchallenge3.restaurante.core.usecase.*;
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
@RequestMapping("/api/v1/restaurantes")
@Tag(name = "Restaurante", description = "Gerenciamento de restaurantes. Permite cadastrar, consultar, atualizar e remover restaurantes, associando cada um ao seu dono (usuário).")
public class RestauranteController {

    private final CriarRestauranteUseCase criarRestauranteUseCase;
    private final BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;
    private final ListarRestaurantesUseCase listarRestaurantesUseCase;
    private final AtualizarRestauranteUseCase atualizarRestauranteUseCase;
    private final DeletarRestauranteUseCase deletarRestauranteUseCase;

    public RestauranteController(
            CriarRestauranteUseCase criarRestauranteUseCase,
            BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase,
            ListarRestaurantesUseCase listarRestaurantesUseCase,
            AtualizarRestauranteUseCase atualizarRestauranteUseCase,
            DeletarRestauranteUseCase deletarRestauranteUseCase) {
        this.criarRestauranteUseCase = criarRestauranteUseCase;
        this.buscarRestaurantePorIdUseCase = buscarRestaurantePorIdUseCase;
        this.listarRestaurantesUseCase = listarRestaurantesUseCase;
        this.atualizarRestauranteUseCase = atualizarRestauranteUseCase;
        this.deletarRestauranteUseCase = deletarRestauranteUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar restaurante", description = "Cadastra um novo restaurante vinculado a um usuário existente como dono.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    })
    public RestauranteResponseDTO criar(@Valid @RequestBody RestauranteRequestDTO dto) {
        Restaurante restaurante = new Restaurante(null, dto.nome(), dto.tipoCozinha(),
                dto.endereco(), dto.horarioFuncionamento(), dto.donoRestauranteId());
        return RestauranteResponseDTO.from(criarRestauranteUseCase.executar(restaurante));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar restaurante por ID", description = "Retorna os dados completos de um restaurante a partir do seu identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public RestauranteResponseDTO buscarPorId(@Parameter(description = "ID do restaurante", required = true) @PathVariable Long id) {
        return RestauranteResponseDTO.from(buscarRestaurantePorIdUseCase.executar(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os restaurantes", description = "Retorna a lista completa de restaurantes cadastrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public List<RestauranteResponseDTO> listarTodos() {
        return listarRestaurantesUseCase.executar().stream()
                .map(RestauranteResponseDTO::from)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar restaurante", description = "Atualiza os dados de um restaurante existente, como nome, tipo de cozinha, endereço e horário de funcionamento.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public RestauranteResponseDTO atualizar(@Parameter(description = "ID do restaurante", required = true) @PathVariable Long id, @Valid @RequestBody RestauranteRequestDTO dto) {
        Restaurante restaurante = new Restaurante(id, dto.nome(), dto.tipoCozinha(),
                dto.endereco(), dto.horarioFuncionamento(), dto.donoRestauranteId());
        return RestauranteResponseDTO.from(atualizarRestauranteUseCase.executar(id, restaurante));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar restaurante", description = "Remove permanentemente um restaurante do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public void deletar(@Parameter(description = "ID do restaurante", required = true) @PathVariable Long id) {
        deletarRestauranteUseCase.executar(id);
    }
}
