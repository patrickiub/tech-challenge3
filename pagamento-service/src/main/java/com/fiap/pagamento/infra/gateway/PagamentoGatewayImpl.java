package com.fiap.pagamento.infra.gateway;

import com.fiap.pagamento.core.domain.Pagamento;
import com.fiap.pagamento.core.gateway.PagamentoGateway;
import com.fiap.pagamento.infra.gateway.db.entity.PagamentoEntity;
import com.fiap.pagamento.infra.gateway.db.repository.PagamentoRepository;
import org.springframework.stereotype.Component;

@Component
public class PagamentoGatewayImpl implements PagamentoGateway {

    private final PagamentoRepository repository;

    public PagamentoGatewayImpl(PagamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pagamento salvar(Pagamento pagamento) {
        PagamentoEntity entity = new PagamentoEntity(
                pagamento.getId(),
                pagamento.getPedidoId(),
                pagamento.getClienteId(),
                pagamento.getValor(),
                pagamento.getStatus(),
                pagamento.getDataCriacao()
        );
        PagamentoEntity saved = repository.save(entity);
        pagamento.setId(saved.getId());
        return pagamento;
    }
}
