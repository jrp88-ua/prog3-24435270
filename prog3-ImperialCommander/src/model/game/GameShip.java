package model.game;

import java.util.List;

import model.Board;
import model.Coordinate;
import model.Fighter;
import model.Ship;
import model.Side;

/**
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class GameShip extends Ship {

	/**
	 * {@inheritDoc}
	 */
	public GameShip(String name, Side side) {
		super(name, side);
	}

	public boolean isFleetDestroyed() {
		// TODO
		return false;
	}
	
	public void improveFighter(int id, int qty, Board b) {
		// TODO
	}
	
	public void patrol(int id, Board board) {
		// TODO
	}
	
	public void launch(int id, Coordinate c, Board board) {
		// TODO
	}
	
	public List<Integer> getFightersId(String where) {
		// TODO
		return null;
	}
	
	private Fighter getFighter(int id) {
		// TODO
		return null;
	}
	
}
