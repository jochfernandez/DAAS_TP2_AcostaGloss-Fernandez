package ar.edu.unju.fi.arquitecturas.tp2.repository;

import ar.edu.unju.fi.arquitecturas.tp2.model.Transaccion;
import ar.edu.unju.fi.arquitecturas.tp2.util.TipoDeTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, UUID> {

    // Query Method 5: Obtiene todo el historial de transacciones de una cuenta específica
    List<Transaccion> findByCuentaId(UUID cuentaId);

    // Query Method 6: Obtiene transacciones de una cuenta filtradas por tipo (ej. ver todos los "DEPOSITOS" de la cuenta X)
    List<Transaccion> findByCuentaIdAndTipo(UUID cuentaId, TipoDeTransaccion tipo);
}