package pruebas;
import personas.Paciente;

public abstract class PruebaAntigenos extends PruebaDiagnostica {

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
		return "PruebaAntigenos [resultado=" + resultado + ", isRealizada()=" + isRealizada() + ", getPaciente()="
				+ getPaciente() + ", getFecha()=" + getFecha() + ", getEnfermero()=" + getEnfermero()
				+ ", getTecnicoLaboratorio()=" + getTecnicoLaboratorio() + ", getId()=" + getId() + "]";
	}

}
