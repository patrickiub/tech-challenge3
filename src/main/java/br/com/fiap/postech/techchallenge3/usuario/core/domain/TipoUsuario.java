package br.com.fiap.postech.techchallenge3.usuario.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoUsuario {
    private Long id;
    private String nome;
}
