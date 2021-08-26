package personas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import pruebas.PruebaDiagnostica;

public class Enfermero extends Empleado {

	public Enfermero(String dni, String nombre, String apellidos, int edad, String direccion, String telefono) {
		super(dni, nombre, apellidos, edad, direccion, telefono);
		pruebasEnfermero = new ArrayList<PruebaDiagnostica>();
		vacunasEnfermero = new ArrayList<Paciente>();
	}

	private List<PruebaDiagnostica> pruebasEnfermero;
	private List<Paciente> vacunasEnfermero;

	public void menuEnfermero() {
		int opcion = 0;
		do {
			try {
				System.out.println("MENU Enfermero");
				System.out.println("----------------------");
				System.out.println("Elige una opcion");
				System.out.println("----------------------");
				System.out.println("0. Volver");
				System.out.println("1. Ver datos de pacientes asignados");
				System.out.println("2. Registro de prueba");
				System.out.println("3. Registro de vacuna");

				Scanner scanner = new Scanner(System.in);

				opcion = Integer.parseInt(scanner.nextLine());

				switch (opcion) {
				case 0:

					System.out.println("Salir");

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
				paciente.setPrimeraDosisAdministada(true);
				paciente.setVacunaCompleta(true);
				vacunasEnfermero.remove(paciente);
			} else {
				if (!paciente.isPrimeraDosisAdministada()) {
					System.out.println("Administrada la primera dosis");
					paciente.setPrimeraDosisAdministada(true);

				} else {
					System.out.println("Administrada la segunda dosis");
					paciente.setSegundaDosisAdministada(true);
					paciente.setVacunaCompleta(true);
					vacunasEnfermero.remove(paciente);
				}

			}

			Persona.actualizarPersona(paciente);
		}
	}

	private void hacerPrueba() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduzca la prueba a realizar");
		int idPrueba = Integer.parseInt(scanner.nextLine());

		PruebaDiagnostica prueba = getPrueba(idPrueba);
		Paciente paciente = (Paciente) Persona.getPersona(prueba.getPaciente().getDni());

		paciente.actualizarPrueba(prueba);

		pruebaRealizada(prueba);

		Persona.actualizarPersona(paciente);

	}

	private void misPacientes() {

		if (pruebasEnfermero != null) {
			System.out.println("Mis pruebas a realizar:");
			for (int i = 0; i < pruebasEnfermero.size(); i++) {
				PruebaDiagnostica pruebaDiagnostica = pruebasEnfermero.get(i);
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

	public boolean puedeRealizarPrueba(Date fechaPruebaDate) {
		int pruebasSemana = 0;
		for (PruebaDiagnostica pruebaDiagnostica : pruebasEnfermero) {
			Calendar calendarPruebaPlanificar = Calendar.getInstance();
			calendarPruebaPlanificar.setTime(fechaPruebaDate);
			Calendar calendarPrueba = Calendar.getInstance();
			calendarPrueba.setTime(pruebaDiagnostica.getFecha());
			if (calendarPruebaPlanificar.get(Calendar.WEEK_OF_YEAR) == calendarPrueba.get(Calendar.WEEK_OF_YEAR)) {
				pruebasSemana++;
				if (pruebasSemana >= 5) {
					return false;
				}
			}
		}

		return true;
	}

	static boolean enfermeroDisponible(List<PruebaDiagnostica> listaPruebasEnfermero, Date fechaPrueba) {
		int pruebasSemana = 0;
		for (PruebaDiagnostica pruebaDiagnostica : listaPruebasEnfermero) {
			Calendar calendarPruebaPlanificar = Calendar.getInstance();
			calendarPruebaPlanificar.setTime(fechaPrueba);
			Calendar calendarPrueba = Calendar.getInstance();
			calendarPrueba.setTime(pruebaDiagnostica.getFecha());
			if (calendarPruebaPlanificar.get(Calendar.WEEK_OF_YEAR) == calendarPrueba.get(Calendar.WEEK_OF_YEAR)) {
				pruebasSemana++;
			}
		}

		return pruebasSemana < 5;
	}

	private PruebaDiagnostica getPrueba(int pruebaId) {
		for (PruebaDiagnostica prueba : pruebasEnfermero) {
			if (prueba.getId() == pruebaId) {
				return prueba;
			}
		}
		return null;

	}

	private Paciente getVacuna(String dni) {
		for (Paciente paciente : vacunasEnfermero) {
			if (paciente.getDni() == dni) {
				return paciente;
			}
		}
		return null;

	}

	private void pruebaRealizada(PruebaDiagnostica prueba) {
		pruebasEnfermero.remove(prueba);

	}

	public void asignarPrueba(PruebaDiagnostica prueba) {
		pruebasEnfermero.add(prueba);
	}

	public void asignarVacuna(Paciente paciente) {
		vacunasEnfermero.add(paciente);
	}

	public List<PruebaDiagnostica> getPruebas() {
		return pruebasEnfermero;
	}

	public List<Paciente> getVacunaciones() {
		return vacunasEnfermero;
	}

	@Override
	public String toString() {
		return "Enfermero [pruebasEnfermero=" + pruebasEnfermero + ", vacunasEnfermero=" + vacunasEnfermero
				+ ", getDni()=" + getDni() + ", getNombre()=" + getNombre() + "]";
	}

}
