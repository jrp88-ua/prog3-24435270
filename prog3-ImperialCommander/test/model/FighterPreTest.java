package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FighterPreTest {

	private final int kInitVelocity = 100;
	private final int kInitAttack = 80;
	private final int kInitShield = 80;
	private Fighter fighter;
	private Ship rebelShip, imperialShip;

	@Before
	public void setUp() throws Exception {
		rebelShip = new Ship("Tydirium", Side.REBEL);
		imperialShip = new Ship("Lanzadera T-4a", Side.IMPERIAL);
		Fighter.resetNextId();
		fighter = new Fighter("XWing", rebelShip);
	}

	/* Test que comprueba los valores del constructor */
	@Test
	public final void testFighterAndGetters() {
		assertEquals("getType", "XWing", fighter.getType());
		assertEquals("Initial velocity", kInitVelocity, fighter.getVelocity());
		assertEquals("Initial attack", kInitAttack, fighter.getAttack());
		assertEquals("Initial shield", kInitShield, fighter.getShield());
		assertSame(rebelShip, fighter.getMotherShip());
		assertNull(fighter.getPosition());
		assertEquals("Initial id = 1", 1, fighter.getId());
	}

	/*
	 * Test que comprueba el constructor copia. Se cambia valores iniciales de un
	 * fighter Se crea una copia y se comprueba que los valores de ambos fighters
	 * son iguales
	 */
	@Test
	public final void testFighterCopyConstructor() {
		fighter = new Fighter("QWing20", rebelShip);
		fighter.setPosition(new Coordinate(4, 6));
		fighter.addShield(30);
		fighter.addVelocity(40);
		Fighter enemy = new Fighter(fighter);
		assertEquals("getType", fighter.getType(), enemy.getType());
		assertEquals("Initial velocity", fighter.getVelocity(), enemy.getVelocity());
		assertEquals("Initial attack", fighter.getAttack(), enemy.getAttack());
		assertEquals("Initial shield", fighter.getShield(), enemy.getShield());
		assertEquals(fighter.getMotherShip(), enemy.getMotherShip());
		assertEquals(fighter.getPosition(), enemy.getPosition());
		assertEquals("Initial id = 1", fighter.getId(), enemy.getId());
	}

	/*
	 * Test que comprueba que nextId vuelve a 1
	 */
	@Test
	public final void testResetNextId() {
		assertEquals("Initial id = 1", 1, fighter.getId());
		Fighter other = new Fighter("YWing", rebelShip);
		assertEquals("Next id = 2", 2, other.getId());
		Fighter.resetNextId();
		Fighter another = new Fighter("AWing", rebelShip);
		assertEquals("(after reset) id = 1", 1, another.getId());
	}

	/*
	 * Test que comprueba el incremento del identificador del Figher cada vez que
	 * creamos uno.
	 */
	@Test
	public final void testGetId() {
		Fighter.resetNextId();
		for (int i = 1; i < 10; i++) {
			fighter = new Fighter("XWing", rebelShip);
			assertEquals(i, fighter.getId());
		}
	}

	/* Test que comprueba addVelocity */

	@Test
	public final void testAddVelocity() {
		fighter.addVelocity(150);
		assertEquals(250, fighter.getVelocity());
		fighter.addVelocity(-1000);
		assertEquals(0, fighter.getVelocity());
	}

	/* Test que comprueba addAttack */
	@Test
	public final void testAddAttack() {
		fighter.addAttack(150);
		assertEquals(230, fighter.getAttack());
		fighter.addAttack(-1000);
		assertEquals(0, fighter.getAttack());
	}

	/* Test que comprueba addShield */
	@Test
	public final void testAddShield() {
		fighter.addShield(150);
		assertEquals(230, fighter.getShield());
		fighter.addShield(-1000);
		assertEquals(-770, fighter.getShield());
	}

	/*
	 * Test que coprueba el metodo setPosition y la relación de asociación con
	 * Coordinate
	 */
	@Test
	public final void testSetPosition() {
		Coordinate coordinate = new Coordinate(3, 8);
		fighter.setPosition(coordinate);
		assertEquals(coordinate, fighter.getPosition());
		assertSame("Asociación", coordinate, fighter.getPosition());
	}

	/*
	 * Test que comprueba el metodo setPosition con null como coordenada
	 */
	@Test
	public final void testSetPositionNull() {
		/*
		 * comprueba que se le asigna bien la posición a un caza, y que después se le
		 * puede asignar null como posición
		 */
		Fighter f = new Fighter(fighter);
		Coordinate c = new Coordinate(1, 1);
		f.setPosition(c);
		assertEquals(c, f.getPosition());
		f.setPosition(null);
		assertEquals(null, f.getPosition());
	}

	/*
	 * Test que comprueba que isDestroyed es false aunque cambiemos los valores
	 * attack y velocity a 0 y shield a 1
	 * 
	 */
	@Test
	public final void testIsDestroyedFalse() {
		assertFalse(fighter.isDestroyed());
		fighter.addAttack(-200);
		fighter.addVelocity(-200);
		assertFalse(fighter.isDestroyed());
		fighter.addShield(-79);
		assertFalse(fighter.isDestroyed());
	}

	/*
	 * Test que prueba que cuando ponemos shield a 0 o valores negativos devuelve
	 * true.
	 * 
	 */
	@Test
	public final void testIsDestroyedTrue() {
		fighter.addShield(-80);
		assertTrue(fighter.isDestroyed());
		// prueba con valores negativos
		fighter.addShield(-1000);
		assertTrue(fighter.isDestroyed());
	}

	/*
	 * Test getDamage attack=80
	 * 
	 */
	@Test
	public final void testGetDamageInitialAttack() {
		Fighter enemy = new Fighter("TIEFighter", imperialShip);
		assertEquals(13, fighter.getDamage(50, enemy));
		assertEquals(6, enemy.getDamage(25, fighter));

	}

	/*
	 * Test 1 que comprueba getDamage tras varios ataques
	 * 
	 */
	@Test
	public final void testGetDamage1() {
		Fighter enemy = new Fighter("TIEFighter", imperialShip);
		enemy.addAttack(-40);
		fighter.addAttack(75);
		assertEquals(5, fighter.getDamage(10, enemy));
		assertEquals(266, enemy.getDamage(2000, fighter));
	}

	/*
	 * Test toString para un fighter recien creado
	 * 
	 */
	@Test
	public final void testToString1() {
		final String sout = "(XWing 1 REBEL null {100,80,80})";
		assertEquals(sout, fighter.toString());
	}

	/*
	 * Test toString un fighter con los valores de sus atributos cambiados
	 * 
	 */
	@Test
	public final void testToString2() {
		/*
		 * cambia los valores de los atributos del fighter y comprueba que en toString
		 * se reflejan los cambios
		 */
		Fighter.resetNextId();
		Fighter f = new Fighter("TypeA", imperialShip);
		f.addVelocity(-99);
		f.addAttack(-78);
		f.addShield(-77);
		assertEquals("(TypeA 1 IMPERIAL null {1,2,3})", f.toString());
	}

	/*
	 * Test para Fight en que lucha un caza contra un enemigo ya destruído")
	 */
	@Test
	public final void testFight1() {
		Fighter enemy = new Fighter("TIEFighter", imperialShip);
		enemy.addShield(-150);
		assertEquals(0, fighter.fight(enemy));
		assertEquals(0, RandomNumber.getRandomNumberList().size());
	}

	/*
	 * Test que comprueba Fight en la lucha de un caza destruído contra un enemigo.
	 * 
	 */
	@Test
	public final void testFight2() {
		/*
		 * similar al anterior, pero al revés
		 */
		Fighter enemy = new Fighter("TIEFighter", imperialShip);
		fighter.addShield(-150);
		assertEquals(0, fighter.fight(enemy));
		assertEquals(0, RandomNumber.getRandomNumberList().size());
	}

	/*
	 * Test que combrueba Fight en la lucha entre un caza y un enemigo que tienen
	 * los mismos valores.
	 * 
	 */
	@Test
	public final void testFight3() {
		Fighter enemy = new Fighter("TIEFighter", imperialShip);
		assertEquals(-1, fighter.fight(enemy));
		assertEquals("[85, 88, 47, 13, 54, 4, 34, 6]", RandomNumber.getRandomNumberList().toString());
	}

	/*
	 * Test que comprueba Fight en la lucha entre un caza y un enemigo que tienen
	 * distintos valores.
	 */
	@Test
	public final void testFight4() {
		/*
		 * cambia algunos valores del caza (velocidad, ataque, escudo) y comprueba que
		 * afectan a la lucha
		 */
		Fighter enemy = new Fighter("Type1", rebelShip);
		Fighter fighter = new Fighter("Type2", imperialShip);
		
		fighter.addShield(1000000);
		assertEquals(1, fighter.fight(enemy));
	}

	/*
	 * Test equals for Fighter
	 * 
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public final void testEqualsObject() {
		Fighter enemy = new Fighter("XWing", imperialShip);
		String s = "XWing";
		assertTrue(fighter.equals(fighter));
		assertFalse(enemy.equals(fighter));
		assertFalse(fighter.equals(null));
		assertFalse(fighter.equals(s));
		enemy = new Fighter(fighter);
		assertTrue(fighter.equals(enemy));
	}

	/*
	 * Test que comprueba hashCode for Fighter
	 * 
	 */
	@Test
	public final void testHashCode() {
		Fighter enemy = new Fighter("XWing", imperialShip);
		assertNotEquals(enemy.hashCode(), fighter.hashCode());
	}

}
