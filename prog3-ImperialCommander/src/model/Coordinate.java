package model;

/**
 * Class that holds an x and y value
 *
 * @author Javier Rodriguez Perez - 24435270R
 */
public class Coordinate {

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