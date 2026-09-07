package ar.edu.unju.fi.arquitecturas.tp2.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass // Hereda estas columnas a las clases hijas
@EntityListeners(AuditingEntityListener.class) // Activa el "escucha" que llena las fechas
public abstract class EntidadBase {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime ultimaModificacion;
}