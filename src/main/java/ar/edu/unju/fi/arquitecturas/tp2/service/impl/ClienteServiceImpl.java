package ar.edu.unju.fi.arquitecturas.tp2.service.impl;

import ar.edu.unju.fi.arquitecturas.tp2.model.Cliente;
import ar.edu.unju.fi.arquitecturas.tp2.repository.ClienteRepository;
import ar.edu.unju.fi.arquitecturas.tp2.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;

    @Override
    public Cliente crearCliente(Cliente cliente) {
        if(clienteRepository.existsByCuilOrMail(cliente.getCuil(), cliente.getMail())) {
            throw new IllegalArgumentException("Ya existe un cliente registrado con el mismo CUIL o Email.");
        }
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente buscarPorId(UUID id) {
        return clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con el ID: " + id));
    }

    // reemplazar el existByCuilOrMail por existsById para verificar si el cliente existe antes de eliminarlo
    @Override
    public Cliente eliminarCliente(UUID id) {
        Cliente cliente = buscarPorId(id);
        if(clienteRepository.existsByCuilOrMail(cliente.getCuil(), cliente.getMail())) {
            clienteRepository.delete(cliente);
            return cliente;
        } else {
            throw new IllegalArgumentException("Cliente no encontrado con el ID: " + id);
        }
    }

    // Aplicar el patron builder para actualizar el cliente, de esta manera se puede actualizar solo los campos que se deseen y no todos los campos del cliente
    @Override
    public Cliente actualizarCliente(UUID id, Cliente cliente) {
        Cliente clienteExistente = buscarPorId(id);
        if(clienteRepository.existsByCuilOrMail(cliente.getCuil(), cliente.getMail()) &&
           (!clienteExistente.getCuil().equals(cliente.getCuil()) || !clienteExistente.getMail().equals(cliente.getMail()))) {
            throw new IllegalArgumentException("Ya existe un cliente registrado con el mismo CUIL o Email.");
        }
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
            throw new IllegalArgumentException("No se puede vincular un cliente como cotitular de sí mismo.");
        }

        cliente.getCotitulares().add(cotitular);
        // Guardar el cliente actualizado con el cotitular vinculado
        clienteRepository.save(cliente);
        return true;
    }
}
