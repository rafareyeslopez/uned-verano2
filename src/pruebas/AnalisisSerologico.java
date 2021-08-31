package pruebas;

import personas.Paciente;

public class AnalisisSerologico extends Prueba {

	private int valorAnticuerpos;

	public int getValorAnticuerpos() {
		return valorAnticuerpos;
	}

	public void setValorAnticuerpos(int valorAnticuerpos) {
		this.valorAnticuerpos = valorAnticuerpos;
	}

	public AnalisisSerologico(Paciente paciente) {
		super(paciente);
	}

	public boolean haPasadoInfeccon() {
		return valorAnticuerpos > 2;
	}

	@Override
	public String toString() {
		return "AnalisisSerologico [valorAnticuerpos=" + valorAnticuerpos + ", isRealizada()=" + isHecha()
				+ ", getPaciente()=" + getPaciente().getDni() + ", getFecha()=" + getFecha() + ", getEnfermero()="
				+ getEnfermero().getDni() + ", getTecnicoLaboratorio()=" + getTecnicoLaboratorio().getDni()
				+ ", getId()=" + getId() + "]";
	}

}
