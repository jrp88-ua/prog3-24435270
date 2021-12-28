package model.game;

import java.util.List;
import java.util.Objects;

import model.Coordinate;
import model.RandomNumber;
import model.Side;
import model.exceptions.FighterAlreadyInBoardException;
import model.exceptions.FighterNotInBoardException;
import model.exceptions.OutOfBoundsException;
import model.game.exceptions.WrongFighterIdException;
import model.game.score.DestroyedFightersScore;
import model.game.score.WinsScore;

/**
 * Represents a random player
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class PlayerRandom implements IPlayer {

	/**
	 * The player game ship
	 */
	private final GameShip ship;
	/**
	 * The board of the player
	 */
	private GameBoard board;
	/**
	 * The number of fighters
	 */
	private int numFighters;

	/**
	 * @param side        The side of the player
	 * @param numFighters The number of fighters
	 */
	public PlayerRandom(Side side, int numFighters) {
		this.ship = new GameShip("PlayerRandom " + Objects.requireNonNull(side).name() + " Ship", side);
		this.numFighters = numFighters;
	}

	@Override
	public void setBoard(GameBoard gb) {
		this.board = Objects.requireNonNull(gb);
	}

	@Override
	public GameShip getGameShip() {
		return ship;
	}

	@Override
	public void initFighters() {
		StringBuilder sb = new StringBuilder();
		String[] types = ship.getSide().getTypes();

		for (String type : types) {
			int ran = RandomNumber.newRandomNumber(numFighters);
			if (ran != 0) {
				if (sb.length() != 0)
					sb.append(":");
				sb.append(ran).append("/").append(type);
			}
		}
		if (sb.length() != 0)
			ship.addFighters(sb.toString());
	}

	@Override
	public boolean isFleetDestroyed() {
		return ship.isFleetDestroyed();
	}

	@Override
	public String showShip() {
		return new StringBuilder(ship.toString()).append("\n").append(ship.showFleet()).toString();
	}

	@Override
	public void purgeFleet() {
		ship.purgeFleet();
	}

	@Override
	public boolean nextPlay() {
		int option = RandomNumber.newRandomNumber(100);
		if (option == 99)
			return false;
		String where = "";
		if (85 <= option && option <= 98) { // upgrade
			where = "";
		} else if (25 <= option && option <= 84) { // launch
			where = "ship";
		} else if (0 <= option && option <= 24) { // patrol
			where = "board";
		}
		List<Integer> ids = ship.getFightersId(where);
		if (ids.isEmpty()) {
			System.out.println("ERROR: No fighters");
			return true;
		}
		int id = ids.get(RandomNumber.newRandomNumber(ids.size()));
		try {
			if (85 <= option && option <= 98) { // upgrade
				ship.improveFighter(id, option, board);
			} else if (25 <= option && option <= 84) { // launch
				int x = RandomNumber.newRandomNumber(board.getSize());
				int y = RandomNumber.newRandomNumber(board.getSize());
				Coordinate c = new Coordinate(x, y);
				ship.launch(id, c, board);
			} else if (0 <= option && option <= 24) { // patrol
				ship.patrol(id, board);
			}
		} catch (WrongFighterIdException | FighterNotInBoardException | FighterAlreadyInBoardException
				| OutOfBoundsException e) {
			throw new RuntimeException(e);
		}
		return true;
	}

	@Override
	public DestroyedFightersScore getDestroyedFightersScore() {
		return getGameShip().getDestroyedFightersScore();
	}
	
	@Override
	public WinsScore getWinsScore() {
		return getGameShip().getWinsScore();
	}
	
}
