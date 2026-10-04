package ar.edu.unju.fi.arquitecturas.tp2.service.impl;

import ar.edu.unju.fi.arquitecturas.tp2.exception.OperacionNoPermitidaException;
import ar.edu.unju.fi.arquitecturas.tp2.exception.RecursoNoEncontradoException;
import ar.edu.unju.fi.arquitecturas.tp2.model.Cliente;
import ar.edu.unju.fi.arquitecturas.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitecturas.tp2.model.Transaccion;
import ar.edu.unju.fi.arquitecturas.tp2.repository.ClienteRepository;
import ar.edu.unju.fi.arquitecturas.tp2.repository.TransaccionRepository;
import ar.edu.unju.fi.arquitecturas.tp2.service.TransaccionService;
import ar.edu.unju.fi.arquitecturas.tp2.util.EstadoDeProcesamientoDeTransaccion;
import ar.edu.unju.fi.arquitecturas.tp2.util.TipoDeTransaccion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public Transaccion registrarTransaccion(CuentaFinanciera cuenta, float monto, TipoDeTransaccion tipoDeTransaccion, EstadoDeProcesamientoDeTransaccion estadoDeProcesamientoDeTransaccion) {
        log.info("Registrando transacción: {} por monto {} en cuenta ID: {}", tipoDeTransaccion, monto, cuenta.getId());

        Transaccion transaccion = Transaccion.builder()
                .cuenta(cuenta)
                .monto(monto)
                .tipo(tipoDeTransaccion)
                .estado(estadoDeProcesamientoDeTransaccion)
                .fechaHora(LocalDateTime.now())
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
                    return new RecursoNoEncontradoException("Transacción no encontrada con el ID: " + idTransaccion);
                });

        transaccion.setEstado(nuevoEstado);
        return transaccionRepository.save(transaccion);
    }

    // TODO: Investigar si se puede hacer de forma diferente la condicion de si es adherente con un patron de diseno o algo similar, porque parece hardcodeado y no me gusta. Tal vez un patron de estrategia o algo asi.
    @Override
    public void validarPermisoOperacion(UUID idCliente, TipoDeTransaccion tipoDeTransaccion) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + idCliente));

        // Si el cliente es Adherente y la operación NO es una extracción, se bloquea.
        if (Boolean.FALSE.equals(cliente.getEsTitular()) && tipoDeTransaccion != TipoDeTransaccion.EXTRACCION) {
            log.warn("Operación bloqueada: El adherente ID {} intentó ejecutar una operación de tipo {}", idCliente, tipoDeTransaccion);
            throw new OperacionNoPermitidaException("Los adherentes tienen restringida la operatividad exclusivamente a operaciones de Extracción.");
        }
        log.info("Validación superada: El cliente ID {} tiene permisos para la operación {}", idCliente, tipoDeTransaccion);
    }
}