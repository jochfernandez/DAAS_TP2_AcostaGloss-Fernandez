package ar.edu.unju.fi.arquitecturas.tp2.repository;

import ar.edu.unju.fi.arquitecturas.tp2.model.Transaccion;
import ar.edu.unju.fi.arquitecturas.tp2.util.TipoDeTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, UUID> {

    List<Transaccion> findByCuentaId(UUID cuentaId);

    // Query Method 6: Obtiene transacciones de una cuenta filtradas por tipo (ej. ver todos los "DEPOSITOS" de la cuenta X)
    List<Transaccion> findByCuentaIdAndTipo(UUID cuentaId, TipoDeTransaccion tipo);

    @Query("SELECT COALESCE(SUM(t.monto), 0.0) FROM Transaccion t WHERE t.cuenta.titularPrincipal.id = :clienteId AND t.tipo = :tipo AND t.fechaHora >= :startOfDay AND t.fechaHora <= :endOfDay")
    float sumMontoByClienteAndTipoAndFecha(
            @Param("clienteId") UUID clienteId,
            @Param("tipo") TipoDeTransaccion tipo,
            @Param("startOfDay") java.time.LocalDateTime startOfDay,
            @Param("endOfDay") java.time.LocalDateTime endOfDay
    );
}