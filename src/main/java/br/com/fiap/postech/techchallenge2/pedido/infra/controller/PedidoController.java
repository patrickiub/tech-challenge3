package br.com.fiap.postech.techchallenge2.pedido.infra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.postech.techchallenge2.pedido.core.domain.ItemPedido;
import br.com.fiap.postech.techchallenge2.pedido.core.domain.Pedido;
import br.com.fiap.postech.techchallenge2.pedido.core.dto.PedidoRequestDTO;
import br.com.fiap.postech.techchallenge2.pedido.core.dto.PedidoResponseDTO;
import br.com.fiap.postech.techchallenge2.pedido.core.gateway.CriarPedidoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final CriarPedidoUseCase criarPedidoUseCase;

    public PedidoController(
            CriarPedidoUseCase criarPedidoUseCase
    ) {
        this.criarPedidoUseCase = criarPedidoUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary =  "Criar pedido", description = "Cadastra um novo pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pedido criado")
        // @ApiResponse(responseCode = "400", description = "Pedido criado"),
    })
    public PedidoResponseDTO criar(@Valid @RequestBody PedidoRequestDTO dto) {
    Pedido pedido = new Pedido(
        dto.clienteId(),
        dto.restauranteId(),
        dto.itens().stream()
            .map(i -> new ItemPedido(
                null,             // id
                i.itemCardapioId(),  // itemCardapioId
                null,           // nome (pode ser resolvido depois)
                i.quantidade(),      // quantidade
                null          // preco
            ))
            .toList() // transforma o Stream em List<ItemPedido>
    );
    return PedidoResponseDTO.from(criarPedidoUseCase.executar(pedido));
}


    

}
