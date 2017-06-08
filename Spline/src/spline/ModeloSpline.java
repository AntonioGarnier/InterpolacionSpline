package spline;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ModeloSpline {

   private static final double EPSILON = 1e-10;		// Para comparar Doubles
   private static final int DESPLAZAMIENTO = 10;	// Desplazamiento en pixeles del punto
	private ArrayList<Point2D.Double> puntosSpline = new ArrayList<Point2D.Double>();	//Puntos iniciales para calcular la SPLINE
	private ArrayList<Point2D.Double> puntosSplineSuavizada = new ArrayList<Point2D.Double>();	// Puntos de la SPLINE con la Y calculada
	private ArrayList<Color> puntosSplineColor = new ArrayList<Color>();		// Colores para cada segmento
	private IntervaloNumeroAleatorio numeroAleatorio = new IntervaloNumeroAleatorio();	//Generador de numeros aleatorios
	private ArrayList<Double> puntosX = new ArrayList<Double>();	// Puntos en X
	private ArrayList<Double> puntosY = new ArrayList<Double>(); 	// Puntos en Y
	private Point2D.Double puntoClickado = new Point2D.Double(- 2 * VistaSpline.getRadioPuntos(), - 2 * VistaSpline.getRadioPuntos()); // Define el punto que ha sido clickado/seleccionado
	private int numeroNodos;	// Numero de nodos
	private int nodoClickado = -1;	// Nodo clickado
	private int anchoPanel, altoPanel; // Ancho y alto del panel de dibujo
	private Double [] puntosKS;	// Array de Double donde se guarda la derivada calculada

	/**
	 * Constructor por defecto en el que establecemos el número de nodos inicial a cero
	 */
	public ModeloSpline () {
		setNumeroNodos(0);
	}

	/**
	 * Limpia el modelo para borra el panel de dibujo
	 */
	public void clearModelo() {
		getPuntosSpline().clear();
		getPuntosSplineSuavizada().clear();
		getPuntosSplineColor().clear();
		setNumeroNodos(0);
		setNodoClickado(-1);
		setPuntoClickado(new Point2D.Double(- 2 * VistaSpline.getRadioPuntos(), - 2 * VistaSpline.getRadioPuntos()));
	}
	
	/**
	 * Mueve el Nodo en un incremento indicado por parámetros
	 * @param moverX Define el incremento en X
	 * @param moverY Define el incremento en Y
	 */
	public void mueveNodo (int moverX, int moverY) {
		if (getNodoClickado() == -1) {
			setNodoClickado(0);
			setPuntoClickado(getPuntosSpline().get(getNodoClickado()));
		}
		muevePunto(new Point2D.Double(getPuntoClickado().getX() + moverX, getPuntoClickado().getY() + moverY));
		recalculaSpline();
	}
	
	/**
	 * Calcula los límites por el lado superior del panel
	 * @return Devuelve True si alcanza el límite
	 */
	public boolean limiteSuperior () {
		if (getPuntoClickado().getY() < 0 + VistaSpline.getRadioPuntos()) {
			getPuntoClickado().setLocation(getPuntoClickado().getX(), 0 + VistaSpline.getRadioPuntos() / 2);
			recalculaSpline();
			return true;
		}
		return false;
	}
	
	/**
	 * Calcula los límites por la izquierda del panel
	 * @return Devuelve True si alcanza el límite
	 */
	public boolean limiteIzquierda () {
		if (getPuntoClickado().getX() < getDesplazamiento()) {
			getPuntoClickado().setLocation(0 + VistaSpline.getRadioPuntos() / 2, getPuntoClickado().getY());
			recalculaSpline();
			return true;
		}
		return false;
	}
	
	/**
	 * Calcula los límites por el lado inferior del panel
	 * @return Devuelve True si alcanza el límite
	 */
	public boolean limiteInferior () {
		if (getPuntoClickado().getY() > getAltoPanel() - getDesplazamiento() - VistaSpline.getRadioPuntos()) {
			getPuntoClickado().setLocation(getPuntoClickado().getX(), getAltoPanel() - VistaSpline.getRadioPuntos() / 2);
			recalculaSpline();
			return true;
		}
		return false;
	}
	
	/**
	 * Comprueba los límites por la derecha del panel
	 * @return Devuelve True si alcanza el límite
	 */
	public boolean limiteDerecha () {
		if (getPuntoClickado().getX() > getAnchoPanel() - getDesplazamiento() - VistaSpline.getRadioPuntos()) {
			getPuntoClickado().setLocation(getAnchoPanel() - VistaSpline.getRadioPuntos() / 2, getPuntoClickado().getY());
			recalculaSpline();
			return true;
		}
		return false;
	}
	
	/**
	 * Selecciona un nodo de la SPLINE
	 * @param direccion Define el incremento o decremento para la seleccion del nodo
	 * siguiente o anterior
	 */
	public void seleccionNodo (int direccion) {
		setNodoClickado(getNodoClickado() + direccion);
		if (getNodoClickado() < 0)
			setNodoClickado(getNumeroNodos() - 1);
		else if (getNodoClickado() > getNumeroNodos() - 1)
			setNodoClickado(0);
		setPuntoClickado(getPuntosSpline().get(getNodoClickado()));
	}
	
	
	/**
	 * Recalcula la SPLINE cuando se produce algún cambio
	 */
	public void recalculaSpline () {
		getPuntosX().clear();
		getPuntosY().clear();		
		for (int i = 0; i < getNumeroNodos(); i++) {
			getPuntosX().add(getPuntosSpline().get(i).getX());
			getPuntosY().add(getPuntosSpline().get(i).getY());
		}
		setPuntosKS(crearSplineCubica(getPuntosX(), getPuntosY()));
		setPuntosSplineSuavizada(creaSpline());
	}
	
	public void addColor () {
		getPuntosSplineColor().add(ColorAleatorio.getColorAleatorioReal());
	}
	
	/**
	 * Genera un array con colores aleatorios para cada segmento
	 * @return
	 */
	public ArrayList<Color> generaColores () {
		ArrayList<Color> colores = new ArrayList<Color> ();
		for (int i = 0; i < getNumeroNodos(); i++)
			colores.add(ColorAleatorio.getColorAleatorioReal());
		return colores;
	}
	
	/**
	 * Inicializa la SPLINE
	 * @param numeroNodos Define el número de nodos que tiene la SPLINE
	 * @param ancho Define el ancho del panel donde se dibuja
	 * @param alto Define el alto del panel donde se dibuja
	 */
	public void initModeloSpline (int numeroNodos, int ancho, int alto) {
		setNumeroNodos(numeroNodos);
		setAnchoPanel(ancho);
		setAltoPanel(alto);
		setPuntosSplineColor(generaColores());
		setPuntosSpline(generarNPuntosAleatorios());
		setPuntosKS(crearSplineCubica(getPuntosX(), getPuntosY()));
		setPuntosSplineSuavizada(creaSpline());
	}
	
	/**
	 * Crea la Spline evaluando las X y generando así los puntos (X, Y)
	 * @return Devuelve el array con los puntos de la SPLINE
	 */
	public ArrayList<Point2D.Double> creaSpline () {
		double x = getPuntosSpline().get(0).getX();
		ArrayList<Point2D.Double> puntos = new ArrayList<Point2D.Double>();
		while (x < getPuntosSpline().get(getPuntosSpline().size() - 1).getX()) {
			puntos.add(new Point2D.Double(x, interpolate(x)));
			x += 0.5;
		}
		return puntos;
	}
	
	/**
	 * Método que genera tantos puntos aleatorios como indicados en el atributo "numeroNodos"
	 * @return Devuelve un array con los puntos generados aleatoriamente y ordenados
	 */
	public ArrayList<Point2D.Double> generarNPuntosAleatorios () {
		ArrayList<Point2D.Double> puntos = new ArrayList<Point2D.Double>();
		for (int i = 0; i < getNumeroNodos(); i++)
			puntos.add(new Point2D.Double(getNumeroAleatorio().getNumeroAleatorio(0, getAnchoPanel()), getNumeroAleatorio().getNumeroAleatorio(0, getAltoPanel())));
		Collections.sort(puntos, new Point2DCompare());
		getPuntosX().clear();
		getPuntosY().clear();
		for (int i = 0; i < puntos.size(); i++) {
			getPuntosX().add(puntos.get(i).getX());
			getPuntosY().add(puntos.get(i).getY());
		}
		/*int separacion = getAnchoPanel() / getNumeroNodos();
		int maximo = separacion;
		int minimo = 0;

		for (int i = 0; i < getNumeroNodos(); i++) {
			puntos.add(new Point2D.Double(getNumeroAleatorio().getNumeroAleatorio(minimo, maximo), getNumeroAleatorio().getNumeroAleatorio(0, getAltoPanel())));
			getPuntosX().add(puntos.get(i).getX());
			getPuntosY().add(puntos.get(i).getY());
			maximo += separacion;
			minimo += separacion;
		}*/
		return puntos;
	}
	
	/**
	 * Crea la Spline Cubica a partir de los valores de X e Y de los puntos iniciales
	 * @param coordenadasX Define los valores de X
	 * @param coordenadasY Define los valores de Y
	 * @return Devuelve un array con las derivadas(tangentes)
	 */
	public Double [] crearSplineCubica(ArrayList<Double> coordenadasX, ArrayList<Double> coordenadasY) {

		final int n = coordenadasX.size();
		Double[] d = new Double[n - 1];
		Double[] tangentes = new Double[n];

		// Calcula las pendintes de dos puntos sucesivos
		for (int i = 0; i < n - 1; i++) {
			Double h = coordenadasX.get(i + 1) - coordenadasX.get(i);
			d[i] = (coordenadasY.get(i + 1) - coordenadasY.get(i)) / h;
		}

		// Inicializa las tangentes a la media de las secantes 
		// para tener un array de igual tamaño que los puntos X e Y
		tangentes[0] = d[0];
		for (int i = 1; i < n - 1; i++) {
			tangentes[i] = (d[i - 1] + d[i]) * 0.5;
		}
		tangentes[n - 1] = d[n - 2];

		return tangentes;
	}

	/**
	 * Interpola el valor de Y = f(x) para un punto X dado.
	 * @param puntoXEvaluado Define el valor de X
	 * @return Devuelve el valor de Y
	 */
	public Double interpolate (Double x) {
	    int i = 1;
	    while(getPuntosX().get(i) < x) 
	    	i++;
			
	    double t = (x - getPuntosX().get(i - 1)) / (getPuntosX().get(i) - getPuntosX().get(i - 1));
			
	    double a =  getPuntosKS()[i - 1] * (getPuntosX().get(i) - getPuntosX().get(i - 1)) - (getPuntosY().get(i) - getPuntosY().get(i - 1));
	    double b = -getPuntosKS()[i] * (getPuntosX().get(i) - getPuntosX().get(i - 1)) + (getPuntosY().get(i) - getPuntosY().get(i - 1));

	    double q = (1 - t) * getPuntosY().get(i - 1) + t * getPuntosY().get(i) + t * (1 - t) * (a * (1 - t) + b * t);
	    return q;
		}
	
	public void ordenaPuntos () {
		Collections.sort(getPuntosSpline(), new Point2DCompare());
	}
	
	public Point2D.Double buscaPunto (Point2D.Double punto) {
		for (int i = 0; i < getPuntosSpline().size(); i++)
			if (Math.abs(getPuntosSpline().get(i).getX() - punto.getX()) <= VistaSpline.getRadioPuntos() && Math.abs(getPuntosSpline().get(i).getY() - punto.getY()) <= VistaSpline.getRadioPuntos()) {
				setNodoClickado(i);
				return getPuntosSpline().get(i);
			}
		return null;
	}
	
	public void muevePunto (Point2D.Double posicionRaton) {
		setPuntoClickado(buscaPunto(posicionRaton));
		if (getPuntoClickado() != null) {
			getPuntosSpline().get(getPuntosSpline().indexOf(getPuntoClickado())).setLocation(posicionRaton.getX(), posicionRaton.getY());
			ordenaPuntos();
		}
	}
	
	public class Point2DCompare implements Comparator<Point2D.Double> {
		
		public int compare(Point2D.Double punto1, Point2D.Double punto2) {
	        if (punto1.x < punto2.x) {
	            return -1;
	        }
	        else if (punto1.x > punto2.x) {
	            return 1;
	        }
	        else {
	            return 0;
	        }
	    }
	}
	
	public void addPunto (int coordenadaX, int coordenadaY, int ancho, int alto) {
		addColor();
		setAnchoPanel(ancho);
		setAltoPanel(alto);
		if (buscaPunto(new Point2D.Double(coordenadaX, coordenadaY)) == null) {
			getPuntosSpline().add(new Point2D.Double(coordenadaX, coordenadaY));
			setNumeroNodos(getNumeroNodos() + 1); 
			if (getPuntosSpline().size() > 1) {
				setPuntoClickado(getPuntosSpline().get(getPuntosSpline().size() - 1));
				setNodoClickado(getPuntosSpline().size() - 1);
				limiteSuperior();
				limiteInferior();
				limiteIzquierda();
				limiteDerecha();
				ordenaPuntos();
				recalculaSpline();
			}
		}
	}
	
	public String mostrarPunto () {
		return "(" + getPuntoClickado().getX() + ", " + getPuntoClickado().getY() + ")";
	}
	
	/**
	 * @return the anchoPanel
	 */
	public int getAnchoPanel() {
		return anchoPanel;
	}

	/**
	 * @param anchoPanel the anchoPanel to set
	 */
	public void setAnchoPanel(int anchoPanel) {
		this.anchoPanel = anchoPanel;
	}

	/**
	 * @return the altoPanel
	 */
	public int getAltoPanel() {
		return altoPanel;
	}

	/**
	 * @param altoPanel the altoPanel to set
	 */
	public void setAltoPanel(int altoPanel) {
		this.altoPanel = altoPanel;
	}

	/**
	 * @return the numeroAleatorio
	 */
	public IntervaloNumeroAleatorio getNumeroAleatorio() {
		return numeroAleatorio;
	}

	/**
	 * @param numeroAleatorio the numeroAleatorio to set
	 */
	public void setNumeroAleatorio(IntervaloNumeroAleatorio numeroAleatorio) {
		this.numeroAleatorio = numeroAleatorio;
	}

	/**
	 * @return the numeroNodos
	 */
	public int getNumeroNodos() {
		return numeroNodos;
	}

	/**
	 * @param numeroNodos the numeroNodos to set
	 */
	public void setNumeroNodos(int numeroNodos) {
		this.numeroNodos = numeroNodos;
	}

	/**
	 * @return the puntos
	 */
	public ArrayList<Point2D.Double> getPuntosSpline() {
		return puntosSpline;
	}

	/**
	 * @param puntos the puntos to set
	 */
	public void setPuntosSpline(ArrayList<Point2D.Double> puntosSpline) {
		this.puntosSpline = puntosSpline;
	}

	/**
	 * @return the epsilon
	 */
	public static double getEpsilon() {
		return EPSILON;
	}

	/**
	 * @return the puntosKS
	 */
	public Double[] getPuntosKS() {
		return puntosKS;
	}

	/**
	 * @param puntosKS the puntosKS to set
	 */
	public void setPuntosKS(Double[] puntosKS) {
		this.puntosKS = puntosKS;
	}
	
	/**
	 * @return the puntosSplineSuavizada
	 */
	public ArrayList<Point2D.Double> getPuntosSplineSuavizada() {
		return puntosSplineSuavizada;
	}

	/**
	 * @param puntosSplineSuavizada the puntosSplineSuavizada to set
	 */
	public void setPuntosSplineSuavizada(ArrayList<Point2D.Double> puntosSplineSuavizada) {
		this.puntosSplineSuavizada = puntosSplineSuavizada;
	}

	/**
	 * @return the puntosX
	 */
	public ArrayList<Double> getPuntosX() {
		return puntosX;
	}

	/**
	 * @param puntosX the puntosX to set
	 */
	public void setPuntosX(ArrayList<Double> puntosX) {
		this.puntosX = puntosX;
	}

	/**
	 * @return the puntosY
	 */
	public ArrayList<Double> getPuntosY() {
		return puntosY;
	}

	/**
	 * @param puntosY the puntosY to set
	 */
	public void setPuntosY(ArrayList<Double> puntosY) {
		this.puntosY = puntosY;
	}
	
	/**
	 * @return the puntoClickado
	 */
	public Point2D.Double getPuntoClickado() {
		return puntoClickado;
	}

	/**
	 * @param puntoClickado the puntoClickado to set
	 */
	public void setPuntoClickado(Point2D.Double puntoClickado) {
		this.puntoClickado = puntoClickado;
	}

	/**
	 * @return the nodoClickado
	 */
	public int getNodoClickado() {
		return nodoClickado;
	}

	/**
	 * @param nodoClickado the nodoClickado to set
	 */
	public void setNodoClickado(int nodoClickado) {
		this.nodoClickado = nodoClickado;
	}

	/**
	 * @return the desplazamiento
	 */
	public static int getDesplazamiento() {
		return DESPLAZAMIENTO;
	}

	
	/**
	 * @return the puntosSplineColor
	 */
	public ArrayList<Color> getPuntosSplineColor() {
		return puntosSplineColor;
	}

	/**
	 * @param puntosSplineColor the puntosSplineColor to set
	 */
	public void setPuntosSplineColor(ArrayList<Color> puntosSplineColor) {
		this.puntosSplineColor = puntosSplineColor;
	}
}
