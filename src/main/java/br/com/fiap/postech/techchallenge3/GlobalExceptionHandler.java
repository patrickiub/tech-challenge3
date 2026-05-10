package br.com.fiap.postech.techchallenge3;

import br.com.fiap.postech.techchallenge3.cardapio.core.exception.ItemCardapioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.restaurante.core.exception.RestauranteNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import br.com.fiap.postech.techchallenge3.usuario.core.exception.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            UsuarioNaoEncontradoException.class,
            TipoUsuarioNaoEncontradoException.class,
            RestauranteNaoEncontradoException.class,
            ItemCardapioNaoEncontradoException.class
    })
    public ResponseEntity<Map<String, Object>> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody(ex.getMessage(), 404));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            erros.put(fe.getField(), fe.getDefaultMessage());
        }
        Map<String, Object> body = errorBody("Erro de validação", 400);
        body.put("campos", erros);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorBody("Erro interno: " + ex.getMessage(), 500));
    }

    private Map<String, Object> errorBody(String mensagem, int status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status);
        body.put("mensagem", mensagem);
        return body;
    }
}
