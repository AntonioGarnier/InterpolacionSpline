import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ControladorSpline extends JApplet {

	private static final long serialVersionUID = 1L;
	private PanelControl panelControl;	// Panel de control con los botones, etiquetas y campos de texto
	private VistaSpline vistaSpline;		// Vista del programa
	private ModeloSpline modeloSpline;	// Modelo del programa

	/**
	 * Método main: se permite ejecutar como aplicación o como applet
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame ("Interpolacion mediante Splines - AntonioGarnier");
		ControladorSpline applet = new ControladorSpline ();
		frame.add(applet, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}

	/**
	 * Constructor por defecto
	 * @param vistaSpline Define la vista
	 * @param modeloSpline Define el modelo
	 * @param panelControl Define el panel de control
	 */
	public ControladorSpline () {
		setModeloSpline(new ModeloSpline());
		setVistaSpline(new VistaSpline(getModeloSpline()));
		setPanelControl(new PanelControl());
		initFrame ();
		initBotonera();
		initVista();
		initEscuchas();
		add(new JLabel ("<html><b>Instrucciones de uso: </b><br><b><font color=#FF0000>"
				+ "- </font></b>Introduce la cantidad de NODOS en el campo de texto<br><b><font color=#FF0000>"
				+ "- </font></b>Pulsa el boton GENERAR para dibujar la SPLINE<br><b><font color=#FF0000>"
				+ "- </font></b>Selecciona un nodo mediante las teclas A y D, y moverlo con las ARROW_KEYS<br><b><font color=#FF0000>"
				+ "- </font></b>Tambien se pueden arrastrar los nodos con el ratón<br><b><font color=#FF0000>"
				+ "- </font></b>Añadir nodos: Haciendo click en el panel dibujable o introduciendo las coordenadas<br><b><font color=#FF0000>"
				+ "- </font></b>Pulse el boton de Reiniciar para borrar la SPLINE", JLabel.CENTER), BorderLayout.NORTH);
	}

	/**
	 * Inicializa el panel de control
	 */
	private void initBotonera() {
		add(getPanelControl(), BorderLayout.SOUTH);
	}

	/**
	 * Muestra una ventana con información sobre el error
	 */
	public void errorNodos () {
		JOptionPane.showMessageDialog(getVistaSpline(), "Debes introducir un número entero mayor o igual a 2", "Número Incorrecto", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Inicializa los listener de los botones
	 */
	private void initEscuchas() {

		// Boton generar: genera una serie de puntos aleatorios y dibuja la SPLINE
		getPanelControl().getBotones().getBoton(BotonEnum.GENERAR).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getModeloSpline().clearModelo();
				String texto = getPanelControl().getBotones().getnNodosField().getText();
				if (texto.matches("[0-9]+")) {
					if (Integer.parseInt(texto) > 1)
						getVistaSpline().initSpline(Integer.parseInt(texto));
					else
						errorNodos();
				}
				else
					errorNodos();
				getVistaSpline().setFocusable(true);
				getVistaSpline().requestFocusInWindow();
				getVistaSpline().repaint();
			}
		});

		// Borra el panel donde se dibuja
		getPanelControl().getBotones().getBoton(BotonEnum.REINICIAR).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getModeloSpline().clearModelo();
				getVistaSpline().setFocusable(true);
				getVistaSpline().requestFocusInWindow();
				getVistaSpline().repaint();
			}
		});

		// Muestra información sobre la práctica
		getPanelControl().getBotones().getBoton(BotonEnum.INFORMACION).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String mensaje = "Interpolación mediante Spline\n";
				mensaje += "Autor: Antonio Jesús López Garnier\n";
				mensaje += "Correo: logarn89@gmail.com\n";
				mensaje += "GitHub ID: AntonioGarnier\n";
				mensaje += "Descripción: Desarrollo de un programa en el que se dibuja una Spline\n";
				mensaje += "dados unos puntos iniciales";
				JOptionPane.showMessageDialog(getVistaSpline(), mensaje, "Información Adicional", JOptionPane.INFORMATION_MESSAGE);
				getVistaSpline().setFocusable(true);
				getVistaSpline().requestFocusInWindow();
				getVistaSpline().repaint();
			}
		});

		// Añade un punto y recalcula de nuevo la SPLINE
		getPanelControl().getBotones().getBoton(BotonEnum.ADD).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String textoX = getPanelControl().getBotones().getCoordenadaXField().getText();
				String textoY = getPanelControl().getBotones().getCoordenadaYField().getText();
				if (textoX.matches("[+-]?[0-9]+") && textoY.matches("[+-]?[0-9]+"))
					getModeloSpline().addPunto(Integer.parseInt(textoX), Integer.parseInt(textoY), getVistaSpline().getWidth(), getVistaSpline().getHeight());
				else
					JOptionPane.showMessageDialog(getVistaSpline(), "Debes introducir un número entero en ambos campos", "Error Coordenadas", JOptionPane.ERROR_MESSAGE);
				getVistaSpline().setFocusable(true);
				getVistaSpline().requestFocusInWindow();
				getVistaSpline().repaint();			}
		});

		// Mose listener para los clicks del ratón
		getVistaSpline().addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				getModeloSpline().addPunto(e.getX(), e.getY(), getVistaSpline().getWidth(), getVistaSpline().getHeight());
				getVistaSpline().repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		getVistaSpline().addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				getModeloSpline().muevePunto(new Point2D.Double(e.getX(), e.getY()));
				getModeloSpline().recalculaSpline();
				getVistaSpline().setFocusable(true);
				getVistaSpline().requestFocusInWindow();
				getVistaSpline().repaint();
			}
		});

		// Listeners para las teclas
		getVistaSpline().addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_A: 	getModeloSpline().seleccionNodo(-1);
												break;
					case KeyEvent.VK_D: 	getModeloSpline().seleccionNodo(1);
												break;

		         case KeyEvent.VK_DOWN: if (!getModeloSpline().limiteInferior())
		         							     getModeloSpline().mueveNodo(0, ModeloSpline.getDesplazamiento()); break;
		         case KeyEvent.VK_UP: if (!getModeloSpline().limiteSuperior())
		         								getModeloSpline().mueveNodo(0, -ModeloSpline.getDesplazamiento()); break;
		         case KeyEvent.VK_LEFT: if (!getModeloSpline().limiteIzquierda())
		         							     getModeloSpline().mueveNodo(-ModeloSpline.getDesplazamiento(), 0); break;
		         case KeyEvent.VK_RIGHT: if (!getModeloSpline().limiteDerecha())
		         								   getModeloSpline().mueveNodo(ModeloSpline.getDesplazamiento(), 0); break;
		         default: ;
				}
				repaint();
			}
		});

	}

	/**
	 * Inicializa la vista
	 */
	private void initVista() {
		add(getVistaSpline());
		getVistaSpline().setFocusable(true);
		getVistaSpline().requestFocusInWindow();
	}

	/**
	 * Inicializa el Frame
	 */
	private void initFrame() {
		Toolkit miPanel = Toolkit.getDefaultToolkit();
		Dimension tamanioPanel = miPanel.getScreenSize();
		setSize (tamanioPanel.width, tamanioPanel.height);
		setLayout(new BorderLayout());
	}

	/**
	 * @return the panelControl
	 */
	public PanelControl getPanelControl() {
		return panelControl;
	}

	/**
	 * @param panelControl the panelControl to set
	 */
	public void setPanelControl(PanelControl panelControl) {
		this.panelControl = panelControl;
	}

	/**
	 * @return the vistaSpline
	 */
	public VistaSpline getVistaSpline() {
		return vistaSpline;
	}

	/**
	 * @param vistaSpline the vistaSpline to set
	 */
	public void setVistaSpline(VistaSpline vistaSpline) {
		this.vistaSpline = vistaSpline;
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

}
