import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Paciente extends Persona {

	private List<PruebaDiagnostica> pruebas;

	public Paciente(String dni, String nombre, String apellidos, int edad, String direccion, String telefono) {
		super(dni, nombre, apellidos, edad, direccion, telefono);
		setVacunaCompleta(false);
		setConfinado(false);
		pruebas = new ArrayList<PruebaDiagnostica>();
	}

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

	public static void pacienteVacunado(Paciente paciente) {
		// TODO Auto-generated method stub

	}

	public boolean puedeRealizarPrueba(Date fechaPruebaDate) {
		for (PruebaDiagnostica pruebaDiagnostica : pruebas) {
			if (pruebaDiagnostica instanceof TestPcr) {

				TestPcr testPcrAnterior = (TestPcr) pruebaDiagnostica;

				long diffInMillies = Math.abs(System.currentTimeMillis() - testPcrAnterior.getFecha().getTime());
				long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				if (diff < 15) {
					System.out.println(
							"No se puede realizar la prueba! Ultimo test PCR realizado fue: " + testPcrAnterior);
					return false;
				}

			}
		}

		for (PruebaDiagnostica pruebaDiagnostica : pruebas) {
			if (pruebaDiagnostica instanceof AnalisisSerologico) {

				AnalisisSerologico analisisSerologicoAnterior = (AnalisisSerologico) pruebaDiagnostica;

				long diffInMillies = Math
						.abs(System.currentTimeMillis() - analisisSerologicoAnterior.getFecha().getTime());
				long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				if (diff < 180) {
					System.out.println("No se puede realizar la prueba! Ultimo Analisis serologico realizado fue: "
							+ analisisSerologicoAnterior);
					return false;
				}

			}
		}
		return true;
	}

	public void asignarPrueba(PruebaDiagnostica prueba) {
		pruebas.add(prueba);
	}

	public void actualizarPrueba(PruebaDiagnostica prueba) {
		for (PruebaDiagnostica pruebaDiagnostica : pruebas) {
			if (pruebaDiagnostica.equals(prueba)) {
				pruebaDiagnostica = prueba;
			}
		}
	}

	@Override
	public String toString() {
		return "Paciente [pruebas=" + pruebas + ", vacunaCompleta=" + vacunaCompleta + ", primeraDosis=" + primeraDosis
				+ ", segundaDosis=" + segundaDosis + ", primeraDosisAdministada=" + primeraDosisAdministada
				+ ", segundaDosisAdministada=" + segundaDosisAdministada + ", vacuna=" + vacuna + ", confinado="
				+ confinado + ", fechaConfinamiento=" + fechaConfinamiento + ", fechaFinConfinamiento="
				+ fechaFinConfinamiento + "]";
	}

}
