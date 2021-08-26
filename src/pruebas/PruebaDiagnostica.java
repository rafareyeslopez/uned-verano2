package pruebas;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import personas.Enfermero;
import personas.Paciente;
import personas.Tecnico;

public abstract class PruebaDiagnostica {
	private static int contadorId = 0;
	private int id;
	private Date fecha;
	private Paciente paciente;
	private Enfermero enfermero;
	private Tecnico tecnicoLaboratorio;
	private boolean realizada;
//	public static HashMap<String, List<Paciente>> vacunacionesPaciente = new HashMap<String, List<Paciente>>();
//	static HashMap<String, List<PruebaDiagnostica>> pruebasPaciente = new HashMap<String, List<PruebaDiagnostica>>();
//	static HashMap<String, List<PruebaDiagnostica>> pruebasTecnico = new HashMap<String, List<PruebaDiagnostica>>();
//	static HashMap<String, List<PruebaDiagnostica>> pruebasEnfermero = new HashMap<String, List<PruebaDiagnostica>>();
//	static HashMap<Integer, PruebaDiagnostica> pruebas = new HashMap<Integer, PruebaDiagnostica>();

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

//	public static void actualizarPruebaRealizadaTecnico(PruebaDiagnostica prueba) {
//		pruebasTecnico.remove(prueba.getTecnicoLaboratorio().getDni());
//		pruebas.put(prueba.getId(), prueba);
//		List<PruebaDiagnostica> listaPruebasPaciente = pruebasPaciente.get(prueba.getPaciente().getDni());
//		for (PruebaDiagnostica pruebaDiagnostica : listaPruebasPaciente) {
//			if (pruebaDiagnostica.getId() == prueba.getId()) {
//				pruebaDiagnostica = prueba;
//			}
//		}
//
//	}

//	public static void actualizarVacunaCompletaEnfermero(Paciente paciente) {
//		vacunacionesPaciente.remove(paciente.getDni());
//	}

//	public static void actualizarPruebaRealizadaEnfermero(PruebaDiagnostica prueba) {
//		pruebasEnfermero.remove(prueba.getEnfermero().getDni());
//		pruebas.put(prueba.getId(), prueba);
//		List<PruebaDiagnostica> listaPruebasPaciente = pruebasPaciente.get(prueba.getPaciente().getDni());
//		for (PruebaDiagnostica pruebaDiagnostica : listaPruebasPaciente) {
//			if (pruebaDiagnostica.getId() == prueba.getId()) {
//				pruebaDiagnostica = prueba;
//			}
//		}
//
//	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PruebaDiagnostica))
			return false;
		PruebaDiagnostica other = (PruebaDiagnostica) obj;
		if (id != other.id)
			return false;
		return true;
	}

//	public static List<PruebaDiagnostica> obtenerPruebasAsignadasTecnico(String dni) {
//		return pruebasTecnico.get(dni);
//	}
//
//	public static List<PruebaDiagnostica> pruebasEnfermero(String id) {
//		return pruebasEnfermero.get(id);
//	}
//
//	public static PruebaDiagnostica obtenerPrueba(Integer idPrueba) {
//		return pruebas.get(idPrueba);
//	}

}
