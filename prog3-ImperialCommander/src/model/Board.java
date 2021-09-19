package model;

/**
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Board)) {
			return false;
		}
		Board other = (Board) obj;
		if (size != other.size) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Board[size=" + size + "]";
	}

}
