package ar.edu.unju.fi.arquitecturas.tp2.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("CUENTA_CORRIENTE")
public class CuentaCorriente extends CuentaFinanciera{
    private float margenDescubiertoAutorizado;
    private float costoDeComisionDeMantenimiento;
}
