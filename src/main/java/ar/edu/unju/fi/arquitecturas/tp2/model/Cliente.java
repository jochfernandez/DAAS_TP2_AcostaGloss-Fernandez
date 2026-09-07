package ar.edu.unju.fi.arquitecturas.tp2.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
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
    @OneToMany(mappedBy = "titularPrincipal", cascade = CascadeType.ALL)
    private List<CuentaFinanciera> cuentas = new ArrayList<>();
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
