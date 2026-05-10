package br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.repository;

import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.entity.TipoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, Long> {
}
