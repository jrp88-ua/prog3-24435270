package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import model.exceptions.FighterAlreadyInBoardException;
import model.exceptions.FighterNotInBoardException;
import model.exceptions.InvalidSizeException;
import model.exceptions.OutOfBoundsException;
import model.game.GameBoard;
import model.game.exceptions.WrongFighterIdException;

public class BoardPreTestP2 {

	Board board;
	Ship rebelShip, imperialShip;
	
	@Before
	public void setUp() throws Exception {
		
		board = new Board(10);
		rebelShip = new Ship("Alderaan",Side.REBEL);
		imperialShip = new Ship("Lanzadera T-4a", Side.IMPERIAL);
		RandomNumber.resetRandomCounter();
	}

	@Test
	public void testBoard() {
		assertEquals(10, board.getSize());
	}

	/* Test que comprueba getFighter en un Board vacío */
	@Test
	public void testGetFighterEmpty() {
		for (int i=0; i<board.getSize(); i++) {
			for (int j=0; j<board.getSize(); j++)
				assertNull (board.getFighter(new Coordinate(i,j)));
		}
	}

	/* Test que añade con launch fighters en un Board inicialmente vacío.
	 * Luego comprueba  getFighter en el mismo Board no vacío */
	@Test
	public void testGetFighterNotEmpty() {
		Fighter.resetNextId();
		addFightersOnBoard();
		Fighter.resetNextId();
		
		Fighter fighter, auxFighter;
		for (int i=0; i<board.getSize(); i++) {
			for (int j=0; j<board.getSize(); j++)
				if (i==j) {
					fighter = board.getFighter(new Coordinate(i,j));
					rebelShip.addFighters("1/XWing"+i);
					auxFighter = rebelShip.getFleetTest().get(i);
					//auxFighter.setPosition(new Coordinate(i,j));
					assertNotNull (fighter);
					assertEquals (fighter, auxFighter);
					assertNotSame ("Copia defensiva", fighter, auxFighter);
				}
				else
				 assertNull (board.getFighter(new Coordinate(i,j)));
		}
	}
	
	/* Test que comprueba que removeFighter devuelve false en una posición
	 * donde no hay Fighter.
	 */
	@Test(expected=FighterNotInBoardException.class)
	public void testRemoveFighter1() throws FighterNotInBoardException {
		rebelShip.addFighters("1/XWing");
		Fighter fighter = rebelShip.getFleetTest().get(0);
		fighter.setPosition(new Coordinate(2,4));
		board.removeFighter(fighter);	// debe lanzar la excepción
	}

	/* Test que comprueba que removeFighter devuelve false en un Fighter no
	 * posicionado.
	 */
	@Test(expected=FighterNotInBoardException.class)
	public void testRemoveFighter2() throws FighterNotInBoardException {
		rebelShip.addFighters("1/XWing");
		Fighter fighter = rebelShip.getFleetTest().get(0);
		board.removeFighter(fighter);	// debe lanzar la excepción
	}

	/* Test que comprueba que removeFighter devuelve false en un Fighter 
	 * posicionado en otra coordenada distinta.
	 */
	@Test(expected=FighterNotInBoardException.class)
	public void testRemoveFighter3() throws FighterAlreadyInBoardException, OutOfBoundsException, FighterNotInBoardException {
		rebelShip.addFighters("1/XWing");
		Fighter fighter = rebelShip.getFleetTest().get(0);
		board.launch(new Coordinate(6,7), fighter);
		fighter.setPosition(new Coordinate(6,8));
		board.removeFighter(fighter);	// debe lanzar la excepción
	}
	
	/* Test que comprueba que removeFighter elimina los cazas ubicados en un Board
	 * y retorna true. 
	 */
	@Test
	public void testRemoveFighter4() {
		addFightersOnBoard();
		Coordinate c;
		for (int i=0; i<board.getSize(); i++) {
			c = new Coordinate(i,i);
			Fighter fighter = board.getFighter(c);
			try {
				board.removeFighter(fighter);
			} catch (FighterNotInBoardException e) {
				fail("No debió lanzar la excepción "+e.getClass().getSimpleName()+ "en la coordenada "+c);
			}	
			assertNull (board.getFighter(new Coordinate(i,i)));
		}
	}
	
	/* Test que comprueba que removeFighter no elimina los Fighters que hay en un Board
	 * ya que los Fighters parametrizados no son los mismos que los que hay en esas posiciones
	 * (tienen otra id)
	 */
	@Test
	public void testRemoveFighter5() {
		addFightersOnBoard();
		Ship auxShip = new Ship("Tydirium", Side.REBEL);
		for (int i=0; i<board.getSize(); i++) {
			auxShip.addFighters("1/XWing");
			Fighter fighter = auxShip.getFleetTest().get(i);
			fighter.setPosition(new Coordinate(i,i));
			try {
				board.removeFighter(fighter);
				fail("Error: Debió lanzar la excepción FighterNotInBoardException en la coordenada "+new Coordinate(i,i));
			} catch (FighterNotInBoardException e) { }	
			assertNotNull (board.getFighter(new Coordinate(i,i)));
		}
	}
	
	/* Test que comprueba que para las coordenadas límite dentro del tablero
	 * inside devuelve true;
	 */
	
	@Test
	public void testInsideTrue() {
		assertTrue (board.inside (new Coordinate(0,0)));
		assertTrue (board.inside (new Coordinate(9,0)));
		assertTrue (board.inside (new Coordinate(0,9)));
		assertTrue (board.inside (new Coordinate(9,9)));
	}
	
	/* Test que comprueba que para las coordenadas límite fuera del tablero
	 * inside devuelve false;
	 */
	
	@Test
	public void testInsideFalse() {
		assertFalse (board.inside (new Coordinate(-1,0)));
		assertFalse (board.inside (new Coordinate(0,-1)));
		assertFalse (board.inside (new Coordinate(-1,9)));
		assertFalse (board.inside (new Coordinate(0,10)));
		assertFalse (board.inside (new Coordinate(10,0)));
		assertFalse (board.inside (new Coordinate(0,10)));
		assertFalse (board.inside (new Coordinate(9,-1)));
		assertFalse (board.inside (new Coordinate(9,10)));
		assertFalse (board.inside (new Coordinate(10,9)));
	}

	/* Test getNeighborhood para la esquina superior izquierdo de un tablero */
	@Test
	public void testGetNeighborhood1() throws OutOfBoundsException {
		
		Set<Coordinate> set = board.getNeighborhood(new Coordinate(0,0));
		assertEquals(3, set.size());
		assertTrue(set.contains(new Coordinate(0, 1)));
		assertTrue(set.contains(new Coordinate(1, 0)));
		assertTrue(set.contains(new Coordinate(1, 1)));
	}
	
	/* Test getNeighborhood para la esquina inferior derecho de un tablero 10x10*/
	@Test
	public void testGetNeighborhood2() throws OutOfBoundsException {
		
		Set<Coordinate> set = board.getNeighborhood(new Coordinate(9,9));
		assertEquals(3, set.size());
		assertTrue(set.contains(new Coordinate(9, 8)));
		assertTrue(set.contains(new Coordinate(8, 8)));
		assertTrue(set.contains(new Coordinate(8, 9)));
	}
	
	/* Test getNeighborhood para las esquinas fuera de un tablero */
	@Test
	public void testGetNeighborhood3() {
		//Esquina superior derecha
		Set<Coordinate> set;
		try {
			set = board.getNeighborhood(new Coordinate(-1,-1));
			fail("Error: Debió lanzar la excepción OutOfBoundsException");
		} catch (OutOfBoundsException e) { }
		//assertEquals(1, set.size());
		//assertTrue(set.contains(new Coordinate(0, 0)));
		//Esquina superior izquierda
		
		try {
			set = board.getNeighborhood(new Coordinate(10,-1));
			fail("Error: Debió lanzar la excepción OutOfBoundsException");
		} catch (OutOfBoundsException e) {	}
		/*assertEquals(1, set.size());
		assertTrue(set.contains(new Coordinate(9, 0)));*/
		//Esquina inferior izquierda
		try {
			set = board.getNeighborhood(new Coordinate(-1,10));
			fail("Error: Debió lanzar la excepción OutOfBoundsException");
		} catch (OutOfBoundsException e) {	}
		/*assertEquals(1, set.size());
		assertTrue(set.contains(new Coordinate(0, 9)));*/
		//Esquina inferior derecha
		try {
			set = board.getNeighborhood(new Coordinate(10,10));
			fail("Error: Debió lanzar la excepción OutOfBoundsException");
		} catch (OutOfBoundsException e) {	}
		/*assertEquals(1, set.size());
		assertTrue(set.contains(new Coordinate(9, 9)));*/
	}
	
	/* Test getNeighborhood para una coordenada central al tablero*/
	@Test
	public void testGetNeighborhood4() throws OutOfBoundsException {		
	
		Set<Coordinate> set;
		set = board.getNeighborhood(new Coordinate(5,5));
		
		assertEquals(8, set.size());
		assertTrue(set.contains(new Coordinate(4, 4)));
		assertTrue(set.contains(new Coordinate(5, 4)));
		assertTrue(set.contains(new Coordinate(6, 4)));
		assertTrue(set.contains(new Coordinate(4, 5)));
		assertTrue(set.contains(new Coordinate(6, 5)));
		assertTrue(set.contains(new Coordinate(4, 6)));
		assertTrue(set.contains(new Coordinate(5, 6)));
		assertTrue(set.contains(new Coordinate(6, 6)));
	}
	
	/* Test getNeighborhood para una coordenada fuera del tablero sin vecinos
	 * dentro del tablero
	 */
	@Test(expected=OutOfBoundsException.class)
	public void testGetNeighborhood5() throws OutOfBoundsException {		
	
		Set<Coordinate> set = board.getNeighborhood(new Coordinate(11,11));
		
		assertEquals(0, set.size());
	}
	
	/* Test launch para una coordenada fuera del tablero */
	@Test(expected=OutOfBoundsException.class)
	public void testLaunch1() throws FighterAlreadyInBoardException, OutOfBoundsException {
		rebelShip.addFighters("1/XWing");
		Fighter fighter = rebelShip.getFleetTest().get(0);
		assertEquals(0,board.launch(new Coordinate(-1,0), fighter));
	}

	/* Test launch para una coordenada dentro del tablero. Comprobamos que se ha puesto
	 * y que se ha actualizado la posición del caza */
	@Test
	public void testLaunch2() throws FighterAlreadyInBoardException, OutOfBoundsException {
		rebelShip.addFighters("1/XWing");
		Fighter f = rebelShip.getFleetTest().get(0);
		Coordinate c = new Coordinate(9,9);
		assertEquals(0,board.launch(c,f));
		assertEquals(f, board.getFighter(c));
		assertEquals (c, f.getPosition());
	}
	
	/* Test launch para una coordenada dentro del tablero donde ya hay otro caza del
	 * mismo bando */
	@Test
	public void testLaunch3() throws FighterAlreadyInBoardException, OutOfBoundsException {	
		rebelShip.addFighters("1/XWing");
		Fighter f = rebelShip.getFleetTest().get(0);
		
		Coordinate c = new Coordinate(2,2);
		assertEquals(0,board.launch(c,f));
		rebelShip.addFighters("1/YWing");
		assertEquals(0,board.launch(c, rebelShip.getFleetTest().get(1)));
		assertEquals(f, board.getFighter(c));
	}
	
	
	
	/* Test launch para una coordenada dentro del tablero donde ya hay otro caza del
	 * bando contrario y gana el que quiere acceder a la coordenada.
	 */
	@Test
	public void testLaunch4() throws FighterAlreadyInBoardException, OutOfBoundsException {
		Coordinate c = new Coordinate(2,7);
		rebelShip.addFighters("1/XWing");
		imperialShip.addFighters("1/TIEFighter");
		Fighter frebel = rebelShip.getFleetTest().get(0);
		Fighter fimperial = imperialShip.getFleetTest().get(0);
        
		frebel.addShield(1000);
		assertEquals(0,board.launch(c,fimperial));
		assertEquals(1,board.launch(c,frebel)); //Gana frebel.
		assertEquals(frebel, board.getFighter(c));
		assertTrue(fimperial.isDestroyed());
	}
	
	/* Test launch para una coordenada dentro del tablero donde ya hay otro caza del
	 * bando contrario que pertenece a una flota IMPERIAL y gana el que ya está en la 
	 * coordenada. Se comprueba además que se actualizan las estadísticas de cada una de
	 * las flotas.
	 */
	@Test
	public void testLaunch5() throws FighterAlreadyInBoardException, OutOfBoundsException {
		Coordinate c = new Coordinate(2,7);
		
		rebelShip.addFighters("1/XWing");
		imperialShip.addFighters("1/TIEFighter");	
		Fighter rebel = rebelShip.getFleetTest().get(0);
		Fighter imperial = imperialShip.getFleetTest().get(0);
		imperial.addShield(1000);
		board.launch(c,imperial);
		assertEquals(-1,board.launch(c,rebel)); //Gana fimperial (que ya estaba en la coordenada)
		
		assertEquals(imperial, board.getFighter(c));
		assertTrue(rebel.isDestroyed());
		
		assertEquals(1,imperialShip.getWins());
		assertEquals(0,imperialShip.getLosses());
		assertEquals(0,rebelShip.getWins());
		assertEquals(1,rebelShip.getLosses());
	}
	
	/* El caza que patrulla no encuentra ningun fighter a su alrededor.
	 * No ocurre nada.
	 */
	@Test
	public void testPatrol1() throws FighterAlreadyInBoardException, OutOfBoundsException, FighterNotInBoardException {
		rebelShip.addFighters("1/XWing");
		Fighter rebel = rebelShip.getFleetTest().get(0);
		Coordinate c = new Coordinate(4,5);
		board.launch(c,rebel);
		board.patrol(rebel);
		assertEquals(rebel, board.getFighter(c));
	}

	/* El caza que patrulla encuentra dos fighters a su alrededor, pero son
	 * de su bando. No ocurre nada.
	 */
	@Test
	public void testPatrol2() throws FighterAlreadyInBoardException, OutOfBoundsException, FighterNotInBoardException {
		addFightersOnBoard();
		rebelShip.addFighters("1/YWing");
		Fighter rebel = rebelShip.getFleetTest().get(10);
		
		Coordinate c = new Coordinate(4,5);
		board.launch(c,rebel);
		board.patrol(rebel);
		assertEquals(rebel, board.getFighter(c));
		assertNotNull(board.getFighter(new Coordinate(4,4)));
		assertNotNull(board.getFighter(new Coordinate(5,5)));
		assertNull(board.getFighter(new Coordinate (3,4))); //No ha añadido nada en las vacías
	}
	
	/* El caza que patrulla encuentra fighters en todas las casillas a su alrededor, son
	 * del bando contrario. Los vence a todos.
	 */
	@Test
	public void testPatrol3() throws FighterAlreadyInBoardException, OutOfBoundsException, FighterNotInBoardException {
		Coordinate c = new Coordinate(4,5);
		addFightersNeighborhoodOnBoard(c);
		rebelShip.addFighters("1/YWing");
		Fighter rebel = rebelShip.getFleetTest().get(0);
		rebel.addAttack(100000);
		rebel.addShield(900000000);
		board.launch(c,rebel);
		board.patrol(rebel);
		assertEquals(rebel, board.getFighter(c));
		for (Coordinate coord : c.getNeighborhood()) {
		   assertNull(board.getFighter(coord));
		}
	}
	
	/* El caza que patrulla encuentra fighters en todas las casillas a su alrededor, son
	 * del bando contrario. Los vence a todos. Comprueba los valores wins, y losses de
	 * los ships y el shield del fighter vencedor y de los derrotados.
	 */
	@Test
	public void testPatrol4() throws FighterAlreadyInBoardException, OutOfBoundsException, FighterNotInBoardException {
		Coordinate c = new Coordinate(4,5);
		addFightersNeighborhoodOnBoard(c);
		rebelShip.addFighters("1/XWing");
		Fighter rebel = rebelShip.getFleetTest().get(0);
		rebel.addAttack(100000);
		rebel.addShield(900);
		board.launch(c,rebel); 
		board.patrol(rebel);
		assertEquals(0, rebelShip.getLosses());
		assertEquals(8, rebelShip.getWins());
		assertEquals(8, imperialShip.getLosses());
		assertEquals(0, imperialShip.getWins());
		assertEquals (833, rebel.getShield());
		
		int shields[]= {-28291,-29292,-17948,-25956,-22953,-24287,-20951,-20617};
		Fighter f;
		for (int i=0; i<imperialShip.getFleetTest().size(); i++) {
			 f = imperialShip.getFleetTest().get(i); 
			 assertEquals (shields[i], f.getShield());
		}
	}
	
	/* El caza que patrulla es rebelde y encuentra fighters en todas las casillas a su alrededor, son
	 * del bando contrario. Vence a algunos imperiales ([3,4][3,5][3,6][4,4][4,6])
	 * Comprueba que para las 5 primeras posiciones sus casillas están vacías. Que en el resto siguen
	 * con cazas y que el caza patrulla ya no está en su posición.
	 */
	@Test
	public void testPatrol5() throws FighterAlreadyInBoardException, OutOfBoundsException, FighterNotInBoardException {
		Coordinate c = new Coordinate(4,5);
		addFightersNeighborhoodOnBoard(c);
		rebelShip.addFighters("1/XWing");
		Fighter rebel = rebelShip.getFleetTest().get(0);
		rebel.addShield(180);
		board.launch(c,rebel); 
		board.patrol(rebel); 
		int i=0;
		for (Coordinate coord : c.getNeighborhood()) {
			if (i<5)
			   assertNull(board.getFighter(coord));
			else
			   assertNotNull("i="+i,board.getFighter(coord));
			i++;
		}
		assertNull(board.getFighter(c));
		assertNull(rebel.getPosition());
	}
	
	/* El caza que patrulla es rebelde y encuentra fighters en todas las casillas a su alrededor, son
	 * del bando contrario. Vence a algunos imperiales ([3,4][3,5][3,6][4,4][4,6])
	 * Comprueba los valores wins, y losses de los ships y el shield de todos los fighters
	 * derrotados.
	 */
	@Test
	public void testPatrol6() throws FighterAlreadyInBoardException, OutOfBoundsException, FighterNotInBoardException {
		Coordinate c = new Coordinate(4,5);
		addFightersNeighborhoodOnBoard(c);
		rebelShip.addFighters("1/XWing");
		Fighter rebel = rebelShip.getFleetTest().get(0);
		rebel.addShield(300);
		board.launch(c,rebel); 
		board.patrol(rebel); 
		
		assertEquals(1, rebelShip.getLosses());
		assertEquals(7, rebelShip.getWins());
		assertEquals(7, imperialShip.getLosses());
		assertEquals(1, imperialShip.getWins());
		assertEquals (-17, rebel.getShield());
		
		int shields[]= {-5,-3,-1,-11,-12};
		Fighter imperial;
		for (int i=0; i<shields.length; i++) {
			 imperial = imperialShip.getFleetTest().get(i); 
			 assertEquals (shields[i], imperial.getShield());
		}	
	}
	
	/* Realiza los test de comprobación de los parámetros null en Board para launch,
	 * patrol, removeFighter, getFighter y getNeigborhood 
	 */
	@Test
	public void testRequireNonNull() throws FighterAlreadyInBoardException, OutOfBoundsException, FighterNotInBoardException  {
		
		fail("Realiza las comprobaciones de los métodos");
	}
	
	
	//METODOS DE APOYO
	void addFightersOnBoard()  {
		for (int i=0; i<board.getSize(); i++) {
			rebelShip.addFighters("1/XWing");
			try {
				board.launch (new Coordinate(i,i), rebelShip.getFleetTest().get(i));
			} catch (FighterAlreadyInBoardException | OutOfBoundsException e) {
				fail("No debió lanzar la excepción "+e.getClass().getSimpleName()+ "en la coordenada "+new Coordinate(i,i));
			}
		}
	}
	
	void addFightersNeighborhoodOnBoard(Coordinate c)  {
		Set<Coordinate> list = c.getNeighborhood();
		int i=0;
		for (Coordinate coord: list) {
			imperialShip.addFighters("1/TIEFighter");
			try {
				board.launch (coord, imperialShip.getFleetTest().get(i));
			} catch (FighterAlreadyInBoardException | OutOfBoundsException e) {
				fail("No debió lanzar la excepción "+e.getClass().getSimpleName()+ "en la coordenada "+coord);
			}
			i++;
		}
	}
	
	
}
