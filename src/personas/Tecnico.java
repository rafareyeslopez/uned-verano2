package personas;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import pruebas.AnalisisSerologico;
import pruebas.Prueba;
import pruebas.PruebaClasica;
import pruebas.PruebaRapida;
import pruebas.TestPcr;

public class Tecnico extends Empleado {

	public Tecnico(String dni, String nombre, String apellidos, int edad, String direccion, String telefono) {
		super(dni, nombre, apellidos, edad, direccion, telefono);
		pruebasTecnico = new ArrayList<Prueba>();
	}

	private List<Prueba> pruebasTecnico;

	/**
	 * Muestra el menu del tecnico
	 */
	public void menuTecnico() {
		int opcion = 0;
		do {
			try {

				System.out.println("HOLA TECNICO");
				System.out.println("*******************");
				System.out.println("0. Salir");
				System.out.println("1. Ver pacientes");
				System.out.println("2. Analizar prueba");

				Scanner scanner = new Scanner(System.in);

				opcion = Integer.parseInt(scanner.nextLine());

				switch (opcion) {
				case 0:

					System.out.println("Sesion tecnico terminada");

					break;

				case 1:
					misPacientes();
					break;
				case 2:
					analizarPrueba();
					break;
				default:

					System.out.println("Opcion invalida");
					break;
				}
			} catch (Exception e) {
				System.out.println("Opcion invalida");
			}
		} while (opcion != 0);
	}

	/**
	 * Analiza una prueba y establece su resultado
	 */
	void analizarPrueba() throws ParseException {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduzca la prueba a analizar");
		int idPrueba = Integer.parseInt(scanner.nextLine());

		Prueba prueba = getPrueba(idPrueba);
		if (prueba != null) {

			Paciente paciente = (Paciente) Persona.getPersona(prueba.getPaciente().getDni());

			if (prueba instanceof PruebaRapida) {
				PruebaRapida pruebaRapida = (PruebaRapida) prueba;
				System.out.println(pruebaRapida);
				System.out.println("Positivo / Negativo (+/-)");
				String resultado = scanner.nextLine();
				if (resultado.equals("+")) {
					pruebaRapida.setPositivo(true);
					pruebaRealizada(pruebaRapida);
					Persona.confinar(paciente, pruebaRapida.getFecha());
					paciente.actualizarPrueba(pruebaRapida);

				} else if (resultado.equals("-")) {
					pruebaRapida.setPositivo(false);
					pruebaRealizada(pruebaRapida);
					paciente.actualizarPrueba(pruebaRapida);
					Persona.actualizarPersona(paciente);
				} else {
					System.out.println("Valor invalido");
				}
			} else if (prueba instanceof PruebaClasica) {
				PruebaClasica pruebaClasica = (PruebaClasica) prueba;
				System.out.println(pruebaClasica);
				System.out.println("Positivo / Negativo (+/-)");
				String resultado = scanner.nextLine();
				if (resultado.equals("+")) {
					pruebaClasica.setPositivo(true);
					pruebaRealizada(pruebaClasica);
					Persona.confinar(paciente, pruebaClasica.getFecha());
					paciente.actualizarPrueba(pruebaClasica);

				} else if (resultado.equals("-")) {
					pruebaClasica.setPositivo(false);
					pruebaRealizada(pruebaClasica);
					paciente.actualizarPrueba(pruebaClasica);
					Persona.actualizarPersona(paciente);
				} else {
					System.out.println("Valor invalido");
				}
			} else if (prueba instanceof TestPcr) {
				TestPcr testPcr = (TestPcr) prueba;
				System.out.println(testPcr);
				System.out.println("Positivo / Negativo (+/-)");
				String resultado = scanner.nextLine();
				if (resultado.equals("+")) {
					testPcr.setPositivo(true);
					pruebaRealizada(testPcr);
					Persona.confinar(paciente, testPcr.getFecha());
					paciente.actualizarPrueba(testPcr);

				} else if (resultado.equals("-")) {
					testPcr.setPositivo(false);
					pruebaRealizada(testPcr);
					paciente.actualizarPrueba(testPcr);
					Persona.actualizarPersona(paciente);
				} else {
					System.out.println("Valor invalido");
				}
			} else if (prueba instanceof AnalisisSerologico) {
				AnalisisSerologico analisisSerologico = (AnalisisSerologico) prueba;
				System.out.println(analisisSerologico);
				System.out.println("Indique valor entre 0 y 10");
				int resultado = Integer.parseInt(scanner.nextLine());
				if (resultado >= 0 || resultado <= 10) {
					analisisSerologico.setValorAnticuerpos(resultado);
					pruebaRealizada(analisisSerologico);
					paciente.actualizarPrueba(analisisSerologico);
					Persona.actualizarPersona(paciente);
				} else {
					System.out.println("Valor invalido");
				}
			}

		}
	}

	/**
	 * Obtiene una prueba
	 */
	private Prueba getPrueba(int id) {
		for (Prueba prueba : pruebasTecnico) {
			if (prueba.getId() == id) {
				return prueba;
			}
		}
		return null;

	}

	/**
	 * Actualiza una prueba como hecha, la borra de su lista de pruebas para hacer
	 */
	private void pruebaRealizada(Prueba prueba) {
		pruebasTecnico.remove(prueba);

	}

	/**
	 * Obtiene las pruebas a realizar por el tecnico
	 *
	 */
	private void misPacientes() {
		if (pruebasTecnico != null) {
			System.out.println("Mis pruebas a realizar:");
			for (Prueba pruebaDiagnostica : pruebasTecnico) {
				System.out.println(pruebaDiagnostica);
			}
		}

	}

	/**
	 * Mira si un tecnico puede realizar una prueba en una fecha
	 *
	 */
	public boolean puedeRealizarPrueba(Date fechaPrueba) {
		int contadorPruebas = 0;

		for (Prueba pruebaDiagnostica : pruebasTecnico) {

			Calendar fechaPrueba2 = Calendar.getInstance();
			fechaPrueba2.setTime(fechaPrueba);
			Calendar fechaOtraPrueba = Calendar.getInstance();
			fechaOtraPrueba.setTime(pruebaDiagnostica.getFecha().getTime());

			if (fechaPrueba2.get(Calendar.WEEK_OF_YEAR) == fechaOtraPrueba.get(Calendar.WEEK_OF_YEAR)) {
				contadorPruebas++;
				if (contadorPruebas == 4) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Establece una nueva prueba a realizar al tecnico
	 *
	 */
	public void asignarPrueba(Prueba prueba) {
		pruebasTecnico.add(prueba);
	}

	/**
	 * Obtiene las pruebas a realizar por el tecnico
	 */
	public List<Prueba> getPruebas() {
		return pruebasTecnico;
	}

	@Override
	public String toString() {
		return "Tecnico [pruebasTecnico=" + pruebasTecnico + ", getDni()=" + getDni() + ", getNombre()=" + getNombre()
				+ "]";
	}

}
