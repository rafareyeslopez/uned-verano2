package vacunas;

public class JohnsonJohnson extends Vacuna {

	private static int cantidadStock = 3;

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
		cantidadStock = cantidadStock + cantidad;
	}

	@Override
	public String toString() {
		return "JohnsonJohnson [getDosis()=" + getDosis() + "stock=" + cantidadStock + "]";
	}

}
