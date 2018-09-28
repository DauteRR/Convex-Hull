/**
 * File containing the QuickHullController entity definition. 
 */

package pai.pract11.convexhull.controller;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pai.pract11.convexhull.model.Line;
import pai.pract11.convexhull.view.MainWindow;

/**
 * Class which represents the controller of the Convex Hull program. It was
 * created for the eleventh practice of PAI (Programación de Aplicaciones
 * Interactivas) course of ULL (Universidad de la Laguna).
 * 
 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
 * @version 1.0
 * @since 20 abr. 2018
 */
public class ConvexHullController implements ActionListener, ChangeListener {
	/**
	 * Auxiliary class created to act as the timer action listener. It was created
	 * for the eleventh practice of PAI (Programación de Aplicaciones
	 * Interactivas) course of ULL (Universidad de la Laguna).
	 * 
	 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
	 * @version 1.0
	 * @since 20 abr. 2018
	 */
	class TimerListener implements ActionListener {
		/**
		 * Handles the events thrown by the timer.
		 * 
		 * @param e
		 *          Event thrown by the timer.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!ConvexHullController.this.initializedHull) {
				ConvexHullController.this.initializeHull();
			} else if(!ConvexHullController.this.callStack.isEmpty()) {
				int previousConvexHullSize = ConvexHullController.this.convexHull.size();
				while(previousConvexHullSize == ConvexHullController.this.convexHull.size()) {
					if (ConvexHullController.this.callStack.isEmpty()) {
						break;
					}
					ConvexHullController.this.addPointToHull();
				}
			} else {
				System.out.println("Finished!");
				ConvexHullController.this.stopSimulation();
			}
		}
	}

	/**
	 * Auxiliary class created to store a Line and a set of points, it is used for
	 * the quick hull algorithm. It was created for the eleventh practice of PAI
	 * (Programación de Aplicaciones Interactivas) course of ULL (Universidad de
	 * la Laguna).
	 * 
	 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
	 * @version 1.0
	 * @since 20 abr. 2018
	 */
	class LinePointsPair {
		/** Line of the line-points pair. */
		private Line							line;
		/** Points of the line-points pair. */
		private ArrayList<Point2D.Double>	points;

		/**
		 * Default constructor.
		 * 
		 * @param line
		 *          Line.
		 * @param points
		 *          Points.
		 */
		public LinePointsPair(Line line, ArrayList<Point2D.Double> points) {
			this.line = line;
			this.points = points;
		}

		/**
		 * Getter method for line attribute.
		 * 
		 * @return line
		 */
		public Line getLine() {
			return line;
		}

		/**
		 * Getter method for points attribute.
		 * 
		 * @return points
		 */
		public ArrayList<Point2D.Double> getPoints() {
			return points;
		}
	}

	/** Points of the convex hull. */
	private ArrayList<Point2D.Double>				points;
	/** View of the program. */
	private MainWindow									view;
	/** Points which conforms the convex hull. */
	private ArrayList<Point2D.Double>						convexHull;
	/** Establishes if the hull is initialized. */
	private boolean											initializedHull;
	/** Simulates a stack of calls to the addPointToHull method. */
	private LinkedList<LinePointsPair>	callStack;
	/** Timer for the simulation. */
	private Timer												timer;
	/** Timer delay. */
	private int													timerDelay;
	/** Establishes the position of the convex hull where the next point must be inserted. */
	private int													insertionIndex;
	/** Amount of points of the convex hull. */
	private int numberOfPoints;
	/** Available colors for view representation. */
	private final Color[]				AVAILABLE_COLORS	= { Color.BLUE, Color.RED,
			Color.CYAN, Color.YELLOW, Color.ORANGE, Color.GREEN, Color.MAGENTA };

	/**
	 * Default constructor for the application mode.
	 * 
	 * @param width
	 *          Width of the GUI.
	 * @param height
	 *          Height of the GUI.
	 * @param numberOfPoints
	 *          Initial amount of points of the convex hull panel.
	 * @param timerDelay
	 *          Delay for the timer.
	 * @param appletMode
	 * 					Establishes if the execution is in applet mode.
	 */
	public ConvexHullController(int width, int height, int numberOfPoints,
			int timerDelay, boolean appletMode) {
		this.numberOfPoints = numberOfPoints;
		this.timerDelay = timerDelay;
		this.points = new ArrayList<Point2D.Double>();
		view = new MainWindow(width, height, new ArrayList<Point2D.Double>(), this, appletMode, this, timerDelay, numberOfPoints);
		convexHull = new ArrayList<Point2D.Double>();
		callStack = new LinkedList<LinePointsPair>();
		insertionIndex = 0;
		timer = new Timer(timerDelay, new TimerListener());
	}
	
	/**
	 * Starts the simulation.
	 */
	private void startSimulation() {
		view.getControlPanel().runningState();
		timer.start();
	}

	/**
	 * Stops the simulation.
	 */
	private void stopSimulation() {
		if (callStack.isEmpty()) {
			view.getControlPanel().finishedState();
		} else {
			view.getControlPanel().stoppedState();
		}
		timer.stop();
	}

	/**
	 * Handles the events thrown by the control panel buttons.
	 * 
	 * @param e
	 *          Event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Generate points")) {
			try {
				numberOfPoints = Integer.parseInt(view.getControlPanel().getAddPointsTextField().getText());
			} catch(NumberFormatException exception) {
				System.err.println("Invalid amount of points.");
			}
			Random random = new Random();
			for (int i = 0; i < numberOfPoints; ++i) {
				view.getConvexHullPanel();
				points.add(new Point2D.Double(
						random.nextInt(view.getConvexHullPanel().getWidth()
								- view.getConvexHullPanel().getDiameter()),
						random.nextInt(view.getConvexHullPanel().getHeight()
								- view.getConvexHullPanel().getDiameter())));
			}
			view.getConvexHullPanel().setPoints(points);
			view.getControlPanel().initializedState();
			initializedHull = false;
			insertionIndex = 0;
			callStack.clear();
		}
		
		if (e.getActionCommand().equals("Next step")) {
			if (!initializedHull) {
				initializeHull();
			} else if(!callStack.isEmpty()) {
				int previousConvexHullSize = convexHull.size();
				while(previousConvexHullSize == convexHull.size()) {
					if (callStack.isEmpty()) {
						break;
					}
					addPointToHull();
				}
			} else {
				System.out.println("Finished!");
				stopSimulation();
			}
		}
		
		if (e.getActionCommand().equals("Run")) {
			if (!initializedHull) {
				initializeHull();
			}
			startSimulation();
		}
		
		if (e.getActionCommand().equals("Pause")) {
			stopSimulation();
		}
		
		if (e.getActionCommand().equals("Reset")) {
			points = new ArrayList<Point2D.Double>();
			view.getConvexHullPanel().setPoints(points);
			convexHull = new ArrayList<>();
			view.getConvexHullPanel().restartConvexHull();
			view.getControlPanel().restartState();
			initializedHull = false;
			insertionIndex = 0;
			callStack.clear();
		}
		
		if (e.getActionCommand().equals("Lines color")) {
			Random random = new Random();
			view.getConvexHullPanel().setLinesColor(AVAILABLE_COLORS[random.nextInt(AVAILABLE_COLORS.length)]);
		}
		
		if (e.getActionCommand().equals("Points color")) {
			Random random = new Random();
			view.getConvexHullPanel().setPointsColor(AVAILABLE_COLORS[random.nextInt(AVAILABLE_COLORS.length)]);
		}
	}

	/**
	 * This method prepares the data structures needed by the quick hull
	 * algorithm.
	 */
	private void initializeHull() {
		convexHull = new ArrayList<>();
		view.getConvexHullPanel().restartConvexHull();
		Point2D.Double firstPoint = new Point2D.Double(view.getConvexHullPanel().getWidth(), 0);
		Point2D.Double lastPoint = new Point2D.Double(0, 0);
		initializedHull = true;
		for (int i = 0; i < points.size(); ++i) {
			if (points.get(i).getX() < firstPoint.getX()) {
				firstPoint = points.get(i);
			}
			if (points.get(i).getX() > lastPoint.getX()) {
				lastPoint = points.get(i);
			}
		}
		
		convexHull.add(insertionIndex, firstPoint);
		view.getConvexHullPanel().addPointToConvexHull(insertionIndex,
				firstPoint);
		insertionIndex++;
		convexHull.add(insertionIndex, lastPoint);
		view.getConvexHullPanel().addPointToConvexHull(insertionIndex,
				lastPoint);
		
		Line initialLine = new Line(firstPoint, lastPoint);
		ArrayList<Point2D.Double> abovePointsSet = new ArrayList<>();
		ArrayList<Point2D.Double> belowPointsSet = new ArrayList<>();
		for (Point2D.Double point : points) {
			if (convexHull.contains(point)) {
				continue;
			}
			if (Line.isPointAboveLine(initialLine, point)) {
				abovePointsSet.add(point);
			} else {
				belowPointsSet.add(point);
			}
			
		}
		callStack.addFirst(new LinePointsPair(initialLine, belowPointsSet));
		callStack.addFirst(new LinePointsPair(new Line(initialLine.getSecondPoint(), initialLine.getFirstPoint()), abovePointsSet));
	}

	/**
	 * This method simulates an iteration of the quick hull algorithm.
	 */
	private void addPointToHull() {
		Line currentLine = callStack.getFirst().getLine();
		ArrayList<Point2D.Double> points = callStack.getFirst().getPoints();
		callStack.removeFirst();
		
		// Delete the points that are inside the current convex hull
		Polygon currentConvexHull = new Polygon();
		ArrayList<Point2D.Double> pointsToRemove = new ArrayList<>();
		for (Point2D.Double point: convexHull) {
			currentConvexHull.addPoint((int)point.getX(), (int)point.getY());
		}
		for (Point2D.Double point: points) {
			if (currentConvexHull.contains(point)) {
				pointsToRemove.add(point);
			}
		}
		points.removeAll(pointsToRemove);
		
		Point2D.Double farthestPoint = null;
		double farthestDistance = -1;
		for(Point2D.Double point: points) {
			if (convexHull.contains(point)) {
				continue;
			}
			if (Line.distanceToPoint(currentLine, point) > farthestDistance) {
				farthestDistance = Line.distanceToPoint(currentLine, point);
				farthestPoint = point;
			}
		}
		
		if (farthestPoint == null) {
			return;
		}
		
		Polygon triangle = new Polygon();
		triangle.addPoint((int)currentLine.getFirstPoint().getX(), (int)currentLine.getFirstPoint().getY());
		triangle.addPoint((int)farthestPoint.x, (int)farthestPoint.y);
		triangle.addPoint((int)currentLine.getSecondPoint().getX(), (int)currentLine.getSecondPoint().getY());
		
		ArrayList<Point2D.Double> inside = new ArrayList<>();
		ArrayList<Point2D.Double> outside = new ArrayList<>();
		
		for (Point2D.Double p: points) {
			if (triangle.contains(p)) {
				inside.add(p);
			} else {
				outside.add(p);
			}
		}
		
		insertionIndex = convexHull.indexOf(currentLine.getSecondPoint());
		convexHull.add(insertionIndex, farthestPoint);
		view.getConvexHullPanel().addPointToConvexHull(insertionIndex, farthestPoint);
		
		boolean farthestPointAvobeLine = Line.isPointAboveLine(currentLine, farthestPoint);
		ArrayList<Point2D.Double> firstPointsSet = new ArrayList<>();
		ArrayList<Point2D.Double> secondPointsSet = new ArrayList<>();
		for(Point2D.Double point: points) {
			if (triangle.contains(point)) {
				continue;
			}
			if (farthestPointAvobeLine) {
				if (Line.isPointAboveLine(new Line(currentLine.getFirstPoint(), farthestPoint), point)) {
					firstPointsSet.add(point);
				}
				if (Line.isPointAboveLine(new Line(farthestPoint, currentLine.getSecondPoint()), point)) {
					secondPointsSet.add(point);
				}
			} else {
				if (!Line.isPointAboveLine(new Line(currentLine.getFirstPoint(), farthestPoint), point)) {
					firstPointsSet.add(point);
				}
				if (!Line.isPointAboveLine(new Line(farthestPoint, currentLine.getSecondPoint()), point)) {
					secondPointsSet.add(point);
				}
			}
		}
		
		if (!firstPointsSet.isEmpty()) {
			callStack.addFirst(new LinePointsPair(new Line(currentLine.getFirstPoint(), farthestPoint), firstPointsSet));
		}
		if (!secondPointsSet.isEmpty()) {
			callStack.addFirst(new LinePointsPair(new Line(farthestPoint, currentLine.getSecondPoint()), secondPointsSet));
		}
	}

	/**
	 * Getter method for the view attribute.
	 * 
	 * @return View.
	 */ 
	public MainWindow getView() {
		return view;
	}
	
	/**
	 * Handles the slider events produced when the users moves the slider on the ControlPanel.
	 * @param e ChangeEvent of the slider.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource().equals(view.getControlPanel().getTimerSlider())) {
			JSlider source = (JSlider)e.getSource();
	    if (!source.getValueIsAdjusting()) {
	        timerDelay = source.getValue();
	        timer.setDelay(timerDelay);
	    }
		} else if (e.getSource().equals(view.getControlPanel().getPointsDiameterSlider())) {
			JSlider source = (JSlider)e.getSource();
	    if (!source.getValueIsAdjusting()) {
	    	view.getConvexHullPanel().setDiameter(source.getValue());
	    }
		}
	}
}
