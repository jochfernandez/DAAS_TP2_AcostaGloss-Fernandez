package ar.edu.unju.fi.arquitecturas.tp2.service;

import ar.edu.unju.fi.arquitecturas.tp2.dto.CuentaRequestDto;
import ar.edu.unju.fi.arquitecturas.tp2.dto.CuentaResponseDto;
import ar.edu.unju.fi.arquitecturas.tp2.model.CuentaFinanciera;

import java.util.UUID;

public interface CuentaFinancieraService {
    CuentaResponseDto crearCuenta(CuentaRequestDto cuentaRequestDto);
    CuentaFinanciera buscarCuentaFinancieraPorId(UUID id);
    CuentaFinanciera depositar(UUID id, float monto);
    CuentaFinanciera extraer(UUID id, float monto);
    void transferir(UUID idCuentaOrigen, UUID idCuentaDestino, float monto);
    void aplicarInteresMensual(UUID id);
    CuentaResponseDto consultarPorCbu(String cbu);
}
