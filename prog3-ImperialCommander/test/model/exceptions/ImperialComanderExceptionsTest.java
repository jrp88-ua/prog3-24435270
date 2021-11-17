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

public class ImperialComanderExceptionsTest {

	Fighter rebel, imperial;
	Ship rebelShip, imperialShip;
	Board board;
	Coordinate c, c1, c2;
	@Before
	public void setUp() throws Exception {
		rebelShip = new Ship("Tydirium", Side.REBEL);
		imperialShip = new Ship("Lanzadera T-4a", Side.IMPERIAL);
		rebel = FighterFactory.createFighter("AWing", rebelShip);
		imperial = FighterFactory.createFighter("TIEBomber", imperialShip);
		board = new Board(5);
	}

	/* Se intenta crear un tablero con tamaño menor de 5. Se comprueba que lanza
	 * la excepción InvalidSizeException
	 */
	@Test
	public void InvalidSizeException() {
		
		try {
			board = new Board(4);
			fail("ERROR: No lanzó la excepción InvalidSizeException");
		} catch (InvalidSizeException e) {
			assertTrue(e.getMessage().startsWith("ERROR:"));
		}
		
		
		
	}
	
	/* Se posiciona un caza en el tablero. Ese mismo caza se intenta posicionar en otra posición del
	 * tablero. Se comprueba que se lanza la excepción FighterAlreadyInBoardException.
	 */
	@Test
	public void FighterAlreadyInBoardExceptionTest() {
		c = new Coordinate(4,4);
		try {
			board.launch(c, rebel);
		} catch (OutOfBoundsException | FighterAlreadyInBoardException e) {
			fail("ERROR: Lanzó la excepción "+e.getClass().getSimpleName());
		}
		c = new Coordinate(2,2);
		try {
		  board.launch(c, rebel);	
		  fail("ERROR: No lanzó la excepción FighterAlreadyInBoardException");
		} catch (FighterAlreadyInBoardException e) {
			assertTrue(e.getMessage().startsWith("ERROR:"));	
		} catch (Exception e) {
			fail("ERROR: Lanzó la excepción "+e.getClass().getSimpleName());
		}
	}

	/* Se intenta poner en una posición fuera del tablero un caza que ya tiene posición asignada.
	 * Se comprueba que se lanza la excepción FighterAlreadyInBoardException. 
	 * (y no OutOfBoundsException)  
	 */
	@Test
	public void FighterAlreadyInBoardExceptionFirstTest()  {
			
		c = new Coordinate(4,4);
		rebel.setPosition(c);
		c = new Coordinate(7,7);
		try {
			board.launch(c, rebel);
			fail("ERROR: No lanzó la excepción FighterAlreadyInBoardException");
		} catch (FighterAlreadyInBoardException e) {
			assertTrue(e.getMessage().startsWith("ERROR:"));
		} catch (Exception e) {
			fail ("ERROR: No se debió originar una "+e.getClass().getSimpleName());
		} 
	}
	
	
}
