package br.com.fiap.postech.techchallenge3.pedido.core.usecase;

import org.springframework.stereotype.Service;

import br.com.fiap.postech.techchallenge3.pedido.core.domain.Pedido;
import br.com.fiap.postech.techchallenge3.pedido.core.dto.CriarPedidoRequestDTO;
import br.com.fiap.postech.techchallenge3.pedido.core.gateway.PedidoGateway;

@Service
public class CriarPedidoUseCase {

    private final PedidoGateway pedidoGateway;

    public CriarPedidoUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido executar(CriarPedidoRequestDTO dto){
        return pedidoGateway.salvar(dto); //TODO: Aqui ocorre um acoplamento do domÃ­nio e infraestrutura ao contrato da API. SugestÃ£o: Criar um prÃ©-pedido na controller e repassar pra esse useCase, este por sua vez enriqueceria (talvez precisaria consultar outros gateways) e enviaria o domÃ­nio Pedido pronto para o gateway salvar.
    }
}
