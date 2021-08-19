
public class Moderna extends Vacuna {
	private static int stock = 0;

	public Moderna() {
		super(2);
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
		return "Moderna [getNumeroDosis()=" + getNumeroDosis() + "]";
	}

}
