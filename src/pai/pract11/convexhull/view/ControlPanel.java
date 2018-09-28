/**
 * File containing the ControlPanel entity definition. 
 */

package pai.pract11.convexhull.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

/**
 * Class which represents the panel where the control buttons will be located in
 * the Convex Hull program GUI. It was created for the eleventh practice of PAI
 * (Programación de Aplicaciones Interactivas) course of ULL (Universidad de la
 * Laguna).
 * 
 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
 * @version 1.0
 * @since 20 abr. 2018
 */
public class ControlPanel extends JPanel {
	/** Default serial version ID. */
	private static final long	serialVersionUID	= 1L;
	/** Generate points button of the control panel. */
	private JButton						generatePointsButton;
	/** Reset button of the control panel. */
	private JButton						resetButton;
	/** Run button of the control panel. */
	private JButton						runButton;
	/** Next step button of the control panel. */
	private JButton						nextStepButton;
	/** Add points text field. */
	private JTextField				addPointsTextField;
	/** Points color button of the control panel. */
	private JButton						pointsColorButton;
	/** Lines color button of the control panel. */
	private JButton						linesColorButton;
	/** Timer label of the control panel. */
	private JLabel						timerLabel;
	/** Timer slider of the control panel. */
	private JSlider						timerSlider;
	/** Point diameter label of the control panel. */
	private JLabel						pointsDiameterLabel;
	/** Point diameter of the control panel. */
	private JSlider						pointsDiameterSlider;
	

	/**
	 * Getter method for timerSlider attribute.
	 * @return timerSlider
	 */
	public JSlider getTimerSlider() {
		return timerSlider;
	}

	/**
	 * Getter method for pointsDiameterSlider attribute.
	 * @return pointsDiameterSlider
	 */
	public JSlider getPointsDiameterSlider() {
		return pointsDiameterSlider;
	}

	/**
	 * Default constructor.
	 * 
	 * @param width
	 *          Width of the panel.
	 * @param height
	 *          Height of the panel.
	 * @param buttonsListener
	 *          Listener for the control buttons.
	 */
	public ControlPanel(int width, int height, ActionListener buttonsListener,
			ChangeListener sliderListener, int initialTimerDelay,
			int numberOfPoints) {
		setName("Control panel");
		setPreferredSize(new Dimension(width, height));

		final int TEXT_FIELD_COLUMNS = 5;
		addPointsTextField = new JTextField(numberOfPoints + "");
		addPointsTextField.setColumns(TEXT_FIELD_COLUMNS);

		generatePointsButton = new JButton("Generate points");
		generatePointsButton.addActionListener(buttonsListener);

		resetButton = new JButton("Reset");
		resetButton.addActionListener(buttonsListener);
		resetButton.setEnabled(false);

		runButton = new JButton("Run");
		runButton.addActionListener(buttonsListener);
		runButton.setEnabled(false);

		nextStepButton = new JButton("Next step");
		nextStepButton.addActionListener(buttonsListener);
		nextStepButton.setEnabled(false);

		timerLabel = new JLabel("Timer delay(ms)");
		final int MS_MIN = 1;
		final int MS_MAX = (initialTimerDelay * 10) + 1;
		final int MS_STARTING = initialTimerDelay;
		final int MINOR_SPACING = MS_MAX / 10;
		final int MAJOR_SPACING = MS_MAX / 5;
		timerSlider = new JSlider(MS_MIN, MS_MAX, MS_STARTING);
		timerSlider.setMajorTickSpacing(MAJOR_SPACING);
		timerSlider.setMinorTickSpacing(MINOR_SPACING);
		timerSlider.setPaintTicks(true);
		timerSlider.setPaintLabels(true);
		timerSlider.addChangeListener(sliderListener);
		
		pointsDiameterLabel = new JLabel("Points diameter(px)");
		final int MIN = 2;
		final int MAX =  10;
		final int STARTING = 4;
		final int MINOR = 1;
		final int MAJOR = 2;
		pointsDiameterSlider = new JSlider(MIN, MAX, STARTING);
		pointsDiameterSlider.setMajorTickSpacing(MAJOR);
		pointsDiameterSlider.setMinorTickSpacing(MINOR);
		pointsDiameterSlider.setPaintTicks(true);
		pointsDiameterSlider.setPaintLabels(true);
		pointsDiameterSlider.addChangeListener(sliderListener);
		pointsDiameterSlider.setEnabled(false);
		
		pointsColorButton = new JButton("Points color");
		pointsColorButton.addActionListener(buttonsListener);
		pointsColorButton.setEnabled(false);
		
		linesColorButton = new JButton("Lines color");
		linesColorButton.addActionListener(buttonsListener);
		linesColorButton.setEnabled(false);
		
		add(addPointsTextField);
		add(generatePointsButton);
		add(resetButton);
		add(runButton);
		add(nextStepButton);
		add(pointsColorButton);
		add(linesColorButton);
		add(timerLabel);
		add(timerSlider);
		add(pointsDiameterLabel);
		add(pointsDiameterSlider);
		
	}

	/**
	 * Represents the restart state of the control panel.
	 */
	public void restartState() {
		runButton.setEnabled(false);
		nextStepButton.setEnabled(false);
		resetButton.setEnabled(false);
		pointsColorButton.setEnabled(false);
		linesColorButton.setEnabled(false);
		pointsDiameterSlider.setEnabled(false);
	}

	/**
	 * Represents the initialized state of the control panel.
	 */
	public void initializedState() {
		runButton.setEnabled(true);
		nextStepButton.setEnabled(true);
		resetButton.setEnabled(true);
		pointsColorButton.setEnabled(true);
		linesColorButton.setEnabled(true);
		pointsDiameterSlider.setEnabled(true);
	}

	/**
	 * Represents the running state of the control panel.
	 */
	public void runningState() {
		generatePointsButton.setEnabled(false);
		runButton.setText("Pause");
		runButton.setActionCommand("Pause");
		nextStepButton.setEnabled(false);
	}

	/**
	 * Represents the stopped state of the control panel.
	 */
	public void stoppedState() {
		generatePointsButton.setEnabled(true);
		runButton.setText("Run");
		runButton.setActionCommand("Run");
		nextStepButton.setEnabled(true);
	}

	/**
	 * Represents the finished state of the control panel.
	 */
	public void finishedState() {
		generatePointsButton.setEnabled(true);
		runButton.setText("Run");
		runButton.setActionCommand("Run");
		runButton.setEnabled(false);
		nextStepButton.setEnabled(false);
	}

	/**
	 * Getter method for addPointsTextField attribute.
	 * 
	 * @return addPointsTextField
	 */
	public JTextField getAddPointsTextField() {
		return addPointsTextField;
	}
}
