
public class Moderna extends Vacuna {
	private static int cantidadStock = 0;

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
		cantidadStock += cantidad;
	}

	@Override
	public String toString() {
		return "Moderna:\ngetDosis()=" + getDosis();
	}

}
