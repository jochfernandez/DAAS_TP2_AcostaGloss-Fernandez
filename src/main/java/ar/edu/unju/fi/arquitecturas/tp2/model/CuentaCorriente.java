package ar.edu.unju.fi.arquitecturas.tp2.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity
@DiscriminatorValue("CUENTA_CORRIENTE")
public class CuentaCorriente extends CuentaFinanciera{
    private float margenDescubiertoAutorizado;
    private float costoDeComisionDeMantenimiento;
}
