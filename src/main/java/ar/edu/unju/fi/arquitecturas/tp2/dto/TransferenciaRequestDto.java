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
public class TransferenciaRequestDto {

    @NotNull(message = "El ID del cliente que realiza la transferencia es obligatorio.")
    private UUID idCliente;

    @NotNull(message = "El ID de la cuenta de origen es obligatorio.")
    private UUID idCuentaOrigen;

    @NotNull(message = "El ID de la cuenta de destino es obligatorio.")
    private UUID idCuentaDestino;

    @NotNull(message = "El monto a transferir es obligatorio.")
    @Positive(message = "El monto a transferir debe ser mayor a cero.")
    private Float monto;
}