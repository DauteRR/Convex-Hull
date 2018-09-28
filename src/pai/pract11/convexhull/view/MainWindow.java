/**
 * File containing the MainWindow entity definition. 
 */

package pai.pract11.convexhull.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeListener;

/**
 * Class which represents the main window of the Convex Hull program GUI. It was
 * created for the eleventh practice of PAI (Programación de Aplicaciones
 * Interactivas) course of ULL (Universidad de la Laguna).
 * 
 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
 * @version 1.0
 * @since 20 abr. 2018
 */
public class MainWindow extends JFrame {

	/** Default serial version ID. */
	private static final long	serialVersionUID	= 1L;
	/** Convex hull panel. */
	ConvexHullPanel						convexHullPanel;
	/** Control panel. */
	ControlPanel							controlPanel;

	/**
	 * Default constructor.
	 * 
	 * @param width
	 *          Width of the GUI.
	 * @param height
	 *          Height of the GUI.
	 * @param points
	 *          Points of the convex hull panel.
	 * @param buttonsListener
	 *          Listener for the control buttons.
	 * @param appletMode
	 *          Establishes if the execution is in applet mode.
	 * @param sliderListener
	 *          Handles the control panel slider events.
	 * @param initialTimerDelay
	 *          Delay for the timer.
	 * @param amountOfPoints
	 *          Initial amount of points of the convex hull panel.
	 */
	public MainWindow(int width, int height, ArrayList<Point2D.Double> points,
			ActionListener buttonsListener, boolean appletMode,
			ChangeListener sliderListener, int initialTimerDelay,
			int amountOfPoints) {
		this.setPreferredSize(new Dimension(width, height));
		final int CONVEX_HULL_PANEL_HEIGHT = (int) (height * 0.88);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setResizable(false);

		this.convexHullPanel = new ConvexHullPanel(width, CONVEX_HULL_PANEL_HEIGHT,
				points);
		this.controlPanel = new ControlPanel(width,
				height - CONVEX_HULL_PANEL_HEIGHT, buttonsListener, sliderListener,
				initialTimerDelay, amountOfPoints);

		if (appletMode) {
			return;
		}

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(this.convexHullPanel);
		mainPanel.add(this.controlPanel);
		this.pack();
		this.add(mainPanel);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Getter method for convexHullPanel attribute.
	 * 
	 * @return convexHullPanel
	 */
	public ConvexHullPanel getConvexHullPanel() {
		return this.convexHullPanel;
	}

	/**
	 * Getter method for controlPanel attribute.
	 * 
	 * @return controlPanel
	 */
	public ControlPanel getControlPanel() {
		return this.controlPanel;
	}

	/**
	 * Setter method for convexHullPanel attribute.
	 * 
	 * @param convexHullPanel
	 */
	public void setConvexHullPanel(ConvexHullPanel convexHullPanel) {
		this.convexHullPanel = convexHullPanel;
	}

	/**
	 * Setter method for controlPanel attribute.
	 * 
	 * @param controlPanel
	 */
	public void setControlPanel(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

}
