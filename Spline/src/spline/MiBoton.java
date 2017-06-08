package spline;

import javax.swing.JButton;


public class MiBoton extends JButton {

	private static final long serialVersionUID = 1L;
	private BotonEnum tipoBoton;	// Establece el tipo de boton
	
	/**
	 * Constructor por defecto de mi boton
	 * @param nombreBoton Define el nombre
	 * @param tipoBoton Define el tipo de boton
	 */
	public MiBoton (String nombreBoton, BotonEnum tipoBoton)	{
		super(nombreBoton);
		setTipoBoton(tipoBoton);
	}

	/**
	 * @return the tipoBoton
	 */
	public BotonEnum getTipoBoton() {
		return tipoBoton;
	}

	/**
	 * @param tipoBoton the tipoBoton to set
	 */
	public void setTipoBoton(BotonEnum tipoBoton) {
		this.tipoBoton = tipoBoton;
	}
	
}

