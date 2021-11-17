package model.game;

import model.Side;

/**
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class PlayerRandom implements IPlayer {

	private final GameShip ship;
	private GameBoard board;
	private int numFighters;

	public PlayerRandom(Side side, int numFighters) {
		this.numFighters = numFighters;
		this.ship = new GameShip("", side);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setBoard(GameBoard gb) {
		// TODO Auto-generated method stub
		this.board = gb;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GameShip getGameShip() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initFighters() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFleetDestroyed() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String showShip() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void purgeFleet() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean nextPlay() {
		// TODO Auto-generated method stub
		return false;
	}

}
