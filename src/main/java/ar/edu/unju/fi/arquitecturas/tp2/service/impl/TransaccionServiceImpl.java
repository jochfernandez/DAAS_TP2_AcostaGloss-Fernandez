package ar.edu.unju.fi.arquitecturas.tp2.service.impl;

import ar.edu.unju.fi.arquitecturas.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitecturas.tp2.model.Transaccion;
import ar.edu.unju.fi.arquitecturas.tp2.repository.TransaccionRepository;
import ar.edu.unju.fi.arquitecturas.tp2.service.TransaccionService;
import ar.edu.unju.fi.arquitecturas.tp2.util.EstadoDeProcesamientoDeTransaccion;
import ar.edu.unju.fi.arquitecturas.tp2.util.TipoDeTransaccion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {

    private final TransaccionRepository transaccionRepository;

    @Override
    public Transaccion registrarTransaccion(CuentaFinanciera cuenta, float monto, TipoDeTransaccion tipoDeTransaccion, EstadoDeProcesamientoDeTransaccion estadoDeProcesamientoDeTransaccion) {
        log.info("Registrando transacción: {} por monto {} en cuenta ID: {}", tipoDeTransaccion, monto, cuenta.getId());

        Transaccion transaccion = Transaccion.builder()
                .cuenta(cuenta)
                .monto(monto)
                .tipo(tipoDeTransaccion)
                .estado(estadoDeProcesamientoDeTransaccion)
                .build();

        return transaccionRepository.save(transaccion);
    }

    @Override
    public List<Transaccion> listarTransaccionesPorId(UUID idCuenta) {
        log.debug("Listando todas las transacciones para la cuenta ID: {}", idCuenta);
        return transaccionRepository.findByCuentaId(idCuenta);
    }

    @Override
    public List<Transaccion> listarTransaccionesPorCuentaYTipo(UUID idCuenta, TipoDeTransaccion tipoDeTransaccion) {
        log.debug("Listando transacciones filtradas por tipo {} para la cuenta ID: {}", tipoDeTransaccion, idCuenta);
        return transaccionRepository.findByCuentaIdAndTipo(idCuenta, tipoDeTransaccion);
    }

    @Override
    public Transaccion actualizarEstadoDeTransaccion(UUID idTransaccion, EstadoDeProcesamientoDeTransaccion nuevoEstado) {
        log.info("Actualizando estado de la transacción ID: {} a {}", idTransaccion, nuevoEstado);

        Transaccion transaccion = transaccionRepository.findById(idTransaccion)
                .orElseThrow(() -> {
                    log.error("Fallo al actualizar: Transacción no encontrada con ID: {}", idTransaccion);
                    return new IllegalArgumentException("Transacción no encontrada con el ID: " + idTransaccion);
                });

        transaccion.setEstado(nuevoEstado);
        return transaccionRepository.save(transaccion);
    }
}