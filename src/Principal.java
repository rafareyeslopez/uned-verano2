
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Principal {
	static String dniUsuarioActivo;
	static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	public static void main(String[] args) {

		Enfermero enfermero1 = new Enfermero("1", "enfermero1", "1p");
		Persona.alta(enfermero1);
		Enfermero enfermero2 = new Enfermero("2", "enfermero2", "2p");
		Persona.alta(enfermero2);
		Enfermero enfermero3 = new Enfermero("3", "enfermero3", "3p");
		Persona.alta(enfermero3);

		Tecnico tecnico1 = new Tecnico("4", "tecnico1", "4p");
		Persona.alta(tecnico1);
		Tecnico tecnico2 = new Tecnico("5", "tecnico2", "5p");
		Persona.alta(tecnico2);
		Tecnico tecnico3 = new Tecnico("6", "tecnico3", "6p");
		Persona.alta(tecnico3);

		Paciente paciente1 = new Paciente("7", "paciente1", 40);
		Persona.alta(paciente1);
//		Paciente paciente2 = new Paciente("8", "paciente2", 68);
//		GestorUsuarios.alta(paciente2);
//		Paciente paciente3 = new Paciente("9", "paciente2", 80);
//		GestorUsuarios.alta(paciente3);

		// prueba vacunacion rango
		Paciente paciente2 = new Paciente("8", "paciente2", 68);
		paciente2.setVacuna(new Pfizer());
		paciente2.setPrimeraDosisAdministada(true);
		paciente2.setPrimeraDosis(new Date(2021, 7, 1));
		paciente2.setSegundaDosis(new Date(2021, 7, 25));
		Persona.alta(paciente2);

		Paciente paciente3 = new Paciente("9", "paciente2", 80);
		paciente3.setVacuna(new Moderna());
		paciente3.setPrimeraDosisAdministada(true);
		paciente3.setPrimeraDosis(new Date(2021, 7, 5));
		paciente3.setSegundaDosis(new Date(2021, 8, 2));
		Persona.alta(paciente3);

		Administrador admin = new Administrador("0", "root", "0");
		Persona.alta(admin);

		int opcionMenu = -1;

		Scanner scanner = new Scanner(System.in);

		do {
			opcionMenu = mostrarMenuInicio(opcionMenu, scanner);

		} while (opcionMenu != 0);

	}

	private static int mostrarMenuInicio(int opcionMenu, Scanner scanner) {
		try {
			System.out.println("Elige una opcion");
			System.out.println("----------------");
			System.out.println("0. Salir");
			System.out.println("1. Indentificacion");

			opcionMenu = Integer.parseInt(scanner.nextLine());

			switch (opcionMenu) {
			case 0:

				System.out.println("Saliendo...");

				break;

			case 1:
				login(scanner);

				break;

			default:

				System.out.println("Opcion invalida");
				break;
			}
		} catch (Exception e) {
			System.out.println("Opcion invalida");
		}
		return opcionMenu;
	}

	private static void login(Scanner scanner) {
		System.out.println("Introduzca DNI");
		String dni = scanner.nextLine();
		System.out.println("Introduzca password");
		String password = scanner.nextLine();

		Persona persona = Persona.getUsuarios().get(dni);

		if (persona != null) {
			if (persona instanceof Administrador) {
				Administrador administradorActivo = (Administrador) persona;
				if (validarLogin(dni, password, administradorActivo)) {
					dniUsuarioActivo = dni;
					Administrador.mostrarMenu(dni);
				}

			} else if (persona instanceof Enfermero) {
				Enfermero enfermeroActivo = (Enfermero) persona;
				if (validarLogin(dni, password, enfermeroActivo)) {
					dniUsuarioActivo = dni;

					Enfermero.menuEnfermero();
				}

			} else if (persona instanceof Tecnico) {
				Tecnico tecnicoActivo = (Tecnico) persona;
				if (validarLogin(dni, password, tecnicoActivo)) {
					dniUsuarioActivo = dni;

					Tecnico.menuTecnico();
				}

			} else {
				System.out.println("DNI no valido");
			}

		} else {
			System.out.println("DNI no valido");
		}
	}

	private static boolean validarLogin(String dni, String password, Empleado empleado) {
		return password.equals(empleado.getPassword());
	}

	private static void menuSeleccionUsuario() {
		int opcionMenuSeleccionUsuario = 0;
		do {
			try {
				System.out.println("MENU SELECCION USUARIO");
				System.out.println("----------------------");
				System.out.println("Elige una opcion");
				System.out.println("----------------------");
				System.out.println("0. Volver");
				System.out.println("1. Administrator");
				System.out.println("2. Enfermero");
				System.out.println("3. Tecnico");

				Scanner scanner = new Scanner(System.in);

				opcionMenuSeleccionUsuario = Integer.parseInt(scanner.nextLine());

				switch (opcionMenuSeleccionUsuario) {
				case 0:

					System.out.println("Volviendo atras...");

					break;

				case 1:
					Administrador.mostrarMenu();
					break;
				case 2:
					Enfermero.menuEnfermero();
					break;
				case 3:
					Tecnico.menuTecnico();
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