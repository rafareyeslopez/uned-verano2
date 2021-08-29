
import java.text.SimpleDateFormat;
import java.util.Scanner;

import personas.Administrador;
import personas.Enfermero;
import personas.Paciente;
import personas.Persona;
import personas.Tecnico;

/**
 * Clase principal del programa desde donde se inicia la ejecucion
 */
public class Clinica {

	/**
	 * Servira para dado una fecha como String leida convertirla a tipo Fecha, bien
	 * Date o Calendar
	 */
	static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

	public static void main(String[] args) {

		Enfermero enfermero1 = new Enfermero("1", "enfermero1", null, 20, null, null);
		Persona.alta(enfermero1);
		Enfermero enfermero2 = new Enfermero("2", "enfermero2", null, 20, null, null);
		Persona.alta(enfermero2);
		Enfermero enfermero3 = new Enfermero("3", "enfermero3", null, 20, null, null);
		Persona.alta(enfermero3);

		Tecnico tecnico1 = new Tecnico("4", "tecnico1", null, 20, null, null);
		Persona.alta(tecnico1);
		Tecnico tecnico2 = new Tecnico("5", "tenico2", null, 20, null, null);
		Persona.alta(tecnico2);
		Tecnico tecnico3 = new Tecnico("6", "enfermero3", null, 20, null, null);
		Persona.alta(tecnico3);

		Paciente paciente1 = new Paciente("7", "paciente1", null, 40, null, null);
		Persona.alta(paciente1);
//		Paciente paciente2 = new Paciente("8", "paciente2", 68);
//		GestorUsuarios.alta(paciente2);
//		Paciente paciente3 = new Paciente("9", "paciente2", 80);
//		GestorUsuarios.alta(paciente3);

		// prueba vacunacion rango
		Paciente paciente2 = new Paciente("8", "paciente2", null, 67, null, null);
		Persona.alta(paciente2);

		Paciente paciente3 = new Paciente("9", "paciente3", null, 80, null, null);
		Persona.alta(paciente3);

		// El admiistrador ha de crearse al menos para poder acceder al sistema
		Administrador admin = new Administrador("0", "admin", null, 40, null, null);
		Persona.alta(admin);

		int opcion = -1;

		// Usamos un bucle do-while para el menu, de esta forma se ira mostrando tras la
		// reailzacion de cada una de las acciones hasta que el usuario decida terminar
		// la ejecucion
		do {

			try {
				Scanner scan = new Scanner(System.in);

				System.out.println("Menu inicio");
				System.out.println("***************************");
				System.out.println("0. Salir");
				System.out.println("1. Administrador");
				System.out.println("2. Enfermero");
				System.out.println("3. Tecnico");

				opcion = Integer.parseInt(scan.nextLine());

				switch (opcion) {

				case 0:

					System.out.println("Aplicacion cerrada");

					break;

				case 1:
					System.out.println("Introduzca ID de administrador");
					String idAdministrador = scan.nextLine();
					try {
						Administrador administrador = (Administrador) Persona.getPersona(idAdministrador);
						administrador.menuAdmin();

					} catch (Exception e) {
						System.out.println("No es un administrador");
					}

					break;
				case 2:
					System.out.println("Introduzca ID de enfermero");
					String idEnfermero = scan.nextLine();
					try {
						Enfermero enfermero = (Enfermero) Persona.getPersona(idEnfermero);
						enfermero.menuEnfermero();

					} catch (Exception e) {
						System.out.println("No es un Enfermero");
					}

					break;
				case 3:
					System.out.println("Introduzca ID de tecnico");
					String idTecnico = scan.nextLine();
					try {
						Tecnico tecnico = (Tecnico) Persona.getPersona(idTecnico);
						tecnico.menuTecnico();

					} catch (Exception e) {
						System.out.println("No es un Tecnico");
					}

					break;
				default:

					System.out.println("Opcion no valida");
					break;
				}
			} catch (Exception e) {
				System.out.println("Error!");
			}

		} while (opcion != 0);

	}

}