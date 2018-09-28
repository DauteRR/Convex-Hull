/**
 * File containing the LineTest entity definition. 
 */

package pai.pract11.convexhull.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
 * @version 1.0
 * @since 20 abr. 2018
 */
public class LineTest {

	/** Testing line. Positive gradient line */
	Line	firstLine;
	/** Testing line. Negative gradient line */
	Line	secondLine;
	/** Testing line. Vertical line */
	Line	thirdLine;
	/** Testing line. Horizontal line */
	Line	fourthLine;
	/** Testing line. Normal line */
	Line	fifthLine;

	/**
	 * Initializes the lines used for testing.
	 */
	@Before
	public final void setUp() {
		this.firstLine = new Line(1.0, 0.0);
		this.secondLine = new Line(new Point2D.Double(0, 0), new Point2D.Double(-1, 1));
		this.thirdLine = new Line(new Point2D.Double(2, 2), new Point2D.Double(2, 3));
		this.fourthLine = new Line(new Point2D.Double(2, 2), new Point2D.Double(3, 2));
		this.fifthLine = new Line(new Point2D.Double(0, 4), new Point2D.Double(3, 0));
	}

	/**
	 * Test method for constructor of the Line class.
	 */
	@Test
	public final void testLineTwoPointsConstructor() {
		Line auxiliarLine = new Line(new Point2D.Double(2, 1), new Point2D.Double(0, 0));
		double expectedGradient = 0.5;
		double expectedYIntercept = 0;
		double expectedXIntercept = 0;
		assertTrue(
				Math.abs(expectedGradient - auxiliarLine.getGradient()) < Line.EPS);
		assertTrue(
				Math.abs(expectedYIntercept - auxiliarLine.getyIntercept()) < Line.EPS);
		assertTrue(
				Math.abs(expectedXIntercept - auxiliarLine.getxIntercept()) < Line.EPS);

		auxiliarLine = this.fourthLine;
		expectedGradient = 0;
		expectedYIntercept = 2;
		assertTrue(
				Math.abs(expectedGradient - auxiliarLine.getGradient()) < Line.EPS);
		assertTrue(
				Math.abs(expectedYIntercept - auxiliarLine.getyIntercept()) < Line.EPS);
		assertTrue(auxiliarLine.getxIntercept() == null);

		auxiliarLine = this.thirdLine;
		expectedXIntercept = 2;
		assertTrue(auxiliarLine.getGradient() == null);
		assertTrue(auxiliarLine.getyIntercept() == null);
		assertTrue(
				Math.abs(auxiliarLine.getxIntercept() - expectedXIntercept) < Line.EPS);
	}

	/**
	 * Test error method for constructor of the Line class.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testLineTwoPointsConstructorError() {
		new Line(new Point2D.Double(0, 0), new Point2D.Double(0, 0));
	}

	/**
	 * Test method for contains method of the Line class.
	 */
	@Test
	public final void testContains() {
		assertTrue(this.fifthLine.contains(new Point2D.Double(0, 4)));
		assertTrue(this.fifthLine.contains(new Point2D.Double(3, 0)));
		assertFalse(this.thirdLine.contains(new Point2D.Double(0, 0)));
	}

	/**
	 * Test method for getImage method of the Line class.
	 */
	@Test
	public final void testGetImage() {
		assertTrue(Math.abs(this.fifthLine.getImage(0) - 4) < Line.EPS);
		assertTrue(Math.abs(this.fifthLine.getImage(3) - 0) < Line.EPS);
		assertTrue(Math.abs(this.fifthLine.getImage(1.8) - 1.6) < Line.EPS);
	}

	/**
	 * Test method for distanceToPoint method of the Line class.
	 */
	@Test
	public final void testDistanceToPoint() {
		assertTrue(Math.abs(Line.distanceToPoint(this.firstLine, new Point2D.Double(8, 0)) - Math.sqrt(32)) < Line.EPS);
		assertTrue(Math.abs(Line.distanceToPoint(this.fifthLine, new Point2D.Double(20, 40)) - 37.599999999999994) < Line.EPS);
	}

	/**
	 * Test method for intersectionBetweenLines method of the Line class.
	 */
	@Test
	public final void testIntersectionBetweenLines() {
		assertTrue(Line.intersectionBetweenLines(this.firstLine, this.secondLine)
				.equals(new Point2D.Double(0, 0)));
		assertTrue(Line.intersectionBetweenLines(this.thirdLine, this.fourthLine)
				.equals(new Point2D.Double(2, 2)));
		assertTrue(Line.intersectionBetweenLines(this.firstLine, this.fourthLine)
				.equals(new Point2D.Double(2, 2)));
		assertTrue(Line.intersectionBetweenLines(this.secondLine, this.fourthLine)
				.equals(new Point2D.Double(-2, 2)));
		assertTrue(Line.intersectionBetweenLines(this.secondLine, this.thirdLine)
				.equals(new Point2D.Double(2, -2)));
		Point2D.Double intersectionPoint = Line.intersectionBetweenLines(this.secondLine, this.fifthLine);
		assertTrue(Math.abs(intersectionPoint.getX() - 12) < Line.EPS);
		assertTrue(Math.abs(intersectionPoint.getY() - (-12)) < Line.EPS);
	}

	/**
	 * Test method for getXIntercept method of the Line class.
	 */
	@Test
	public final void testGetxIntercept() {
		assertTrue(this.fourthLine.getxIntercept() == null);
		assertTrue(Math.abs(this.thirdLine.getxIntercept() - 2) < Line.EPS);
	}

}
