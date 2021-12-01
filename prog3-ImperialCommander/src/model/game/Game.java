package model.game;

import java.util.Objects;

import model.Side;
import model.exceptions.InvalidSizeException;

public class Game {

	public static final int BOARD_SIZE = 10;

	private final GameBoard board;
	private final IPlayer imperial;
	private final IPlayer rebel;

	public Game(IPlayer imperial, IPlayer rebel) {
		Objects.requireNonNull(imperial);
		Objects.requireNonNull(rebel);
		try {
			this.board = new GameBoard(BOARD_SIZE);
		} catch (InvalidSizeException e) {
			throw new RuntimeException(e);
		}
		this.imperial = imperial;
		this.rebel = rebel;
		this.imperial.setBoard(this.board);
		this.rebel.setBoard(this.board);
	}

	public GameBoard getGameBoard() {
		return board;
	}

	public Side play() {
		imperial.initFighters();
		rebel.initFighters();
		Side winner = null;
		do {
			System.out.println("BEFORE IMPERIAL");
			showBoardAndShip();
			System.out.println("IMPERIAL(" + board.numFighters(Side.IMPERIAL) + "):");
			if (!imperial.nextPlay()) {
				winner = Side.REBEL;
				break;
			}
			System.out.println("AFTER IMPERIAL, BEFORE REBEL");
			showBoardAndShip();
			winner = fleetDestroyed();
			if (winner != null)
				break;
			System.out.println("REBEL(" + board.numFighters(Side.REBEL) + "):");
			if (!rebel.nextPlay()) {
				winner = Side.IMPERIAL;
				break;
			}
			System.out.println("AFTER REBEL");
			showBoardAndShip();
			purgeFLeets();
			winner = fleetDestroyed();
			if (winner != null)
				break;
		} while (winner != null);
		purgeFLeets();
		return winner;
	}

	/**
	 * @return The winner if someone's fleet is destroyed
	 */
	private Side fleetDestroyed() {
		if (imperial.isFleetDestroyed()) {
			return Side.REBEL;
		}
		if (rebel.isFleetDestroyed()) {
			return Side.IMPERIAL;
		}
		return null;
	}

	/**
	 * Shows the board and the ship
	 */
	private void showBoardAndShip() {
		System.out.println(board);
		System.out.println(imperial.getGameShip());
		System.out.println(rebel.getGameShip());
	}

	/**
	 * Purges the fleets
	 */
	private void purgeFLeets() {
		imperial.purgeFleet();
		rebel.purgeFleet();
	}

}
