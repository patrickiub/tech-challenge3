package br.com.fiap.postech.techchallenge3.cardapio.infra.controller;

import br.com.fiap.postech.techchallenge3.cardapio.core.domain.ItemCardapio;
import br.com.fiap.postech.techchallenge3.cardapio.core.dto.ItemCardapioRequestDTO;
import br.com.fiap.postech.techchallenge3.cardapio.core.dto.ItemCardapioResponseDTO;
import br.com.fiap.postech.techchallenge3.cardapio.core.usecase.*;
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
@RequestMapping("/api/v1/itens-cardapio")
@Tag(name = "ItemCardapio", description = "Gerenciamento de itens do cardápio. Permite criar e consultar pratos, bebidas e outros itens associados a um restaurante, com categorias como ENTRADA, PRATO_PRINCIPAL, SOBREMESA, BEBIDA e LANCHE.")
public class ItemCardapioController {

    private final CriarItemCardapioUseCase criarItemCardapioUseCase;
    private final BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;
    private final ListarItensCardapioUseCase listarItensCardapioUseCase;
    private final ListarItensCardapioPorRestauranteUseCase listarItensCardapioPorRestauranteUseCase;
    private final AtualizarItemCardapioUseCase atualizarItemCardapioUseCase;
    private final DeletarItemCardapioUseCase deletarItemCardapioUseCase;

    public ItemCardapioController(
            CriarItemCardapioUseCase criarItemCardapioUseCase,
            BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase,
            ListarItensCardapioUseCase listarItensCardapioUseCase,
            ListarItensCardapioPorRestauranteUseCase listarItensCardapioPorRestauranteUseCase,
            AtualizarItemCardapioUseCase atualizarItemCardapioUseCase,
            DeletarItemCardapioUseCase deletarItemCardapioUseCase) {
        this.criarItemCardapioUseCase = criarItemCardapioUseCase;
        this.buscarItemCardapioPorIdUseCase = buscarItemCardapioPorIdUseCase;
        this.listarItensCardapioUseCase = listarItensCardapioUseCase;
        this.listarItensCardapioPorRestauranteUseCase = listarItensCardapioPorRestauranteUseCase;
        this.atualizarItemCardapioUseCase = atualizarItemCardapioUseCase;
        this.deletarItemCardapioUseCase = deletarItemCardapioUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar item de cardápio", description = "Cadastra um novo item no cardápio de um restaurante existente, informando nome, descrição, preço e categoria.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    })
    public ItemCardapioResponseDTO criar(@Valid @RequestBody ItemCardapioRequestDTO dto) {
        ItemCardapio item = new ItemCardapio(null, dto.nome(), dto.descricao(),
                dto.preco(), dto.categoria(), dto.restauranteId(), dto.fotoPath());
        return ItemCardapioResponseDTO.from(criarItemCardapioUseCase.executar(item));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item de cardápio por ID", description = "Retorna os dados completos de um item do cardápio a partir do seu identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item encontrado"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    public ItemCardapioResponseDTO buscarPorId(@Parameter(description = "ID do item de cardápio", required = true) @PathVariable Long id) {
        return ItemCardapioResponseDTO.from(buscarItemCardapioPorIdUseCase.executar(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os itens de cardápio", description = "Retorna a lista completa de itens de cardápio de todos os restaurantes.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public List<ItemCardapioResponseDTO> listarTodos() {
        return listarItensCardapioUseCase.executar().stream()
                .map(ItemCardapioResponseDTO::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/restaurante/{restauranteId}")
    @Operation(summary = "Listar itens de cardápio por restaurante", description = "Retorna todos os itens do cardápio de um restaurante específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public List<ItemCardapioResponseDTO> listarPorRestaurante(@Parameter(description = "ID do restaurante", required = true) @PathVariable Long restauranteId) {
        return listarItensCardapioPorRestauranteUseCase.executar(restauranteId).stream()
                .map(ItemCardapioResponseDTO::from)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item de cardápio", description = "Atualiza os dados de um item do cardápio existente, como nome, descrição, preço e categoria.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    public ItemCardapioResponseDTO atualizar(@Parameter(description = "ID do item de cardápio", required = true) @PathVariable Long id, @Valid @RequestBody ItemCardapioRequestDTO dto) {
        ItemCardapio item = new ItemCardapio(id, dto.nome(), dto.descricao(),
                dto.preco(), dto.categoria(), dto.restauranteId(), dto.fotoPath());
        return ItemCardapioResponseDTO.from(atualizarItemCardapioUseCase.executar(id, item));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar item de cardápio", description = "Remove permanentemente um item do cardápio do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    public void deletar(@Parameter(description = "ID do item de cardápio", required = true) @PathVariable Long id) {
        deletarItemCardapioUseCase.executar(id);
    }
}
