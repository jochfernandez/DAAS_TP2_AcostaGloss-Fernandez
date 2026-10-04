package ar.edu.unju.fi.arquitecturas.tp2.exception;

import ar.edu.unju.fi.arquitecturas.tp2.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Captura las excepciones de tu servicio (ej. Cliente duplicado o no encontrado)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .mensaje(ex.getMessage()) // Aquí viaja el texto exacto que pusiste en tu servicio
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 2. Captura los errores de Jakarta Validation (@Valid en el DTO)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extraemos el primer mensaje de error (por ejemplo: "El mail debe tener un formato válido")
        String mensajeError = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        ErrorResponseDto error = ErrorResponseDto.builder()
                .mensaje(mensajeError)
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Captura cuenta no encontrada (Devuelve 404 Not Found)
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDto> handleRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .mensaje(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Captura problemas de saldo (Devuelve 400 Bad Request o 409 Conflict)
    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<ErrorResponseDto> handleSaldoInsuficiente(SaldoInsuficienteException ex) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .mensaje(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    // Captura operaciones denegadas para Adherentes (Devuelve 403 Forbidden)
    @ExceptionHandler(OperacionNoPermitidaException.class)
    public ResponseEntity<ErrorResponseDto> handleOperacionNoPermitida(OperacionNoPermitidaException ex) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .mensaje(ex.getMessage())
                .status(HttpStatus.FORBIDDEN.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
}