package ar.edu.unju.fi.arquitecturas.tp2.model;

import ar.edu.unju.fi.arquitecturas.tp2.util.EstadoDeProcesamientoDeTransaccion;
import ar.edu.unju.fi.arquitecturas.tp2.util.TipoDeTransaccion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Transaccion extends EntidadBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private LocalDateTime fechaHora;
    @Column(nullable = false)
    private float monto;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDeTransaccion tipo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoDeProcesamientoDeTransaccion estado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private CuentaFinanciera cuenta;
}
