package ar.edu.unju.fi.arquitecturas.tp2.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("CAJA_AHORRO")
public class CajaAhorro extends CuentaFinanciera{
    private float tasaInteresAnual;
    private int cupoLimite;

    public float calcularInteresMensual() {
        return (this.getSaldo() * (tasaInteresAnual / 12)) / 100;
    }
}
