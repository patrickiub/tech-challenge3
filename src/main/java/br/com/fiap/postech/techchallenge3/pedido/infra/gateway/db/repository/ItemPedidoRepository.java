package br.com.fiap.postech.techchallenge3.pedido.infra.gateway.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.postech.techchallenge3.pedido.infra.gateway.db.entity.PedidoItemEntity;

public interface ItemPedidoRepository extends JpaRepository<PedidoItemEntity, Long>{    

}
