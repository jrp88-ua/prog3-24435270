package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that generates random numbers
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 *
 */
public class RandomNumber {

	private static Random generator = new Random(1L);
	private static List<Integer> list = new ArrayList<Integer>();

	/**
	 * Creates a pseudo-random generated number
	 * 
	 * @param max The max number, exclusive
	 * @return A number between 0 and max-1
	 */
	public static int newRandomNumber(int max) {
		int r = generator.nextInt(max);
		list.add(r);
		return r;
	}

	/**
	 * Gets all the generated random numbers
	 * 
	 * @return A list with all generated numbers
	 */
	public static List<Integer> getRandomNumberList() {
		return list;
	}

	/**
	 * Resets the list of random numbers
	 */
	public static void resetRandomCounter() {
		list.clear();
		generator = new Random(1L);
	}
}