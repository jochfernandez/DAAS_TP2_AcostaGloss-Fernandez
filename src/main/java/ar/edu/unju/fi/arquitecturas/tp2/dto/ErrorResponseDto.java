package ar.edu.unju.fi.arquitecturas.tp2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorResponseDto {
    private String mensaje;
    private int status;
    private LocalDateTime timestamp;
}