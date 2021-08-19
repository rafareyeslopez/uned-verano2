import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Administrador extends Empleado {

	private static String dniUsuario;

	static void mostrarMenu(String dni) {
		dniUsuario = dni;
		int opcion = 0;

		do {
			try {

				Scanner scan = new Scanner(System.in);

				System.out.println("HOLA ADMINISTRADOR");
				System.out.println("***********************");
				System.out.println("0. Salir");
				System.out.println("1. Alta persona");
				System.out.println("2. Modificar person");
				System.out.println("3. Borrar persona");
				System.out.println("4. Prueba diagnostica");
				System.out.println("5. Planificar vacunas");
				System.out.println("6. Ver todas las personas");
				System.out.println("7. Ver pacientes asignados");
				System.out.println("8. Ver pacientes confinados");
				System.out.println("9. Programacion pruebas serologicas confinamiento");
				System.out.println("10. Gestion tock de vacunas");
				System.out.println("11. Tentativa a vacunas");

				opcion = Integer.parseInt(scan.nextLine());

				switch (opcion) {
				case 0:

					System.out.println("Salir");

					break;

				case 1:
					altaPersona();

					break;
				case 2:
					editarPersona();
					break;

				case 3:
					borrarPersona();
					break;
				case 4:
					prueba();

					break;

				case 5:
					vacunar();

					break;
				case 6:
					verPersonas();
					break;
				case 7:
					verAsignaciones();
					break;
				case 8:
					verConfinamiento();
					break;
				case 9:
					analisisConfinamiento();
					break;
				case 10:
					gestionStock();
					break;
				case 11:
					tentativaVacunas();
					break;
				default:

					System.out.println("Error!");
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (opcion != 0);
	}

	private static void analisisConfinamiento() throws ParseException {

		List<Paciente> pacientesConfinados = Persona.getPacientesConfinados();
		for (Paciente paciente : pacientesConfinados) {
			System.out.println("Programando analisis para " + paciente);
			AnalisisSerologico analisisSerologico = new AnalisisSerologico(paciente);
			asignarEnfermeroTecnico(analisisSerologico);
		}

	}

	private static void tentativaVacunas() {
		List<Paciente> listaPacientes = Persona.obtenerPacientes();
		for (Paciente paciente : listaPacientes) {
			if (!paciente.isVacunaCompleta() && paciente.getVacuna() != null) {
				System.out.println(paciente);
			}
		}

	}

	private static void gestionStock() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Vacuna recibida?");
		System.out.println("1. Pfizer");
		System.out.println("2. Johnson&Johnson");
		System.out.println("3. Moderna");
		int vacunaRecibida = Integer.parseInt(scanner.nextLine());
		System.out.println("Cantidad?");
		int cantidad = Integer.parseInt(scanner.nextLine());

		switch (vacunaRecibida) {
		case 1:
			Pfizer.actualizarStock(cantidad);
			break;
		case 2:
			JohnsonJohnson.actualizarStock(cantidad);
			break;
		case 3:
			Moderna.actualizarStock(cantidad);
			break;
		default:
			System.out.println("Error!");
			break;
		}
	}

	private static void verConfinamiento() {
		List<Paciente> pacientesConfinados = Persona.getPacientesConfinados();
		for (Paciente paciente : pacientesConfinados) {
			System.out.println(paciente);
		}
	}

	private static void verAsignaciones() {
		List<Persona> listaPersonas = Persona.getUsuarios();
		for (Persona persona : listaPersonas) {
			if (persona instanceof Enfermero) {
				Enfermero enfermero = (Enfermero) persona;

				List<PruebaDiagnostica> pruebasEnfermero = PruebaDiagnostica.pruebasEnfermero(enfermero.getId());
				if (pruebasEnfermero != null) {
					System.out.println(enfermero);
					System.out.println("Pruebas:");
					for (PruebaDiagnostica pruebaMostrar : pruebasEnfermero) {
						System.out.println(pruebaMostrar);

					}
				}

				List<Paciente> listaPacientesVacunacionEnfermero = PruebaDiagnostica.vacunacionesPaciente
						.get(dniUsuarioMostrar);
				if (listaPacientesVacunacionEnfermero != null) {
					System.out.println(enfermero);
					System.out.println("Vacunaciones:");
					for (Paciente pacienteVacunarMostrar : listaPacientesVacunacionEnfermero) {
						System.out.println(pacienteVacunarMostrar);
					}
				}
			} else if (personaVerPacientesASignados instanceof Tecnico) {
				Tecnico tecnicoPacientesMostrar = (Tecnico) personaVerPacientesASignados;

				List<PruebaDiagnostica> obtenerPruebasAsignadasTecnico = GestorPruebas
						.obtenerPruebasAsignadasTecnico(tecnicoPacientesMostrar.getDni());
				if (obtenerPruebasAsignadasTecnico != null) {
					System.out.println(tecnicoPacientesMostrar);

					for (PruebaDiagnostica pruebaMostrar : obtenerPruebasAsignadasTecnico) {
						System.out.println(pruebaMostrar);

					}
				}

			}

		}
	}

	private static void verPersonas() {
		Set<String> keySetMostrarUsuarios = Persona.getUsuarios().keySet();
		for (String dniUsuarioMostrar : keySetMostrarUsuarios) {
			System.out.println(Persona.getUsuarios().get(dniUsuarioMostrar));
		}
	}

	private static void vacunar() throws ParseException {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduzca DNI del paciente al que vacunar");
		String dniPacienteVacunar = scanner.nextLine();
		Paciente pacienteVacunar = (Paciente) Persona.getUsuarios().get(dniPacienteVacunar);
		Vacuna vacuna = aleatorioVacuna();
		if (vacuna != null) {
			System.out.println("Vacunacion programada con " + vacuna);
			System.out.println("Introduzca fecha primera dosis");
			String fechaVacuna = scanner.nextLine();
			Date fechaVacunaDate = Principal.format.parse(fechaVacuna);

			if (pacienteVacunar.getEdad() < 65 && verificarGrupoPrioritarioCompleto()) {
				programarVacunacion(pacienteVacunar, fechaVacunaDate, vacuna);
			} else if (pacienteVacunar.getEdad() < 65 && !verificarGrupoPrioritarioCompleto()
					&& verificarGrupoPrioritarioPeriodo(fechaVacunaDate)) {
				programarVacunacion(pacienteVacunar, fechaVacunaDate, vacuna);

			} else if (pacienteVacunar.getEdad() >= 65) {
				programarVacunacion(pacienteVacunar, fechaVacunaDate, vacuna);
			} else {
				System.out.println("Paciente no valido para vacunacion");
			}
		}

	}

	private static void prueba() throws ParseException {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduzca DNI del paciente al que realizar la prueba diagnostica");
		String dniPaciente = scanner.nextLine();
		Paciente paciente = (Paciente) Persona.getPersona(dniPaciente);
		System.out.println("Tipo de prueba a relizar?");
		System.out.println("-------------------------");
		System.out.println("1. Prueba antigenos");
		System.out.println("2. Test PCR");
		System.out.println("3. Analisis Serologico");
		int opcionPrueba = Integer.parseInt(scanner.nextLine());
		switch (opcionPrueba) {
		case 1:
			System.out.println("Tipo de prueba de antigenos?");
			System.out.println("-------------------------");
			System.out.println("1. Prueba rapida");
			System.out.println("2. Prueba clasica");
			int opcionPruebaAntigenos = Integer.parseInt(scanner.nextLine());

			if (opcionPruebaAntigenos == 1) {
				PruebaRapida pruebaRapida = new PruebaRapida(paciente);

				asignarEnfermeroTecnico(scanner, pruebaRapida);

			} else if (opcionPruebaAntigenos == 2) {
				PruebaClasica pruebaClasica = new PruebaClasica(paciente);
				asignarEnfermeroTecnico(scanner, pruebaClasica);
			} else {
				System.out.println("Opcion no valida");
			}
			break;
		case 2:
			TestPcr testPcr = new TestPcr(paciente);
			asignarEnfermeroTecnico(scanner, testPcr);
			break;
		case 3:
			AnalisisSerologico analisisSerologico = new AnalisisSerologico(paciente);
			asignarEnfermeroTecnico(scanner, analisisSerologico);
			break;
		default:
			break;
		}
	}

	private static void borrarPersona() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduzca DNI del usuario a eliminar");
		String dniEliminar = scanner.nextLine();
		Persona.baja(dniEliminar);
	}

	private static void editarPersona() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduzca DNI del usuario a modificar");
		String dni = scanner.nextLine();
		Persona usuario = Persona.getPersona(dni);
		System.out.println(usuario);
		System.out.println("Introduzca nombre actualizaco");
		String nuevoNombre = scanner.nextLine();
		usuario.setNombre(nuevoNombre);
		Persona.modificacion(usuario);
	}

	private static void altaPersona() {
		System.out.println("Que usuario desea dar de alta?");
		System.out.println("-------------------------------");
		System.out.println("1. Paciente");
		System.out.println("2. Enfermero");
		System.out.println("3. Tecnico");
		int opcionMenuSeleccionAlta = Integer.parseInt(scanner.nextLine());

		if (opcionMenuSeleccionAlta == 1) {
			System.out.println("Introduzca DNI del paciente");
			String dni = scanner.nextLine();
			System.out.println("Introduzca nombre completo del paciente");
			String nombre = scanner.nextLine();
			System.out.println("Introduzca edad del paciente");
			int edad = Integer.parseInt(scanner.nextLine());
			Paciente pacienteNuevo = new Paciente(dni, nombre, edad);
			Persona.alta(pacienteNuevo);

		} else if (opcionMenuSeleccionAlta == 2) {
			System.out.println("Introduzca DNI del enfermero");
			String dni = scanner.nextLine();
			System.out.println("Introduzca nombre completo del enfermero");
			String nombre = scanner.nextLine();
			System.out.println("Introduzca password");
			String password = scanner.nextLine();
			Enfermero enfermeroNuevo = new Enfermero(dni, nombre, password);
			Persona.alta(enfermeroNuevo);

		} else if (opcionMenuSeleccionAlta == 3) {
			System.out.println("Introduzca DNI del tecnico");
			String dni = scanner.nextLine();
			System.out.println("Introduzca nombre completo del tecnico");
			String nombre = scanner.nextLine();
			System.out.println("Introduzca password");
			String password = scanner.nextLine();
			Tecnico tecnicoNuevo = new Tecnico(dni, nombre, password);

			Persona.alta(tecnicoNuevo);

		} else {
			System.out.println("Opcion invalida");
		}
	}

	static void programarVacunacion(Paciente paciente, Date fechaVacuna, Vacuna vacuna) throws ParseException {
		Scanner scanner = new Scanner(System.in);

		if (vacuna.getNumeroDosis() == 1) {
			paciente.setPrimeraDosis(fechaVacuna);
			System.out.println("Introduzca DNI enfermero para vacunacion");
			String dniEnfermeroVacunacion = scanner.nextLine();
			Enfermero enfermeroVacunacion = (Enfermero) Persona.getUsuarios().get(dniEnfermeroVacunacion);
			paciente.setEnfermeroVacunacion(enfermeroVacunacion);

			List<Paciente> listaPacientesEnfermeroVacunacion = PruebaDiagnostica.vacunacionesPaciente
					.get(dniEnfermeroVacunacion);
			if (listaPacientesEnfermeroVacunacion == null) {
				listaPacientesEnfermeroVacunacion = new ArrayList<Paciente>();
			}
			listaPacientesEnfermeroVacunacion.add(paciente);
			PruebaDiagnostica.vacunacionesPaciente.put(dniEnfermeroVacunacion, listaPacientesEnfermeroVacunacion);
			actualizarStockVacunacion(vacuna);

		} else {
			Date primeraVacunaDate = fechaVacuna;

			System.out.println("Introduzca fecha segunda dosis");
			String fechaSegundaVacuna = scanner.nextLine();
			Date segundaVacunaDate = Principal.format.parse(fechaSegundaVacuna);

			long diffInMillies = Math.abs(primeraVacunaDate.getTime() - segundaVacunaDate.getTime());
			long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			if (diff < 21) {
				System.out.println("La segunda fecha no es valida! Es menos de 21 dias");
			} else {
				paciente.setPrimeraDosis(primeraVacunaDate);
				System.out.println("Introduzca DNI enfermero para vacunacion");
				String dniEnfermeroVacunacion = scanner.nextLine();
				Enfermero enfermeroVacunacion = (Enfermero) Persona.getUsuarios().get(dniEnfermeroVacunacion);
				paciente.setEnfermeroVacunacion(enfermeroVacunacion);

				List<Paciente> listaPacientesEnfermeroVacunacion = PruebaDiagnostica.vacunacionesPaciente
						.get(dniEnfermeroVacunacion);
				if (listaPacientesEnfermeroVacunacion == null) {
					listaPacientesEnfermeroVacunacion = new ArrayList<Paciente>();
				}
				listaPacientesEnfermeroVacunacion.add(paciente);
				PruebaDiagnostica.vacunacionesPaciente.put(dniEnfermeroVacunacion, listaPacientesEnfermeroVacunacion);
				actualizarStockVacunacion(vacuna);
			}

		}

	}

	private static void actualizarStockVacunacion(Vacuna vacuna) {
		if (vacuna instanceof Pfizer) {
			Pfizer.actualizarStockVacunaPlanificada();

		} else if (vacuna instanceof JohnsonJohnson) {
			JohnsonJohnson.actualizarStockVacunaPlanificada();

		}
		if (vacuna instanceof Moderna) {
			Moderna.actualizarStockVacunaPlanificada();

		}
	}

	static Vacuna aleatorioVacuna() {
		Random r = new Random();
		int valorDado = r.nextInt(3); // Entre 0 y 2.
		if (valorDado == 0) {
			if (Pfizer.getStock() > 0) {
				return new Pfizer();
			} else {
				System.out.println("No hay suficiente stock de Pfizer");
				return null;
			}
		} else if (valorDado == 1) {
			if (JohnsonJohnson.getStock() > 0) {
				return new JohnsonJohnson();
			} else {
				System.out.println("No hay suficiente stock de Johnson&Jonson");
				return null;
			}
		} else if (valorDado == 2) {
			if (Moderna.getStock() > 0) {
				return new Moderna();
			} else {
				System.out.println("No hay suficiente stock de Moderna");
				return null;
			}
		} else {
			System.out.println("valor fuera de rango!! " + valorDado);
			return null;
		}
	}

	static boolean verificarGrupoPrioritarioCompleto() {
		boolean prioritariosVacunados = true;
		Set<String> keySet = Persona.getUsuarios().keySet();
		for (String dni : keySet) {
			Persona persona = Persona.getUsuarios().get(dni);
			if (persona instanceof Paciente) {
				Paciente paciente = (Paciente) persona;
				if (paciente.getEdad() >= 65 && !paciente.isVacunaCompleta()) {
					prioritariosVacunados = false;
				}
			}
		}

		return prioritariosVacunados;
	}

	static boolean verificarGrupoPrioritarioPeriodo(Date fechaVacuna) {
		boolean prioritariosVacunados = true;
		Set<String> keySet = Persona.getUsuarios().keySet();
		for (String dni : keySet) {
			Persona persona = Persona.getUsuarios().get(dni);
			if (persona instanceof Paciente) {
				Paciente paciente = (Paciente) persona;

				if (paciente.getEdad() >= 65 && (paciente.getPrimeraDosis() == null
						|| (paciente.getSegundaDosis() != null && paciente.getSegundaDosis().before(fechaVacuna)))) {
					prioritariosVacunados = false;
				}

				// if (paciente.getEdad() >= 65 && !paciente.isVacunaCompleta()
				// && (paciente.getVacuna() != null && paciente.getVacuna().getNumeroDosis() ==
				// 2)
				// && (paciente.getPrimeraDosis() != null
				// && paciente.getPrimeraDosis().before(Calendar.getInstance().getTime()))
				// && paciente.isPrimeraDosisAdministada() &&
				// !paciente.isSegundaDosisAdministada()
				// && (paciente.getSegundaDosis() != null
				// && paciente.getSegundaDosis().before(Calendar.getInstance().getTime()))) {
				//
				// prioritariosVacunados = false;
				// }
			}
		}

		return prioritariosVacunados;
	}

	static void asignarEnfermeroTecnico(PruebaDiagnostica prueba) throws ParseException {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Fecha de la prueba? Ej: dd-mm-aaaa");
		String fechaPrueba = scanner.nextLine();
		Date fechaPruebaDate = Principal.format.parse(fechaPrueba);
		if (prueba.getPaciente().isConfinado()
				&& prueba.getPaciente().getFechaFinConfinamiento().getTime().after(fechaPruebaDate)) {
			System.out.println("No se puede planificar la prueba para esa fecha, el paciente esta confinado hasta "
					+ prueba.getPaciente().getFechaFinConfinamiento());
		} else {
			prueba.setFecha(fechaPruebaDate);
			System.out.println("Asignar Enfermero a la prueba");
			System.out.println("-------------------------------");
			List<Enfermero> listaEnfermerosDisponibles = PruebaDiagnostica
					.obtenerEnfermerosDisponibles(fechaPruebaDate);
			for (Enfermero enfermero : listaEnfermerosDisponibles) {
				System.out.println(enfermero);
			}

			System.out.println("Introduzca DNI enfermero");
			String dniEnfermero = scanner.nextLine();
			Enfermero enfermeroAsignado = (Enfermero) Persona.getUsuarios().get(dniEnfermero);

			System.out.println("Asignar Tecnico a la prueba");
			System.out.println("-------------------------------");
			List<Tecnico> listaTecnicosDisponibles = PruebaDiagnostica.obtenerTecnicosDisponibles(fechaPruebaDate);
			for (Tecnico tecnico : listaTecnicosDisponibles) {
				System.out.println(tecnico);
			}

			System.out.println("Introduzca DNI tecnico");
			String dniTecnico = scanner.nextLine();
			Tecnico tecnicoAsignado = (Tecnico) Persona.getUsuarios().get(dniTecnico);

			PruebaDiagnostica.registrarPruebaPaciente(prueba, enfermeroAsignado, tecnicoAsignado);
		}
	}

}
