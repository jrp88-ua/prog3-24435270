package model.exceptions;

/**
 * Class that represents an exception thrown when there is no fighter avilable
 *
 * @author Javier Rodríguez Pérez - 24435270R
 */
@SuppressWarnings("serial")
public class NoFighterAvailableException extends Exception {

	/**
	 * Object that caused the error
	 */
	private final String type;

	/**
	 * @param type Object that caused the error
	 */
	public NoFighterAvailableException(String type) {
		this.type = type;
	}

	/**
	 * @return The message
	 */
	@Override
	public String getMessage() {
		return "ERROR: There are no fighters of the type " + type + " that are not destroyed";
	}

}
