package ar.edu.unju.fi.arquitecturas.tp2.service.impl;

import ar.edu.unju.fi.arquitecturas.tp2.exception.RecursoNoEncontradoException;
import ar.edu.unju.fi.arquitecturas.tp2.exception.SaldoInsuficienteException;
import ar.edu.unju.fi.arquitecturas.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitecturas.tp2.repository.CuentaFinancieraRepository;
import ar.edu.unju.fi.arquitecturas.tp2.service.CuentaFinancieraService;
import ar.edu.unju.fi.arquitecturas.tp2.service.TransaccionService;
import ar.edu.unju.fi.arquitecturas.tp2.util.EstadoDeProcesamientoDeTransaccion;
import ar.edu.unju.fi.arquitecturas.tp2.util.TipoDeTransaccion;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CuentaFinancieraServiceImpl implements CuentaFinancieraService {

    private final CuentaFinancieraRepository cuentaFinancieraRepository;
    private final TransaccionService transaccionService;

    @Override
    public CuentaFinanciera buscarCuentaFinancieraPorId(UUID id) {
        return cuentaFinancieraRepository.findById(id).orElseThrow(() -> {
            log.error("Cuenta financiera no encontrada con el ID: {}", id);
            return new RecursoNoEncontradoException("Cuenta financiera no encontrada con el ID: " + id);
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
            throw new SaldoInsuficienteException("Saldo insuficiente en la cuenta financiera con el ID: " + id);
        }
    }

    @Transactional
    @Override
    public void transferir(UUID idCuentaOrigen, UUID idCuentaDestino, float monto) {
        log.info("Iniciando solicitud de transferencia por monto: {}", monto);
        CuentaFinanciera cuentaOrigen = cuentaFinancieraRepository.findById(idCuentaOrigen).orElseThrow(() -> {
            log.error("Fallo en transferencia: Cuenta origen no encontrada (ID: {})", idCuentaOrigen);
            return new RecursoNoEncontradoException("La cuenta de origen especificada no existe en el sistema.");
        });
        CuentaFinanciera cuentaDestino = cuentaFinancieraRepository.findById(idCuentaDestino).orElseThrow(() -> {
            log.error("Fallo en transferencia: Cuenta destino no encontrada (ID: {})", idCuentaDestino);
            return new RecursoNoEncontradoException("La cuenta de destino especificada no existe en el sistema.");
        });
        if (cuentaOrigen.getSaldo() < monto) {
            log.warn("Transferencia denegada: Saldo insuficiente en cuenta origen asociada al CUIL: {}", cuentaOrigen.getTitularPrincipal().getCuil());
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar la transferencia.");
        }
        // 1. Modificación de saldos
        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);
        cuentaFinancieraRepository.save(cuentaOrigen);
        cuentaFinancieraRepository.save(cuentaDestino);
        // 2. Auditoría: Registro físico de la transacción (Débito y Crédito)
        transaccionService.registrarTransaccion(cuentaOrigen, monto, TipoDeTransaccion.DEBITO, EstadoDeProcesamientoDeTransaccion.APROBADO);
        transaccionService.registrarTransaccion(cuentaDestino, monto, TipoDeTransaccion.CREDITO, EstadoDeProcesamientoDeTransaccion.APROBADO);
        log.info("Transferencia completada exitosamente entre CUIL origen: {} y CUIL destino: {}",
                cuentaOrigen.getTitularPrincipal().getCuil(),
                cuentaDestino.getTitularPrincipal().getCuil());
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