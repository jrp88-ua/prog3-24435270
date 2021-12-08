package model.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.OptionalInt;

import model.Coordinate;
import model.Side;
import model.exceptions.FighterAlreadyInBoardException;
import model.exceptions.FighterNotInBoardException;
import model.exceptions.NoFighterAvailableException;
import model.exceptions.OutOfBoundsException;
import model.game.exceptions.WrongFighterIdException;

/**
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class PlayerFile implements IPlayer {

  /**
   * the reader reading the instructions
   */
	private final BufferedReader br;
	/**
	 * The ship of the player
	 */
	private final GameShip ship;
	/**
	 * The board of the game
	 */
	private GameBoard board;
	/**
	 * The fighters of the player
	 */
	private String fighters;

  /**
   * @param side The side of the player
   * @param br the reader reading the instructions
   */
	public PlayerFile(Side side, BufferedReader br) {
		Objects.requireNonNull(side);
		Objects.requireNonNull(br);
		this.ship = new GameShip("PlayerFile " + side.name() + " Ship", side);
		this.br = br;
	}

	@Override
	public void setBoard(GameBoard gb) {
		Objects.requireNonNull(gb);
		this.board = gb;
	}

	@Override
	public GameShip getGameShip() {
		return ship;
	}

	@Override
	public void initFighters() {
		try {
			fighters = br.readLine();
			ship.addFighters(fighters);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isFleetDestroyed() {
		return getGameShip().isFleetDestroyed();
	}

	@Override
	public String showShip() {
		return new StringBuilder(getGameShip().toString()).append("\n").append(getGameShip().showFleet()).toString();
	}

	@Override
	public void purgeFleet() {
		getGameShip().purgeFleet();
	}

	@Override
	public boolean nextPlay() {
		String line;
		try {
			line = br.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if ("exit".equals(line))
			return false;
		String[] args = line.split(" ");
		if (line.startsWith("improve")) {
			if (args.length != 3) {
				System.out.println("ERROR: argumentos invalidos");
				return true;
			}
			int id = Integer.parseInt(args[1]);
			int qty = Integer.parseInt(args[2]);
			if (qty >= 100) {
				System.out.println("ERROR: qty invalido");
				return true;
			}
			try {
				ship.improveFighter(id, qty, board);
			} catch (WrongFighterIdException e) {
				System.out.println("ERROR: " + e.getMessage());
			}
		} else if (line.startsWith("patrol")) {
			if (args.length != 2) {
				System.out.println("ERROR: argumentos invalidos");
				return true;
			}
			try {
				ship.patrol(Integer.parseInt(args[1]), board);
			} catch (FighterNotInBoardException | WrongFighterIdException e) {
				System.out.println("ERROR: " + e.getMessage());
			}
		} else if (line.startsWith("launch")) {
			if (args.length != 3 && args.length != 4) {
				System.out.println("ERROR: argumentos invalidos");
				return true;
			}
			try {
				int x = Integer.parseInt(args[1]);
				int y = Integer.parseInt(args[2]);
				Coordinate c = new Coordinate(x, y);
				int id;
				OptionalInt oId = parseNum(args[3]);
				if (oId.isPresent()) {
					id = oId.getAsInt();
				} else {
					id = ship.getFirstAvailableFighter(args.length == 4 ? args[3] : "").getId();
				}
				ship.launch(id, c, board);
			} catch (FighterAlreadyInBoardException | OutOfBoundsException | WrongFighterIdException
					| NoFighterAvailableException e) {
				System.out.println("ERROR: " + e.getMessage());
			}
			return true;
		} else {
			System.out.println("ERROR: option invalida");
		}
		return true;
	}

	/**
	 * Parses a string to a number
	 * 
	 * @param str The string to parse
	 * @return An optional, empty if the string could not be parsed
	 */
	private static OptionalInt parseNum(String str) {
		try {
			return OptionalInt.of(Integer.valueOf(str));
		} catch (NumberFormatException e) {
			return OptionalInt.empty();
		}
	}

}
