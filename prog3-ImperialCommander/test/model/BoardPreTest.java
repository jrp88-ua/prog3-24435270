package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class BoardPreTest {

	Board board;
	Ship rebelShip, imperialShip;

	@Before
	public void setUp() throws Exception {
		board = new Board(10);
		rebelShip = new Ship("Alderaan", Side.REBEL);
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
		for (int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++)
				assertNull(board.getFighter(new Coordinate(i, j)));
		}
	}

	/*
	 * Test que añade con launch fighters en un Board inicialmente vacío. Luego
	 * comprueba getFighter en el mismo Board no vacío
	 */
	@Test
	public void testGetFighterNotEmpty() {
		Fighter.resetNextId();
		addFightersOnBoard();
		Fighter.resetNextId();

		Fighter fighter, auxFighter;
		for (int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++)
				if (i == j) {
					fighter = board.getFighter(new Coordinate(i, j));
					rebelShip.addFighters("1/XWing" + i);
					auxFighter = rebelShip.getFleetTest().get(i);
					assertNotNull(fighter);
					assertEquals(fighter, auxFighter);
					assertNotSame("Comprueba que getFighter devuelve una copia defensiva", fighter, auxFighter);
				} else
					assertNull(board.getFighter(new Coordinate(i, j)));
		}
	}

	/*
	 * Test que comprueba que removeFighter devuelve false en una posición donde no
	 * hay Fighter.
	 */
	@Test
	public void testRemoveFighter1() {
		rebelShip.addFighters("1/XWing");
		Fighter fighter = rebelShip.getFleetTest().get(0);
		fighter.setPosition(new Coordinate(2, 4));
		assertFalse(board.removeFighter(fighter));
	}

	/*
	 * Test que comprueba que removeFighter devuelve false en un Fighter no
	 * posicionado.
	 */
	@Test
	public void testRemoveFighter2() {
		assertFalse(new Board(10).removeFighter(new Fighter("A", imperialShip)));
	}

	/*
	 * Test que comprueba que removeFighter elimina los cazas ubicados en un Board y
	 * retorna true.
	 */
	@Test
	public void testRemoveFighter4() {
		addFightersOnBoard();
		Coordinate c;
		for (int i = 0; i < board.getSize(); i++) {
			c = new Coordinate(i, i);
			Fighter fighter = board.getFighter(c);
			assertTrue(board.removeFighter(fighter));
			assertNull(board.getFighter(new Coordinate(i, i)));
		}
	}

	/*
	 * Test que comprueba que para las coordenadas límite dentro del tablero inside
	 * devuelve true;
	 */

	@Test
	public void testInsideTrue() {
		assertTrue(board.inside(new Coordinate(0, 0)));
		assertTrue(board.inside(new Coordinate(9, 0)));
		assertTrue(board.inside(new Coordinate(0, 9)));
		assertTrue(board.inside(new Coordinate(9, 9)));
	}

	/*
	 * Test que comprueba que para las coordenadas límite fuera del tablero inside
	 * devuelve false;
	 */

	@Test
	public void testInsideFalse() {
		// izquierda
		for (int i = -1; i <= board.getSize(); i++) {
			assertFalse(board.inside(new Coordinate(-1, i)));
		}
		// derecha
		for (int i = -1; i <= board.getSize(); i++) {
			assertFalse(board.inside(new Coordinate(board.getSize(), i)));
		}
		// arriba
		for (int i = -1; i <= board.getSize(); i++) {
			assertFalse(board.inside(new Coordinate(i, -1)));
		}
		// abajo
		for (int i = -1; i <= board.getSize(); i++) {
			assertFalse(board.inside(new Coordinate(i, board.getSize())));
		}
	}

	/* Test getNeighborhood para la esquina superior izquierdo de un tablero */
	@Test
	public void testGetNeighborhood1() {

		Set<Coordinate> set = board.getNeighborhood(new Coordinate(0, 0));
		assertEquals(3, set.size());
		assertTrue(set.contains(new Coordinate(0, 1)));
		assertTrue(set.contains(new Coordinate(1, 0)));
		assertTrue(set.contains(new Coordinate(1, 1)));
	}

	/* Test getNeighborhood para la esquina inferior derecho de un tablero 10x10 */
	@Test
	public void testGetNeighborhood2() {
		Board board = new Board(10);
		Set<Coordinate> set = board.getNeighborhood(new Coordinate(9, 9));
		assertEquals("[[8,8], [8,9], [9,8]]", set.toString());
	}

	/* Test getNeighborhood para una coordenada central al tablero */
	@Test
	public void testGetNeighborhood4() {

		Set<Coordinate> set = board.getNeighborhood(new Coordinate(5, 5));

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

	/*
	 * Test launch para una coordenada dentro del tablero. Comprobamos que se ha
	 * puesto y que se ha actualizado la posición del caza
	 */
	@Test
	public void testLaunch2() {
		rebelShip.addFighters("1/XWing");
		Fighter f = rebelShip.getFleetTest().get(0);
		Coordinate c = new Coordinate(9, 9);
		assertEquals(0, board.launch(c, f));
		assertEquals(f, board.getFighter(c));
		assertEquals(c, f.getPosition());
	}

	/*
	 * Test launch para una coordenada dentro del tablero donde ya hay otro caza del
	 * mismo bando (debe devolver 0 y no colocar el caza)
	 */
	@Test
	public void testLaunch3() {
		Board board = new Board(10);
		Ship imperial = new Ship("I", Side.IMPERIAL);
		imperial.addFighters("1/Test1:1/Test2");
		board.launch(new Coordinate(0, 0), imperial.getFleetTest().get(0));
		assertEquals(0, board.launch(new Coordinate(0, 0), imperial.getFleetTest().get(1)));
	}

	/*
	 * Test launch para una coordenada dentro del tablero donde ya hay otro caza del
	 * bando contrario y gana el que quiere acceder a la coordenada.
	 */
	@Test
	public void testLaunch4() {
		Coordinate c = new Coordinate(2, 7);
		rebelShip.addFighters("1/XWing");
		imperialShip.addFighters("1/ZXWing");
		Fighter frebel = rebelShip.getFleetTest().get(0);
		Fighter fimperial = imperialShip.getFleetTest().get(0);

		frebel.addShield(1000);
		assertEquals(0, board.launch(c, fimperial));
		assertEquals(1, board.launch(c, frebel)); // Gana frebel.
		assertEquals(frebel, board.getFighter(c));
		assertFalse(frebel.isDestroyed());
		assertTrue(fimperial.isDestroyed());

		/*
		 * comprueba que se actualizan las estadísticas de victorias/derrotas de las
		 * naves
		 */
		assertEquals(0, imperialShip.getWins());
		assertEquals(1, imperialShip.getLosses());
		assertEquals(1, rebelShip.getWins());
		assertEquals(0, rebelShip.getLosses());
	}

	/*
	 * El caza que patrulla encuentra fighters en todas las casillas a su alrededor,
	 * son del bando contrario. Los vence a todos.
	 */
	@Test
	public void testPatrol3() {
		Coordinate c = new Coordinate(4, 5);
		addFightersNeighborhoodOnBoard(c);
		rebelShip.addFighters("1/ZWing");
		Fighter rebel = rebelShip.getFleetTest().get(0);
		rebel.addAttack(100000);
		rebel.addShield(900000000);
		board.launch(c, rebel);
		board.patrol(rebel);
		assertEquals(rebel, board.getFighter(c));
		for (Coordinate coord : c.getNeighborhood()) {
			assertNull(board.getFighter(coord));
		}
	}

	/*
	 * El caza que patrulla es rebelde y encuentra fighters en todas las casillas a
	 * su alrededor, son del bando contrario. Vence a algunos imperiales
	 * ([3,4][3,5][3,6][4,4][4,6]) Comprueba que para las 5 primeras posiciones sus
	 * casillas están vacías. Que en el resto siguen con cazas y que el caza
	 * patrulla ya no está en su posición.
	 */
	@Test
	public void testPatrol5() {
		Coordinate c = new Coordinate(4, 5);
		addFightersNeighborhoodOnBoard(c);
		rebelShip.addFighters("1/ZWing");
		Fighter rebel = rebelShip.getFleetTest().get(0);
		rebel.addShield(300);
		board.launch(c, rebel);
		board.patrol(rebel);
		int i = 0;
		for (Coordinate coord : c.getNeighborhood()) {
			if (i < 5)
				assertNull(board.getFighter(coord));
			else
				assertNotNull(board.getFighter(coord));
			i++;
		}
		assertNull(board.getFighter(c));
		assertNull(rebel.getPosition());
	}

	/*
	 * El caza que patrulla es rebelde y encuentra fighters en todas las casillas a
	 * su alrededor, son del bando contrario. Vence a algunos imperiales
	 * ([3,4][3,5][3,6][4,4][4,6]) Comprueba los valores wins, y losses de los ships
	 * y el shield de todos los fighters derrotados.
	 */
	@Test
	public void testPatrol6() {
		Coordinate c = new Coordinate(4, 5);
		addFightersNeighborhoodOnBoard(c);
		rebelShip.addFighters("1/ZWing");
		Fighter rebel = rebelShip.getFleetTest().get(0);
		rebel.addShield(300);
		board.launch(c, rebel);
		board.patrol(rebel);
		assertEquals(5, rebelShip.getWins());
		assertEquals(1, rebelShip.getLosses());
		assertEquals(1, imperialShip.getWins());
		assertEquals(5, imperialShip.getLosses());
	}

	// METODOS DE APOYO
	void addFightersOnBoard() {
		for (int i = 0; i < board.getSize(); i++) {
			rebelShip.addFighters("1/XWing" + i);
			board.launch(new Coordinate(i, i), rebelShip.getFleetTest().get(i));
		}
	}

	void addFightersNeighborhoodOnBoard(Coordinate c) {
		Set<Coordinate> list = c.getNeighborhood();
		int i = 0;
		for (Coordinate coord : list) {
			imperialShip.addFighters("1/XWing" + i);
			board.launch(coord, imperialShip.getFleetTest().get(i));
			i++;
		}
	}
}
