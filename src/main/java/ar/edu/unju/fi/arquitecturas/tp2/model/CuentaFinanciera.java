package ar.edu.unju.fi.arquitecturas.tp2.model;

import ar.edu.unju.fi.arquitecturas.tp2.util.EstadoCuentaFinanciera;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_cuenta", discriminatorType = DiscriminatorType.STRING)
public abstract class CuentaFinanciera extends EntidadBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Column(unique = true, nullable = false)
    protected String cbu;

    @Column(unique = true, nullable = false)
    protected String alias;

    protected float saldo;

    @Enumerated(EnumType.STRING)
    protected EstadoCuentaFinanciera estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titular_principal_id", nullable = false)
    protected Cliente titularPrincipal;

    @OneToMany(mappedBy = "cuenta")
    protected List<Transaccion> transacciones;
}
