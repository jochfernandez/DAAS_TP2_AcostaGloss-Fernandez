package ar.edu.unju.fi.arquitecturas.tp2.util;

public enum TipoDeTransaccion {
    DEPOSITO("Depósito"),
    EXTRACCION("Extracción"),
    TRANSFERENCIA_ENVIADA("Transferencia enviada"),
    TRANSFERENCIA_RECIBIDA("Transferencia recibida"),
    DEBITO("Débito"),
    CREDITO("Crédito"),
    DEBITO_COMISION("Débito Comisión");

    private final String descripcion;

    TipoDeTransaccion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
