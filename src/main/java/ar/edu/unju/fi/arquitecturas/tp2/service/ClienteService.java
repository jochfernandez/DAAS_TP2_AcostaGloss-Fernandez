package ar.edu.unju.fi.arquitecturas.tp2.service;

import ar.edu.unju.fi.arquitecturas.tp2.model.Cliente;

import java.util.UUID;

/**
 *  Contrato de servicio para la gestión del dominio de Clientes.
 */
public interface ClienteService {
    Cliente crearCliente(Cliente cliente);
    Cliente buscarPorId(UUID id);
    Cliente eliminarCliente(UUID id);
    Cliente actualizarCliente(UUID id, Cliente cliente);
    boolean vincularCotitular(UUID idCliente, UUID idCotitular);
}
