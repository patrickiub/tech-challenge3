package br.com.fiap.postech.techchallenge3.usuario.core.dto;

public record LoginResponseDTO(
        String token,
        String tipo,
        Long clienteId,
        String nome
) {
}
