package ar.edu.unju.fi.arquitecturas.tp2.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequestDto {
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El mail no puede estar vacío")
    @Email(message = "El mail debe tener un formato válido")
    private String mail;

    @NotBlank(message = "El CUIL no puede estar vacío")
    @Size(min = 11, max = 15, message = "El CUIL debe contener entre 11 y 15 caracteres")
    private String cuil;

    @NotBlank(message = "El teléfono no puede estar vacío")
    private String telefono;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;
}
