package pruebas;

import personas.Paciente;

public class PruebaRapida extends PruebaAntigenos {

	public PruebaRapida(Paciente paciente) {
		super(paciente);
	}

	@Override
	public String toString() {
		return "PruebaRapida [isPositivo()=" + isPositivo() + ", isHecha()=" + isHecha() + ", getPaciente()="
				+ getPaciente().getDni() + ", getFecha()=" + getFecha() + ", getEnfermero()=" + getEnfermero().getDni()
				+ ", getTecnicoLaboratorio()=" + getTecnicoLaboratorio().getDni() + ", getId()=" + getId() + "]";
	}

}
