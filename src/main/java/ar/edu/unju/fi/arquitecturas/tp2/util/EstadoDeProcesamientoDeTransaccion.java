package ar.edu.unju.fi.arquitecturas.tp2.util;

public enum EstadoDeProcesamientoDeTransaccion {
    PENDIENTE("Pendiente"),
    COMPLETADA("Completada"),
    RECHAZADA("Rechazada"),
    REVERTIDA("Revertida");

    private final String descripcion;

    EstadoDeProcesamientoDeTransaccion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
