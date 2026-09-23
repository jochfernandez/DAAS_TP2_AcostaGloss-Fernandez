package ar.edu.unju.fi.arquitecturas.tp2.service.impl;

import ar.edu.unju.fi.arquitecturas.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitecturas.tp2.model.Transaccion;
import ar.edu.unju.fi.arquitecturas.tp2.repository.CuentaFinancieraRepository;
import ar.edu.unju.fi.arquitecturas.tp2.repository.TransaccionRepository;
import ar.edu.unju.fi.arquitecturas.tp2.service.TransaccionService;
import ar.edu.unju.fi.arquitecturas.tp2.util.EstadoDeProcesamientoDeTransaccion;
import ar.edu.unju.fi.arquitecturas.tp2.util.TipoDeTransaccion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {
    private final TransaccionRepository transaccionRepository;
    @Override
    public Transaccion registrarTransaccion(CuentaFinanciera cuenta, float monto, TipoDeTransaccion tipoDeTransaccion, EstadoDeProcesamientoDeTransaccion estadoDeProcesamientoDeTransaccion) {
        Transaccion transaccion = new Transaccion();
        transaccion.setCuenta(cuenta);
        transaccion.setMonto(monto);
        transaccion.setTipo(tipoDeTransaccion);
        transaccion.setEstado(estadoDeProcesamientoDeTransaccion);
        return transaccionRepository.save(transaccion);
    }

    @Override
    public List<Transaccion> listarTransaccionesPorId(UUID idCuenta) {
       /* if(!transaccionRepository.existsByCuentaId(idCuenta)) {
            throw new IllegalArgumentException("No se encontraron transacciones para la cuenta con ID: " + idCuenta);
        } */
        return transaccionRepository.findByCuentaId(idCuenta);
    }

    @Override
    public List<Transaccion> listarTransaccionesPorCuentaYTipo(UUID idCuenta, TipoDeTransaccion tipoDeTransaccion) {
        /*if(!transaccionRepository.existsByCuentaIdAndTipo(idCuenta, tipoDeTransaccion)) {
            throw new IllegalArgumentException("No se encontraron transacciones para la cuenta con ID: " + idCuenta + " y tipo de transacción: " + tipoDeTransaccion);
        }*/
        return transaccionRepository.findByCuentaIdAndTipo(idCuenta, tipoDeTransaccion);
    }

    @Override
    public Transaccion actualizarEstadoDeTransaccion(UUID idTransaccion, EstadoDeProcesamientoDeTransaccion nuevoEstado) {
        Transaccion transaccion = transaccionRepository.findById(idTransaccion)
                .orElseThrow(() -> new IllegalArgumentException("Transacción no encontrada con el ID: " + idTransaccion));
        transaccion.setEstado(nuevoEstado);
        return transaccionRepository.save(transaccion);
    }
}
