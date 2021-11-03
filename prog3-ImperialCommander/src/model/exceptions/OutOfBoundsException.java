package model.exceptions;

import model.Coordinate;

@SuppressWarnings("serial")
public class OutOfBoundsException extends Exception {

	private final Coordinate c;

	public OutOfBoundsException(Coordinate c) {
		this.c = c;
	}

	@Override
	public String getMessage() {
		return "Tryed to access a coordinate out of the board: " + c;
	}

}
