package controles;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Botonera extends ArrayList<MiBoton> {

	private static final long serialVersionUID = 1L;
	private static final int ALINEACION_TAG = 0;     // 0 = Centrado; 2 = Izquierda; 4 = Derecha
	private static final int IMAGEN_SIZE = 20;		 // Tamaño de la imagen del icono de información 
	private JTextField nNodosField = new JTextField();	// Campo de texto para el número de nodos
	private JLabel nNodosTag = new JLabel ("Número de Nodos:");	// Etiqueta informativa para el número de nodos
	private JLabel coordenadasTag = new JLabel ("Introduzca las coordenadas: ", getAlineacionTag());	// Etiqueta informativa para la introducción de un punto
	private JTextField coordenadaXField = new JTextField();	// Campo de texto para la CoordenadaX
	private JTextField coordenadaYField = new JTextField();  // Campo de texto para la CoordenadaY
	
	/**
	 * Constructor de la botonera
	 * @param indice Define el número identificativo
	 */
	public Botonera ()	{
		super();
		initBotonera();
		addIconoInformacion();
	}

	/**
	 * Método para encontrar un boton
	 * @param tipoBoton Define el tipo de boton
	 * @return Devuelve el boton que coincide con el tipo de boton especificado por parámetros
	 */
	public MiBoton getBoton (BotonEnum tipoBoton) {
		for (MiBoton boton : this)
			if (boton.getTipoBoton() == tipoBoton)
				return boton;
		return null;
	}
	
	/**
	 * Aquí se inicializan e instancian los botones de la botonera
	 */
	public void initBotonera ()	{
		for (int i = 0; i < BotonEnum.values().length; i ++) {
			add(new MiBoton (BotonEnum.values()[i].getTexto(), BotonEnum.values()[i]));
		}
	}

	/**
	 * Inicializa el boton de información
	 */
	private void addIconoInformacion() {
		ImageIcon icono = new ImageIcon ("./src/controles/info.png");
		getBoton(BotonEnum.INFORMACION).setText(BotonEnum.INFORMACION.getTexto());
		icono.setImage(icono.getImage().getScaledInstance(getImagenSize(), getImagenSize(), getImagenSize()));
		getBoton(BotonEnum.INFORMACION).setIcon(icono);
		getnNodosTag().setHorizontalAlignment(getAlineacionTag());
	}

	/**
	 * @return the alineacionTag
	 */
	public static int getAlineacionTag() {
		return ALINEACION_TAG;
	}

	/**
	 * @return the imagensize
	 */
	public static int getImagenSize() {
		return IMAGEN_SIZE;
	}

	/**
	 * @return the nNodosField
	 */
	public JTextField getnNodosField() {
		return nNodosField;
	}

	/**
	 * @param nNodosField the nNodosField to set
	 */
	public void setnNodosField(JTextField nNodosField) {
		this.nNodosField = nNodosField;
	}

	/**
	 * @return the nNodosTag
	 */
	public JLabel getnNodosTag() {
		return nNodosTag;
	}

	/**
	 * @param nNodosTag the nNodosTag to set
	 */
	public void setnNodosTag(JLabel nNodosTag) {
		this.nNodosTag = nNodosTag;
	}

	/**
	 * @return the coordenadasTag
	 */
	public JLabel getCoordenadasTag() {
		return coordenadasTag;
	}

	/**
	 * @param coordenadasTag the coordenadasTag to set
	 */
	public void setCoordenadasTag(JLabel coordenadasTag) {
		this.coordenadasTag = coordenadasTag;
	}

	/**
	 * @return the coordenadaXField
	 */
	public JTextField getCoordenadaXField() {
		return coordenadaXField;
	}

	/**
	 * @param coordenadaXField the coordenadaXField to set
	 */
	public void setCoordenadaXField(JTextField coordenadaXField) {
		this.coordenadaXField = coordenadaXField;
	}

	/**
	 * @return the coordenadaYField
	 */
	public JTextField getCoordenadaYField() {
		return coordenadaYField;
	}

	/**
	 * @param coordenadaYField the coordenadaYField to set
	 */
	public void setCoordenadaYField(JTextField coordenadaYField) {
		this.coordenadaYField = coordenadaYField;
	}
	
}
