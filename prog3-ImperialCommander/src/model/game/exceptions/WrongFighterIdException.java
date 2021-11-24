package model.game.exceptions;

/**
 * Thrown when a wrong id is used
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 */
@SuppressWarnings("serial")
public class WrongFighterIdException extends Exception {

	/**
	 * Id that caused the error
	 */
	private final int id;

	/**
	 * @param id Id that caused the error
	 */
	public WrongFighterIdException(int id) {
		this.id = id;
	}

	/**
	 * @return The message
	 */
	@Override
	public String getMessage() {
		return "ERROR: Wrong id: " + id;
	}

}
