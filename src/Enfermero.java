import java.util.List;
import java.util.Scanner;

public class Enfermero extends Empleado {

	static void menuEnfermero(String idEnfermero) {
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
					misPacientes(idEnfermero);

					break;
				case 2:
					hacerPrueba(idEnfermero);
					break;
				case 3:

					vacunar(idEnfermero);

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

	static void vacunar(String idEnfermero) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("ID paciente para vacunar");
		String id = scanner.nextLine();
		Paciente paciente = (Paciente) Persona.getPersona(id);
		System.out.println(paciente);
		if (paciente.getVacuna() == null) {
			System.out.println("Vacunacion no programada");
		} else {
			if (paciente.getVacuna().getNumeroDosis() == 1) {
				System.out.println("Administrando unica dosis...");
				paciente.setPrimeraDosisAdministada(true);
				paciente.setVacunaCompleta(true);
			} else {
				if (!paciente.isPrimeraDosisAdministada()) {
					System.out.println("Administrando primera dosis...");
					paciente.setPrimeraDosisAdministada(true);

				} else {
					System.out.println("Administrando segunda dosis...");
					paciente.setSegundaDosisAdministada(true);
					paciente.setVacunaCompleta(true);
				}

			}
			Persona.actualizarVacunacion(paciente);
		}
	}

	private static void hacerPrueba(Scanner scanner) {
		System.out.println("Introduzca ID de la prueba a registrar");
		int idPrueba = Integer.parseInt(scanner.nextLine());
		PruebaDiagnostica prueba = PruebaDiagnostica.obtenerPrueba(idPrueba);
		if (prueba != null) {

			prueba.setRealizada(true);
			PruebaDiagnostica.actualizarPruebaRealizadaEnfermero(prueba);
		}
	}

	private static void misPacientes(String idEnfermero) {
		List<PruebaDiagnostica> obtenerPruebasAsignadasEnfermero = PruebaDiagnostica.pruebasEnfermero(idEnfermero);
		if (obtenerPruebasAsignadasEnfermero != null) {
			System.out.println("Mis pruebas a realziar:");
			for (PruebaDiagnostica pruebaDiagnostica : obtenerPruebasAsignadasEnfermero) {
				System.out.println(pruebaDiagnostica);
			}
		}
		List<Paciente> listaPacientesVacunacionEnfermero = PruebaDiagnostica.vacunacionesPaciente
				.get(Principal.dniUsuarioActivo);
		if (listaPacientesVacunacionEnfermero != null) {
			System.out.println("Vacunaciones:");
			for (Paciente pacienteVacunarMostrar : listaPacientesVacunacionEnfermero) {
				System.out.println(pacienteVacunarMostrar);
			}
		}
	}

}
