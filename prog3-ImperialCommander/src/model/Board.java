package model;

/**
 * Class that represents a board
 * 
 * @author Javier Rodriguez Perez - 24435270R
 */
public class Board {

	/**
	 * Size of the board
	 */
	private int size;

	/**
	 * Creates a new board with the given size
	 * 
	 * @param size The size of the board
	 */
	public Board(int size) {
		this.size = size;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Creates the hash code of this board
	 */
	@Override
	public int hashCode() {
		return getSize();
	}

	/**
	 * Compares this board with the given object
	 * 
	 * @param obj The other object to compare
	 * 
	 * @return True if {@code this == other} or both boards have the same size,
	 *         false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Board)) {
			return false;
		}
		Board other = (Board) obj;
		return getSize() == other.getSize();
	}

	/**
	 * A string representation of the board
	 */
	@Override
	public String toString() {
		return "Board [size=" + size + "]";
	}

}
