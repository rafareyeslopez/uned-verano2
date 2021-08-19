
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

}
