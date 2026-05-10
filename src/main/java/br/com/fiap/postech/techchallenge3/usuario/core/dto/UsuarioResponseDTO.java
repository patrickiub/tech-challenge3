package br.com.fiap.postech.techchallenge3.usuario.core.dto;

import br.com.fiap.postech.techchallenge3.usuario.core.domain.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        TipoUsuarioResponseDTO tipoUsuario
) {

    public static UsuarioResponseDTO from(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                TipoUsuarioResponseDTO.from(usuario.getTipoUsuario())
        );
    }
}
