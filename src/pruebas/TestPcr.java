package pruebas;

import personas.Paciente;

public class TestPcr extends Prueba {

	private boolean positivo;

	public boolean isPositivo() {
		return positivo;
	}

	public void setPositivo(boolean positivo) {
		this.positivo = positivo;
	}

	public TestPcr(Paciente paciente) {
		super(paciente);
	}

	@Override
	public String toString() {
		return "TestPcr [positivo=" + positivo + ", isHecha()=" + isHecha() + ", getPaciente()="
				+ getPaciente().getDni() + ", getFecha()=" + getFecha() + ", getEnfermero()=" + getEnfermero().getDni()
				+ ", getTecnicoLaboratorio()=" + getTecnicoLaboratorio().getDni() + ", getId()=" + getId() + "]";
	}

}
