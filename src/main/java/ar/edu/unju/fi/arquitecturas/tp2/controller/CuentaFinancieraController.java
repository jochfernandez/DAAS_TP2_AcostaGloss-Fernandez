package ar.edu.unju.fi.arquitecturas.tp2.controller;

import ar.edu.unju.fi.arquitecturas.tp2.dto.CuentaRequestDto;
import ar.edu.unju.fi.arquitecturas.tp2.dto.CuentaResponseDto;
import ar.edu.unju.fi.arquitecturas.tp2.service.CuentaFinancieraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cuentas")
@RequiredArgsConstructor
public class CuentaFinancieraController {

    private final CuentaFinancieraService cuentaFinancieraService;

    @PostMapping
    public ResponseEntity<CuentaResponseDto> crearCuenta(@Valid @RequestBody CuentaRequestDto requestDto) {

        // La Capa de Servicio recibe el DTO, hace el mapeo interno hacia la entidad,
        // la guarda en MySQL y devuelve un CuentaResponseDto.
        CuentaResponseDto responseDto = cuentaFinancieraService.crearCuenta(requestDto);

        // Se retorna estrictamente el DTO con el código HTTP 201 CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @GetMapping("/{cbu}")
    public ResponseEntity<CuentaResponseDto> consultarCuentaPorCbu(@PathVariable String cbu) {
        CuentaResponseDto responseDto = cuentaFinancieraService.consultarPorCbu(cbu);

        return ResponseEntity.ok(responseDto);
    }
}