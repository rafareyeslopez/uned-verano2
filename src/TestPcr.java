
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
		return "TestPcr:\npositivo=" + positivo + "\nisPositivo()=" + isPositivo() + "\nisRealizada()=" + isRealizada()
				+ "\ngetPaciente()=" + getPaciente() + "\ngetFecha()=" + getFecha() + "\ngetEnfermero()="
				+ getEnfermero() + "\ngetTecnicoLaboratorio()=" + getTecnicoLaboratorio() + "\ngetId()=" + getId();
	}

}
