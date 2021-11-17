package model.exceptions;

/**
 * Class that represents an invalid size exception
 *
 * @author Javier Rodríguez Pérez - 24435270R
 */
@SuppressWarnings("serial")
public class InvalidSizeException extends Exception {

	/**
	 * Object that caused the error
	 */
	private final int s;

	/**
	 * @param s Object that caused the error
	 */
	public InvalidSizeException(int s) {
		this.s = s;
	}

	/**
	 * @return The message
	 */
	@Override
	public String getMessage() {
		return "ERROR: Tryed to create a board with size < 5 (" + s + ")";
	}

}
