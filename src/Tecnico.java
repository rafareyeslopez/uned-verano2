import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class Tecnico extends Empleado {

	static void actualizarResultado(String dniTecnico) throws ParseException {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Las  pruebas que tiene asignadas y sin resultado aun son:");
		List<PruebaDiagnostica> obtenerPruebasAsignadasTecnico = PruebaDiagnostica
				.obtenerPruebasAsignadasTecnico(dniTecnico);
		for (PruebaDiagnostica pruebaDiagnostica : obtenerPruebasAsignadasTecnico) {
			System.out.println(pruebaDiagnostica);
		}
		System.out.println("Introduzca ID de la prueba a registrar");
		int idPrueba = Integer.parseInt(scanner.nextLine());
		PruebaDiagnostica prueba = PruebaDiagnostica.obtenerPrueba(idPrueba);
		if (prueba != null) {
			if (prueba instanceof PruebaRapida) {
				PruebaRapida pruebaRapida = (PruebaRapida) prueba;
				System.out.println(pruebaRapida);
				System.out.println("Indique resultado, positivo 'P', negativo 'N'");
				String resultado = scanner.nextLine();
				if (resultado.equals("P")) {
					pruebaRapida.setResultado(true);
					PruebaDiagnostica.actualizarPruebaRealizadaTecnico(pruebaRapida);
					Paciente paciente = prueba.getPaciente();
					Persona.confinar(paciente, prueba.getFecha());
				} else if (resultado.equals("N")) {
					pruebaRapida.setResultado(false);
					PruebaDiagnostica.actualizarPruebaRealizadaTecnico(pruebaRapida);
				} else {
					System.out.println("Resultado no valido para prueba");
				}
			} else if (prueba instanceof PruebaClasica) {
				PruebaClasica pruebaClasica = (PruebaClasica) prueba;
				System.out.println(pruebaClasica);
				System.out.println("Indique resultado, positivo 'P', negativo 'N'");
				String resultado = scanner.nextLine();
				if (resultado.equals("P")) {
					pruebaClasica.setResultado(true);
					PruebaDiagnostica.actualizarPruebaRealizadaTecnico(pruebaClasica);
					Paciente paciente = prueba.getPaciente();
					Persona.confinar(paciente, prueba.getFecha());
				} else if (resultado.equals("N")) {
					pruebaClasica.setResultado(false);
					PruebaDiagnostica.actualizarPruebaRealizadaTecnico(pruebaClasica);
				} else {
					System.out.println("Resultado no valido para prueba");
				}
			} else if (prueba instanceof TestPcr) {
				TestPcr testPcr = (TestPcr) prueba;
				System.out.println(testPcr);
				System.out.println("Indique resultado, positivo 'P', negativo 'N'");
				String resultado = scanner.nextLine();
				if (resultado.equals("P")) {
					testPcr.setPositivo(true);
					Paciente paciente = prueba.getPaciente();
					Persona.confinar(paciente, prueba.getFecha());

					PruebaDiagnostica.actualizarPruebaRealizadaTecnico(testPcr);
				} else if (resultado.equals("N")) {
					testPcr.setPositivo(false);
					PruebaDiagnostica.actualizarPruebaRealizadaTecnico(testPcr);
				} else {
					System.out.println("Resultado no valido para prueba");
				}
			} else if (prueba instanceof AnalisisSerologico) {
				AnalisisSerologico analisisSerologico = (AnalisisSerologico) prueba;
				System.out.println(analisisSerologico);
				System.out.println("Indique resultado, entre 0 y 10");
				int resultado = Integer.parseInt(scanner.nextLine());
				if (resultado >= 0 || resultado <= 10) {
					analisisSerologico.setValorAnticuerpos(resultado);
					PruebaDiagnostica.actualizarPruebaRealizadaTecnico(prueba);
				} else {
					System.out.println("Resultado no valido para prueba");
				}
			}

		} else {
			System.out.println("Prueba no encontrada!");
		}
	}

	static void verPacientesAsignados() {
		List<PruebaDiagnostica> obtenerPruebasAsignadasTecnico = PruebaDiagnostica
				.obtenerPruebasAsignadasTecnico(Principal.dniUsuarioActivo);
		for (PruebaDiagnostica pruebaDiagnostica : obtenerPruebasAsignadasTecnico) {
			if (pruebaDiagnostica.isRealizada()) {
				System.out.println(pruebaDiagnostica);
			}
		}

	}

	static void menuTecnico() {
		int opcionMenuSeleccionUsuario = 0;
		do {
			try {
				System.out.println("MENU TECNICO");
				System.out.println("----------------------");
				System.out.println("Elige una opcion");
				System.out.println("----------------------");
				System.out.println("0. Volver");
				System.out.println("1. Ver datos de pacientes asignados");
				System.out.println("2. Registro de resultado");

				Scanner scanner = new Scanner(System.in);

				opcionMenuSeleccionUsuario = Integer.parseInt(scanner.nextLine());

				switch (opcionMenuSeleccionUsuario) {
				case 0:

					System.out.println("Volviendo atras...");

					break;

				case 1:
					GestorTecnico.verPacientesAsignados();
					break;
				case 2:
					Tecnico.actualizarResultado(Principal.dniUsuarioActivo);
					break;
				default:

					System.out.println("Opcion invalida");
					break;
				}
			} catch (Exception e) {
				System.out.println("Opcion invalida");
			}
		} while (opcionMenuSeleccionUsuario != 0);
	}
}
