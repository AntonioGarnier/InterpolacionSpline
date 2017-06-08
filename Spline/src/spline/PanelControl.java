package spline;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class PanelControl extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private Botonera botones;	// Array list con los botones, ademas contiene atributos con etiquetas y campos de texto
	
	public PanelControl () {
		setLayout(new GridLayout(0, 5));
		initBotonera();
	}

	/**
	 * Inicializa el panel de botones
	 */
	public void initBotonera () {
		setBotones(new Botonera ());
		add(getBotones().getnNodosTag());
		add(getBotones().getnNodosField());
		getBotones().forEach(boton -> add(boton) );
		add(getBotones().getCoordenadasTag());
		add(getBotones().getCoordenadaXField());
		add(getBotones().getCoordenadaYField());
	}
	
	/**
	 * @return the botones
	 */
	public Botonera getBotones() {
		return botones;
	}

	/**
	 * @param botones the botones to set
	 */
	public void setBotones(Botonera botones) {
		this.botones = botones;
	}
	
	
}
