
public class JohnsonJohnson extends Vacuna {

	private static int stock = 0;

	public JohnsonJohnson() {
		super(1);
	}

	public static int getStock() {
		return stock;
	}

	public static void actualizarStockVacunaPlanificada() {
		stock--;
	}

	public static void actualizarStock(int stockRecibido) {
		stock += stockRecibido;
	}

	@Override
	public String toString() {
		return "Johnson [getNumeroDosis()=" + getNumeroDosis() + "]";
	}

}
