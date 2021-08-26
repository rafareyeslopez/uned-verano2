package pruebas;
import personas.Paciente;

public class PruebaRapida extends PruebaAntigenos {

	public PruebaRapida(Paciente paciente) {
		super(paciente);
	}

	@Override
	public String toString() {
		return "PruebaRapida [isRealizada()=" + isRealizada() + ", getPaciente()=" + getPaciente() + ", getFecha()="
				+ getFecha() + ", getEnfermero()=" + getEnfermero() + ", getTecnicoLaboratorio()="
				+ getTecnicoLaboratorio() + "]";
	}

}