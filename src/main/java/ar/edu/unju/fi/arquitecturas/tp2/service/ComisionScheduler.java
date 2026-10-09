package ar.edu.unju.fi.arquitecturas.tp2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ComisionScheduler {

    private final LiquidacionService liquidacionService;

    @Scheduled(cron = "${comisiones.cron}")
    public void ejecutar() {
        log.info("Ejecutando scheduler de comisiones");
        liquidacionService.ejecutarLiquidacionMasiva();
    }
}
