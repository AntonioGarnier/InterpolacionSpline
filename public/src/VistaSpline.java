/**
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@version 1.0
*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

public class VistaSpline extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int RADIO_PUNTOS = 15;	// Radio de los puntos a dibujar
	private static final Color COLOR_PUNTOS = Color.RED;	// Color de los puntos
	private static final Color COLOR_LINEA = Color.BLACK;	// Color de la SPLINE
	private static final Color COLOR_FONDO = Color.WHITE;	// Color de fondo del panel
	private static final Color COLOR_PUNTO_CLICKADO = Color.BLUE;	// Color del punto seleccionado
	private ModeloSpline modeloSpline;	// Modelo de la SPLINE

	/**
	 * Constructor por defecto de la vista
	 * @param modeloSpline Define el modelo de la spline
	 */
	public VistaSpline (ModeloSpline modeloSpline) {
		setBackground(getColorFondo());
		setModeloSpline(modeloSpline);
	}
	
	/**
	 * Inicializa la SPLINE
	 * @param nNodos Define el numero de nodos
	 */
	public void initSpline (int nNodos) {
		getModeloSpline().initModeloSpline(nNodos, getWidth(), getHeight());
	}
	
	/**
	 * Método sobreescrito encargado de pintar la SPLINE
	 */
	protected void paintComponent (Graphics objetoGrafico) {
		super.paintComponent(objetoGrafico);
		Graphics2D graficador = (Graphics2D) objetoGrafico;
		graficador.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graficador.setColor(getColorPuntos());
		getModeloSpline().getPuntosSpline().forEach(punto -> {
			graficador.fillOval((int)punto.getX() - getRadioPuntos() / 2, (int)punto.getY() - getRadioPuntos() / 2, getRadioPuntos(), getRadioPuntos());
		});
		if (getModeloSpline().getPuntosSpline().size() > 1)
			drawSpline(graficador);
		graficador.setColor(getColorPuntoClickado());
		graficador.drawString(getModeloSpline().mostrarPunto(), (int)getModeloSpline().getPuntoClickado().getX(), (int)(getModeloSpline().getPuntoClickado().getY() + getRadioPuntos()));
		graficador.fillOval((int)getModeloSpline().getPuntoClickado().getX() - getRadioPuntos() / 2, (int)getModeloSpline().getPuntoClickado().getY() - getRadioPuntos() / 2, getRadioPuntos(), getRadioPuntos());
	}

	protected void drawSpline (Graphics2D graficador) {
		double x = getModeloSpline().getPuntosSpline().get(0).getX();
		int j = 0;
		while (x < getModeloSpline().getPuntosSpline().get(getModeloSpline().getPuntosSpline().size() - 1).getX()) {
			Point2D puntoA = new Point2D.Double(x, getModeloSpline().interpolate(x));
			x += 0.5;
			Point2D puntoB = new Point2D.Double(x, getModeloSpline().interpolate(x));
			graficador.draw(new Line2D.Double(puntoA.getX(), puntoA.getY(), puntoB.getX(), puntoB.getY()));
			if (x >= getModeloSpline().getPuntosSpline().get(j + 1).getX()) {
				graficador.setColor(getModeloSpline().getPuntosSplineColor().get(j));
				j++;
			}
		}
	}
	/**
	 * @return the colorLinea
	 */
	public static Color getColorLinea() {
		return COLOR_LINEA;
	}

	/**
	 * @return the radioPuntos
	 */
	public static int getRadioPuntos() {
		return RADIO_PUNTOS;
	}

	/**
	 * @return the modeloSpline
	 */
	public ModeloSpline getModeloSpline() {
		return modeloSpline;
	}

	/**
	 * @param modeloSpline the modeloSpline to set
	 */
	public void setModeloSpline(ModeloSpline modeloSpline) {
		this.modeloSpline = modeloSpline;
	}

	/**
	 * @return the colorPuntos
	 */
	public static Color getColorPuntos() {
		return COLOR_PUNTOS;
	}

	/**
	 * @return the colorFondo
	 */
	public static Color getColorFondo() {
		return COLOR_FONDO;
	}

	/**
	 * @return the colorPuntoClickado
	 */
	public static Color getColorPuntoClickado() {
		return COLOR_PUNTO_CLICKADO;
	}
	
}
