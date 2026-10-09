package ar.edu.unju.fi.arquitecturas.tp2.service.impl;

import ar.edu.unju.fi.arquitecturas.tp2.model.CajaAhorro;
import ar.edu.unju.fi.arquitecturas.tp2.model.CuentaCorriente;
import ar.edu.unju.fi.arquitecturas.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitecturas.tp2.repository.CuentaFinancieraRepository;
import ar.edu.unju.fi.arquitecturas.tp2.service.LiquidacionService;
import ar.edu.unju.fi.arquitecturas.tp2.service.TransaccionService;
import ar.edu.unju.fi.arquitecturas.tp2.util.EstadoCuentaFinanciera;
import ar.edu.unju.fi.arquitecturas.tp2.util.EstadoDeProcesamientoDeTransaccion;
import ar.edu.unju.fi.arquitecturas.tp2.util.TipoDeTransaccion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiquidacionServiceImpl implements LiquidacionService {

    private final CuentaFinancieraRepository cuentaFinancieraRepository;
    private final TransaccionService transaccionService;

    @Value("${comisiones.montoCA}")
    private float montoCA;

    @Value("${comisiones.montoCC}")
    private float montoCC;

    @Override
    public void ejecutarLiquidacionMasiva() {
        log.info("Iniciando liquidación masiva de comisiones");
        List<CuentaFinanciera> cuentas = cuentaFinancieraRepository.findByEstado(EstadoCuentaFinanciera.ACTIVA);

        for (CuentaFinanciera cuenta : cuentas) {
            try {
                float monto = 0;
                if (cuenta instanceof CajaAhorro) {
                    monto = montoCA;
                } else if (cuenta instanceof CuentaCorriente) {
                    monto = montoCC;
                }

                if (cuenta.getSaldo() < monto && cuenta instanceof CajaAhorro) {
                    throw new ar.edu.unju.fi.arquitecturas.tp2.exception.SaldoInsuficienteException("Saldo insuficiente para debitar comisión");
                } else if (cuenta instanceof CuentaCorriente) {
                    CuentaCorriente cc = (CuentaCorriente) cuenta;
                    if (cuenta.getSaldo() + cc.getMargenDescubiertoAutorizado() < monto) {
                         throw new ar.edu.unju.fi.arquitecturas.tp2.exception.SaldoInsuficienteException("Saldo insuficiente (incluyendo descubierto) para debitar comisión");
                    }
                }

                cuenta.setSaldo(cuenta.getSaldo() - monto);
                cuentaFinancieraRepository.save(cuenta);
                transaccionService.registrarTransaccion(cuenta, monto, TipoDeTransaccion.DEBITO_COMISION, EstadoDeProcesamientoDeTransaccion.APROBADO);
                log.info("Comisión de {} debitada exitosamente de la cuenta ID: {}", monto, cuenta.getId());
            } catch (Exception e) {
                log.error("Fallo cobro en cuenta ID: " + cuenta.getId(), e);
            }
        }
        log.info("Finalizada liquidación masiva de comisiones");
    }
}
