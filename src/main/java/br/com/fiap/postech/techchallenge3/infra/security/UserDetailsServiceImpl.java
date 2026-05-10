package br.com.fiap.postech.techchallenge3.infra.security;

import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository repository;

    public UserDetailsServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        try {
            Long userId = Long.parseLong(id);
            return repository.findById(userId)
                    .map(entity -> User.builder()
                            .username(String.valueOf(entity.getId()))
                            .password(entity.getSenha())
                            .authorities(entity.getRole() != null ? entity.getRole().name() : "ROLE_CLIENTE")
                            .build())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + id));
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("ID inválido: " + id);
        }
    }
}
