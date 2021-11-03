package model.exceptions;

@SuppressWarnings("serial")
public class InvalidSizeException extends Exception {

	private final int s;

	public InvalidSizeException(int s) {
		this.s = s;
	}

	@Override
	public String getMessage() {
		return "Tryed to create a board with size < 5 (" + s + ")";
	}

}
