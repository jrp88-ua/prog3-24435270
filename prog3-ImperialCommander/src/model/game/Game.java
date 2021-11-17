package model.game;

import model.Side;

public class Game {

	public static final int BOARD_SIZE = 10;

	private final GameBoard board;

	public Game(IPlayer imperial, IPlayer rebel) {
		// TODO Auto-generated constructor stub
		board = new GameBoard(BOARD_SIZE);
	}

	public GameBoard getGameBoard() {
		return board;
		// TODO
	}

	public Side play() {
		// TODO
	}

}
