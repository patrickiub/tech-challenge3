package com.fiap.pagamento.core.gateway;

import com.fiap.pagamento.core.domain.Pagamento;

public interface PagamentoGateway {
    Pagamento salvar(Pagamento pagamento);
}
