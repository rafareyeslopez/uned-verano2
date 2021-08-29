package personas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import pruebas.AnalisisSerologico;
import pruebas.Prueba;
import pruebas.PruebaClasica;
import pruebas.PruebaRapida;
import pruebas.TestPcr;
import vacunas.JohnsonJohnson;
import vacunas.Moderna;
import vacunas.Pfizer;
import vacunas.Vacuna;

/**
 * Clase que modela al Administrador del sistema, extienda de Empleado.
 */
public class Administrador extends Empleado {
	/**
	 * Servira para dado una fecha como String leida convertirla a tipo Fecha, bien
	 * Date o Calendar
	 */
	static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

	public Administrador(String dni, String nombre, String apellidos, int edad, String direccion, String telefono) {
		super(dni, nombre, apellidos, edad, direccion, telefono);
	}

	/**
	 * Muestra el menu del administrador
	 */
	public void menuAdmin() {
		int opcion = 0;

		do {
			try {

				Scanner scan = new Scanner(System.in);

				System.out.println("HOLA ADMINISTRADOR");
				System.out.println("***********************");
				System.out.println("0. Salir");
				System.out.println("1. Alta persona");
				System.out.println("2. Modificar persona");
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

					System.out.println("Sesion administrador terminada");

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

	/**
	 * Gestiona los analisis serologicos para los pacientes que han estado
	 * confinados
	 */
	private static void analisisConfinamiento() throws ParseException {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduzca dni del paciente confinado");
		String dni = scanner.nextLine();
		Paciente paciente = (Paciente) Persona.getPersona(dni);
		// dado un paciente que este confinado (con la fecha de confinamiento
		// establecida) vamos a planificar para el una prueba de tipo analisis
		// serologico
		if (paciente.getFechaConfinamiento() != null) {
			AnalisisSerologico analisisSerologico = new AnalisisSerologico(paciente);
			planificarPrueba(paciente, analisisSerologico);
		} else {
			System.out.println("El paciente no esta confinado");
		}
	}

	/**
	 * Muestra las vacunas que se van a administrar a los pacientes
	 */
	private static void tentativaVacunas() {
		List<Persona> lista = Persona.getUsuarios();
		for (Persona persona : lista) {
			if (persona instanceof Paciente) {
				Paciente paciente = (Paciente) persona;
				// primero miramos si tiene asginada vacuna
				if (paciente.getVacuna() != null) {
					// despues miramos si no tiene la primera dosis pues o bien si tiene la primera
					// puesta y la segunda no
					if (!paciente.isPrimeraDosisPuesta()
							|| (paciente.isPrimeraDosisPuesta() && !paciente.isSegundaDosisPuesta())) {
						System.out.println(paciente);
					}
				}
			}

		}

	}

	/**
	 * Gestiona el stock de las vacunas
	 */
	private static void gestionStock() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Que Vacuna hemos recibido?");
		System.out.println("1. Pfizer");
		System.out.println("2. Johnson&Johnson");
		System.out.println("3. Moderna");
		int vacunaRecibida = Integer.parseInt(scanner.nextLine());
		System.out.println("Cantidad?");
		int cantidad = Integer.parseInt(scanner.nextLine());

		switch (vacunaRecibida) {
		case 1:
			Pfizer.aumentarStock(cantidad);
			break;
		case 2:
			JohnsonJohnson.aumentarStock(cantidad);
			break;
		case 3:
			Moderna.aumentarStock(cantidad);
			break;
		default:
			System.out.println("Error!");
			break;
		}
	}

	/**
	 * Visualiza los pacientes que estan confinados
	 */
	private static void verConfinamiento() {
		List<Paciente> pacientesConfinados = Persona.getPacientesConfinados();
		for (Paciente paciente : pacientesConfinados) {
			System.out.println(paciente);
		}
	}

	/**
	 * Visualiza las pruebas asginadas a los enfermeros y tecnico y las vacunas de
	 * los enfermeros
	 */
	private static void verAsignaciones() {
		List<Persona> listaPersonas = Persona.getUsuarios();

		for (Persona persona : listaPersonas) {
			// si es un enfermero nos traeremos sus pruebas
			if (persona instanceof Enfermero) {
				Enfermero enfermero = (Enfermero) persona;

				List<Prueba> listaPruebas = enfermero.getPruebas();

				if (listaPruebas != null) {
					System.out.println(enfermero);
					System.out.println("Pruebas:");
					for (Prueba prueba : listaPruebas) {
						System.out.println(prueba);

					}
				}
// TODO: por aqui sabado
				List<Paciente> listaPacientesVacunacionEnfermero = enfermero.getVacunaciones();
				if (listaPacientesVacunacionEnfermero != null) {
					System.out.println(enfermero);
					System.out.println("Vacunaciones:");
					for (Paciente pacienteVacunarMostrar : listaPacientesVacunacionEnfermero) {
						System.out.println(pacienteVacunarMostrar);
					}
				}
			} else if (persona instanceof Tecnico) {
				Tecnico tecnicoPacientesMostrar = (Tecnico) persona;

				List<Prueba> obtenerPruebasAsignadasTecnico = tecnicoPacientesMostrar.getPruebas();
				if (obtenerPruebasAsignadasTecnico != null) {
					System.out.println(tecnicoPacientesMostrar);

					for (Prueba pruebaMostrar : obtenerPruebasAsignadasTecnico) {
						System.out.println(pruebaMostrar);

					}
				}

			}

		}
	}

	private static void verPersonas() {
		List<Persona> usuarios2 = Persona.getUsuarios();
		for (Persona persona : usuarios2) {
			System.out.println(persona);
		}
	}

	private static void vacunar() throws ParseException {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduzca DNI del paciente al que vacunar");
		String dni = scanner.nextLine();
		Paciente pacienteVacunar = (Paciente) Persona.getPersona(dni);
		Vacuna vacuna = aleatorioVacuna();
		if (vacuna != null) {
			System.out.println("La vacuna es" + vacuna);
			System.out.println("Introduzca fecha primera dosis");
			String fechaVacuna = scanner.nextLine();
			Date fechaVacunaDate = formatoFecha.parse(fechaVacuna);

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
		System.out.println("1. Prueba rapida");
		System.out.println("1. Prueba clasira");
		System.out.println("3. Test PCR");
		System.out.println("4. Analisis Serologico");
		int opcionPrueba = Integer.parseInt(scanner.nextLine());
		switch (opcionPrueba) {
		case 1:
			PruebaRapida pruebaRapida = new PruebaRapida(paciente);

			planificarPrueba(paciente, pruebaRapida);

			break;
		case 2:
			PruebaClasica pruebaClasica = new PruebaClasica(paciente);

			planificarPrueba(paciente, pruebaClasica);

			break;
		case 3:
			TestPcr testPcr = new TestPcr(paciente);
			planificarPrueba(paciente, testPcr);
			break;
		case 4:
			AnalisisSerologico analisisSerologico = new AnalisisSerologico(paciente);
			planificarPrueba(paciente, analisisSerologico);
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
		Scanner scanner = new Scanner(System.in);

		System.out.println("Que tipo vas a dar de alta?");
		System.out.println("-------------------------------");
		System.out.println("1. Paciente");
		System.out.println("2. Enfermero");
		System.out.println("3. Tecnico");
		int opcionMenuSeleccionAlta = Integer.parseInt(scanner.nextLine());

		if (opcionMenuSeleccionAlta == 1) {
			System.out.println("Introduzca DNI del paciente");
			String dni = scanner.nextLine();
			System.out.println("Introduzca nombre del paciente");
			String nombre = scanner.nextLine();
			System.out.println("Introduzca appellidos del paciente");
			String apellidos = scanner.nextLine();
			System.out.println("Introduzca edad del paciente");
			int edad = Integer.parseInt(scanner.nextLine());
			System.out.println("Introduzca direccion del paciente");
			String direccion = scanner.nextLine();
			System.out.println("Introduzca telefono del paciente");
			String telefono = scanner.nextLine();
			Paciente pacienteNuevo = new Paciente(dni, nombre, apellidos, edad, direccion, telefono);

			Persona.alta(pacienteNuevo);

		} else if (opcionMenuSeleccionAlta == 2) {
			System.out.println("Introduzca DNI del enfermero");
			String dni = scanner.nextLine();
			System.out.println("Introduzca nombre del enfermero");
			String nombre = scanner.nextLine();
			System.out.println("Introduzca appellidos del enfermero");
			String apellidos = scanner.nextLine();
			System.out.println("Introduzca edad del enfermero");
			int edad = Integer.parseInt(scanner.nextLine());
			System.out.println("Introduzca direccion del enfermero");
			String direccion = scanner.nextLine();
			System.out.println("Introduzca telefono del enfermero");
			String telefono = scanner.nextLine();
			Enfermero enfermeroNuevo = new Enfermero(dni, nombre, apellidos, edad, direccion, telefono);

			Persona.alta(enfermeroNuevo);

		} else if (opcionMenuSeleccionAlta == 3) {
			System.out.println("Introduzca DNI del tecnico");
			String dni = scanner.nextLine();
			System.out.println("Introduzca nombre del tecnico");
			String nombre = scanner.nextLine();
			System.out.println("Introduzca appellidos del tecnico");
			String apellidos = scanner.nextLine();
			System.out.println("Introduzca edad del tecnico");
			int edad = Integer.parseInt(scanner.nextLine());
			System.out.println("Introduzca direccion del tecnico");
			String direccion = scanner.nextLine();
			System.out.println("Introduzca telefono del tecnico");
			String telefono = scanner.nextLine();
			Tecnico tecnicoNuevo = new Tecnico(dni, nombre, apellidos, edad, direccion, telefono);

			Persona.alta(tecnicoNuevo);
		} else {
			System.out.println("Opcion invalida");
		}
	}

	static void programarVacunacion(Paciente paciente, Date fechaVacuna, Vacuna vacuna) throws ParseException {
		Scanner scanner = new Scanner(System.in);

		if (vacuna.getDosis() == 1) {
			paciente.setPrimeraDosis(fechaVacuna);
			System.out.println("Introduzca DNI enfermero para vacunacion");
			String dniEnfermeroVacunacion = scanner.nextLine();
			Enfermero enfermeroVacunacion = (Enfermero) Persona.getPersona(dniEnfermeroVacunacion);

			paciente.setEnfermeroVacunacion(enfermeroVacunacion);

			enfermeroVacunacion.asignarVacuna(paciente);

			actualizarStockVacunacion(vacuna);
			paciente.setVacuna(vacuna);

		} else {
			Date primeraVacunaDate = fechaVacuna;

			System.out.println("Introduzca fecha segunda dosis");
			String fechaSegundaVacuna = scanner.nextLine();
			Date segundaVacunaDate = formatoFecha.parse(fechaSegundaVacuna);

			long diffInMillies = Math.abs(primeraVacunaDate.getTime() - segundaVacunaDate.getTime());
			long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			if (diff < 21) {
				System.out.println("La segunda fecha no es valida! Es menos de 21 dias");
			} else {
				paciente.setPrimeraDosis(primeraVacunaDate);
				System.out.println("Introduzca DNI enfermero para vacunacion");

				String dniEnfermeroVacunacion = scanner.nextLine();
				Enfermero enfermeroVacunacion = (Enfermero) Persona.getPersona(dniEnfermeroVacunacion);

				paciente.setEnfermeroVacunacion(enfermeroVacunacion);

				enfermeroVacunacion.asignarVacuna(paciente);

				actualizarStockVacunacion(vacuna);
				paciente.setVacuna(vacuna);

			}

		}

	}

	private static void actualizarStockVacunacion(Vacuna vacuna) {
		if (vacuna instanceof Pfizer) {
			Pfizer.quitarStock();

		} else if (vacuna instanceof JohnsonJohnson) {
			JohnsonJohnson.quitarStock();

		}
		if (vacuna instanceof Moderna) {
			Moderna.quitarStock();

		}
	}

	private static Vacuna aleatorioVacuna() {
		Random random = new Random();
		int numero = random.nextInt(3);
		if (numero == 0) {
			if (Pfizer.getStock() > 0) {
				return new Pfizer();
			} else {
				System.out.println("No hay bastantes vacunas de Pfizer");
				return null;
			}
		} else if (numero == 1) {
			if (JohnsonJohnson.getStock() > 0) {
				return new JohnsonJohnson();
			} else {
				System.out.println("No hay bastantes vacunas de Johnson&Jonson");
				return null;
			}
		} else if (numero == 2) {
			if (Moderna.getStock() > 0) {
				return new Moderna();
			} else {
				System.out.println("No hay bastantes vacunas de Moderna");
				return null;
			}
		}
		return null;
	}

	static boolean verificarGrupoPrioritarioCompleto() {
		boolean prioritariosVacunados = true;
		List<Persona> pacientes = Persona.getUsuarios();
		for (Persona persona : pacientes) {
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
		List<Persona> pacientes = Persona.getUsuarios();
		for (Persona persona : pacientes) {
			if (persona instanceof Paciente) {
				Paciente paciente = (Paciente) persona;

				if (paciente.getEdad() >= 65 && (paciente.getPrimeraDosis() == null
						|| (paciente.getSegundaDosis() != null && paciente.getSegundaDosis().before(fechaVacuna)))) {
					prioritariosVacunados = false;
				}

			}
		}

		return prioritariosVacunados;
	}

	static void planificarPrueba(Paciente paciente, Prueba prueba) throws ParseException {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Fecha de la prueba? Ej: dd-mm-aaaa");
		String fechaPrueba = scanner.nextLine();
		Date fechaPruebaDate = formatoFecha.parse(fechaPrueba);
		if (paciente.isConfinado() && paciente.getFechaFinConfinamiento().getTime().after(fechaPruebaDate)) {
			System.out.println("El paciente esta confinado");
		} else {

			if (paciente.puedeRealizarPrueba(fechaPruebaDate)) {

				prueba.setFecha(fechaPruebaDate);
				System.out.println("Asignar Enfermero a la prueba");
				System.out.println("-------------------------------");

				System.out.println("Introduzca DNI enfermero");
				String dniEnfermero = scanner.nextLine();
				Enfermero enfermero = (Enfermero) Persona.getPersona(dniEnfermero);
				if (enfermero.puedeRealizarPrueba(fechaPruebaDate)) {

					System.out.println("Asignar Tecnico a la prueba");
					System.out.println("-------------------------------");

					System.out.println("Introduzca DNI tecnico");
					String dniTecnico = scanner.nextLine();
					Tecnico tecnico = (Tecnico) Persona.getPersona(dniTecnico);

					if (tecnico.puedeRealizarPrueba(fechaPruebaDate)) {
						prueba.setEnfermero(enfermero);
						prueba.setTecnicoLaboratorio(tecnico);
						prueba.setPaciente(paciente);
						paciente.establecerPrueba(prueba);
						enfermero.asignarPrueba(prueba);
						tecnico.asignarPrueba(prueba);

					} else {
						System.out.println("El tecnico tiene demasiadas pruebas esa semana");

					}
				} else {
					System.out.println("El enfermero tiene demasiadas pruebas esa semana");
				}
			} else {
				System.out.println("El pacienteno puede realizar la prueba");

			}
		}

	}

	@Override
	public String toString() {
		return "Administrador:\ngetPassword()=" + getPassword() + "\ngetDni()=" + getDni() + "\ngetNombre()="
				+ getNombre() + "\ngetApellidos()=" + getApellidos() + "\ngetEdad()=" + getEdad() + "\ngetDireccion()="
				+ getDireccion() + "\ngetTelefono()=" + getTelefono();
	}

}
