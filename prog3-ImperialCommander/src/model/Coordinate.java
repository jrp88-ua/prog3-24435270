package model;

import java.util.Set;
import java.util.TreeSet;

/**
 * Class that holds an x and y value
 *
 * @author Javier Rodriguez Perez - 24435270R
 */
public class Coordinate implements Comparable<Coordinate> {

	/**
	 * x component
	 */
	private int x;
	/**
	 * y component
	 */
	private int y;

	/**
	 * Creates a new coordinate with the given values
	 * 
	 * @param x The x value of the coordinate
	 * @param y The y value of the coordinate
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates a copy of the given coordinate
	 * 
	 * @param coordinate The coordinate to copy
	 */
	public Coordinate(Coordinate coordinate) {
		this(coordinate.getX(), coordinate.getY());
	}

	/**
	 * Compares this coordinate with the given object
	 * 
	 * @param obj The other object to compare
	 * 
	 * @return True if {@code this == other} or both components of the coordinates
	 *         match, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Coordinate)) {
			return false;
		}
		Coordinate other = (Coordinate) obj;
		return getX() == other.getX() && getY() == other.getY();
	}

	/**
	 * Compares this coordinate such that returns:
	 * <ol>
	 * <li>-1 if {@code getX() < o.getX()}</li>
	 * <li>1 if {@code getX() > o.getX()}</li>
	 * <li>-1 if {@code getY() < o.getY()}</li>
	 * <li>1 if {@code getY() > o.getY()}</li>
	 * <li>0 otherwise</li>
	 * </ol>
	 * in that order
	 * 
	 * @param o The other coordinate
	 * 
	 * @return -1, 0 or 1 according to what is established before
	 */
	@Override
	public int compareTo(Coordinate o) {
		if (getX() < o.getX())
			return -1;
		if (getX() > o.getX())
			return 1;
		if (getY() < o.getY())
			return -1;
		if (getY() > o.getY())
			return 1;
		return 0;
	}

	/**
	 * Gets the surrounding coordinates
	 * 
	 * @return A ordered set with the surrounding coordinates
	 */
	public Set<Coordinate> getNeighborhood() {
		Set<Coordinate> s = new TreeSet<>();
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				if (i != 0 || j != 0)
					s.add(this.add(i, j));
		return s;
	}

	/**
	 * Creates the hash code of the coordinate
	 */
	@Override
	public int hashCode() {
		return getX() + getY();
	}

	/**
	 * Creates a new coordinate with its components being the sum of the instance
	 * and the given coordinate
	 * 
	 * @param c The other coordinate, cannot be null
	 * 
	 * @return A new coordinate with its components being the sum of both
	 *         coordinates
	 */
	public Coordinate add(Coordinate c) {
		return add(c.getX(), c.getY());
	}

	/**
	 * Creates a new coordinate with its components being the sum of the instance
	 * and the given coordinate
	 * 
	 * @param x The x component
	 * @param y The y component
	 * 
	 * @return A new coordinate with its components being the sum of both
	 *         coordinates
	 */
	public Coordinate add(int x, int y) {
		return new Coordinate(getX() + x, getY() + y);
	}

	/**
	 * @return The x component of the coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return The y component of the coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return A string representing the coordinate with the format {@code [x, y]}
	 */
	@Override
	public String toString() {
		return "[" + getX() + "," + getY() + "]";
	}

}