package model.exceptions;

import model.Fighter;

@SuppressWarnings("serial")
public class FighterNotInBoardException extends Exception {

	private final Fighter f;

	public FighterNotInBoardException(Fighter f) {
		this.f = f;
	}

	@Override
	public String getMessage() {
		return "The fighter " + f + " is not in the board";
	}

}
