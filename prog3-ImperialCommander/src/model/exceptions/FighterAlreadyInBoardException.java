package model.exceptions;

import model.Fighter;

@SuppressWarnings("serial")
public class FighterAlreadyInBoardException extends Exception {

	private final Fighter f;

	public FighterAlreadyInBoardException(Fighter f) {
		this.f = f;
	}

	@Override
	public String getMessage() {
		return "Tryed to launch " + f + " but it is already in the board";
	}

}
