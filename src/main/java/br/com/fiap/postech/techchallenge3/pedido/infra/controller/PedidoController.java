package br.com.fiap.postech.techchallenge3.pedido.infra.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.postech.techchallenge3.pedido.core.dto.CriarPedidoRequestDTO;
import br.com.fiap.postech.techchallenge3.pedido.core.dto.CriarPedidoResponseDTO;
import br.com.fiap.postech.techchallenge3.pedido.core.dto.PedidoResponseDTO;
import br.com.fiap.postech.techchallenge3.pedido.core.usecase.ConfirmarPedidoUseCase;
import br.com.fiap.postech.techchallenge3.pedido.core.usecase.ConsultarPedidoUseCase;
import br.com.fiap.postech.techchallenge3.pedido.core.usecase.ConsultarPedidosClienteUseCase;
import br.com.fiap.postech.techchallenge3.pedido.core.usecase.CriarPedidoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedido", description = "Gerenciamento de pedidos. Permite cadastrar novo pedido, consultar informaÃ§Ãµes de um pedido e consultar todos os pedidos associados ao cliente autenticado.")
public class PedidoController {

    private final CriarPedidoUseCase criarPedidoUseCase;
    private final ConsultarPedidoUseCase consultarPedidoUseCase;
    private final ConsultarPedidosClienteUseCase consultarPedidosClienteUseCase;
    private final ConfirmarPedidoUseCase confirmarPedidoUseCase;

    public PedidoController(
            CriarPedidoUseCase criarPedidoUseCase,
            ConsultarPedidoUseCase consultarPedidoUseCase,
            ConsultarPedidosClienteUseCase consultarPedidosClienteUseCase,
            ConfirmarPedidoUseCase confirmarPedidoUseCase
    ) {
        this.criarPedidoUseCase = criarPedidoUseCase;
        this.consultarPedidoUseCase = consultarPedidoUseCase;
        this.consultarPedidosClienteUseCase = consultarPedidosClienteUseCase;
        this.confirmarPedidoUseCase = confirmarPedidoUseCase;
    }

    @GetMapping("/confirmar/{id}") 
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary =  "Confirmar pedido", description = "Altera o status do pedido para confirmado")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pedido confirmado")
        // @ApiResponse(responseCode = "400", description = "Pedido criado"),
    })
    public PedidoResponseDTO confirmarPedido(@PathVariable Long id) {
        
        Long clienteId = 2L;

        return PedidoResponseDTO.from(confirmarPedidoUseCase.executar(id, clienteId));
    } 

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary =  "Criar pedido", description = "Cadastra um novo pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pedido criado")
        // @ApiResponse(responseCode = "400", description = "Pedido criado"),
    })
    public CriarPedidoResponseDTO criar(@Valid @RequestBody CriarPedidoRequestDTO dto, Authentication authentication) {
        Long clienteId = Long.parseLong(authentication.getName());
        CriarPedidoRequestDTO dtoComCliente = new CriarPedidoRequestDTO(clienteId, dto.restauranteId(), dto.itens());
        return CriarPedidoResponseDTO.from(criarPedidoUseCase.executar(dtoComCliente));
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

    
    @GetMapping("/cliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary =  "Listar todos pedidos de um cliente", description = "Pesquisa todos os pedidos de um cliente a partir de seu id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido Encontrado")        
    })
    public List<PedidoResponseDTO> listarPedidosCliente(@PathVariable Long id) {
    
        return consultarPedidosClienteUseCase.executar(id).stream()
            .map(PedidoResponseDTO::from) // converte cada Pedido â†’ PedidoResponseDTO
            .toList();
    }       

}
