package model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ShipPreTest {

	Ship ship;
	final String kFleet1 = "5/XWing:12/AWing:3/YWing:2/XWing";
	final String kFleet2 = "40/XWing:10/AWing:30/YWing:25/XWing:35/TIEFighter:55/TIEBomber:45/TIEShuttle:100/ZWing";

	final String kFleet21 = "45/XWing:3/AWing:30/YWing:1/TIEBomber:10/TIEShuttle";
	final String kFleet22 = "65/XWing:10/AWing:30/YWing:35/TIEFighter:55/TIEBomber:45/TIEShuttle:100/ZWing";
	final String kToString1 = "Ship [Tydirium 0/0] 7/XWing:12/AWing:3/YWing";
	final String kToString2 = "Ship [Tydirium 30/45] 65/XWing:10/AWing:30/YWing:35/TIEFighter:55/TIEBomber:45/TIEShuttle:100/ZWing";
	final String kShow = "(XWing 1 REBEL null {100,80,-120}) (X)\n" + 
			"(XWing 2 REBEL null {100,80,80})\n" + 
			"(XWing 3 REBEL null {100,80,80})\n" + 
			"(XWing 4 REBEL null {100,80,80})\n" + 
			"(XWing 5 REBEL null {100,80,-120}) (X)\n" + 
			"(AWing 6 REBEL null {100,80,80})\n" + 
			"(AWing 7 REBEL null {100,80,80})\n" + 
			"(AWing 8 REBEL null {100,80,80})\n" + 
			"(AWing 9 REBEL null {100,80,-120}) (X)\n" + 
			"(AWing 10 REBEL null {100,80,80})\n" + 
			"(AWing 11 REBEL null {100,80,-120}) (X)\n" + 
			"(AWing 12 REBEL null {100,80,80})\n" + 
			"(AWing 13 REBEL null {100,80,80})\n" + 
			"(AWing 14 REBEL null {100,80,80})\n" + 
			"(AWing 15 REBEL null {100,80,80})\n" + 
			"(AWing 16 REBEL null {100,80,80})\n" + 
			"(AWing 17 REBEL null {100,80,80})\n" + 
			"(YWing 18 REBEL null {100,80,80})\n" + 
			"(YWing 19 REBEL null {100,80,-120}) (X)\n" + 
			"(YWing 20 REBEL null {100,80,80})\n" + 
			"(XWing 21 REBEL null {100,80,-120}) (X)\n" + 
			"(XWing 22 REBEL null {100,80,-120}) (X)\n";
	
	List<Fighter>fleet ;
	
	@Before
	public void setUp() throws Exception {
		ship = new Ship("Tydirium", Side.REBEL);
	}

	
	/* Comprueba los atributos de un Ship creado */
	@Test
	public void testShip() {
		assertEquals ("Tydirium", ship.getName());
		assertEquals (Side.REBEL, ship.getSide());
		assertEquals (0,  ship.getWins());
		assertEquals (0,  ship.getLosses());
		fleet = (List<Fighter>) ship.getFleetTest();
		assertNotNull (fleet);
	}

	/* Comprueba que getFleetTest devuelve 'fleet' y no hace copia (no debe hacerla) 
	 */
	@Test
	public void testGetFleetTest() {
		List<Fighter> l1 = ship.getFleetTest();
		assertEquals(0, l1.size());
		Fighter f = new Fighter("AWing",ship);
		l1.add(f);
		
		List<Fighter> l2 = ship.getFleetTest();
		assertEquals(1, l2.size());
		assertEquals(1, l1.size());
		assertEquals(f,l2.get(0));
		assertEquals(f,l1.get(0));
	}
	
	/* Comprueba getSide */
	@Test
	public void testGetSide() {
		ship = new Ship("Lanzadera T-4a", Side.IMPERIAL);
		assertEquals(Side.IMPERIAL, ship.getSide());
	}
	
	/* Comprueba addFighter con un único fighter */
	@Test
	public void testAddFighters1() {
		ship.addFighters("1/XWing");
		 
		List<Fighter> lfleet = (List<Fighter>)ship.getFleetTest();
		assertNotNull(lfleet);
		assertEquals(1,lfleet.size());
		assertEquals("XWing", lfleet.get(0).getType());
		
	}
	
	/* Comprueba addFighter en un Ship con varios fighters */
	@Test
	public void testAddFighters2() {
		ship.addFighters(kFleet1);
		 
		List<Fighter> lfleet = (List<Fighter>) ship.getFleetTest();
		String type="";
		assertEquals (22, lfleet.size());
		for (int i=0; i<lfleet.size(); i++) {	
			if (i<5) type = "XWing"; //
			else if (i<17) type = "AWing"; 
			else if (i<20) type = "YWing";
			else if (i<22) type = "XWing";
			assertEquals (type,lfleet.get(i).getType());
			if (i>0)
				assertNotSame (lfleet.get(i-1), lfleet.get(i));
		}
	}	

	
	
	/* Se comprueba que UpdateResults(1) incrementa wins en 1 */
	@Test
	public void testUpdateResults1() {
		ship.updateResults(1);
		assertEquals(1,ship.getWins());
		assertEquals(0, ship.getLosses());
	}
	
	
	/* Se comprueba que UpdateResults(-1) incrementa wins en 1 */
	@Test
	public void testUpdateResults2() {
		fail("completa el test");
	}
	
	
	/* Comprueba que getFirstAvailableFighter devuelve null en una nave vacía */
	@Test
	public void testGetFirstAvailableFighter1() {
		assertNull(ship.getFirstAvailableFighter("XWing"));
	}

	
	/* Comprueba que getFirstAvailableFighter devuelve null en una nave cuyos cazas
	 * están todos destruídos.
	 */
	@Test
	public void testGetFirstAvailableFighter3() {
		ship.addFighters(kFleet1);
		destroy("YWing",3);
		assertNull(ship.getFirstAvailableFighter("YWing"));
	}
	
	
	/* Se crean los cazas de la constante kFleet1 en una nave. Se obtiene uno de 
	 * ellos, YWing, con getFirstAvailableFighter y se comprueba que:
	 *  - No devuelve null
	 *  - Que el fighter es el mismo que el que está en la posición 17 del vector 
	 *  de la nave
	 */
	@Test
	public void testGetFirstAvailableFighter4() {
		ship.addFighters(kFleet1);
		 
		List<Fighter> lfleet = (List<Fighter>) ship.getFleetTest();
		Fighter fighter = ship.getFirstAvailableFighter("YWing");
		assertNotNull(fighter);
		assertEquals("YWing",fighter.getType());
		assertEquals(lfleet.get(17), fighter);
		assertSame(lfleet.get(17), fighter);
	}
	//"5/XWing:12/AWing:3/YWing:2/XWing"
	/* Se crean los cazas de la constante kFleet1 en una nave. Se destruyen todos (se usa
	 * el método auxiliar destroy(String, int) para ello) y se intenta obtener el primer
	 * caza no destruído de la nave. Se comprueba que devuelve null. 
	 */
	@Test
	public void testGetFirstAvailableFighter5() {
		ship.addFighters(kFleet1);
		destroy("",0);
		assertNull(ship.getFirstAvailableFighter(""));
	}
	
	
	/* Se crean los cazas de la constante kFleet1 en una nave. Se destruyen todos 
	 * del tipo 'XWing' excepto el último (se usa el método auxiliar destroy(String, int) 
	 * para ello) y se intenta obtener el primer caza no destruído de la nave. 
	 * Se comprueba que devuelve exactamente el último de los 'XWing'. 
	 */
	@Test
	public void testGetFirstAvailableFighter6() {
		ship.addFighters(kFleet1);
		destroy("XWing",6);
		Fighter fighter = ship.getFirstAvailableFighter("XWing");
		assertNotNull(fighter);
		/*
		 * comprueba que fighter es un XWing y es el que ocupa la posición 21 de la flota
		 */
		
		fail("completa el test como se indica en el comentario");
	}
	
	
	
	//@DisplayName ("PurgeFleet whose fighters are all ok")
	/* Crea cazas en un Ship y comprueba que PurgeFleet no elimina los cazas 
	 * cuando están todos ok.
	 */
	@Test
	public void testPurgeFleet1() {
		ship.addFighters(kFleet2);
		ship.purgeFleet();
		 
		List<Fighter> lfleet = (List<Fighter>) ship.getFleetTest();
		assertEquals(340, lfleet.size());
	}
	
	/* Crea cazas en un Ship,  destruye muchos cazas y comprueba que PurgeFleet ha eliminado
	 * solo los destruídos.
	 */
	@Test
	public void testPurgeFleet2() {
		ship.addFighters(kFleet2);
		
		destroy("XWing", 20);
		destroy("AWing",7);
		// completa el test con algunos destroy(...) más
		// destroy(...)
		
		ship.purgeFleet();
		//Comprobamos total Fighters en ship
		 
		List<Fighter> auxFleet = ship.getFleetTest();
		// assertEquals( ?? , auxFleet.size());
		//Comprobamos que coinciden las cantidades con cada tipo
		assertEquals(45, numberOfFightersOk("XWing"));
		assertEquals (3, numberOfFightersOk("AWing"));
		// haz las mismas comprobaciones para otros cazas que hayas destruido 
		//assertEquals (??, numberOfFightersOk("?????"));
		
		fail("completa el test");
	}
	
	 
	/* Comprueba showFleet en una nave vacía 
	 */
	@Test
	public void testShowFleet1() {
		
		assertEquals("",ship.showFleet());
	}
	
	
	/* Crea cazas en una nave y destruye algunos de ellos y comprueba que showFleet 
	 * los muestra todos correctamente.
	 */
	@Test
	public void testShowFleet2() {
		Fighter.resetNextId();
		ship.addFighters(kFleet1);
	
		List<Fighter> list = (List<Fighter>) ship.getFleetTest();
		(list.get(0)).addShield(-200);
		(list.get(4)).addShield(-200);
		(list.get(8)).addShield(-200);
		(list.get(10)).addShield(-200);
		(list.get(18)).addShield(-200);
		(list.get(20)).addShield(-200);
		(list.get(21)).addShield(-200);
		assertEquals(kShow,ship.showFleet());
	}

	/* Comprueba que en una nave sin cazas, myFleet devuelve una cadena vacía
	 */
	@Test
	public void testMyFleet1() {
		assertTrue(ship.myFleet().isEmpty());
	}
	
	/* Crea cazas en una nave y comprueba que showFleet los muestra todos correctamente.
	 */
	@Test
	public void testMyFleet2() {
		
		ship.addFighters(kFleet2);
		assertEquals(kFleet22, ship.myFleet());	
	}

	/* Crea cazas en una nave. Destrúyelos todos y comprueba que showFleet devuelve
	 * la cadena vacía.
	 */
	@Test
	public void testMyFleet3() {
		fail("completa el test");
	}
	
	/* Crea cazas en una nave. Destruye muchos y comprueba que showFleet solo devuelve
	 * una cadena con los no destruídos.
	 */
	@Test
	public void testMyFleet4() {
		fail("completa el test");
	}

	/* Comprueba toString para una nave sin cazas */
	@Test
	public void testToString1() {
		assertEquals ("Ship [Tydirium 0/0] ", ship.toString());
	}
	
	/* En una nave crea cazas. Comprueba que la salida con toString es correcta.
	 */
	@Test
	public void testToString2() {
		
		ship.addFighters(kFleet1);
		assertEquals (kToString1, ship.toString());
	}
	
	/* En una nave crea cazas y  modifica el número de luchas ganadas y perdidas de
	 * sus cazas. Comprueba que la salida con toString es correcta.
	 */
	@Test
	public void testToString3() {
		for (int i=0; i<30; i++) ship.updateResults(1);
		for (int i=0; i<45; i++) ship.updateResults(-1);
		ship.addFighters(kFleet2);
		assertEquals (kToString2, ship.toString());
	}
	
	/* En una nave crea cazas con muchas repeticiones de cada tipo de caza.  
	 * Comprueba que la salida con toString es correcta.
	 */
	@Test
	public void testToString4() {
		fail("completa el test");
	}

	/*************************************/
	//METODOS AUXILIARES PARA LOS TESTS
	/*************************************/
	/*Destruye 'max' número de cazas del tipo 'type'. Si type="" destruye
	 * los 'max' primeros de cualquier tipo.
	 */
	
	void destroy(String type, int max) {
		int i=0;
		int shield;
		List<Fighter> list =  ship.getFleetTest();
		for (Fighter fighter : list) {
			
	      if (!fighter.isDestroyed()) {
		     if (!type.isEmpty()) {
		    	 if ( (fighter.getType().equals(type))) 
		    		 if (i<max) {
		    			 shield = fighter.getShield();
		    			 fighter.addShield(-(shield+1)); //Destruímos el fighter
		    			 i++;
		    		 }
		     }
		     else {
			 	shield = fighter.getShield();
				fighter.addShield(-(shield+1)); //Destruímos el fighter
				i++;
		     }
	      }
		}
	}
	
	//Nos devuelve el número de cazas del tipo 'type' que no están destruídos
	int numberOfFightersOk(String type) {
		int count = 0;
		 
		List<Fighter> list = (List<Fighter>) ship.getFleetTest();
		for (Fighter fighter : list) 
			if ( !(fighter.isDestroyed()) && type.equals(fighter.getType()) ) 
						count++;				
		return count;
	}

}
