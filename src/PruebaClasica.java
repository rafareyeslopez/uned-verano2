
public class PruebaClasica extends PruebaAntigenos {

	public PruebaClasica(Paciente paciente) {
		super(paciente);
	}

	@Override
	public String toString() {
		return "PruebaClasica [isResultado()=" + isResultado() + ", isRealizada()=" + isRealizada() + ", getPaciente()="
				+ getPaciente().getDni() + ", getFecha()=" + getFecha() + ", getEnfermero()=" + getEnfermero()
				+ ", getTecnicoLaboratorio()=" + getTecnicoLaboratorio() + "]";
	}

}
