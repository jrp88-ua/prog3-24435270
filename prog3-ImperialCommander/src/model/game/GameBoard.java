package model.game;

import model.Board;
import model.Side;
import model.exceptions.InvalidSizeException;

/**
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class GameBoard extends Board {

	/**
	 * {@inheritDoc}
	 */
	public GameBoard(int size) throws InvalidSizeException {
		super(size);
	}

	public int numFighters(Side side) {
		// TODO
		return 0;
	}
	
	@Override
	public String toString() {
		// TODO
		return super.toString();
	}

}
