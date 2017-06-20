

import java.awt.Color;
import java.util.Random;

public class ColorAleatorio {

	private static Random aleatorio = new Random();
	private static final int MAX_VALOR_COLOR = 255;	// Máximo valor que puede tomar un color
	
	public ColorAleatorio () {
		
	}
	
	/**
	 * Genera un color aleatorio 
	 * @return Devuelve un color aleatorio en RGB
	 */
	public static Color getColorAleatorioReal() {
		return new Color (getAleatorio().nextInt(getMaxValorColor()), getAleatorio().nextInt(getMaxValorColor()), getAleatorio().nextInt(getMaxValorColor()));
	}
	
	/**
	 * @return the maxValorColor
	 */
	public static int getMaxValorColor() {
		return MAX_VALOR_COLOR;
	}

	/**
	 * @return the aleatorio
	 */
	public static Random getAleatorio() {
		return aleatorio;
	}
	
}

