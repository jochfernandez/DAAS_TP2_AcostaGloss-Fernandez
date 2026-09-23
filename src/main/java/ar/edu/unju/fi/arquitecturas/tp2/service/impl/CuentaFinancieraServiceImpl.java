package ar.edu.unju.fi.arquitecturas.tp2.service.impl;

import ar.edu.unju.fi.arquitecturas.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitecturas.tp2.repository.CuentaFinancieraRepository;
import ar.edu.unju.fi.arquitecturas.tp2.service.CuentaFinancieraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CuentaFinancieraServiceImpl implements CuentaFinancieraService {
    private final CuentaFinancieraRepository cuentaFinancieraRepository;

    @Override
    public CuentaFinanciera buscarCuentaFinancieraPorId(UUID id) {
        return cuentaFinancieraRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cuenta financiera no encontrada con el ID: " + id));
    }

    @Override
    public CuentaFinanciera depositar(UUID id, float monto) {
        CuentaFinanciera cuenta = buscarCuentaFinancieraPorId(id);
        if (cuenta != null) {
            cuenta.setSaldo(cuenta.getSaldo() + monto);
            return cuentaFinancieraRepository.save(cuenta);
        } else {
            throw new IllegalArgumentException("Cuenta financiera no encontrada con el ID: " + id);
        }
    }

    @Override
    public CuentaFinanciera extraer(UUID id, float monto) {
        CuentaFinanciera cuenta = buscarCuentaFinancieraPorId(id);
        if (cuenta != null) {
            if (cuenta.getSaldo() >= monto) {
                cuenta.setSaldo(cuenta.getSaldo() - monto);
                return cuentaFinancieraRepository.save(cuenta);
            } else {
                throw new IllegalArgumentException("Saldo insuficiente en la cuenta financiera con el ID: " + id);
            }
        } else {
            throw new IllegalArgumentException("Cuenta financiera no encontrada con el ID: " + id);
        }
    }

    @Override
    public void transferir(UUID idCuentaOrigen, UUID idCuentaDestino, float monto) {
        CuentaFinanciera cuentaOrigen = buscarCuentaFinancieraPorId(idCuentaOrigen);
        CuentaFinanciera cuentaDestino = buscarCuentaFinancieraPorId(idCuentaDestino);

        if (cuentaOrigen != null && cuentaDestino != null) {
            if (cuentaOrigen.getSaldo() >= monto) {
                cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
                cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);
                cuentaFinancieraRepository.save(cuentaOrigen);
                cuentaFinancieraRepository.save(cuentaDestino);
            } else {
                throw new IllegalArgumentException("Saldo insuficiente en la cuenta financiera de origen con el ID: " + idCuentaOrigen);
            }
        } else {
            throw new IllegalArgumentException("Una o ambas cuentas financieras no fueron encontradas.");
        }
    }

    @Override
    public void aplicarInteresMensual(UUID id) {
        CuentaFinanciera cuenta = buscarCuentaFinancieraPorId(id);
        if (cuenta != null) {
            float interesMensual = cuenta.getSaldo() * 0.01f; // Suponiendo un interés mensual del 1%
            cuenta.setSaldo(cuenta.getSaldo() + interesMensual);
            cuentaFinancieraRepository.save(cuenta);
        } else {
            throw new IllegalArgumentException("Cuenta financiera no encontrada con el ID: " + id);
        }
    }
}
