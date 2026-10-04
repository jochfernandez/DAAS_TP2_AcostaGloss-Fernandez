package ar.edu.unju.fi.arquitecturas.tp2.event;

import ar.edu.unju.fi.arquitecturas.tp2.model.Cliente;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ClienteRegistradoEvent extends ApplicationEvent {
    private final Cliente cliente;

    public ClienteRegistradoEvent(Object source, Cliente cliente) {
        super(source);
        this.cliente = cliente;
    }
}