package ar.edu.unju.fi.arquitecturas.tp2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cliente {
    private UUID id;
    private String nombre;
    private String cuil;
    private String mail;
    private String telefono;
    private String direccion;
    private List<CuentaFinanciera> cuentas;
    private boolean esTitular;
    private List<Cliente> cotitulares;

    public Cliente( String nombre, String cuil, String mail, String telefono, String direccion) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.cuil = cuil;
        this.mail = mail;
        this.telefono = telefono;
        this.direccion = direccion;
        this.cuentas = new ArrayList<>();
        this.esTitular = true;
        this.cotitulares = new ArrayList<>();
    }
}
