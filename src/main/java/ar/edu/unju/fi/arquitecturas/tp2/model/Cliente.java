package ar.edu.unju.fi.arquitecturas.tp2.model;

import ar.edu.unju.fi.arquitecturas.tp2.util.EstadoCliente;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity
@Table(name="clientes")
public class Cliente extends EntidadBase{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String nombre;
    @Column(unique = true, nullable = false)
    private String cuil;
    @Column(unique = true, nullable = false)
    private String mail;
    @Column(nullable = false)
    private String telefono;
    @Column(nullable = false)
    private String direccion;
    @Column(nullable = false)
    @Builder.Default
    private Boolean esTitular = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titular_asociado_id")
    private Cliente titularAsociado;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EstadoCliente estado = EstadoCliente.PENDIENTE_ACTIVACION;
    @Column(name = "token_activacion", unique = true)
    private String tokenActivacion;

    @Column(name = "fecha_expiracion_token")
    private LocalDateTime fechaExpiracionToken;
    @Builder.Default
    @OneToMany(mappedBy = "titularAsociado", cascade = CascadeType.ALL)
    private List<Cliente> adherentes = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "titularPrincipal", cascade = CascadeType.ALL)
    private List<CuentaFinanciera> cuentas = new ArrayList<>();
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cliente_cotitular",
            joinColumns = @JoinColumn(name = "cliente_principal_id"),
            inverseJoinColumns = @JoinColumn(name = "cotitular_id")
    )
    private List<Cliente> cotitulares = new ArrayList<>();

    public Cliente( String nombre, String cuil, String mail, String telefono, String direccion) {
        this.nombre = nombre;
        this.cuil = cuil;
        this.mail = mail;
        this.telefono = telefono;
        this.direccion = direccion;
    }

}
