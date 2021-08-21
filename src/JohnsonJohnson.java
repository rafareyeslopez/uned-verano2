
public class JohnsonJohnson extends Vacuna {

	private static int cantidadStock = 0;

	public JohnsonJohnson() {
		super(1);
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
		return "JohnsonJohnson:\ngetDosis()=" + getDosis();
	}

}
