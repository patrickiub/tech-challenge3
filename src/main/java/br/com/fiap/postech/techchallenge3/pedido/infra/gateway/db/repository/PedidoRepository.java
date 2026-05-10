package br.com.fiap.postech.techchallenge3.pedido.infra.gateway.db.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.postech.techchallenge3.pedido.infra.gateway.db.entity.PedidoEntity;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long>{
    List<PedidoEntity> findByUsuarioId(Long usuarioId);
}
