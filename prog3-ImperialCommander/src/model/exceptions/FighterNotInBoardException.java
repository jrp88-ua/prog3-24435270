package model.exceptions;

import model.Fighter;

/**
 * Class that represents an exception thrown when a requested fighter is not in
 * the board
 *
 * @author Javier Rodríguez Pérez - 24435270R
 */
@SuppressWarnings("serial")
public class FighterNotInBoardException extends Exception {
	/**
	 * Object that caused the error
	 */
	private final Fighter f;

	/**
	 * @param f Object that caused the error
	 */
	public FighterNotInBoardException(Fighter f) {
		this.f = f;
	}

	/**
	 * @return The message
	 */
	@Override
	public String getMessage() {
		return "The fighter " + f + " is not in the board";
	}

}
