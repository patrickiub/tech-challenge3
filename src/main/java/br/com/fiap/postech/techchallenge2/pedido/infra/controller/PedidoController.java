package br.com.fiap.postech.techchallenge2.pedido.infra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.fiap.postech.techchallenge2.pedido.core.dto.PedidoRequestDTO;
import br.com.fiap.postech.techchallenge2.pedido.core.dto.PedidoResponseDTO;
import br.com.fiap.postech.techchallenge2.pedido.core.gateway.ConsultarPedidoUseCase;
import br.com.fiap.postech.techchallenge2.pedido.core.gateway.CriarPedidoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedido", description = "Gerenciamento de pedidos. Permite cadastrar novo pedido, consultar informações de um pedido e consultar todos os pedidos associados ao cliente autenticado.")
public class PedidoController {

    private final CriarPedidoUseCase criarPedidoUseCase;
    private final ConsultarPedidoUseCase consultarPedidoUseCase;

    public PedidoController(
            CriarPedidoUseCase criarPedidoUseCase,
            ConsultarPedidoUseCase consultarPedidoUseCase
    ) {
        this.criarPedidoUseCase = criarPedidoUseCase;
        this.consultarPedidoUseCase = consultarPedidoUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary =  "Criar pedido", description = "Cadastra um novo pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pedido criado")
        // @ApiResponse(responseCode = "400", description = "Pedido criado"),
    })
    public PedidoResponseDTO criar(@Valid @RequestBody PedidoRequestDTO dto) {
    
        return PedidoResponseDTO.from(criarPedidoUseCase.executar(dto));
    } 

    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary =  "Consultar pedido", description = "Consulta dados de um pedido a partir do id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido Encontrado")        
    })
    public PedidoResponseDTO consultar(@PathVariable Long id) {
    
        return PedidoResponseDTO.from(consultarPedidoUseCase.executar(id));
    }   


    

}
