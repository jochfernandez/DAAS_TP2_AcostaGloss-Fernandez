package ar.edu.unju.fi.arquitecturas.tp2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaResponseDto {
    private UUID id;
    private String cbu;
    private String alias;
    private String tipoCuenta;
    private Float saldo;
    private String estado;
    private LocalDateTime fechaCreacion;
}