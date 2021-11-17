package model.exceptions;

import model.Coordinate;

/**
 * Class that represents an out of bouns exception
 *
 * @author Javier Rodríguez Pérez - 24435270R
 */
@SuppressWarnings("serial")
public class OutOfBoundsException extends Exception {

	/**
	 * Object that caused the error
	 */
	private final Coordinate c;

	/**
	 * @param c Object that caused the error
	 */
	public OutOfBoundsException(Coordinate c) {
		this.c = c;
	}

	/**
	 * @return The message
	 */
	@Override
	public String getMessage() {
		return "ERROR: Tryed to access a coordinate out of the board: " + c;
	}

}
