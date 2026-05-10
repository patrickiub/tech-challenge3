package br.com.fiap.postech.techchallenge3.restaurante.core.dto;

import br.com.fiap.postech.techchallenge3.restaurante.core.domain.Restaurante;

public record RestauranteResponseDTO(
        Long id,
        String nome,
        String tipoCozinha,
        String endereco,
        String horarioFuncionamento,
        Long donoRestauranteId
) {

    public static RestauranteResponseDTO from(Restaurante restaurante) {
        return new RestauranteResponseDTO(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getTipoCozinha(),
                restaurante.getEndereco(),
                restaurante.getHorarioFuncionamento(),
                restaurante.getDonoRestauranteId()
        );
    }
}
