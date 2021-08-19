import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Paciente extends Persona {

	private int edad;
	private boolean vacunaCompleta;
	private Date primeraDosis;
	private Date segundaDosis;
	private boolean primeraDosisAdministada;
	private boolean segundaDosisAdministada;
	private Vacuna vacuna;
	private Enfermero enfermeroVacunacion;
	private boolean confinado;
	private Calendar fechaConfinamiento;
	private Calendar fechaFinConfinamiento;

	public Paciente(String dni, String nombre, int edad) {
		super(dni, nombre);
		this.setEdad(edad);
		setVacunaCompleta(false);
		setConfinado(false);
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public boolean isVacunaCompleta() {
		return vacunaCompleta;
	}

	public void setVacunaCompleta(boolean vacunaCompleta) {
		this.vacunaCompleta = vacunaCompleta;
	}

	public Date getPrimeraDosis() {
		return primeraDosis;
	}

	public void setPrimeraDosis(Date primeraDosis) {
		this.primeraDosis = primeraDosis;
	}

	public Date getSegundaDosis() {
		return segundaDosis;
	}

	public void setSegundaDosis(Date segundaDosis) {
		this.segundaDosis = segundaDosis;
	}

	public Vacuna getVacuna() {
		return vacuna;
	}

	public void setVacuna(Vacuna vacuna) {
		this.vacuna = vacuna;
	}

	public boolean isPrimeraDosisAdministada() {
		return primeraDosisAdministada;
	}

	public void setPrimeraDosisAdministada(boolean primeraDosisAdministada) {
		this.primeraDosisAdministada = primeraDosisAdministada;
	}

	public boolean isSegundaDosisAdministada() {
		return segundaDosisAdministada;
	}

	public void setSegundaDosisAdministada(boolean segundaDosisAdministada) {
		this.segundaDosisAdministada = segundaDosisAdministada;
	}

	public Enfermero getEnfermeroVacunacion() {
		return enfermeroVacunacion;
	}

	public void setEnfermeroVacunacion(Enfermero enfermeroVacunacion) {
		this.enfermeroVacunacion = enfermeroVacunacion;
	}

	public boolean isConfinado() {
		return confinado;
	}

	public void setConfinado(boolean confinado) {
		this.confinado = confinado;
	}

	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String finConfinamiento = null;
		if (getFechaFinConfinamiento() != null) {

			finConfinamiento = formatter.format(getFechaFinConfinamiento().getTime());
		}

		return "Paciente [edad=" + edad + ", vacunaCompleta=" + vacunaCompleta + ", primeraDosis=" + primeraDosis
				+ ", segundaDosis=" + segundaDosis + ", primeraDosisAdministada=" + primeraDosisAdministada
				+ ", segundaDosisAdministada=" + segundaDosisAdministada + ", vacuna=" + vacuna
				+ ", enfermeroVacunacion=" + enfermeroVacunacion + ", confinado=" + confinado + ", fechaConfinamiento="
				+ getFechaConfinamiento() + ", fechaFinConfinamiento=" + finConfinamiento + ", getDni()=" + getDni()
				+ ", getNombre()=" + getNombre() + "]";
	}

	public Calendar getFechaFinConfinamiento() {
		return fechaFinConfinamiento;
	}

	public Calendar getFechaConfinamiento() {
		return fechaConfinamiento;
	}

	public void setFechaConfinamiento(Calendar fechaConfinamiento) {
		this.fechaConfinamiento = fechaConfinamiento;
	}

	public void setFechaFinConfinamiento(Calendar fechaFinConfinamiento) {
		this.fechaFinConfinamiento = fechaFinConfinamiento;
	}

}
