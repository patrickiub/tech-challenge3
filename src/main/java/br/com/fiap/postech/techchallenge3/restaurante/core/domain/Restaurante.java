package br.com.fiap.postech.techchallenge3.restaurante.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurante {
    private Long id;
    private String nome;
    private String tipoCozinha;
    private String endereco;
    private String horarioFuncionamento;
    private Long donoRestauranteId;
}
