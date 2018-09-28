/**
* File containing the ConvexHull entity definition. 
 */

package pai.pract11.convexhull;

import pai.pract11.convexhull.controller.ConvexHullController;

/**
 * Class which contains the main method of the Convex Hull program. It was created 
 * for the eleventh practice of PAI (Programación de Aplicaciones Interactivas)
 * course of ULL (Universidad de la Laguna).
 * 
 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
 * @version 1.0
 * @since 20 abr. 2018
 */
public class ConvexHull {

	/**
	 * Main method.
	 * 
	 * @param args
	 *          Arguments given to the program.
	 */
	public static void main(String[] args) {
		final int EXPECTED_ARGUMENTS = 2;
		if (args.length != EXPECTED_ARGUMENTS) {
			System.err.println("Use: java -jar amountOfPoints timerDelay(ms)");
			return;
		}
		
		try {
			final int POINTS = Integer.parseInt(args[0]);
			final int TIMER_DELAY = Integer.parseInt(args[1]);
			final int WIDTH = 700;
			final int HEIGHT = 700;

			if (POINTS < 2) {
				System.err.println("Use a higher amount of points (2 or more)");
				return;
			} else if (TIMER_DELAY < 1) {
				System.err.println(
						"Use a higher amount of milliseconds for the timer delay (1 or more)");
				return;
			}

			ConvexHullController controller = new ConvexHullController(WIDTH, HEIGHT,
					POINTS, TIMER_DELAY, false);

		} catch (NumberFormatException e) {
			System.err.println("Use: java -jar amountOfPoints timerDelay");
		}
		
	}

}
