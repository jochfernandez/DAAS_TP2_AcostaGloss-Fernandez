package ar.edu.unju.fi.arquitecturas.tp2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaRequestDto {

    @NotNull(message = "El ID del titular principal es obligatorio.")
    private UUID titularPrincipalId;

    @NotNull(message = "El tipo de cuenta es obligatorio (ej. CAJA_AHORRO o CUENTA_CORRIENTE).")
    private String tipoCuenta;

    @NotNull(message = "El saldo inicial es obligatorio.")
    @PositiveOrZero(message = "El saldo inicial no puede ser negativo.")
    private Float saldoInicial;
}