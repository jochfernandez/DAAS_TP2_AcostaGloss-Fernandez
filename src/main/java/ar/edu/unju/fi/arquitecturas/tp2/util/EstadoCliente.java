package ar.edu.unju.fi.arquitecturas.tp2.util;

public enum EstadoCliente {
    PENDIENTE_ACTIVACION("Pendiente de Activación"),
    ACTIVO("Activo"),
    INACTIVO("Inactivo");
    private final String descripcion;
    EstadoCliente(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }
}
