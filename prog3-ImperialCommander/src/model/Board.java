package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import model.exceptions.FighterAlreadyInBoardException;
import model.exceptions.FighterIsDestroyedException;
import model.exceptions.FighterNotInBoardException;
import model.exceptions.InvalidSizeException;
import model.exceptions.OutOfBoundsException;

/**
 * Class that represents a board
 * 
 * @author Javier Rodriguez Perez - 24435270R
 */
public class Board {

	/**
	 * Size of the board
	 */
	private int size;

	/**
	 * Board of the game
	 */
	private Map<Coordinate, Fighter> board;

	/**
	 * Creates a new board with the given size
	 * 
	 * @param size The size of the board
	 * @throws InvalidSizeException If size is less than 5
	 */
	public Board(int size) throws InvalidSizeException {
		if (size < 5)
			throw new InvalidSizeException(size);
		this.size = size;
		board = new HashMap<>();
	}

	/**
	 * Makes the given fighter fight all enemies on the neighborhood
	 * 
	 * @param f The fighter
	 * @throws FighterNotInBoardException If the fighter has no position assigned
	 */
	public void patrol(Fighter f) throws FighterNotInBoardException {
		Objects.requireNonNull(f);
		if (!inBoard(f))
			throw new FighterNotInBoardException(f);
		Set<Coordinate> neigh;
		try {
			neigh = getNeighborhood(f.getPosition());
		} catch (OutOfBoundsException e) {
			throw new RuntimeException("The fighter " + f + " is not in the board");
		}
		for (Coordinate c : neigh) {
			fight(c, f, false, true);
			if (f.isDestroyed()) {
				// removeFighter(f); - fight(Coordinate, Fighter, boolean) ya elimina el
				// destruido
				return;
			}
		}
	}

	/**
	 * Makes the given fighter fight the enemy in the given coordinate, if there is
	 * an enemy, if not updates the location if not occupied
	 * 
	 * @param c The coordinate
	 * @param f The fighter
	 * @return The result of the fight, 0 in any other case
	 * @throws FighterAlreadyInBoardException If the fighter already has a position
	 * @throws OutOfBoundsException           If the coordinate is not in the board
	 * @throws RuntimeException               If any of the fighters is destroyed
	 */
	public int launch(Coordinate c, Fighter f) throws FighterAlreadyInBoardException, OutOfBoundsException {
		Objects.requireNonNull(c);
		Objects.requireNonNull(f);
		if (inBoard(f))
			throw new FighterAlreadyInBoardException(f);
		if (!inside(c))
			throw new OutOfBoundsException(c);
		return fight(c, f, true, true);
	}

	/**
	 * Makes the given fighter fight the enemy in the given coordinate, if there is
	 * an enemy
	 * 
	 * @param c               The coordinate
	 * @param f               The fighter
	 * @param updatePosition  If true, positions are updated
	 * @param removeDestroyed If true, the destroyed fighter will be removed from
	 *                        the board
	 * @return The result of the fight, 0 in any other case
	 * @throws RuntimeException If any of the fighters is destroyed
	 */
	private int fight(Coordinate c, Fighter f, boolean updatePosition, boolean removeDestroyed) {
		Objects.requireNonNull(c);
		Objects.requireNonNull(f);
		if (!inside(c))
			return 0;
		Fighter enemy = getFighter0(c);
		if (enemy == null) {
			// no hay nadie
			if (updatePosition) {
				board.put(c, f);
				f.setPosition(c);
			}
			return 0;
		}
		if (enemy.getSide() == f.getSide()) {
			// esta ocupada por una caza del mismo bando
			// no se hace nada
			return 0;
		}
		int result;
		try {
			result = f.fight(enemy);
		} catch (FighterIsDestroyedException e) {
			throw new RuntimeException(e.getMessage());
		}
		if (result == 1) {
			f.getMotherShip().updateResults(1);
			enemy.getMotherShip().updateResults(-1);
			if (removeDestroyed) {
				try {
					removeFighter(enemy);
				} catch (FighterNotInBoardException e) {
					// imposible llegar aqui
				}
			}
			if (updatePosition) {
				board.put(c, f);
				f.setPosition(c);
			}
		} else if (result == -1) {
			f.getMotherShip().updateResults(-1);
			enemy.getMotherShip().updateResults(1);
			if (removeDestroyed) {
				try {
					removeFighter(f);
				} catch (FighterNotInBoardException e) {
					// imposible llegar aqui
				}
			}
		}

		return result;
	}

	/**
	 * @param c The central coordinate
	 * @return An ordered set with the coordinates that surround the given
	 *         coordinate and are inside the board
	 * @throws OutOfBoundsException If the coordinate is not in the board
	 */
	public Set<Coordinate> getNeighborhood(Coordinate c) throws OutOfBoundsException {
		Objects.requireNonNull(c);
		if (!inside(c))
			throw new OutOfBoundsException(c);
		Set<Coordinate> s = c.getNeighborhood();
		Iterator<Coordinate> it = s.iterator();
		while (it.hasNext()) {
			Coordinate n = it.next();
			if (!inside(n))
				it.remove();
		}
		return s;
	}

	/**
	 * @param c The coordinate
	 * @return True if the coordinate is inside the board
	 */
	public boolean inside(Coordinate c) {
		if (c == null)
			return false;
		return (0 <= c.getX() && c.getX() < size) && (0 <= c.getY() && c.getY() < size);
	}

	/**
	 * Removes the given fighter from the board if the board contains the given
	 * fighter
	 * 
	 * @param f The fighter to remove
	 * @return True if the fighter was removed, false otherwise
	 * @throws FighterNotInBoardException If the fighter is not in the board
	 */
	public void removeFighter(Fighter f) throws FighterNotInBoardException {
		Objects.requireNonNull(f);
		if (!isSynchronizedWithBoard(f))
			throw new FighterNotInBoardException(f);
		Fighter inPos = board.get(f.getPosition());
		if (!f.equals(inPos))
			throw new FighterNotInBoardException(f);
		board.remove(f.getPosition()).setPosition(null);
	}

	/**
	 * Checks if a fighter is in the board but not if it is synchronized with the
	 * board
	 * 
	 * @param f The fighter
	 * @return True if the fighter is in the board
	 */
	public boolean inBoard(Fighter f) {
		Objects.requireNonNull(f);
		return f.getPosition() != null;
	}

	/**
	 * @param f The fighter to check if is consistent with the board
	 * @return True if {@link #inBoard(Fighter)} returns true and is synchronized
	 *         with the board
	 */
	public boolean isSynchronizedWithBoard(Fighter f) {
		Objects.requireNonNull(f);
		return inBoard(f) && board.containsKey(f.getPosition()) && board.get(f.getPosition()).equals(f);
	}

	/**
	 * @param c The coordinate
	 * @return A copy of the fighter in the given coordinate, null if no fighter is
	 *         there
	 */
	public Fighter getFighter(Coordinate c) {
		Fighter f = getFighter0(c);
		if (f == null)
			return null;
		return f.copy();
	}

	/**
	 * @param c The coordinate
	 * @return The fighter in the given coordinate, null if no fighter is there
	 */
	public Fighter getFighter0(Coordinate c) {
		Objects.requireNonNull(c);
		if (board.get(c) == null)
			return null;
		return board.get(c);
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Creates the hash code of this board
	 */
	@Override
	public int hashCode() {
		return getSize();
	}

	/**
	 * Compares this board with the given object
	 * 
	 * @param obj The other object to compare
	 * 
	 * @return True if {@code this == other} or both boards have the same size,
	 *         false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Board)) {
			return false;
		}
		Board other = (Board) obj;
		return getSize() == other.getSize();
	}

	/**
	 * A string representation of the board
	 */
	@Override
	public String toString() {
		return "Board [size=" + size + "]";
	}

}
