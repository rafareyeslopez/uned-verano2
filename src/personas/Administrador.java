package personas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
				System.out.println("10. Gestion stock de vacunas");
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
				System.out.println("Error!");
			}
		} while (opcion != 0);
	}

	/**
	 * Gestiona los analisis serologicos para los pacientes que han estado
	 * confinados
	 */
	private void analisisConfinamiento() throws ParseException {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduzca dni del paciente confinado");
		String dni = scanner.nextLine();
		Paciente paciente = (Paciente) Persona.getPersona(dni);
		// dado un paciente que este confinado (con la fecha de confinamiento
		// establecida) vamos a planificar para el una prueba de tipo analisis
		// serologico
		if (paciente.isConfinado()) {
			AnalisisSerologico analisisSerologico = new AnalisisSerologico(paciente);
			planificarAnalisisSerologico(paciente, analisisSerologico);
		} else {
			System.out.println("El paciente no esta confinado");
		}
	}

	/**
	 * Muestra las vacunas que se van a administrar a los pacientes
	 */
	private void tentativaVacunas() {
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
	private void gestionStock() {
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
	private void verAsignaciones() {
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
				List<Paciente> listaVacunas = enfermero.getVacunaciones();
				if (listaVacunas != null) {
					System.out.println(enfermero);
					System.out.println("Vacunaciones:");
					for (Paciente paciente : listaVacunas) {
						System.out.println(paciente);
					}
				}
			} else if (persona instanceof Tecnico) {
				Tecnico tecnico = (Tecnico) persona;

				List<Prueba> listaPruebas = tecnico.getPruebas();
				if (listaPruebas != null) {
					System.out.println(tecnico);

					for (Prueba prueba : listaPruebas) {
						System.out.println(prueba);

					}
				}

			}

		}
	}

	/**
	 * Visualiza todas las personas del sistema
	 */
	private static void verPersonas() {
		List<Persona> usuarios2 = Persona.getUsuarios();
		for (Persona persona : usuarios2) {
			System.out.println(persona);
		}
	}

	/**
	 * Crea una tentativa a vacunacion
	 */
	private void vacunar() throws ParseException {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduzca DNI del paciente al que vacunar");
		String dni = scanner.nextLine();
		Paciente paciente = (Paciente) Persona.getPersona(dni);

		Vacuna vacuna = cogerVacuna();

		if (vacuna != null) {
			System.out.println("Vamos a vacunar con " + vacuna);

			System.out.println("Introducir fecha primera vacuna");

			String fechaVacuna = scanner.nextLine();
			Date fecha = formatoFecha.parse(fechaVacuna);
			Calendar fecha2 = Calendar.getInstance();
			fecha2.setTime(fecha);

			if (paciente.getEdad() < 65) {
				if (todosMayoresVacunados()) {
					ponerVacuna(paciente, vacuna, fecha2);

				} else if (mayoresEntreDosis(fecha2)) {
					ponerVacuna(paciente, vacuna, fecha2);
				} else {
					System.out.println(
							"Aun no se puede vacunar al paciente, hay otros del grupo prioritario que van antes");
				}

			} else if (paciente.getEdad() >= 65) {
				ponerVacuna(paciente, vacuna, fecha2);
			} else {
				System.out.println("Vacunacion invalida");
			}
		} else {
			System.out.println("No hay bastantes vacunas");
		}

	}

	/**
	 * Organiza una prueba
	 */
	private void prueba() throws ParseException {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduzca DNI del paciente");
		String dniPaciente = scanner.nextLine();
		Paciente paciente = (Paciente) Persona.getPersona(dniPaciente);

		System.out.println("Que prueba se va a hacera ?");
		System.out.println("********************************");
		System.out.println("1. Prueba rapida");
		System.out.println("1. Prueba clasica");
		System.out.println("3. Test PCR");
		System.out.println("4. Analisis Serologico");

		int opcion = Integer.parseInt(scanner.nextLine());
		switch (opcion) {
		case 1:
			PruebaRapida pruebaRapida = new PruebaRapida(paciente);

			planificarTestAntigenos(paciente, pruebaRapida);

			break;
		case 2:
			PruebaClasica pruebaClasica = new PruebaClasica(paciente);

			planificarTestAntigenos(paciente, pruebaClasica);

			break;
		case 3:
			TestPcr testPcr = new TestPcr(paciente);
			planificarPcr(paciente, testPcr);
			break;
		case 4:
			AnalisisSerologico analisisSerologico = new AnalisisSerologico(paciente);
			planificarAnalisisSerologico(paciente, analisisSerologico);
			break;
		default:
			break;
		}

	}

	/**
	 * Borra una persona
	 */
	private static void borrarPersona() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduzca DNI del usuario a eliminar");
		String dniEliminar = scanner.nextLine();
		Persona.baja(dniEliminar);
	}

	/**
	 * Modifica una persona
	 */
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

	/**
	 * Da de alta una nueva persona
	 */
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

	/**
	 * Planifica las dosis de la vacuna para el paciente
	 */
	private void ponerVacuna(Paciente paciente, Vacuna vacuna, Calendar fecha) throws ParseException {

		Scanner scanner = new Scanner(System.in);

		if (vacuna.getDosis() == 1) {
			paciente.setPrimeraDosis(fecha);
			System.out.println("Introduzca el enfermero que pondra la vacuna");

			String dni = scanner.nextLine();
			Enfermero enfermero = (Enfermero) Persona.getPersona(dni);

			paciente.setEnfermero(enfermero);
			paciente.setVacuna(vacuna);

			enfermero.vacunar(paciente);

			actualizarCantidadVacunas(vacuna);

		} else {
			Calendar fechaPrimeraVacuna = fecha;

			System.out.println("Introduzca la fecha de la segunda vacuna");

			String fecha2 = scanner.nextLine();
			Date segundaVacuna = formatoFecha.parse(fecha2);
			Calendar fecha2vacuna = Calendar.getInstance();
			fecha2vacuna.setTime(segundaVacuna);

			// Calculamos la diferencia en dias, pasando primero a milisegudos y luego a
			// dias
			long milisegundos = fecha.getTimeInMillis() - fecha2vacuna.getTimeInMillis();
			long diferenciaDias = TimeUnit.MILLISECONDS.toDays(milisegundos);

			if (Math.abs(diferenciaDias) < 21) {
				System.out.println("Hay menos de 21 dias");
			} else {
				paciente.setPrimeraDosis(fechaPrimeraVacuna);
				paciente.setSegundaDosis(fecha2vacuna);

				System.out.println("Introduzca el enfermero que pondra la vacuna");

				String dni = scanner.nextLine();
				Enfermero enfermero = (Enfermero) Persona.getPersona(dni);

				paciente.setEnfermero(enfermero);
				paciente.setVacuna(vacuna);

				enfermero.vacunar(paciente);

				actualizarCantidadVacunas(vacuna);

			}

		}

	}

	/**
	 * Para una vacuna le quitamos la cantidad que hemos puesto
	 */
	private static void actualizarCantidadVacunas(Vacuna vacuna) {

		if (vacuna instanceof Pfizer) {
			Pfizer.quitarStock();

		} else if (vacuna instanceof JohnsonJohnson) {
			JohnsonJohnson.quitarStock();

		}
		if (vacuna instanceof Moderna) {
			Moderna.quitarStock();

		}
	}

	/**
	 * Genera una vacuna aleatoria
	 */
	private static Vacuna cogerVacuna() {

		int aleatorio = (int) (Math.random() * 2);

		if (aleatorio == 0) {
			if (Pfizer.getStock() > 0) {
				return new Pfizer();
			}
		} else if (aleatorio == 1) {
			if (JohnsonJohnson.getStock() > 0) {
				return new JohnsonJohnson();
			}
		} else if (aleatorio == 2) {
			if (Moderna.getStock() > 0) {
				return new Moderna();
			}
		}
		return null;
	}

	/**
	 * Mira si todos los mayores de 65 estan ya vacunados
	 */
	private static boolean todosMayoresVacunados() {
		List<Persona> pacientes = Persona.getUsuarios();
		for (Persona persona : pacientes) {
			if (persona instanceof Paciente) {
				Paciente paciente = (Paciente) persona;
				if (paciente.getEdad() >= 65
						// && (!paciente.isPrimeraDosisPuesta() || !paciente.isSegundaDosisPuesta())) {
						&& !paciente.pautaCompleta()) {
					return false;
				}
			}
		}

		return true;

	}

	/**
	 * Mira si a los mayores de 65 no se les puede planificar una vacunacion porque
	 * estan en el periodo entre las 2 dosis
	 */
	static boolean mayoresEntreDosis(Calendar fecha) {
		List<Persona> pacientes = Persona.getUsuarios();
		for (Persona persona : pacientes) {
			if (persona instanceof Paciente) {
				Paciente paciente = (Paciente) persona;

				if (paciente.getEdad() >= 65) {
					if (!paciente.isPrimeraDosisPuesta()) {
						return false;
					} else if (paciente.isPrimeraDosisPuesta() && paciente.getSegundaDosis() != null
							&& !paciente.isSegundaDosisPuesta() && paciente.getSegundaDosis().before(fecha)) {
						return false;
					}

				}
			}
		}

		return true;

	}

	/**
	 * Planifica un test PCR
	 */
	private void planificarPcr(Paciente paciente, Prueba prueba) throws ParseException {

		Scanner scanner = new Scanner(System.in);

		boolean fechaValida = true;

		System.out.println("Cuando se hara la prueba? Ej: dd-mm-aaaa");
		String fechaPrueba = scanner.nextLine();
		Date fechaPruebaDate = formatoFecha.parse(fechaPrueba);
		Calendar fechaPrueba2 = Calendar.getInstance();
		fechaPrueba2.setTime(fechaPruebaDate);

		Calendar fechaConfinamiento = paciente.getFechaConfinamiento();
		if (fechaConfinamiento != null) {
			fechaConfinamiento.add(Calendar.DAY_OF_YEAR, 10);
			if (fechaConfinamiento.getTime().after(fechaPruebaDate)) {
				System.out.println("El paciente aun estara confinado");
				fechaValida = false;

			} else {
				fechaValida = true;
			}
		} else {
			fechaValida = true;
		}

		if (fechaValida) {
			if (prueba instanceof TestPcr) {
				TestPcr pcr = (TestPcr) prueba;

				if (paciente.puedeRealizarPcr(fechaPrueba2)) {
					System.out.println("Introduzca DNI enfermero");
					String dniEnfermero = scanner.nextLine();
					Enfermero enfermero = (Enfermero) Persona.getPersona(dniEnfermero);
					System.out.println("Introduzca DNI tecnico");
					String dniTecnico = scanner.nextLine();
					Tecnico tecnico = (Tecnico) Persona.getPersona(dniTecnico);

					if (enfermero.puedeRealizarPrueba(fechaPruebaDate)) {
						if (tecnico.puedeRealizarPrueba(fechaPruebaDate)) {
							prueba.setEnfermero(enfermero);
							prueba.setTecnicoLaboratorio(tecnico);
							prueba.setPaciente(paciente);
							prueba.setFecha(fechaPrueba2);
							paciente.establecerPrueba(prueba);
							enfermero.asignarPrueba(prueba);
							tecnico.asignarPrueba(prueba);
						} else {
							System.out.println("Ese tenico tiene demasiadas pruebas esa semana");
						}
					} else {
						System.out.println("Ese enfermero tiene demasiadas pruebas esa semana");

					}
				} else {
					System.out.println("El paciente no puede hacerse la prueba");
				}

			}
		}

	}

	/**
	 * Planifica un analisis serologico
	 */
	private void planificarAnalisisSerologico(Paciente paciente, Prueba prueba) throws ParseException {

		Scanner scanner = new Scanner(System.in);

		boolean fechaValida = true;

		System.out.println("Cuando se hara la prueba? Ej: dd-mm-aaaa");
		String fechaPrueba = scanner.nextLine();
		Date fechaPruebaDate = formatoFecha.parse(fechaPrueba);
		Calendar fechaPrueba2 = Calendar.getInstance();
		fechaPrueba2.setTime(fechaPruebaDate);
		Calendar fechaConfinamiento = paciente.getFechaConfinamiento();
		if (fechaConfinamiento != null) {
			fechaConfinamiento.add(Calendar.DAY_OF_YEAR, 10);
			if (fechaConfinamiento.getTime().after(fechaPruebaDate)) {
				System.out.println("El paciente aun estara confinado");
				fechaValida = false;

			} else {
				fechaValida = true;
			}
		} else {
			fechaValida = true;
		}

		if (fechaValida) {

			AnalisisSerologico analisis = (AnalisisSerologico) prueba;
			if (paciente.puedeRealizarAnalisisSerologico(fechaPrueba2)) {
				System.out.println("Introduzca DNI enfermero");
				String dniEnfermero = scanner.nextLine();
				Enfermero enfermero = (Enfermero) Persona.getPersona(dniEnfermero);
				System.out.println("Introduzca DNI tecnico");
				String dniTecnico = scanner.nextLine();
				Tecnico tecnico = (Tecnico) Persona.getPersona(dniTecnico);
				if (enfermero.puedeRealizarPrueba(fechaPruebaDate)) {
					if (tecnico.puedeRealizarPrueba(fechaPruebaDate)) {
						prueba.setEnfermero(enfermero);
						prueba.setTecnicoLaboratorio(tecnico);
						prueba.setPaciente(paciente);
						prueba.setFecha(fechaPrueba2);
						paciente.establecerPrueba(prueba);
						enfermero.asignarPrueba(prueba);
						tecnico.asignarPrueba(prueba);
					} else {
						System.out.println("Ese tenico tiene demasiadas pruebas esa semana");
					}
				} else {
					System.out.println("Ese enfermero tiene demasiadas pruebas esa semana");

				}
			} else {
				System.out.println("El paciente no puede hacerse la prueba");
			}
		}

	}

	/**
	 * Planifica Test de Antigenos, bien prueba rapida o clasica
	 */
	private void planificarTestAntigenos(Paciente paciente, Prueba prueba) throws ParseException {

		Scanner scanner = new Scanner(System.in);
		boolean fechaValida = true;

		System.out.println("Cuando se hara la prueba? Ej: dd-mm-aaaa");
		String fechaPrueba = scanner.nextLine();
		Date fechaPruebaDate = formatoFecha.parse(fechaPrueba);
		Calendar fechaPrueba2 = Calendar.getInstance();
		fechaPrueba2.setTime(fechaPruebaDate);

		Calendar fechaConfinamiento = paciente.getFechaConfinamiento();
		if (fechaConfinamiento != null) {
			fechaConfinamiento.add(Calendar.DAY_OF_YEAR, 10);
			if (fechaConfinamiento.getTime().after(fechaPruebaDate)) {
				System.out.println("El paciente aun estara confinado");
				fechaValida = false;

			} else {
				fechaValida = true;
			}
		} else {
			fechaValida = true;
		}
		if (fechaValida) {

			System.out.println("Introduzca DNI enfermero");
			String dniEnfermero = scanner.nextLine();
			Enfermero enfermero = (Enfermero) Persona.getPersona(dniEnfermero);
			System.out.println("Introduzca DNI tecnico");
			String dniTecnico = scanner.nextLine();
			Tecnico tecnico = (Tecnico) Persona.getPersona(dniTecnico);
			prueba.setEnfermero(enfermero);
			prueba.setTecnicoLaboratorio(tecnico);
			prueba.setPaciente(paciente);
			prueba.setFecha(fechaPrueba2);
			paciente.establecerPrueba(prueba);
			enfermero.asignarPrueba(prueba);
			tecnico.asignarPrueba(prueba);
		}

	}

	@Override
	public String toString() {
		return "Administrador [getDni()=" + getDni() + ", getNombre()=" + getNombre() + "]";
	}

}
