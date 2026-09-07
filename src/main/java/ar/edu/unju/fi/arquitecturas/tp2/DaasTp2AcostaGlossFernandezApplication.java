package ar.edu.unju.fi.arquitecturas.tp2;

import ar.edu.unju.fi.arquitecturas.tp2.model.Cliente;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DaasTp2AcostaGlossFernandezApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaasTp2AcostaGlossFernandezApplication.class, args);
    }

}
