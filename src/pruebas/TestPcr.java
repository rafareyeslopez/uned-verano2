package pruebas;
import personas.Paciente;

public class TestPcr extends PruebaDiagnostica {

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
		return "TestPcr [positivo=" + positivo + ", isRealizada()=" + isRealizada() + ", getPaciente()=" + getPaciente()
				+ ", getFecha()=" + getFecha() + "]";
	}

}
