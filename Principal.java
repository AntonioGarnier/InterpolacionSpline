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

public class Principal extends JApplet {

	private static final long serialVersionUID = 1L;


   public Principal () {
      JLabel tag = new JLabel ("Probando applet");
      add(tag);
   }
	/**
	 * Método main: se permite ejecutar como aplicación o como applet
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame ("Interpolacion mediante Splines - AntonioGarnier");
		Principal applet = new Principal ();
		frame.add(applet, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
}
