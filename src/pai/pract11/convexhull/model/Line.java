/**
 * File containing the Line entity definition. 
 */

package pai.pract11.convexhull.model;

import java.awt.geom.Point2D;

/**
 * Class which represents a line. It was created for the eleventh practice of
 * PAI (Programación de Aplicaciones Interactivas) course of ULL (Universidad de
 * la Laguna).
 * 
 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
 * @version 1.0
 * @since 20 abr. 2018
 */
public class Line {
	/** Epsilon for Double comparisons. */
	public static final Double	EPS	= 0.01;
	/** Gradient or slope of the line. */
	private Double							gradient;
	/** Point at which the line crosses the y-axis. */
	private Double							yIntercept;
	/** Point at which the line crosses the x-axis. */
	private Double							xIntercept;
	/** Point used to create the line. */
	private Point2D.Double firstPoint;
	/** Point used to create the line. */
	private Point2D.Double secondPoint;

	/**
	 * Constructs a line with the given gradient and yIntercept.
	 * 
	 * @param gradient
	 *          Gradient or slope of the line.
	 * @param yIntercept
	 *          Point at which the line crosses the y-axis.
	 */
	public Line(Double gradient, Double yIntercept) {
		this.gradient = gradient;
		this.yIntercept = yIntercept;
	}

	/**
	 * Constructs a line which contains the given points.
	 * 
	 * @param firstPoint
	 *          First point of the line.
	 * @param secondPoint
	 *          Second point of the line.
	 */
	public Line(Point2D.Double firstPoint, Point2D.Double secondPoint) {
		if (firstPoint.equals(secondPoint)) {
			throw new IllegalArgumentException("The points must be differents!");
		}
		Double firstXCoord = firstPoint.getX();
		Double firstYCoord = firstPoint.getY();
		Double secondXCoord = secondPoint.getX();
		Double secondYCoord = secondPoint.getY();

		if (Math.abs(secondXCoord - firstXCoord) < EPS) {
			this.gradient = null;
			this.yIntercept = null;
			this.xIntercept = secondXCoord;
		} else {
			this.gradient = (secondYCoord - firstYCoord)
					/ (secondXCoord - firstXCoord);
			this.yIntercept = firstYCoord - (this.gradient * firstXCoord);
			if (this.gradient < EPS) {
				this.xIntercept = null;
			} else {
				this.xIntercept = (-this.yIntercept) / this.gradient;
			}
		}
		this.firstPoint = firstPoint;
		this.secondPoint = secondPoint;
	}

	/**
	 * Getter method for gradient attribute.
	 * 
	 * @return gradient
	 */
	public Double getGradient() {
		return this.gradient;
	}

	/**
	 * Getter method for yIntercept attribute.
	 * 
	 * @return yIntercept
	 */
	public Double getyIntercept() {
		return this.yIntercept;
	}

	/**
	 * Checks if the line contains a given point.
	 * 
	 * @param point
	 *          Point to check.
	 * @return Result.
	 */
	public boolean contains(Point2D.Double point) {
		if (this.gradient != null && Math.abs(point.getY()
				- ((this.gradient * point.getX()) + this.yIntercept)) < EPS) {
			return true;
		} else if (this.gradient == null
				&& Math.abs(point.getX() - this.xIntercept) < EPS) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the image of a given x axis point.
	 * 
	 * @param xCoord
	 *          X axis point.
	 * @return Image.
	 */
	public Double getImage(double xCoord) {
		if (this.gradient == null) {
			return null;
		} else {
			return (this.getGradient() * xCoord) + this.getyIntercept();
		}
	}

	/**
	 * Returns the distance between a point and a line.
	 * 
	 * @param line
	 *          Line.
	 * @param point
	 *          Point
	 * @return Distance
	 */
	public static Double distanceToPoint(Line line, Point2D.Double point) {
		if (line.getGradient() == null
				&& Math.abs(line.getxIntercept() - point.getX()) < EPS) {
			return 0.0;
		} else if (line.getGradient() == null) {
			return Math.abs(line.getxIntercept() - point.getX());
		}
		return Math.abs(-line.getGradient() * point.getX() + point.getY() - line.getyIntercept()) / 
				   Math.sqrt(Math.pow(-line.getGradient(), 2) + 1);
	}

	/**
	 * Returns the intersection point of two lines.
	 * 
	 * @param firstLine
	 *          First line.
	 * @param secondLine
	 *          Second line.
	 * @return Intersection point.
	 */
	public static Point2D.Double intersectionBetweenLines(Line firstLine,
			Line secondLine) {
		if ((firstLine.getGradient() == null && secondLine.getGradient() == null)
				|| (firstLine.getyIntercept() == null
						&& secondLine.getyIntercept() == null)) {
			return null;
		} else if (firstLine.getGradient() == null) {
			return new Point2D.Double(firstLine.getxIntercept(),
					secondLine.getImage(firstLine.getxIntercept()));
		} else if (secondLine.getGradient() == null) {
			return new Point2D.Double(secondLine.getxIntercept(),
					firstLine.getImage(secondLine.getxIntercept()));
		} else if (Math
				.abs((secondLine.getyIntercept() - firstLine.getyIntercept())) < EPS
				&& Math.abs(firstLine.getGradient() - secondLine.getGradient()) < EPS) {
			return new Point2D.Double(0, 0);
		}

		Double xCoord = ((secondLine.getyIntercept() - firstLine.getyIntercept())
				/ (firstLine.getGradient() - secondLine.getGradient()));
		Double yCoord = firstLine.getImage(xCoord);
		return new Point2D.Double(xCoord, yCoord);
	}
	
	/**
	 * Checks if a point is above or below a line.
	 * 
	 * @param line Line.
	 * @param point Point.
	 * @return Result.
	 */
	public static boolean isPointAboveLine(Line line, Point2D.Double point) {
		
		if (line.getyIntercept() == null || line.getGradient() == null) {
			return (line.getxIntercept() > point.getX());
		}
		
		Double pointYCoord = point.getY();
		Double image = (point.getX() * line.getGradient()) + line.getyIntercept();
		
		// Due to the coordinate systems of the GUI's windows,
		// we identify the points in reverse coordinate system.
		if (image > pointYCoord) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Getter method for xIntercept attribute.
	 * 
	 * @return xIntercept
	 */
	public Double getxIntercept() {
		return this.xIntercept;
	}

	/**
	 * Returns the string representation of the line.
	 * 
	 * @return String representation.
	 */
	@Override
	public String toString() {
		return "y = " + this.getGradient() + "x + (" + this.getyIntercept() + ")";
	}

	/**
	 * Getter method for firstPoint attribute.
	 * @return firstPoint
	 */
	public Point2D.Double getFirstPoint() {
		return this.firstPoint;
	}

	/**
	 * Getter method for secondPoint attribute.
	 * @return secondPoint
	 */
	public Point2D.Double getSecondPoint() {
		return this.secondPoint;
	}

}
