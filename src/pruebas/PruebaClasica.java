package pruebas;

import personas.Paciente;

public class PruebaClasica extends PruebaAntigenos {

	public PruebaClasica(Paciente paciente) {
		super(paciente);
	}

	@Override
	public String toString() {
		return "PruebaClasica [isPositivo()=" + isPositivo() + ", isHecha()=" + isHecha() + ", getPaciente()="
				+ getPaciente().getDni() + ", getFecha()=" + getFecha() + ", getEnfermero()=" + getEnfermero().getDni()
				+ ", getTecnicoLaboratorio()=" + getTecnicoLaboratorio().getDni() + ", getId()=" + getId() + "]";
	}

}
