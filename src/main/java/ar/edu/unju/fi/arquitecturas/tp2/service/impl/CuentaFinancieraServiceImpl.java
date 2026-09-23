package ar.edu.unju.fi.arquitecturas.tp2.service.impl;

import ar.edu.unju.fi.arquitecturas.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitecturas.tp2.repository.CuentaFinancieraRepository;
import ar.edu.unju.fi.arquitecturas.tp2.service.CuentaFinancieraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CuentaFinancieraServiceImpl implements CuentaFinancieraService {

    private final CuentaFinancieraRepository cuentaFinancieraRepository;

    @Override
    public CuentaFinanciera buscarCuentaFinancieraPorId(UUID id) {
        return cuentaFinancieraRepository.findById(id).orElseThrow(() -> {
            log.error("Cuenta financiera no encontrada con el ID: {}", id);
            return new IllegalArgumentException("Cuenta financiera no encontrada con el ID: " + id);
        });
    }

    @Override
    public CuentaFinanciera depositar(UUID id, float monto) {
        CuentaFinanciera cuenta = buscarCuentaFinancieraPorId(id);
        log.info("Acreditando depósito de {} en cuenta ID: {}", monto, id);
        cuenta.setSaldo(cuenta.getSaldo() + monto);
        return cuentaFinancieraRepository.save(cuenta);
    }

    @Override
    public CuentaFinanciera extraer(UUID id, float monto) {
        CuentaFinanciera cuenta = buscarCuentaFinancieraPorId(id);

        if (cuenta.getSaldo() >= monto) {
            log.info("Procesando extracción de {} en cuenta ID: {}", monto, id);
            cuenta.setSaldo(cuenta.getSaldo() - monto);
            return cuentaFinancieraRepository.save(cuenta);
        } else {
            log.warn("Extracción denegada: Saldo insuficiente ({}) para extraer {} en cuenta ID: {}", cuenta.getSaldo(), monto, id);
            throw new IllegalArgumentException("Saldo insuficiente en la cuenta financiera con el ID: " + id);
        }
    }

    @Override
    public void transferir(UUID idCuentaOrigen, UUID idCuentaDestino, float monto) {
        log.info("Iniciando transferencia de {} desde cuenta ID: {} hacia cuenta ID: {}", monto, idCuentaOrigen, idCuentaDestino);

        CuentaFinanciera cuentaOrigen = buscarCuentaFinancieraPorId(idCuentaOrigen);
        CuentaFinanciera cuentaDestino = buscarCuentaFinancieraPorId(idCuentaDestino);

        if (cuentaOrigen.getSaldo() >= monto) {
            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
            cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);

            cuentaFinancieraRepository.save(cuentaOrigen);
            cuentaFinancieraRepository.save(cuentaDestino);
            log.info("Transferencia completada exitosamente.");
        } else {
            log.warn("Transferencia denegada: Saldo insuficiente en cuenta origen ID: {}", idCuentaOrigen);
            throw new IllegalArgumentException("Saldo insuficiente en la cuenta financiera de origen con el ID: " + idCuentaOrigen);
        }
    }

    @Override
    public void aplicarInteresMensual(UUID id) {
        CuentaFinanciera cuenta = buscarCuentaFinancieraPorId(id);
        float interesMensual = cuenta.getSaldo() * 0.01f;

        log.info("Aplicando interés mensual del 1% ({}) a cuenta ID: {}", interesMensual, id);
        cuenta.setSaldo(cuenta.getSaldo() + interesMensual);
        cuentaFinancieraRepository.save(cuenta);
    }
}