package model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import model.Board;
import model.Coordinate;
import model.Fighter;
import model.Ship;
import model.Side;
import model.exceptions.FighterAlreadyInBoardException;
import model.exceptions.FighterNotInBoardException;
import model.exceptions.OutOfBoundsException;
import model.game.exceptions.WrongFighterIdException;
import model.game.score.DestroyedFightersScore;
import model.game.score.WinsScore;

/**
 * Class that represents a game ship
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class GameShip extends Ship {

	private final WinsScore winsScore;
	private final DestroyedFightersScore destroyedFightersScore;

	/**
	 * @param name the name of the ship
	 * @param side the side of the ship
	 */
	public GameShip(String name, Side side) {
		super(name, side);
		this.winsScore = new WinsScore(getSide());
		this.destroyedFightersScore = new DestroyedFightersScore(getSide());
	}

	@Override
	public void updateResults(int r, Fighter f) {
		super.updateResults(r, f);
		if (r == 1) {
			getWinsScore().score(r);
			getDestroyedFightersScore().score(f);
		}
	}

	/**
	 * @return the winsScore
	 */
	public WinsScore getWinsScore() {
		return winsScore;
	}

	/**
	 * @return the destroyedFightersScore
	 */
	public DestroyedFightersScore getDestroyedFightersScore() {
		return destroyedFightersScore;
	}

	/**
	 * @return true if all the fleet is destroyed
	 */
	public boolean isFleetDestroyed() {
		for (Fighter f : fleet)
			if (!f.isDestroyed())
				return false;
		return true;
	}

	/**
	 * Removes a fighter from the board and gives half of qty to the attack and
	 * shield
	 * 
	 * @param id  If of the fighter
	 * @param qty Amount to improve
	 * @param b   The board
	 * @throws WrongFighterIdException If there is no non destroyed fighter with the
	 *                                 given id
	 */
	public void improveFighter(int id, int qty, Board b) throws WrongFighterIdException {
		Objects.requireNonNull(b);
		Fighter f = getFighter(id);
		try {
			b.removeFighter(f);
		} catch (FighterNotInBoardException ignored) {
		}
		if (qty % 2 == 0) {
			f.addAttack(qty / 2);
			f.addShield(qty / 2);
		} else {
			f.addAttack(qty / 2);
			f.addShield((qty / 2) + 1);
		}
	}

	/**
	 * Makes the fighter with the given id patrol on the given board
	 * 
	 * @param id    The id of the fighter
	 * @param board The board to patrol on
	 * @throws FighterNotInBoardException If the fighter is not in the board
	 * @throws WrongFighterIdException    If there is no non destroyed fighter with
	 *                                    the given id
	 */
	public void patrol(int id, Board board) throws FighterNotInBoardException, WrongFighterIdException {
		Objects.requireNonNull(board);
		board.patrol(getFighter(id));
	}

	/**
	 * Launches a fighter to the board
	 * 
	 * @param id    The id of the fighter
	 * @param c     The coordinate to launch on
	 * @param board The board to launch on
	 * @throws FighterAlreadyInBoardException If the fighter is already in the board
	 * @throws OutOfBoundsException           If the coordinate is out of the bounds
	 *                                        of the board
	 * @throws WrongFighterIdException        If there is no non destroyed fighter
	 *                                        with the given id
	 */
	public void launch(int id, Coordinate c, Board board)
			throws FighterAlreadyInBoardException, OutOfBoundsException, WrongFighterIdException {
		Objects.requireNonNull(c);
		Objects.requireNonNull(board);
		board.launch(c, getFighter(id));
	}

	/**
	 * @param where board for all fighters in the board, ship for all the fighters
	 *              in the ship, any other value all the fighters
	 * @return A list with all the id's of the non destroyed fighters matching where
	 */
	public List<Integer> getFightersId(String where) {
		Predicate<Fighter> noDestroyed = (f) -> !f.isDestroyed();
		Predicate<Fighter> filter;
		if ("board".equals(where)) {
			filter = noDestroyed.and((f) -> f.getPosition() != null);
		} else if ("ship".equals(where)) {
			filter = noDestroyed.and((f) -> f.getPosition() == null);
		} else {
			filter = noDestroyed;
		}
		return new ArrayList<>(fleet.stream().filter(filter).map(Fighter::getId).collect(Collectors.toList()));
	}

	/**
	 * @param id The if of the fighter to search for
	 * @return The fighter with the given id
	 * @throws WrongFighterIdException If there is no fighter with the given id or
	 *                                 the fighter is destroyed
	 */
	private Fighter getFighter(int id) throws WrongFighterIdException {
		for (Fighter f : fleet) {
			if (f.getId() == id) {
				if (!f.isDestroyed()) {
					return f;
				} else {
					throw new WrongFighterIdException(id);
				}
			}
		}
		throw new WrongFighterIdException(id);
	}

}
