package com.fiap.pagamento.infra.gateway.db.repository;

import com.fiap.pagamento.infra.gateway.db.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<PagamentoEntity, Long> {
}
