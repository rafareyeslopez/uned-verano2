package vacunas;

public class Moderna extends Vacuna {
	private static int cantidadStock = 3;

	public Moderna() {
		super(2);
	}

	public static int getStock() {
		return cantidadStock;
	}

	public static void quitarStock() {
		cantidadStock--;
	}

	public static void aumentarStock(int cantidad) {
		cantidadStock = cantidadStock + cantidad;
	}

	@Override
	public String toString() {
		return "Moderna [getDosis()=" + getDosis() + "stock=" + cantidadStock + "]";
	}

}
