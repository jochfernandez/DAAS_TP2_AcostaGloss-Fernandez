package ar.edu.unju.fi.arquitecturas.tp2.service.impl;

import ar.edu.unju.fi.arquitecturas.tp2.model.CajaAhorro;
import ar.edu.unju.fi.arquitecturas.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitecturas.tp2.model.Transaccion;
import ar.edu.unju.fi.arquitecturas.tp2.repository.TransaccionRepository;
import ar.edu.unju.fi.arquitecturas.tp2.util.EstadoDeProcesamientoDeTransaccion;
import ar.edu.unju.fi.arquitecturas.tp2.util.TipoDeTransaccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransaccionServiceImplTest {

    @Mock
    private TransaccionRepository transaccionRepository;

    @InjectMocks
    private TransaccionServiceImpl transaccionService;

    private CuentaFinanciera cuentaMock;
    private Transaccion transaccionMock;
    private UUID idCuenta;
    private UUID idTransaccion;

    @BeforeEach
    void setUp() {
        idCuenta = UUID.randomUUID();
        idTransaccion = UUID.randomUUID();

        cuentaMock = CajaAhorro.builder()
                .id(idCuenta)
                .build();

        transaccionMock = Transaccion.builder()
                .id(idTransaccion)
                .cuenta(cuentaMock)
                .monto(5000.0f)
                .tipo(TipoDeTransaccion.DEPOSITO)
                .estado(EstadoDeProcesamientoDeTransaccion.COMPLETADA)
                .build();
    }

    @Test
    void registrarTransaccion_Exitoso() {
        // Le decimos al mock que cuando guarde cualquier transacción, devuelva nuestra transaccionMock
        when(transaccionRepository.save(any(Transaccion.class))).thenReturn(transaccionMock);

        Transaccion resultado = transaccionService.registrarTransaccion(
                cuentaMock,
                5000.0f,
                TipoDeTransaccion.DEPOSITO,
                EstadoDeProcesamientoDeTransaccion.COMPLETADA
        );

        assertNotNull(resultado);
        assertEquals(5000.0f, resultado.getMonto());
        assertEquals(TipoDeTransaccion.DEPOSITO, resultado.getTipo());
        verify(transaccionRepository, times(1)).save(any(Transaccion.class));
    }

    @Test
    void listarTransaccionesPorId_RetornaLista() {
        when(transaccionRepository.findByCuentaId(idCuenta)).thenReturn(List.of(transaccionMock));

        List<Transaccion> resultado = transaccionService.listarTransaccionesPorId(idCuenta);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(idTransaccion, resultado.get(0).getId());
    }

    @Test
    void listarTransaccionesPorCuentaYTipo_RetornaListaFiltrada() {
        when(transaccionRepository.findByCuentaIdAndTipo(idCuenta, TipoDeTransaccion.DEPOSITO)).thenReturn(List.of(transaccionMock));

        List<Transaccion> resultado = transaccionService.listarTransaccionesPorCuentaYTipo(idCuenta, TipoDeTransaccion.DEPOSITO);

        assertFalse(resultado.isEmpty());
        assertEquals(TipoDeTransaccion.DEPOSITO, resultado.get(0).getTipo());
    }

    @Test
    void actualizarEstadoDeTransaccion_Exitoso() {
        // Arrange: Simulamos que la transacción original estaba PENDIENTE
        transaccionMock.setEstado(EstadoDeProcesamientoDeTransaccion.PENDIENTE);
        when(transaccionRepository.findById(idTransaccion)).thenReturn(Optional.of(transaccionMock));
        when(transaccionRepository.save(any(Transaccion.class))).thenReturn(transaccionMock);

        // Act: Cambiamos el estado a RECHAZADA
        Transaccion resultado = transaccionService.actualizarEstadoDeTransaccion(idTransaccion, EstadoDeProcesamientoDeTransaccion.RECHAZADA);

        // Assert
        assertEquals(EstadoDeProcesamientoDeTransaccion.RECHAZADA, resultado.getEstado());
        verify(transaccionRepository, times(1)).save(transaccionMock);
    }

    @Test
    void actualizarEstadoDeTransaccion_LanzaExcepcion_SiNoExiste() {
        // Arrange: Simulamos que la base de datos no encuentra el ID
        when(transaccionRepository.findById(idTransaccion)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transaccionService.actualizarEstadoDeTransaccion(idTransaccion, EstadoDeProcesamientoDeTransaccion.COMPLETADA);
        });

        assertTrue(exception.getMessage().contains("Transacción no encontrada"));
        verify(transaccionRepository, never()).save(any());
    }
}