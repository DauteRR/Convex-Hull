/**
 * File containing the QuickHullPanel entity definition. 
 */

package pai.pract11.convexhull.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Class which represents the panel where the convex hull will be painted in the
 * Convex Hull program GUI. It was created for the eleventh practice of PAI
 * (Programación de Aplicaciones Interactivas) course of ULL (Universidad de la
 * Laguna).
 * 
 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
 * @version 1.0
 * @since 20 abr. 2018
 */
public class ConvexHullPanel extends JPanel {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	/** Points of the convex hull panel. */
	private ArrayList<Point2D.Double> points;
	/** Convex hull. */
	private ArrayList<Point2D.Double> convexHull;
	/** Diameter of the points. */
	private int diameter = 4;
	/** Radius of the points. */
	private int radius = diameter / 2;
	/** Color of the lines of the convex hull. */
	private Color linesColor;
	/** Color of the points. */
	private Color pointsColor;
	

	/**
	 * Default constructor.
	 * 
	 * @param width
	 *          Width of the panel.
	 * @param height
	 *          Height of the panel.
	 * @param points
	 *          Points of the convex hull panel.
	 */
	public ConvexHullPanel(int width, int height, ArrayList<Point2D.Double> points) {
		this.points = points;
		this.setName("Quick hull panel");
		this.setPreferredSize(new Dimension(width, height));
		this.convexHull = new ArrayList<Point2D.Double>();
		this.linesColor = Color.RED;
		this.pointsColor = Color.BLUE;
	}
	
	/**
	 * Getter method for diameter attribute.
	 * @return diameter
	 */
	public int getDiameter() {
		return diameter;
	}

	/**
	 * Setter method for diameter attribute.
	 * @param diameter 
	 */
	public void setDiameter(int diameter) {
		this.diameter = diameter;
		this.radius = diameter / 2;
		repaint();
	}

	/**
	 * Getter method for radius attribute.
	 * @return radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Setter method for radius attribute.
	 * @param radius 
	 */
	public void setRadius(int radius) {
		this.diameter = radius * 2;
		this.radius = radius;
		repaint();
	}

	/**
	 * Paints the panel in the graphics object given as a parameter.
	 * 
	 * @param g
	 *          Graphic object where the convex hull will be painted.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		g.setColor(pointsColor);
		for (Point2D.Double point : this.points) {
			g.fillOval((int)point.x, (int)point.y, diameter, diameter);
		}
		g.setColor(linesColor);
		this.drawConvexHull(g);
	}

	/**
	 * Setter method for linesColor attribute.
	 * @param linesColor 
	 */
	public void setLinesColor(Color linesColor) {
		this.linesColor = linesColor;
		repaint();
	}

	/**
	 * Setter method for pointsColor attribute.
	 * @param pointsColor 
	 */
	public void setPointsColor(Color pointsColor) {
		this.pointsColor = pointsColor;
		repaint();
	}

	/**
	 * Paints the convex hull in the graphics object given as a parameter.
	 * 
	 * @param g
	 *          Graphic object where the convex hull will be painted.
	 */
	private void drawConvexHull(Graphics g) {
		for (int i = 0; i < this.convexHull.size(); ++i) {
			Point2D.Double firstPoint = this.convexHull.get(i);
			Point2D.Double secondPoint = this.convexHull.get((i + 1) % this.convexHull.size());
			g.drawLine((int) firstPoint.getX() + radius,
					(int) firstPoint.getY() + radius, (int) secondPoint.getX() + radius,
					(int) secondPoint.getY() + radius);
		}
	}

	/**
	 * Setter method for points attribute.
	 * 
	 * @param points
	 */
	public void setPoints(ArrayList<Point2D.Double> points) {
		this.points = points;
		this.repaint();
	}

	/**
	 * Adds a point to the convex hull and repaints it.
	 * 
	 * @param insertionPosition
	 *          Position to insert the new point in the convex hull.
	 * @param newPoint
	 *          Point to add.
	 */
	public void addPointToConvexHull(int insertionPosition, Point2D.Double newPoint) {
		this.convexHull.add(insertionPosition, newPoint);
		this.repaint();
	}

	/**
	 * Getter method for points attribute.
	 * 
	 * @return points
	 */
	public ArrayList<Point2D.Double> getPoints() {
		return this.points;
	}

	/**
	 * Deletes the current convex hull.
	 */
	public void restartConvexHull() {
		this.convexHull = new ArrayList<>();
	}

}
