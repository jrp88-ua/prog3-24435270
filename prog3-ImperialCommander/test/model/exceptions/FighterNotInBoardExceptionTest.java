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

public class FighterNotInBoardExceptionTest {

	Fighter rebel, imperial;
	Ship rebelShip, imperialShip;
	Board board;
	Coordinate c;
	
	@Before
	public void setUp() throws Exception {
		rebelShip = new Ship("Tydirium", Side.REBEL);
		imperialShip = new Ship("Lanzadera T-4a", Side.IMPERIAL);
		rebel = FighterFactory.createFighter("AWing", rebelShip);
		imperial = FighterFactory.createFighter("TIEBomber", imperialShip);
		board = new Board(5);
	}

	/* Se intenta eliminar de un tablero un caza que no está en el tablero ni tiene posición */
	@Test
	public void removeFighterNoExistInBoardTest1() {
		try {
			board.removeFighter(rebel);
			fail("ERROR: Debió lanzar la excepción FighterNotInBoardException");
		} catch (FighterNotInBoardException e) {
			assertTrue (e.getMessage().startsWith("ERROR:"));
		}
	}
	
	/* Se intenta eliminar de un tablero un caza que está en el tablero pero no en la posición
	 * que dice estar el caza */
	@Test
	public void removeFighterExistInBoardTest() throws OutOfBoundsException, FighterAlreadyInBoardException {
		board.launch(new Coordinate(2,2), rebel);
		c = new Coordinate (3,3);
		rebel.setPosition(c);	
		try {
			board.removeFighter(rebel);
			fail("ERROR: Debió lanzar la excepción FighterNotInBoardException");
		} catch (FighterNotInBoardException e) {
			assertTrue (e.getMessage().startsWith("ERROR:"));
		}
	}

	/* Se intenta eliminar de un tablero un caza que es distinto al que está en esa posición */
	@Test
	public void removeFighterNoExistInBoardTest2() throws OutOfBoundsException, FighterAlreadyInBoardException {
		
		c = new Coordinate (3,3);
		board.launch(c, rebel);
		imperial.setPosition(c);	
		try {
			board.removeFighter(imperial);
			fail("ERROR: Debió lanzar la excepción FighterNotInBoardException");
		} catch (FighterNotInBoardException e) {
			assertTrue (e.getMessage().startsWith("ERROR:"));
		}
	}
	
	/* El caza que patrulla no se ha posicionado todavía en el tablero */
	@Test
	public void patrolNoExistInBoardTest() throws OutOfBoundsException, FighterAlreadyInBoardException {
		
		try {
			board.patrol(rebel);
			fail("ERROR: Debió lanzar la excepción FighterNotInBoardException");
		} catch (FighterNotInBoardException e) {
			assertTrue (e.getMessage().startsWith("ERROR:"));
		}
	}
	
}
