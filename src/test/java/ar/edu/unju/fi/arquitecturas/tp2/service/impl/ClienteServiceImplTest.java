package ar.edu.unju.fi.arquitecturas.tp2.service.impl;

import ar.edu.unju.fi.arquitecturas.tp2.model.Cliente;
import ar.edu.unju.fi.arquitecturas.tp2.repository.ClienteRepository;
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
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente clienteMock;
    private UUID idCliente;

    @BeforeEach
    void setUp() {
        idCliente = UUID.randomUUID();

        clienteMock = Cliente.builder()
                .id(idCliente)
                .nombre("Ana Perez")
                .cuil("27-30111222-3")
                .mail("ana@example.com")
                .telefono("388-4551001")
                .direccion("Av. Belgrano 125")
                .build();
    }

    @Test
    void crearCliente_Exitoso() {
        when(clienteRepository.existsById(idCliente)).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMock);

        // Llamamos al métodoo real del servicio
        Cliente clienteCreado = clienteService.crearCliente(clienteMock);

        // Verificamos los resultados
        assertNotNull(clienteCreado);
        assertEquals("Ana Perez", clienteCreado.getNombre());
        verify(clienteRepository, times(1)).save(clienteMock);
    }

    @Test
    void crearCliente_LanzaExcepcion_SiExisteDuplicado() {
        // Simulamos que ya existe un cliente con ese ID
        when(clienteRepository.existsById(idCliente)).thenReturn(true);

        // Verificamos que lance la excepción exacta
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.crearCliente(clienteMock);
        });

        assertEquals("Ya existe un cliente registrado con el mismo ID.", exception.getMessage());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void buscarPorId_Exitoso() {
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(clienteMock));

        Cliente clienteEncontrado = clienteService.buscarPorId(idCliente);

        assertNotNull(clienteEncontrado);
        assertEquals(idCliente, clienteEncontrado.getId());
    }
}