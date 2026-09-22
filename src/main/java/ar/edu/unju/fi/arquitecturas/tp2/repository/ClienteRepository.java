package ar.edu.unju.fi.arquitecturas.tp2.repository;

import ar.edu.unju.fi.arquitecturas.tp2.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    /**
     * Busca un cliente por su cuil
     * @param cuil
     * @return
     */
    Optional<Cliente> findByCuil(String cuil);

    /**
     * Busca un cliente por su mail
     * @param mail
     * @return
     */
    Optional<Cliente> findByMail(String mail);

    /**
     * Busca un cliente por su Cuil y su Correo
     * @param cuil
     * @param mail
     * @return
     */
    Optional<Cliente> findByCuilAndMail(String cuil, String mail);

    /**
     * Devuelve verdadero si exite un cliente por su cuil o su correo
     * @param cuil
     * @param mail
     * @return
     */
    boolean  existsByCuilOrMail(String cuil, String mail);


}