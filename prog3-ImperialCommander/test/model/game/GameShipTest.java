package model.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Coordinate;
import model.Fighter;
import model.Ship;
import model.Side;
import model.exceptions.FighterAlreadyInBoardException;
import model.exceptions.FighterNotInBoardException;
import model.exceptions.InvalidSizeException;
import model.exceptions.OutOfBoundsException;
import model.game.exceptions.WrongFighterIdException;

public class GameShipTest {

	GameShip gameShip;
	List<Fighter>fleet ;
	GameBoard gameBoard;
	@Before
	public void setUp() throws Exception {
		 gameShip = new GameShip("Lanzadera T-4a", Side.IMPERIAL);
		 Fighter.resetNextId();
		 
		 //fleet = (List<Fighter>) gameShip.getFleetTest();
	}

	/* Se comprueba que el constructor copia de GameShip es correcto y que
	   GameShip es una clase derivada de Ship (no se han duplicado los atributos)
	 */
	@Test
	public void testGameShip() {
		assertEquals ("Lanzadera T-4a", gameShip.getName());
		assertEquals (Side.IMPERIAL, gameShip.getSide());
		assertEquals (0,  gameShip.getWins());
		assertEquals (0,  gameShip.getLosses());
		fleet = (List<Fighter>) gameShip.getFleetTest();
		assertNotNull (fleet);
		assertTrue(gameShip instanceof Ship );
	}

	/* Se comprueba que isFleetDestroyed devuelve true si no hay cazas en
	 * la nave.
	 */
	@Test
	public void testIsFleetDestroyedEmpty() {
		assertTrue(gameShip.isFleetDestroyed());
	}
	
	/* Se comprueba que isFleetDestroyed devuelve true si hay cazas en
	 * la nave pero todos están destruídos. Para ello se añaden cazas a la nave
	 * se destruyen todos y se comprueba que isFleetDestroyed() devuelve true
	 */
	@Test
	public void testIsFleetDestroyedAllDestroyed() {
		gameShip.addFighters("10/TIEFighter:35/TIEInterceptor:5/TIEBomber");
		for (Fighter f : gameShip.getFleetTest()) {
			f.addShield(-300);
		}
		assertTrue(gameShip.isFleetDestroyed());
	}
	
	/* Se comprueba que isFleetDestroyed devuelve false si hay cazas en
	 * la nave pero todos están destruídos excepto uno. Para ello 
	 * se añaden cazas a la nave se destruyen todos menos uno y se comprueba que 
	 * isFleetDestroyed() devuelve false
	 */
	@Test
	public void testIsFleetDestroyedNotAllDestroyed() {
		fleet = gameShip.getFleetTest();
		gameShip.addFighters("10/TIEFighter:35/TIEInterceptor:5/TIEBomber");
		for (Fighter f : fleet) {
			f.addShield(-300);
		}
		fleet.get(49).addShield(300); //El último es el único que no está destruído
		assertFalse(gameShip.isFleetDestroyed());
		fleet.get(49).addShield(-300);
		fleet.get(0).addShield(300); //Ahora el primero es el único que está destruído
		assertFalse(gameShip.isFleetDestroyed());
	}
	
	/* Se comprueba que se obtiene una lista vacía de una nave sin cazas. Luego se
	 * añaden cazas, se destruyen todos y se comprueba que se sigue devolviendo una
	 * lista vacía.
	 */
	@Test
	public void testGetFightersIdListEmpty() {
		List<Integer> l = gameShip.getFightersId("");
		assertTrue (l.isEmpty());
		gameShip.addFighters("10/TIEFighter:35/TIEInterceptor:5/TIEBomber");
		for (Fighter f : gameShip.getFleetTest()) {
			f.addShield(-300);
		}
		l = gameShip.getFightersId("");
		assertTrue (l.isEmpty());
	}

	/* Se añaden cazas a un Ship. Se comprueba que al invocar a  getFightersId("ship") se devuelve una lista con los id
	 * de todos los cazas del la nave. Además se comprueba que al invocar a getFightesId("board") se devuelve una lista 
	 * vacía.
	 */
	@Test
	public void testGetFightersIdListNotEmpty1()  {
		gameShip.addFighters("10/TIEFighter:35/TIEInterceptor:5/TIEBomber");	
		
		List<Integer> l = gameShip.getFightersId("ship");
		assertEquals(50, l.size());
		for (int i=0; i<50; i++) {
			assertEquals(i+1, l.get(i).intValue());
		}
		l = gameShip.getFightersId("board");
		assertTrue (l.isEmpty());
		
	}
	
	/* Se añaden cazas a un Ship. Se añaden todos a un tablero. Se comprueba que al invocar a  getFightersId("board") 
	 * se devuelve una lista con los id de todos los cazas del la nave.
	 * Además se comprueba que getFightersId("ship") devuelve una lista vacía
	 */
	@Test
	public void testGetFightersIdListNotEmpty2() throws InvalidSizeException, FighterAlreadyInBoardException, OutOfBoundsException  {
		gameShip.addFighters("10/TIEFighter:35/TIEInterceptor:5/TIEBomber");	
		fleet = gameShip.getFleetTest();
		gameBoard = new GameBoard(fleet.size());
		for (int i=0; i<gameBoard.getSize(); i++) {
			gameBoard.launch(new Coordinate(i,i), fleet.get(i));
		}	
		List<Integer> l = gameShip.getFightersId("board");	
		assertEquals(50, l.size());
		for (int i=0; i<50; i++) {
			assertEquals(i+1, l.get(i).intValue());
		}
		l = gameShip.getFightersId("ship");
		assertTrue (l.isEmpty());
	}
	
	/* Se añaden cazas a un Ship. Se añaden algunos a un tablero. Algunos otros se destruyen. 
	 * Se comprueba que al invocar a:
	 *  - getFightersId("board") se devuelve una lista solo con los que realmente
	 * 		están en el tablero. 
     *  - getFightersId("ship") se devuelve una lista solo con los que no están en el tablero y no están destruídos.
     *  - getFightersId("") se devuelve una lista con todos los no destruídos.
	 */
	@Test
	public void testGetFightersIdListNotEmpty3() throws InvalidSizeException, FighterAlreadyInBoardException, OutOfBoundsException  {
		gameShip.addFighters("10/TIEFighter:35/TIEInterceptor:6/TIEBomber");	
		final Integer kBOARD[] = {1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34, 37, 40, 43, 46, 49};
		final Integer kNOBOARD[] = {2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35, 38, 41, 44, 47, 50};
		final Integer kALL[] = {1, 2, 4, 5, 7, 8, 10, 11, 13, 14, 16, 17, 19, 20, 22, 23, 25, 26, 28, 29, 31, 32, 34, 35, 37, 38, 40, 41, 43, 44, 46, 47, 49, 50};
		
		fleet = gameShip.getFleetTest();
		gameBoard = new GameBoard(fleet.size());
		int i=0;
		while (i<gameBoard.getSize()-2) {
			gameBoard.launch(new Coordinate(i,i), fleet.get(i));
			fleet.get(i+2).addShield(-300);
			i+=3;
		}	
		// Se comprueban los del tablero
		List<Integer> lBoard = gameShip.getFightersId("board");	
		List<Integer> lNoBoard = gameShip.getFightersId("ship");
		List<Integer> lAll = gameShip.getFightersId("");
		assertEquals(17, lBoard.size());
		assertEquals(17, lNoBoard.size());
		assertEquals(34, lAll.size());
		for (int j=0; j<34; j++) {
			if (j<17) {
			   assertEquals(kBOARD[j], lBoard.get(j));
			   assertEquals(kNOBOARD[j],lNoBoard.get(j));
			}
			assertEquals(kALL[j],lAll.get(j));
		}	
	}
	
	/* Se añaden cazas a un GameShip y se intenga poner uno, con launch, con una id que no existe. 
	 * Se comprueba que se lanza la excepción WrongFighterIdException y que no lo ha puesto.
	 * Luego se destruye uno del GameShip y se intenta poner en el tablero. Se comprueba que
	 * también se lanza la excepción WrongFighterIdException y que tampoco se ha puesto.
	 */
	@Test
	public void testLaunchWrongFighterIdException() throws InvalidSizeException {
		gameShip.addFighters("4/TIEFighter:3/TIEInterceptor:6/TIEBomber");
		Coordinate c=new Coordinate(4,3);
		fleet = gameShip.getFleetTest();
		gameBoard = new GameBoard(fleet.size());
		try {
			gameShip.launch(20, c, gameBoard);
			fail("ERROR: Debió lanzar la excepción WrongFighterIdException");
		} catch (WrongFighterIdException e1) {
			assertTrue(e1.getMessage().startsWith("ERROR:"));
			assertNull(gameBoard.getFighter(c));
		} catch (Exception e2) {
			fail("ERROR: No debió lanzar la excepción "+e2.getClass().getSimpleName());
		}	
		
		Fighter f = gameShip.getFleetTest().get(5);
		f.addShield(-300);
		try {
			gameShip.launch(6, c, gameBoard);
			fail("ERROR: Debió lanzar la excepción WrongFighterIdException");
		} catch (WrongFighterIdException e1) {
			assertTrue(e1.getMessage().startsWith("ERROR:"));
			assertNull(gameBoard.getFighter(c));
		} catch (Exception e2) {
			fail("ERROR: No debió lanzar la excepción "+e2.getClass().getSimpleName());
		}	
		
		
	}
	
	/* Se añaden cazas a un GameShip y se poner uno en el tablero en una posición donde no hay 
	 * ningún otro caza. Se comprueba que lo pone comprobando que tiene la misma id.
	 * Luego se intenta poner el mismo con su id en otra posición. Se comprueba que se lanza
	 * la excepción FighterAlreadyInBoardException y no otra.
	 */
	@Test
	public void testLaunchFighterAlreadyInBoardException() throws InvalidSizeException {
		gameShip.addFighters("4/TIEFighter:3/TIEInterceptor:6/TIEBomber");
		fleet = gameShip.getFleetTest();
		gameBoard = new GameBoard(fleet.size());
		
		Coordinate c=new Coordinate(4,3);
		try {
			gameShip.launch(8, c, gameBoard);	
		} catch (WrongFighterIdException | FighterAlreadyInBoardException | OutOfBoundsException e) {
			fail("ERROR: No debió lanzar la excepción "+e.getMessage());
		}	
		assertEquals(8,gameBoard.getFighter(c).getId());
		c=new Coordinate(2,5);
		try {
			gameShip.launch(8, c, gameBoard);
			fail("ERROR: Debió lanzar la excepción FighterAlreadyInBoardException");
		} catch (FighterAlreadyInBoardException e1) {
			assertTrue(e1.getMessage().startsWith("ERROR:"));
			assertNull(gameBoard.getFighter(c));
		} catch (Exception e2) {
			fail("ERROR: No debió lanzar la excepción "+e2.getClass().getSimpleName());
		}	
	}
	
	/* Se añaden cazas a un GameShip y se intenga poner uno, con launch, en una coordenada
	 * fuera del tablero. 
	 * Se comprueba que se lanza la excepción OutOfBoundsException.
	 */
	@Test
	public void testLaunchOutOfBoundsException() throws InvalidSizeException {
		gameShip.addFighters("4/TIEFighter:3/TIEInterceptor:6/TIEBomber");
		fleet = gameShip.getFleetTest();
		gameBoard = new GameBoard(fleet.size());
		Coordinate c=new Coordinate(24,3);
		try {
			gameShip.launch(13, c, gameBoard);
			fail("ERROR: Debió lanzar la excepción OutOfBoundsException");
		} catch (OutOfBoundsException e1) {
			assertTrue(e1.getMessage().startsWith("ERROR:"));
		} catch (Exception e2) {
			fail("ERROR: No debió lanzar la excepción "+e2.getClass().getSimpleName());
		}		
	}
	
	/* Se añaden cazas a un GameShip y se pone a patrullar a uno de ellos en un
	 * tablero. Como no está en él, se comprueba que se lanza la excepción 
	 * FighterNotInBoardException y no otra.
	 */
	@Test
	public void testPatrolNotInBoardException() throws InvalidSizeException {
		gameShip.addFighters("4/TIEFighter:3/TIEInterceptor:6/TIEBomber");
		fleet = gameShip.getFleetTest();
		gameBoard = new GameBoard(fleet.size());
		try {
			gameShip.patrol(13, gameBoard);
			fail("ERROR: Debió lanzar la excepción FighterNotInBoardException");
		} catch (FighterNotInBoardException e1) {
			assertTrue(e1.getMessage().startsWith("ERROR:"));
		} catch (Exception e2) {
			fail("ERROR: No debió lanzar la excepción "+e2.getClass().getSimpleName());
		}	
	}

	/* Se añaden cazas a un GameShip y se pone a patrullar a uno con una id que
	 * no existe en el tablero. Como no está en él, se comprueba que se lanza la excepción 
	 * WrongFighterIdException y no otra.
	 */
	@Test
	public void testPatrolWrongFighterIdException() throws InvalidSizeException {
		gameShip.addFighters("4/TIEFighter:3/TIEInterceptor:6/TIEBomber");
		fleet = gameShip.getFleetTest();
		gameBoard = new GameBoard(fleet.size());
		try {
			gameShip.patrol(14, gameBoard);
			fail("ERROR: Debió lanzar la excepción WrongFighterIdException");
		} catch (WrongFighterIdException e1) {
			assertTrue(e1.getMessage().startsWith("ERROR:"));
		} catch (Exception e2) {
			fail("ERROR: No debió lanzar la excepción "+e2.getClass().getSimpleName());
		}	
	}
	
	/* Se añaden cazas a un GameShip y se pone un TIEInterceptor en un tablero.
	 * Se añade una mejora de 97 al caza del tablero. Se comprueba que ya no está en
	 * el tablero y que el ataque ahora es de 133 y el escudo de 109.
	 */
	@Test
	public void testImproveFighter() throws WrongFighterIdException, FighterAlreadyInBoardException, OutOfBoundsException, InvalidSizeException {
		gameShip.addFighters("4/TIEFighter:3/TIEInterceptor:6/TIEBomber");
		fleet = gameShip.getFleetTest();
		gameBoard = new GameBoard(fleet.size());
		
		Coordinate c = new Coordinate(4,7);
		gameShip.launch(6,c,gameBoard);
		Fighter f = gameShip.getFleetTest().get(5); //gameBoard.getFighter(c).getId());
		assertEquals(6, f.getId());
		
		gameShip.improveFighter(6, 97, gameBoard);
		assertNull(gameBoard.getFighter(c));
		assertEquals(133,f.getAttack() );
		assertEquals(109, f.getShield());
	}
	
	/* Se añaden cazas a un GameShip. Se intenta mejorar uno de los cazas del GameShip que
	 * no está en tablero alguno. Se comprueba que no ha existido ninguna mejora en dicho
	 * caza.
	 * Se intenta mejorar un id de un caza que no existe. Se comprueba que se lanza la excepción
	 * WrongFighterIdException y que lanza el mensaje con el inicio de 'ERROR:'
	 */
	@Test
	public void testImproveFighterExceptions() throws FighterAlreadyInBoardException, OutOfBoundsException, InvalidSizeException {
		gameShip.addFighters("4/TIEFighter:3/TIEInterceptor:6/TIEBomber");
		fleet = gameShip.getFleetTest();
		gameBoard = new GameBoard(fleet.size());
		
		try {
			gameShip.improveFighter(6, 97, gameBoard);
		} catch (WrongFighterIdException e) {
			fail("ERROR: No debió lanzar la excepción "+e.getClass().getSimpleName());
		}
		Fighter f=gameShip.getFleetTest().get(5);
		assertEquals(133,f.getAttack() );
		assertEquals(109, f.getShield());
		
		try {
			gameShip.improveFighter(14, 97, gameBoard);
			fail("ERROR: Debió lanzar la excepción WrongFighterIdException");	
		} catch (WrongFighterIdException e) {
			assertTrue(e.getMessage().startsWith("ERROR:"));
		}
	}
	
	/* Test de comprobación de los parámetros null en GameShip */
	@Test
	public void testRequireNonNull() throws WrongFighterIdException, FighterAlreadyInBoardException, OutOfBoundsException, InvalidSizeException, FighterNotInBoardException  {
		
		try {
			gameShip.launch(2, null, new GameBoard(10));
			fail("ERROR: Debió lanzar NullPointerException");
		}catch (NullPointerException e) {}
		try {
			gameShip.launch(2, new Coordinate(3,2), null);
			fail("ERROR: Debió lanzar NullPointerException");
		}catch (NullPointerException e) {}
		
		try {
			gameShip.patrol(2,  null);
			fail("ERROR: Debió lanzar NullPointerException");
		}catch (NullPointerException e) {}
		try {
			gameShip.improveFighter(2,  33,null);
			fail("ERROR: Debió lanzar NullPointerException");
		}catch (NullPointerException e) {}
	}

}
