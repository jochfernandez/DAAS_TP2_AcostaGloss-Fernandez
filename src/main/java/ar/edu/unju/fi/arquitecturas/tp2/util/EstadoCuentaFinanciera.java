package ar.edu.unju.fi.arquitecturas.tp2.util;

public enum EstadoCuentaFinanciera {
    ACTIVA("Activa"),
    SUSPENDIDA("Suspendida"),
    BLOQUEADA("Bloqueada");

    private final String descripcion;

    EstadoCuentaFinanciera(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
