package pruebas;

import java.util.Calendar;

import personas.Enfermero;
import personas.Paciente;
import personas.Tecnico;

public abstract class Prueba {
	private static int contador = 0;
	private int id;
	private Calendar fecha;
	private Paciente paciente;
	private Enfermero enfermero;
	private Tecnico tecnicoLaboratorio;
	private boolean hecha;

	public Prueba(Paciente paciente) {
		contador++;
		id = contador;
		this.setPaciente(paciente);
		hecha = false;

	}

	public boolean isHecha() {
		return hecha;
	}

	public void setHecha(boolean hecha) {
		this.hecha = hecha;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Calendar getFecha() {
		return fecha;
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}

	public Enfermero getEnfermero() {
		return enfermero;
	}

	public void setEnfermero(Enfermero enfermero) {
		this.enfermero = enfermero;
	}

	public Tecnico getTecnicoLaboratorio() {
		return tecnicoLaboratorio;
	}

	public void setTecnicoLaboratorio(Tecnico tecnicoLaboratorio) {
		this.tecnicoLaboratorio = tecnicoLaboratorio;
	}

	public int getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Prueba))
			return false;
		Prueba other = (Prueba) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
