package pruebas;
import personas.Paciente;

public abstract class PruebaAntigenos extends Prueba {

	private boolean resultado;

	public PruebaAntigenos(Paciente paciente) {
		super(paciente);
	}

	public boolean isResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}

	@Override
	public String toString() {
		return "PruebaAntigenos [resultado=" + resultado + ", isRealizada()=" + isHecha() + ", getPaciente()="
				+ getPaciente() + ", getFecha()=" + getFecha() + ", getEnfermero()=" + getEnfermero()
				+ ", getTecnicoLaboratorio()=" + getTecnicoLaboratorio() + ", getId()=" + getId() + "]";
	}

}
