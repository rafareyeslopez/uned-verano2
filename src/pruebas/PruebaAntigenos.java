package pruebas;

import personas.Paciente;

public abstract class PruebaAntigenos extends Prueba {

	private boolean positivo;

	public PruebaAntigenos(Paciente paciente) {
		super(paciente);
	}

	public boolean isPositivo() {
		return positivo;
	}

	public void setPositivo(boolean positivo) {
		this.positivo = positivo;
	}

}
