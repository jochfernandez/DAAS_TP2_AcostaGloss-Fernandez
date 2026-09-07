package ar.edu.unju.fi.arquitecturas.tp2.repository;

import ar.edu.unju.fi.arquitecturas.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitecturas.tp2.util.EstadoCuentaFinanciera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CuentaFinancieraRepository extends JpaRepository<CuentaFinanciera, UUID> {

    Optional<CuentaFinanciera> findByCbu(String cbu);

    //Lista todas las cuentas según su estado (ej. buscar todas las "BLOQUEADAS")
    List<CuentaFinanciera> findByEstado(EstadoCuentaFinanciera estado);
}