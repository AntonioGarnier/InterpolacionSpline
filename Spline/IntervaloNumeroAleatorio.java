import java.util.Random;

public class IntervaloNumeroAleatorio {

	private Random aleatorio = new Random ();

	public IntervaloNumeroAleatorio () {

	}

	public int getNumeroAleatorio(int minimo, int maximo) {
		return minimo + getAleatorio().nextInt(maximo + 1 - minimo);
	}

	/**
	 * @return the aleatorio
	 */
	public Random getAleatorio() {
		return aleatorio;
	}

	/**
	 * @param aleatorio the aleatorio to set
	 */
	public void setAleatorio(Random aleatorio) {
		this.aleatorio = aleatorio;
	}

}
