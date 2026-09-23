package ar.edu.unju.fi.arquitecturas.tp2.service;

import ar.edu.unju.fi.arquitecturas.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitecturas.tp2.model.Transaccion;
import ar.edu.unju.fi.arquitecturas.tp2.util.EstadoDeProcesamientoDeTransaccion;
import ar.edu.unju.fi.arquitecturas.tp2.util.TipoDeTransaccion;

import java.util.List;
import java.util.UUID;

public interface TransaccionService {
    Transaccion registrarTransaccion(CuentaFinanciera cuenta, float monto, TipoDeTransaccion tipoDeTransaccion, EstadoDeProcesamientoDeTransaccion estadoDeProcesamientoDeTransaccion);
    List<Transaccion> listarTransaccionesPorId(UUID idCuenta);
    List<Transaccion> listarTransaccionesPorCuentaYTipo(UUID idCuenta, TipoDeTransaccion tipoDeTransaccion);
    Transaccion actualizarEstadoDeTransaccion(UUID idTransaccion, EstadoDeProcesamientoDeTransaccion nuevoEstado);

}
