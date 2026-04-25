package br.com.fiap.postech.techchallenge2.pedido.infra.gateway.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.postech.techchallenge2.pedido.infra.gateway.db.entity.PedidoEntity;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long>{

}
