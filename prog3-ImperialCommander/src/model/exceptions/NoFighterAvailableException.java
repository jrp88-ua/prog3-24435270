package model.exceptions;

@SuppressWarnings("serial")
public class NoFighterAvailableException extends Exception {

	private final String type;

	public NoFighterAvailableException(String type) {
		this.type = type;
	}

	@Override
	public String getMessage() {
		return "There are no fighters of the type " + type + " that are not destroyed";
	}

}
