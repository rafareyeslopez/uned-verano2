package personas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pruebas.AnalisisSerologico;
import pruebas.Prueba;
import pruebas.TestPcr;
import vacunas.Vacuna;

public class Paciente extends Persona {

	private List<Prueba> pruebas;

	public Paciente(String dni, String nombre, String apellidos, int edad, String direccion, String telefono) {
		super(dni, nombre, apellidos, edad, direccion, telefono);
		pruebas = new ArrayList<Prueba>();
	}

	/**
	 * Fecha de la primera dosis
	 */
	private Calendar primeraDosis;
	/**
	 * Fecha de la segunda dosis
	 */
	private Calendar segundaDosis;
	/**
	 * Booleano para ver si tiene puesta la primera dosis
	 */
	private boolean primeraDosisPuesta;
	/**
	 * Booleano para ver si tiene puesta la segunda dosis
	 *
	 */
	private boolean segundaDosisPuesta;
	/**
	 * Que vacuna se le va a poner
	 */
	private Vacuna vacuna;
	/**
	 * Que enfermero el vacunara
	 */
	private Enfermero enfermero;
	/**
	 * Fecha cuando se ha confinado, o null sino esta confinado
	 */
	private Calendar fechaConfinamiento;

	public List<Prueba> getPruebas() {
		return pruebas;
	}

	public void setPruebas(List<Prueba> pruebas) {
		this.pruebas = pruebas;
	}

	public Calendar getPrimeraDosis() {
		return primeraDosis;
	}

	public void setPrimeraDosis(Calendar primeraDosis) {
		this.primeraDosis = primeraDosis;
	}

	public Calendar getSegundaDosis() {
		return segundaDosis;
	}

	public void setSegundaDosis(Calendar segundaDosis) {
		this.segundaDosis = segundaDosis;
	}

	public boolean isPrimeraDosisPuesta() {
		return primeraDosisPuesta;
	}

	public void setPrimeraDosisPuesta(boolean primeraDosisPuesta) {
		this.primeraDosisPuesta = primeraDosisPuesta;
	}

	public boolean isSegundaDosisPuesta() {
		return segundaDosisPuesta;
	}

	public void setSegundaDosisPuesta(boolean segundaDosisPuesta) {
		this.segundaDosisPuesta = segundaDosisPuesta;
	}

	public Vacuna getVacuna() {
		return vacuna;
	}

	public void setVacuna(Vacuna vacuna) {
		this.vacuna = vacuna;
	}

	public Enfermero getEnfermero() {
		return enfermero;
	}

	public void setEnfermero(Enfermero enfermero) {
		this.enfermero = enfermero;
	}

	public Calendar getFechaConfinamiento() {
		return fechaConfinamiento;
	}

	public void setFechaConfinamiento(Calendar fechaConfinamiento) {
		this.fechaConfinamiento = fechaConfinamiento;
	}

	/**
	 * Nos dice si un paciente tiene la vacunacion completa dependiendo del numero
	 * de dosis de la vacuna
	 */
	public boolean pautaCompleta() {
		if (vacuna != null) {
			if (vacuna.getDosis() == 1) {
				if (isPrimeraDosisPuesta()) {
					return true;
				} else {
					return false;
				}
			} else {
				if (isSegundaDosisPuesta()) {
					return true;
				} else {
					return false;
				}
			}
		}

		else {
			return false;
		}
	}

	public boolean puedeRealizarPcr(Calendar fecha) {
		// Comprobamos primero los PCR
		for (Prueba prueba : pruebas) {
			if (prueba instanceof TestPcr) {

				TestPcr otraPrueba = (TestPcr) prueba;

				// Calculamos la diferencia en dias, pasando primero a milisegudos y luego a
				// dias
				long milisegundos = fecha.getTimeInMillis() - otraPrueba.getFecha().getTimeInMillis();
				long diferenciaDias = TimeUnit.MILLISECONDS.toDays(milisegundos);

				if (diferenciaDias < 15) {
					System.out.println("No se puede realizar test PCR porque ha hecho uno hace poco: " + otraPrueba);
					return false;
				}

			}
		}
		return true;
	}

	public boolean puedeRealizarAnalisisSerologico(Calendar fecha) {

		for (Prueba prueba : pruebas) {
			if (prueba instanceof AnalisisSerologico) {

				AnalisisSerologico otraPrueba = (AnalisisSerologico) prueba;

				// Calculamos la diferencia en dias, pasando primero a milisegudos y luego a
				// dias
				long milisegundos = fecha.getTimeInMillis() - otraPrueba.getFecha().getTimeInMillis();
				long diferenciaDias = TimeUnit.MILLISECONDS.toDays(milisegundos);

				// 6 meses son 180 dias
				if (diferenciaDias < 180) {
					System.out.println(
							"No se puede realizar el analisis serologico  porque ha hecho uno hace menos de 6 meses:"
									+ otraPrueba);
					return false;
				}

			}
		}
		return true;
	}

	/**
	 * Incluye la prueba su listado
	 */
	public void establecerPrueba(Prueba prueba) {
		pruebas.add(prueba);
	}

	/**
	 * Dada una prueba la actualiza de su listado, para ello la buscamos primero y
	 * luego la actualizamos
	 */
	public void actualizarPrueba(Prueba prueba) {
		for (Prueba pruebaLista : pruebas) {
			if (pruebaLista.equals(prueba)) {
				pruebaLista = prueba;
			}
		}
	}

}
