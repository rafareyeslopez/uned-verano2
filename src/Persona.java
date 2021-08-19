import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

public abstract class Persona {

	private String id;
	private String nombre;
	private String apellidos;
	private String edad;
	private String direccion;
	private String telefono;

	static List<Persona> usuarios = new ArrayList<Persona>();

	public Persona() {
	}

	public Persona(String id, String nombre, String apellidos, String edad, String direccion, String telefono) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public static void actualizarVacunacion(Paciente paciente) {
		if (paciente.isVacunaCompleta()) {
			PruebaDiagnostica.actualizarVacunaCompletaEnfermero(paciente);
		} else {
			List<Paciente> listaPacientesVacunacionEnfermero = PruebaDiagnostica.vacunacionesPaciente
					.get(paciente.getDni());
			for (Paciente pacienteVacunar : listaPacientesVacunacionEnfermero) {
				if (pacienteVacunar.getDni().equals(paciente.getDni())) {
					pacienteVacunar = paciente;
				}
			}

		}

	}

	public static List<Paciente> getPacientesConfinados() {
		List<Paciente> pacientes = obtenerPacientes();
		for (int i = 0; i < pacientes.size(); i++) {
			Paciente paciente = pacientes.get(i);
			if (!paciente.isConfinado()) {
				pacientes.remove(i);
			}
		}
		return pacientes;
	}

	public static void confinar(Paciente paciente, Date date) {
		paciente.setConfinado(true);
		Calendar fechaConfinamiento = Calendar.getInstance();
		fechaConfinamiento.setTime(date);
		Calendar fechaFinConfinamiento = Calendar.getInstance();
		fechaFinConfinamiento.add(Calendar.DAY_OF_YEAR, 10);
		paciente.setFechaConfinamiento(fechaConfinamiento);
		paciente.setFechaFinConfinamiento(fechaFinConfinamiento);
		usuarios.put(paciente.getDni(), paciente);
	}

	public static List<Tecnico> obtenerTecnicos() {

		List<Tecnico> listaTecnicos = new ArrayList<Tecnico>();

		Set<String> keySet = usuarios.keySet();

		for (String dni : keySet) {
			Persona persona = usuarios.get(dni);

			if (persona instanceof Tecnico) {
				listaTecnicos.add((Tecnico) persona);

			}

		}
		return listaTecnicos;

	}

	public static List<Paciente> obtenerPacientes() {

		List<Paciente> listaPacientes = new ArrayList<Paciente>();

		Set<String> keySet = usuarios.keySet();

		for (String dni : keySet) {
			Persona persona = usuarios.get(dni);

			if (persona instanceof Paciente) {
				listaPacientes.add((Paciente) persona);

			}

		}
		return listaPacientes;

	}

	public static List<Enfermero> obtenerEnfermeros() {

		List<Enfermero> listaEnfermeros = new ArrayList<Enfermero>();

		Set<String> keySet = usuarios.keySet();

		for (String dni : keySet) {
			Persona persona = usuarios.get(dni);

			if (persona instanceof Enfermero) {
				listaEnfermeros.add((Enfermero) persona);

			}

		}
		return listaEnfermeros;

	}

	public static Persona getPersona(String id) {
		for (Persona persona : usuarios) {
			if (persona.getId().equals(id)) {
				return persona;
			}
		}
		return null;

	}

	public static void baja(String dni) {
		usuarios.remove(dni);

	}

	public static void modificacion(Persona persona) {
		usuarios.put(persona.getDni(), persona);

	}

	public static void alta(Persona persona) {
		if (usuarios.containsKey(persona.getDni())) {
			throw new IllegalArgumentException("El usuario ya esta dado de alta");
		} else {
			usuarios.put(persona.getDni(), persona);
		}

	}

	public static List<Persona> getUsuarios() {
		return usuarios;
	}

}
