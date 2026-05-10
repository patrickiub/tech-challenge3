package br.com.fiap.postech.techchallenge3.restaurante.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RestauranteRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 200, message = "Nome deve ter no máximo 200 caracteres")
        String nome,

        @NotBlank(message = "Tipo de cozinha é obrigatório")
        @Size(max = 100, message = "Tipo de cozinha deve ter no máximo 100 caracteres")
        String tipoCozinha,

        @NotBlank(message = "Endereço é obrigatório")
        @Size(max = 300, message = "Endereço deve ter no máximo 300 caracteres")
        String endereco,

        @Size(max = 100, message = "Horário de funcionamento deve ter no máximo 100 caracteres")
        String horarioFuncionamento,

        @NotNull(message = "Dono do restaurante é obrigatório")
        Long donoRestauranteId
) {
}
