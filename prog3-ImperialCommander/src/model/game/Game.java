package model.game;

import java.util.Objects;

import model.Side;
import model.exceptions.InvalidSizeException;
import model.game.score.DestroyedFightersScore;
import model.game.score.Ranking;
import model.game.score.WinsScore;

/**
 * Class that represents a game
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class Game {

	/**
	 * Size of a board
	 */
	public static final int BOARD_SIZE = 10;

	/**
	 * Board of the game
	 */
	private final GameBoard board;
	/**
	 * The imperial player
	 */
	private final IPlayer imperial;
	/**
	 * The rebel player
	 */
	private final IPlayer rebel;

	/**
	 * @param imperial the imperial player
	 * @param rebel    the rebel player
	 */
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

	/**
	 * @return the board of the game
	 */
	public GameBoard getGameBoard() {
		return board;
	}

	/**
	 * Starts the game
	 * 
	 * @return the wining side
	 */
	public Side play() {
		imperial.initFighters();
		rebel.initFighters();
		Side winner = null;
		do {
			showRanking();
			System.out.println("BEFORE IMPERIAL");
			showBoardAndShip();
			System.out.print("IMPERIAL(" + board.numFighters(Side.IMPERIAL) + "): ");
			if (!imperial.nextPlay()) {
				winner = Side.REBEL;
				break;
			}
			System.out.println("AFTER IMPERIAL, BEFORE REBEL");
			showBoardAndShip();
			winner = fleetDestroyed();
			if (winner != null)
				break;
			System.out.print("REBEL(" + board.numFighters(Side.REBEL) + "): ");
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
		} while (winner == null);
		purgeFLeets();
		showRanking();
		return winner;
	}

	/**
	 * Shows the ranking
	 */
	private void showRanking() {
		Ranking<WinsScore> ws = new Ranking<>();
		Ranking<DestroyedFightersScore> des = new Ranking<>();

		ws.addScore(imperial.getWinsScore());
		ws.addScore(rebel.getWinsScore());

		des.addScore(imperial.getDestroyedFightersScore());
		des.addScore(rebel.getDestroyedFightersScore());

		System.out.println("RANKING WINS: " + ws);
		System.out.println("RANKING DESTROYED: " + des);
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
		System.out.println(imperial.showShip());
		System.out.println(rebel.showShip());
	}

	/**
	 * Purges the fleets
	 */
	private void purgeFLeets() {
		imperial.purgeFleet();
		rebel.purgeFleet();
	}

}
