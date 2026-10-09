package ar.edu.unju.fi.arquitecturas.tp2.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionResponseDto {
    private String mensaje;
    private Float monto;
    private LocalDateTime timestamp;
}
