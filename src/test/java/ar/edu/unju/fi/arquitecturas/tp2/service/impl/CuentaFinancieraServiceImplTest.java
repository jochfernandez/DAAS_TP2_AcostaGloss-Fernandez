package ar.edu.unju.fi.arquitecturas.tp2.service.impl;

import ar.edu.unju.fi.arquitecturas.tp2.model.CajaAhorro;
import ar.edu.unju.fi.arquitecturas.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitecturas.tp2.repository.CuentaFinancieraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuentaFinancieraServiceImplTest {

    @Mock
    private CuentaFinancieraRepository cuentaFinancieraRepository;

    @InjectMocks
    private CuentaFinancieraServiceImpl cuentaFinancieraService;

    private CuentaFinanciera cuentaMock;
    private UUID idCuenta;

    @BeforeEach
    void setUp() {
        idCuenta = UUID.randomUUID();
        cuentaMock = CajaAhorro.builder()
                .id(idCuenta)
                .saldo(10000.0f)
                .build();
    }
    @Test
    void depositar_ActualizaSaldoCorrectamente() {
        when(cuentaFinancieraRepository.findById(idCuenta)).thenReturn(Optional.of(cuentaMock));
        when(cuentaFinancieraRepository.save(any(CuentaFinanciera.class))).thenReturn(cuentaMock);

        CuentaFinanciera cuentaActualizada = cuentaFinancieraService.depositar(idCuenta, 5000.0f);

        assertEquals(15000.0f, cuentaActualizada.getSaldo());
        verify(cuentaFinancieraRepository, times(1)).save(cuentaMock);
    }

    @Test
    void extraer_ConSaldoSuficiente_ActualizaSaldo() {
        when(cuentaFinancieraRepository.findById(idCuenta)).thenReturn(Optional.of(cuentaMock));
        when(cuentaFinancieraRepository.save(any(CuentaFinanciera.class))).thenReturn(cuentaMock);

        CuentaFinanciera cuentaActualizada = cuentaFinancieraService.extraer(idCuenta, 3000.0f);

        assertEquals(7000.0f, cuentaActualizada.getSaldo());
    }

    @Test
    void extraer_ConSaldoInsuficiente_LanzaExcepcion() {
        when(cuentaFinancieraRepository.findById(idCuenta)).thenReturn(Optional.of(cuentaMock));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cuentaFinancieraService.extraer(idCuenta, 15000.0f);
        });

        assertTrue(exception.getMessage().contains("Saldo insuficiente"));
        verify(cuentaFinancieraRepository, never()).save(any());
    }
}