package model.exceptions;

import model.Fighter;

/**
 * Class that represents an exception thrown when a fighter is already in the
 * board
 *
 * @author Javier Rodríguez Pérez - 24435270R
 */
@SuppressWarnings("serial")
public class FighterAlreadyInBoardException extends Exception {
	/**
	 * Object that caused the error
	 */
	private final Fighter f;

	/**
	 * @param f Object that caused the error
	 */
	public FighterAlreadyInBoardException(Fighter f) {
		this.f = f;
	}

	/**
	 * @return The message
	 */
	@Override
	public String getMessage() {
		return "ERROR: Tryed to launch " + f + " but it is already in the board";
	}

}
