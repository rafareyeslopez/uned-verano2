package personas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import pruebas.Prueba;

public class Enfermero extends Empleado {

	/**
	 * Lista de las pruebas a hacer por el enfermero
	 */
	private List<Prueba> pruebasEnfermero;
	/**
	 * Lista de vacunas a poner por el enfermero
	 */
	private List<Paciente> vacunasEnfermero;

	public Enfermero(String dni, String nombre, String apellidos, int edad, String direccion, String telefono) {
		super(dni, nombre, apellidos, edad, direccion, telefono);
		pruebasEnfermero = new ArrayList<Prueba>();
		vacunasEnfermero = new ArrayList<Paciente>();
	}

	/**
	 * Muesta el menu del enfermero
	 */
	public void menuEnfermero() {
		int opcion = 0;
		do {
			try {
				System.out.println("HOLA ENFERMERO");
				System.out.println("*******************");
				System.out.println("0. Salir");
				System.out.println("1. Ver pacientes");
				System.out.println("2. Hacer prueba");
				System.out.println("3. Vacunar");

				Scanner scanner = new Scanner(System.in);

				opcion = Integer.parseInt(scanner.nextLine());

				switch (opcion) {
				case 0:

					System.out.println("Sesion enfermero terminada");

					break;

				case 1:
					misPacientes();

					break;
				case 2:
					hacerPrueba();
					break;
				case 3:

					vacunar();

					break;
				default:

					System.out.println("Error");
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (opcion != 0);

	}

	/**
	 * Vacuna a un paciente
	 */
	private void vacunar() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Paciente para vacunar?");
		String dni = scanner.nextLine();
		Paciente paciente = getVacuna(dni);
		System.out.println(paciente);
		if (paciente.getVacuna() == null) {
			System.out.println("Vacunacion no valida");
		} else {
			if (paciente.getVacuna().getDosis() == 1) {
				System.out.println("Administrada unica dosis");
				paciente.setPrimeraDosisPuesta(true);
				if (paciente.getVacuna().getDosis() == 1) {
					vacunasEnfermero.remove(paciente);
				}
			} else {
				if (!paciente.isPrimeraDosisPuesta()) {
					System.out.println("Administrada la primera dosis");
					paciente.setPrimeraDosisPuesta(true);
					if (paciente.getVacuna().getDosis() == 1) {
						vacunasEnfermero.remove(paciente);
					}

				} else {
					System.out.println("Administrada la segunda dosis");
					paciente.setSegundaDosisPuesta(true);
					vacunasEnfermero.remove(paciente);
				}

			}

			Persona.actualizarPersona(paciente);
		}
	}

	/**
	 * El enfermero hace una prueba
	 */
	private void hacerPrueba() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduzca la prueba a realizar");
		int id = Integer.parseInt(scanner.nextLine());

		Prueba prueba = getPrueba(id);

		Paciente paciente = (Paciente) Persona.getPersona(prueba.getPaciente().getDni());

		paciente.actualizarPrueba(prueba);

		pruebaRealizada(prueba);

		Persona.actualizarPersona(paciente);

	}

	/**
	 * Visualiza los pacientes del enfermero
	 */
	private void misPacientes() {

		if (pruebasEnfermero != null) {
			System.out.println("Mis pruebas a realizar:");
			for (int i = 0; i < pruebasEnfermero.size(); i++) {
				Prueba pruebaDiagnostica = pruebasEnfermero.get(i);
				System.out.println(pruebaDiagnostica);
			}
		}
		if (vacunasEnfermero != null) {
			System.out.println("Vacunaciones:");
			for (int i = 0; i < vacunasEnfermero.size(); i++) {
				Paciente pacienteVacunarMostrar = vacunasEnfermero.get(i);
				System.out.println(pacienteVacunarMostrar);
			}
		}
	}

	/**
	 * Mira si un enfermero puede realizar una prueba en una fecha
	 */
	public boolean puedeRealizarPrueba(Date fechaPrueba) {
		int contadorPruebas = 0;

		for (Prueba pruebaDiagnostica : pruebasEnfermero) {

			Calendar fechaPrueba2 = Calendar.getInstance();
			fechaPrueba2.setTime(fechaPrueba);
			Calendar fechaOtraPrueba = Calendar.getInstance();
			fechaOtraPrueba.setTime(pruebaDiagnostica.getFecha().getTime());

			if (fechaPrueba2.get(Calendar.WEEK_OF_YEAR) == fechaOtraPrueba.get(Calendar.WEEK_OF_YEAR)) {
				contadorPruebas++;
				if (contadorPruebas == 5) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Busca una prueba por su id
	 */
	private Prueba getPrueba(int id) {
		for (Prueba prueba : pruebasEnfermero) {
			if (prueba.getId() == id) {
				return prueba;
			}
		}
		return null;

	}

	/**
	 * Obtiene el paciente a vacunar
	 */
	private Paciente getVacuna(String dni) {
		for (Paciente paciente : vacunasEnfermero) {
			if (paciente.getDni() == dni) {
				return paciente;
			}
		}
		return null;

	}

	/**
	 * Actualiza una prueba como hecha, la borra de su lista de pruebas para hacer
	 */
	private void pruebaRealizada(Prueba prueba) {
		pruebasEnfermero.remove(prueba);

	}

	/**
	 * Establece una nueva prueba a realizar al enfermero
	 */
	public void asignarPrueba(Prueba prueba) {
		pruebasEnfermero.add(prueba);
	}

	/**
	 * Establece una nueva vacuna a poner para el enfemero
	 */
	public void vacunar(Paciente paciente) {
		vacunasEnfermero.add(paciente);
	}

	/**
	 * Obtiene las pruebas a realizar por el enfermero
	 */
	public List<Prueba> getPruebas() {
		return pruebasEnfermero;
	}

	/**
	 * Obtiene las vacunas a poner por el enfermero urn
	 */
	public List<Paciente> getVacunaciones() {
		return vacunasEnfermero;
	}

	@Override
	public String toString() {
		return "Enfermero [pruebasEnfermero=" + pruebasEnfermero + ", vacunasEnfermero=" + vacunasEnfermero
				+ ", getDni()=" + getDni() + ", getNombre()=" + getNombre() + "]";
	}

}
