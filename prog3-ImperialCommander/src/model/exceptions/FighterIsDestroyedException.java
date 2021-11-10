package model.exceptions;

import model.Fighter;

/**
 * Class that represents an exception thrown when a destroyed fighter tryes to
 * do an action
 *
 * @author Javier Rodríguez Pérez - 24435270R
 */
@SuppressWarnings("serial")
public class FighterIsDestroyedException extends Exception {
	/**
	 * Object that caused the error
	 */
	private final Fighter f;

	/**
	 * @param f Object that caused the error
	 */
	public FighterIsDestroyedException(Fighter f) {
		this.f = f;
	}

	/**
	 * @return The message
	 */
	@Override
	public String getMessage() {
		return "The fighter" + f + " was used, but is destroyed";
	}

}
