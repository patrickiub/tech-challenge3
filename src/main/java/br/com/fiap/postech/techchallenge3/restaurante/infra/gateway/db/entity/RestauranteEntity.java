package br.com.fiap.postech.techchallenge3.restaurante.infra.gateway.db.entity;

import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.entity.UsuarioEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurantes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(name = "tipo_cozinha", length = 100)
    private String tipoCozinha;

    @Column(length = 300)
    private String endereco;

    @Column(name = "horario_funcionamento", length = 100)
    private String horarioFuncionamento;

    @ManyToOne
    @JoinColumn(name = "dono_restaurante_id", nullable = false)
    private UsuarioEntity donoRestaurante;
}
