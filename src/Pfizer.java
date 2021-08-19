
public class Pfizer extends Vacuna {
	private static int stock = 0;

	public Pfizer() {
		super(2);
	}

	public static void actualizarStock(int stockRecibido) {
		stock += stockRecibido;
	}

	public static void actualizarStockVacunaPlanificada() {
		stock--;
	}

	@Override
	public String toString() {
		return "Pfizer [getNumeroDosis()=" + getNumeroDosis() + "]";
	}

	public static int getStock() {
		return stock;
	}

}
