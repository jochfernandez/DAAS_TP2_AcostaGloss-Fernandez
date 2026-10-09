package ar.edu.unju.fi.arquitecturas.tp2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtraccionRequestDto {
    @NotNull(message = "El ID del cliente es obligatorio.")
    private UUID idCliente;

    @NotNull(message = "El ID de la cuenta es obligatorio.")
    private UUID idCuenta;

    @NotNull(message = "El monto a extraer es obligatorio.")
    @Positive(message = "El monto a extraer debe ser mayor a cero.")
    private Float monto;
}
