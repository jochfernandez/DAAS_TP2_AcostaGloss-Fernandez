package ar.edu.unju.fi.arquitecturas.tp2.service.impl;

import ar.edu.unju.fi.arquitecturas.tp2.model.Cliente;
import ar.edu.unju.fi.arquitecturas.tp2.repository.ClienteRepository;
import ar.edu.unju.fi.arquitecturas.tp2.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente crearCliente(Cliente cliente) {
        if(cliente.getId() != null && clienteRepository.existsById(cliente.getId())) {
            log.warn("Intento de creación fallido: Ya existe un cliente registrado con el mismo ID: {}", cliente.getId());
            throw new IllegalArgumentException("Ya existe un cliente registrado con el mismo ID.");
        }
        log.info("Creando nuevo cliente con CUIL: {}", cliente.getCuil());
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente buscarPorId(UUID id) {
        return clienteRepository.findById(id).orElseThrow(() -> {
            log.error("Búsqueda fallida: Cliente no encontrado con ID: {}", id);
            return new IllegalArgumentException("Cliente no encontrado con el ID: " + id);
        });
    }

    @Override
    public Cliente eliminarCliente(UUID id) {
        Cliente cliente = buscarPorId(id);
        log.info("Eliminando cliente con ID: {}", id);
        clienteRepository.delete(cliente);
        return cliente;
    }

    @Override
    public Cliente actualizarCliente(UUID id, Cliente cliente) {
        Cliente clienteExistente = buscarPorId(id);

        if(clienteRepository.existsByCuilOrMail(cliente.getCuil(), cliente.getMail()) &&
                (!clienteExistente.getCuil().equals(cliente.getCuil()) || !clienteExistente.getMail().equals(cliente.getMail()))) {
            log.warn("Conflicto de actualización: Ya existe otro cliente registrado con el CUIL {} o Email {}", cliente.getCuil(), cliente.getMail());
            throw new IllegalArgumentException("Ya existe un cliente registrado con el mismo CUIL o Email.");
        }

        log.info("Actualizando datos del cliente ID: {}", id);
        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setCuil(cliente.getCuil());
        clienteExistente.setMail(cliente.getMail());
        return clienteRepository.save(clienteExistente);
    }

    @Override
    public boolean vincularCotitular(UUID idCliente, UUID idCotitular) {
        Cliente cliente = buscarPorId(idCliente);
        Cliente cotitular = buscarPorId(idCotitular);

        if(cliente.getId().equals(cotitular.getId())) {
            log.warn("Operación rechazada: Intento de vincular al cliente ID: {} consigo mismo", idCliente);
            throw new IllegalArgumentException("No se puede vincular un cliente como cotitular de sí mismo.");
        }

        log.info("Vinculando cotitular ID: {} al cliente principal ID: {}", idCotitular, idCliente);
        cliente.getCotitulares().add(cotitular);
        clienteRepository.save(cliente);
        return true;
    }
}