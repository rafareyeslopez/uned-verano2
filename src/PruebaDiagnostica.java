import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class PruebaDiagnostica {
	private static int contadorId = 0;
	private int id;
	private Date fecha;
	private Paciente paciente;
	private Enfermero enfermero;
	private Tecnico tecnicoLaboratorio;
	private boolean realizada;
	public static HashMap<String, List<Paciente>> vacunacionesPaciente = new HashMap<String, List<Paciente>>();
	static HashMap<String, List<PruebaDiagnostica>> pruebasPaciente = new HashMap<String, List<PruebaDiagnostica>>();
	static HashMap<String, List<PruebaDiagnostica>> pruebasTecnico = new HashMap<String, List<PruebaDiagnostica>>();
	static HashMap<String, List<PruebaDiagnostica>> pruebasEnfermero = new HashMap<String, List<PruebaDiagnostica>>();
	static HashMap<Integer, PruebaDiagnostica> pruebas = new HashMap<Integer, PruebaDiagnostica>();

	public PruebaDiagnostica(Paciente paciente) {
		contadorId++;
		id = contadorId;
		setFecha(new Date());
		this.setPaciente(paciente);
		realizada = false;

	}

	public boolean isRealizada() {
		return realizada;
	}

	public void setRealizada(boolean realizada) {
		this.realizada = realizada;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Enfermero getEnfermero() {
		return enfermero;
	}

	public void setEnfermero(Enfermero enfermero) {
		this.enfermero = enfermero;
	}

	public Tecnico getTecnicoLaboratorio() {
		return tecnicoLaboratorio;
	}

	public void setTecnicoLaboratorio(Tecnico tecnicoLaboratorio) {
		this.tecnicoLaboratorio = tecnicoLaboratorio;
	}

	public int getId() {
		return id;
	}

	public static void actualizarPruebaRealizadaTecnico(PruebaDiagnostica prueba) {
		pruebasTecnico.remove(prueba.getTecnicoLaboratorio().getDni());
		pruebas.put(prueba.getId(), prueba);
		List<PruebaDiagnostica> listaPruebasPaciente = pruebasPaciente.get(prueba.getPaciente().getDni());
		for (PruebaDiagnostica pruebaDiagnostica : listaPruebasPaciente) {
			if (pruebaDiagnostica.getId() == prueba.getId()) {
				pruebaDiagnostica = prueba;
			}
		}

	}

	public static void actualizarVacunaCompletaEnfermero(Paciente paciente) {
		vacunacionesPaciente.remove(paciente.getDni());
	}

	public static void actualizarPruebaRealizadaEnfermero(PruebaDiagnostica prueba) {
		pruebasEnfermero.remove(prueba.getEnfermero().getDni());
		pruebas.put(prueba.getId(), prueba);
		List<PruebaDiagnostica> listaPruebasPaciente = pruebasPaciente.get(prueba.getPaciente().getDni());
		for (PruebaDiagnostica pruebaDiagnostica : listaPruebasPaciente) {
			if (pruebaDiagnostica.getId() == prueba.getId()) {
				pruebaDiagnostica = prueba;
			}
		}

	}

	public static void registrarPruebaPaciente(PruebaDiagnostica prueba, Enfermero enfermeroAsignado,
			Tecnico tecnicoAsignado) {
		boolean puedeRealizarPrueba = true;
		if (prueba instanceof TestPcr) {
			TestPcr testPcr = (TestPcr) prueba;
			List<PruebaDiagnostica> list = pruebasPaciente.get(prueba.getPaciente().getDni());
			if (list != null) {

				for (PruebaDiagnostica pruebaDiagnostica : list) {
					if (pruebaDiagnostica instanceof TestPcr) {

						TestPcr testPcrAnterior = (TestPcr) pruebaDiagnostica;

						long diffInMillies = Math
								.abs(System.currentTimeMillis() - testPcrAnterior.getFecha().getTime());
						long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
						if (diff < 15) {
							System.out.println(
									"No se puede realizar la prueba! Ultimo test PCR realizado fue: " + testPcr);
							puedeRealizarPrueba = false;
						}

					}
				}
			}

		} else if (prueba instanceof AnalisisSerologico) {

			AnalisisSerologico analisisSerologico = (AnalisisSerologico) prueba;
			List<PruebaDiagnostica> list = pruebasPaciente.get(prueba.getPaciente().getDni());
			if (list != null) {

				for (PruebaDiagnostica pruebaDiagnostica : list) {
					if (pruebaDiagnostica instanceof AnalisisSerologico) {

						AnalisisSerologico analisisSerologicoAnterior = (AnalisisSerologico) pruebaDiagnostica;

						long diffInMillies = Math
								.abs(System.currentTimeMillis() - analisisSerologicoAnterior.getFecha().getTime());
						long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
						if (diff < 180) {
							System.out.println(
									"No se puede realizar la prueba! Ultimo Analisis serologico realizado fue: "
											+ analisisSerologicoAnterior);
							puedeRealizarPrueba = false;
						}

					}
				}
			}

		}

		if (puedeRealizarPrueba) {
			prueba.setEnfermero(enfermeroAsignado);
			prueba.setTecnicoLaboratorio(tecnicoAsignado);
			pruebas.put(prueba.getId(), prueba);
			List<PruebaDiagnostica> listaPruebasPaciente = pruebasPaciente.get(prueba.getPaciente().getDni());
			if (listaPruebasPaciente == null) {
				listaPruebasPaciente = new ArrayList<PruebaDiagnostica>();
			}
			listaPruebasPaciente.add(prueba);
			pruebasPaciente.put(prueba.getPaciente().getDni(), listaPruebasPaciente);

			List<PruebaDiagnostica> listaPruebasEnfermero = pruebasEnfermero.get(enfermeroAsignado.getDni());
			if (listaPruebasEnfermero == null) {
				listaPruebasEnfermero = new ArrayList<PruebaDiagnostica>();
			}
			listaPruebasEnfermero.add(prueba);
			pruebasEnfermero.put(enfermeroAsignado.getDni(), listaPruebasEnfermero);

			List<PruebaDiagnostica> listaPruebasTecnico = pruebasTecnico.get(tecnicoAsignado.getDni());
			if (listaPruebasTecnico == null) {
				listaPruebasTecnico = new ArrayList<PruebaDiagnostica>();
			}
			listaPruebasTecnico.add(prueba);
			pruebasTecnico.put(tecnicoAsignado.getDni(), listaPruebasTecnico);

		}

	}

	static boolean tecnicoDisponible(List<PruebaDiagnostica> listaPruebasTecnico, Date fechaPrueba) {
		int pruebasSemana = 0;
		for (PruebaDiagnostica pruebaDiagnostica : listaPruebasTecnico) {
			Calendar calendarPruebaPlanificar = Calendar.getInstance();
			calendarPruebaPlanificar.setTime(fechaPrueba);
			Calendar calendarPrueba = Calendar.getInstance();
			calendarPrueba.setTime(pruebaDiagnostica.getFecha());
			if (calendarPruebaPlanificar.get(Calendar.WEEK_OF_YEAR) == calendarPrueba.get(Calendar.WEEK_OF_YEAR)) {
				pruebasSemana++;
			}
		}

		return pruebasSemana < 4;
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

	public static List<Tecnico> obtenerTecnicosDisponibles(Date fechaPruebaDate) {
		List<Tecnico> tecnicosDisponibles = new ArrayList<Tecnico>();

		List<Tecnico> listaTecnicos = Persona.obtenerTecnicos();

		for (Tecnico tecnico : listaTecnicos) {
			List<PruebaDiagnostica> listaPruebasTecnico = pruebasTecnico.get(tecnico.getDni());
			if (listaPruebasTecnico != null) {
				if (PruebaDiagnostica.tecnicoDisponible(listaPruebasTecnico, fechaPruebaDate)) {
					tecnicosDisponibles.add((Tecnico) Persona.getUsuarios().get(tecnico.getDni()));
				}
			} else {
				tecnicosDisponibles.add((Tecnico) Persona.getUsuarios().get(tecnico.getDni()));
			}
		}
		return tecnicosDisponibles;

	}

	public static List<Enfermero> obtenerEnfermerosDisponibles(Date fechaPrueba) {
		List<Enfermero> enfermerosDisponibles = new ArrayList<Enfermero>();

		List<Enfermero> listaEnfemeros = Persona.obtenerEnfermeros();

		for (Enfermero enfermero : listaEnfemeros) {
			List<PruebaDiagnostica> listaPruebasEnfermero = pruebasEnfermero.get(enfermero.getDni());
			if (listaPruebasEnfermero != null) {
				if (PruebaDiagnostica.enfermeroDisponible(listaPruebasEnfermero, fechaPrueba)) {
					enfermerosDisponibles.add((Enfermero) Persona.getUsuarios().get(enfermero.getDni()));
				}
			} else {
				enfermerosDisponibles.add((Enfermero) Persona.getUsuarios().get(enfermero.getDni()));
			}
		}
		return enfermerosDisponibles;
	}

	public static List<PruebaDiagnostica> obtenerPruebasAsignadasTecnico(String dni) {
		return pruebasTecnico.get(dni);
	}

	public static List<PruebaDiagnostica> pruebasEnfermero(String id) {
		return pruebasEnfermero.get(id);
	}

	public static PruebaDiagnostica obtenerPrueba(Integer idPrueba) {
		return pruebas.get(idPrueba);
	}

}
