package br.com.fiap.postech.techchallenge3.usuario.infra.controller;

import br.com.fiap.postech.techchallenge3.infra.security.JwtService;
import br.com.fiap.postech.techchallenge3.usuario.core.domain.Role;
import br.com.fiap.postech.techchallenge3.usuario.core.domain.TipoUsuario;
import br.com.fiap.postech.techchallenge3.usuario.core.domain.Usuario;
import br.com.fiap.postech.techchallenge3.usuario.core.dto.LoginRequestDTO;
import br.com.fiap.postech.techchallenge3.usuario.core.dto.LoginResponseDTO;
import br.com.fiap.postech.techchallenge3.usuario.core.dto.UsuarioRequestDTO;
import br.com.fiap.postech.techchallenge3.usuario.core.dto.UsuarioResponseDTO;
import br.com.fiap.postech.techchallenge3.usuario.core.gateway.UsuarioGateway;
import br.com.fiap.postech.techchallenge3.usuario.core.usecase.CriarUsuarioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Registro e login de usuários.")
public class AuthController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final UsuarioGateway usuarioGateway;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(CriarUsuarioUseCase criarUsuarioUseCase, UsuarioGateway usuarioGateway,
                          JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.usuarioGateway = usuarioGateway;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar usuário", description = "Cria um novo usuário com role ROLE_CLIENTE e senha criptografada.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    })
    public UsuarioResponseDTO register(@Valid @RequestBody UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario(null, dto.nome(), dto.email(), passwordEncoder.encode(dto.senha()),
                new TipoUsuario(dto.tipoUsuarioId(), null), Role.ROLE_CLIENTE);
        return UsuarioResponseDTO.from(criarUsuarioUseCase.executar(usuario));
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Valida credenciais e retorna token JWT Bearer.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO dto) {
        Usuario usuario = usuarioGateway.buscarPorEmail(dto.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas"));

        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }

        String token = jwtService.gerarToken(usuario.getId());
        return new LoginResponseDTO(token, "Bearer", usuario.getId(), usuario.getNome());
    }
}
