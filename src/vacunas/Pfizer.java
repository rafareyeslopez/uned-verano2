package vacunas;

public class Pfizer extends Vacuna {
	private static int cantidadStock = 3;

	public Pfizer() {
		super(2);
	}

	public static int getStock() {
		return cantidadStock;
	}

	public static void quitarStock() {
		cantidadStock--;
	}

	public static void aumentarStock(int cantidad) {
		cantidadStock += cantidad;
	}

	@Override
	public String toString() {
		return "Pfizer:\ngetDosis()=" + getDosis();
	}

}
