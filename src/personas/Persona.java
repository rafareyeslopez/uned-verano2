package personas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class Persona {

	/**
	 * La lista de los diferentes usuarios
	 */
	static List<Persona> usuarios = new ArrayList<Persona>();

	private String dni;
	private String nombre;
	private String apellidos;
	private int edad;
	private String direccion;
	private String telefono;

	public Persona(String dni, String nombre, String apellidos, int edad, String direccion, String telefono) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
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

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
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

	/**
	 * Obtiene la lista de los pacientes confinados
	 */
	public static List<Paciente> getPacientesConfinados() {
		List<Paciente> confinados = new ArrayList<Paciente>();
		List<Persona> lista = Persona.getUsuarios();
		for (Persona persona : lista) {
			if (persona instanceof Paciente) {
				Paciente paciente = (Paciente) persona;

				if (paciente.isConfinado()) {
					confinados.add(paciente);
				}
			}
		}
		return confinados;
	}

	/**
	 * Confina a un paciente
	 */
	public static void confinar(Paciente paciente, Calendar fecha) {
		paciente.setConfinado(true);
		paciente.setFechaConfinamiento(fecha);
		actualizarPersona(paciente);
	}

	/**
	 * Dada una persona la actualiza en la lista
	 */
	public static void actualizarPersona(Persona personaActualizada) {
		modificacion(personaActualizada);

	}

	/**
	 * Dado un dni devuelve la persona
	 */
	public static Persona getPersona(String dni) {
		for (Persona persona : usuarios) {
			if (persona.getDni().equals(dni)) {
				return persona;
			}
		}
		return null;

	}

	/**
	 * Elimina a una persona
	 */
	public static void baja(String dni) {
		Persona persona = getPersona(dni);
		usuarios.remove(persona);

	}

	/**
	 * Elimina a una persona
	 */
	public static void modificacion(Persona persona) {
		baja(persona.getDni());
		usuarios.add(persona);

	}

	/**
	 * Da de alta a una persona
	 */
	public static void alta(Persona persona) {
		usuarios.add(persona);

	}

	/**
	 * Devuelve la lista de las personas
	 */
	public static List<Persona> getUsuarios() {
		return usuarios;
	}

	/**
	 * Dos personas son iguales si tienen el mismo dni
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Persona))
			return false;
		Persona other = (Persona) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

}
