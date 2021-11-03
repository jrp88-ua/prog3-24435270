package model.exceptions;

import model.Fighter;

@SuppressWarnings("serial")
public class FighterIsDestroyedException extends Exception {

	private final Fighter f;

	public FighterIsDestroyedException(Fighter f) {
		this.f = f;
	}

	@Override
	public String getMessage() {
		return "The fighter" + f + " was used, but is destroyed";
	}

}
