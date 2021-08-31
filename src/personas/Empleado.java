package personas;

public abstract class Empleado extends Persona {
	// TODO: borrar password
	private String password;

	public Empleado(String dni, String nombre, String apellidos, int edad, String direccion, String telefono) {
		super(dni, nombre, apellidos, edad, direccion, telefono);

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
