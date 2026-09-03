package ar.edu.unju.fi.arquitecturas.tp2.model;

import java.util.List;
import java.util.UUID;

public abstract class CuentaFinanciera {
    protected UUID id;
    protected String cbu;
    protected String alias;
    protected float saldo;
    protected String estado;
    protected Cliente cliente;
    protected List<Transaccion> transacciones;
}
