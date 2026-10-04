package ar.edu.unju.fi.arquitecturas.tp2.listener;
import ar.edu.unju.fi.arquitecturas.tp2.event.ClienteRegistradoEvent;
import ar.edu.unju.fi.arquitecturas.tp2.model.Cliente;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailNotificationListener {

    private final JavaMailSender mailSender;
    @Value("${app.base-url}")
    private String baseUrl;
    @Value("${spring.mail.username}")
    private String mailFrom;

    @Async
    @EventListener
    public void handleClienteRegistradoEvent(ClienteRegistradoEvent event) {
        Cliente cliente = event.getCliente();
        log.info("Iniciando hilo asíncrono para enviar email a: {}", cliente.getMail());

        String enlaceActivacion = baseUrl + "/api/v1/clientes/activar?token=" + cliente.getTokenActivacion();

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(mailFrom, "Nexo");
            helper.setTo(cliente.getMail());
            helper.setSubject("¡Bienvenido a NEXO, " + cliente.getNombre() + "!");

            // Leer el archivo HTML desde resources/templates
            ClassPathResource resource = new ClassPathResource("templates/email-activacion.html");
            String htmlTemplate = StreamUtils.copyToString(resource.getInputStream(), java.nio.charset.StandardCharsets.UTF_8);

            // Reemplazar las variables dinámicas
            String htmlMsg = htmlTemplate.replace("{{NOMBRE}}", cliente.getNombre())
                    .replace("{{ENLACE}}", enlaceActivacion);

            helper.setText(htmlMsg, true);

            mailSender.send(message);
            log.info("Email enviado asíncronamente con éxito a: {}", cliente.getMail());

        } catch (Exception e) {
            log.error("Error al enviar el email a {}: {}", cliente.getMail(), e.getMessage());
        }
    }
}