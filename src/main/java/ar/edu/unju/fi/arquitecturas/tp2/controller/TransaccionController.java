package ar.edu.unju.fi.arquitecturas.tp2.controller;

import ar.edu.unju.fi.arquitecturas.tp2.dto.TransferenciaRequestDto;
import ar.edu.unju.fi.arquitecturas.tp2.dto.TransferenciaResponseDto;
import ar.edu.unju.fi.arquitecturas.tp2.service.CuentaFinancieraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    // Inyectamos el servicio que contiene tu lógica con @Transactional
    private final CuentaFinancieraService cuentaFinancieraService;

    @PostMapping("/transferir")
    public ResponseEntity<TransferenciaResponseDto> realizarTransferencia(@Valid @RequestBody TransferenciaRequestDto requestDto) {

        // 1. Ejecutamos la lógica de negocio en la capa de Servicios
        cuentaFinancieraService.transferir(
                requestDto.getIdCuentaOrigen(),
                requestDto.getIdCuentaDestino(),
                requestDto.getMonto()
        );

        // 2. Armamos el comprobante de respuesta
        TransferenciaResponseDto responseDto = TransferenciaResponseDto.builder()
                .mensaje("Transferencia realizada con éxito.")
                .montoTransferido(requestDto.getMonto())
                .timestamp(LocalDateTime.now())
                .build();

        // 3. Retornamos con estado HTTP 200 OK (ResponseEntity.ok)
        return ResponseEntity.ok(responseDto);
    }
}