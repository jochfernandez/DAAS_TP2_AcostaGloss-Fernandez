package ar.edu.unju.fi.arquitecturas.tp2.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 Esta es la clase CajaAhorro
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity
@DiscriminatorValue("CAJA_AHORRO")
public class CajaAhorro extends CuentaFinanciera{
    private float tasaInteresAnual;
    private int cupoLimite;

    public float calcularInteresMensual() {
        return (this.getSaldo() * (tasaInteresAnual / 12)) / 100;
    }
}
