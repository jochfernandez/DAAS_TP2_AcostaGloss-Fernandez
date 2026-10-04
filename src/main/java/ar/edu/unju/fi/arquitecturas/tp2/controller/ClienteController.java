package ar.edu.unju.fi.arquitecturas.tp2.controller;

import ar.edu.unju.fi.arquitecturas.tp2.dto.ClienteRequestDto;
import ar.edu.unju.fi.arquitecturas.tp2.dto.ClienteResponseDto;
import ar.edu.unju.fi.arquitecturas.tp2.mapper.ClienteMapper;
import ar.edu.unju.fi.arquitecturas.tp2.model.Cliente;
import ar.edu.unju.fi.arquitecturas.tp2.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper; // Inyectamos el mapper automático

    @PostMapping
    public ResponseEntity<ClienteResponseDto> registrarCliente(@Valid @RequestBody ClienteRequestDto requestDto) {
        Cliente nuevoCliente = clienteMapper.toEntity(requestDto);
        Cliente clienteGuardado = clienteService.crearCliente(nuevoCliente);

        // Pasamos el mensaje personalizado al mapper
        String mensaje = "Registro exitoso. La cuenta se encuentra pendiente de activación. Por favor, verifique su correo electrónico.";
        ClienteResponseDto responseDto = clienteMapper.toDto(clienteGuardado, mensaje);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @GetMapping("/activar")
    public ResponseEntity<String> activarCuenta(@RequestParam("token") String token) {
        try {
            clienteService.activarCliente(token);
            return ResponseEntity.ok(leerPlantillaHtml("templates/activacion-exitosa.html"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(leerPlantillaHtml("templates/activacion-error.html"));
        }
    }
    private String leerPlantillaHtml(String ruta) {
        try {
            org.springframework.core.io.ClassPathResource resource = new org.springframework.core.io.ClassPathResource(ruta);
            return org.springframework.util.StreamUtils.copyToString(resource.getInputStream(), java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "Error interno al cargar la página de Nexo.";
        }
    }
}
