package br.com.fiap.postech.techchallenge3.restaurante.infra.gateway.db.repository;

import br.com.fiap.postech.techchallenge3.restaurante.infra.gateway.db.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestauranteRepository extends JpaRepository<RestauranteEntity, Long> {
    List<RestauranteEntity> findByDonoRestauranteId(Long donoRestauranteId);
}
