import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Tecnico extends Empleado {

	public Tecnico(String dni, String nombre, String apellidos, int edad, String direccion, String telefono) {
		super(dni, nombre, apellidos, edad, direccion, telefono);
		pruebasTecnico = new ArrayList<PruebaDiagnostica>();
	}

	private List<PruebaDiagnostica> pruebasTecnico;

	public void menuTecnico() {
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
					verPruebasTecnico();
					break;
				case 2:
					actualizarResultado();
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

	void actualizarResultado() throws ParseException {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduzca ID de la prueba a registrar");
		int idPrueba = Integer.parseInt(scanner.nextLine());
		PruebaDiagnostica prueba = getPrueba(idPrueba);
		if (prueba != null) {
			Tecnico tecnico = (Tecnico) Persona.getPersona(prueba.getTecnicoLaboratorio().getDni());
			Paciente paciente = (Paciente) Persona.getPersona(prueba.getPaciente().getDni());
			if (prueba instanceof PruebaRapida) {
				PruebaRapida pruebaRapida = (PruebaRapida) prueba;
				System.out.println(pruebaRapida);
				System.out.println("Indique resultado, positivo 'P', negativo 'N'");
				String resultado = scanner.nextLine();
				if (resultado.equals("P")) {
					pruebaRapida.setResultado(true);
					tecnico.pruebaRealizada(pruebaRapida);
					Persona.confinar(paciente, prueba.getFecha());
					paciente.actualizarPrueba(pruebaRapida);
				} else if (resultado.equals("N")) {
					pruebaRapida.setResultado(false);
					tecnico.pruebaRealizada(pruebaRapida);
					paciente.actualizarPrueba(pruebaRapida);
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
					tecnico.pruebaRealizada(pruebaClasica);
					Persona.confinar(paciente, prueba.getFecha());
					paciente.actualizarPrueba(pruebaClasica);
				} else if (resultado.equals("N")) {
					pruebaClasica.setResultado(false);
					tecnico.pruebaRealizada(pruebaClasica);
					paciente.actualizarPrueba(pruebaClasica);
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
					Persona.confinar(paciente, prueba.getFecha());

					tecnico.pruebaRealizada(testPcr);
					paciente.actualizarPrueba(testPcr);
				} else if (resultado.equals("N")) {
					testPcr.setPositivo(false);
					tecnico.pruebaRealizada(testPcr);
					paciente.actualizarPrueba(testPcr);
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
					tecnico.pruebaRealizada(analisisSerologico);
					paciente.actualizarPrueba(analisisSerologico);
				} else {
					System.out.println("Resultado no valido para prueba");
				}
			}

		} else {
			System.out.println("Prueba no encontrada!");
		}
	}

	private PruebaDiagnostica getPrueba(int pruebaId) {
		for (PruebaDiagnostica prueba : pruebasTecnico) {
			if (prueba.getId() == pruebaId) {
				return prueba;
			}
		}
		return null;

	}

	private void pruebaRealizada(PruebaDiagnostica prueba) {
		pruebasTecnico.remove(prueba);

	}

	private void verPruebasTecnico() {
		if (pruebasTecnico != null) {
			System.out.println("Mis pruebas a realizar:");
			for (PruebaDiagnostica pruebaDiagnostica : pruebasTecnico) {
				System.out.println(pruebaDiagnostica);
			}
		}

	}

	public boolean puedeRealizarPrueba(Date fechaPruebaDate) {
		int pruebasSemana = 0;
		for (PruebaDiagnostica pruebaDiagnostica : pruebasTecnico) {
			Calendar calendarPruebaPlanificar = Calendar.getInstance();
			calendarPruebaPlanificar.setTime(fechaPruebaDate);
			Calendar calendarPrueba = Calendar.getInstance();
			calendarPrueba.setTime(pruebaDiagnostica.getFecha());
			if (calendarPruebaPlanificar.get(Calendar.WEEK_OF_YEAR) == calendarPrueba.get(Calendar.WEEK_OF_YEAR)) {
				pruebasSemana++;
				if (pruebasSemana >= 4) {
					return false;
				}
			}
		}

		return true;
	}

	public void asignarPrueba(PruebaDiagnostica prueba) {
		pruebasTecnico.add(prueba);
	}

	public List<PruebaDiagnostica> getPruebas() {
		return pruebasTecnico;
	}

	@Override
	public String toString() {
		return "Tecnico [getPassword()=" + getPassword() + ", getDni()=" + getDni() + ", getNombre()=" + getNombre()
				+ ", getApellidos()=" + getApellidos() + ", getEdad()=" + getEdad() + ", getDireccion()="
				+ getDireccion() + ", getTelefono()=" + getTelefono() + "]";
	}

}
