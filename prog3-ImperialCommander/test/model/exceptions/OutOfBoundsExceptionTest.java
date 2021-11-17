package model.exceptions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Board;
import model.Coordinate;
import model.Fighter;
import model.FighterFactory;
import model.Ship;
import model.Side;

public class OutOfBoundsExceptionTest {

	Fighter rebel, imperial;
	Ship rebelShip;
	Board board;
	Coordinate c;
	
	@Before
	public void setUp() throws Exception {
		rebelShip = new Ship("Tydirium", Side.REBEL);
		rebel = FighterFactory.createFighter("AWing", rebelShip);
		board = new Board(5);
	}

	/* Se intenta poner en un tablero un caza en una posición fuera del tablero.
	 * Se comprueba que se lanza la excepción OutOfBoundsException y no otra. 
	 */
	@Test
	public void LaunchOutOfBoard() {
		c = new Coordinate(5,5);
		try {
		  board.launch(c, rebel);
		  fail("ERROR: debió  lanzar OutOfBoundsException");
		} catch (OutOfBoundsException e1){
			assertTrue(e1.getMessage().startsWith("ERROR:"));
		} catch (Exception e2) {
			fail("ERROR: NO debió  lanzar "+e2.getClass().getSimpleName());
		}
	}
	
	
	/* Se intenta obtener en un tablero las coordenadas vecinas de una coordenada
	 * que está fuera del tablero.
	 * Se comprueba que se lanza la excepción OutOfBoundsException y no otra. 
	 */
	@Test
	public void getNeigborhoodOutOfBoard() {
		c = new Coordinate(6,6);
	
		try {
		  board.getNeighborhood(c);
		  fail("ERROR: debió  lanzar OutOfBoundsException");
		} catch (OutOfBoundsException e1){
			assertTrue(e1.getMessage().startsWith("ERROR:"));
		} catch (Exception e2) {
			fail("ERROR: NO debió  lanzar "+e2.getClass().getSimpleName());
		}
	}
	
	/* Se intenta hacer que un caza posicionado fuera del tablero patrulle. Se comprueba
	 * que se lanza la excepción RuntimeException (y no OutOfBoundsException)
	 */
	@Test
	public void getPatrolOutOfBoard() {
		c = new Coordinate(6,6);
		rebel.setPosition(c);
		try {
		  board.patrol(rebel);
		  fail("ERROR: debió  lanzar RuntimeException");
		} catch (RuntimeException e1){
			
		} catch (Exception e2) {
			fail("ERROR: NO debió  lanzar "+e2.getClass().getSimpleName());
		}
	}

}
