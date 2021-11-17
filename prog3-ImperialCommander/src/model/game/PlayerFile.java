package model.game;

import java.io.BufferedReader;

import model.Side;

/**
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class PlayerFile implements IPlayer {

	private final BufferedReader br;
	private final GameShip ship;
	private GameBoard board;
	
	public PlayerFile(Side side, BufferedReader br) {
		this.ship = new GameShip(null, side);
		this.br = br;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setBoard(GameBoard gb) {
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