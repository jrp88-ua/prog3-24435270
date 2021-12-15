package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.exceptions.NoFighterAvailableException;

public class ShipTestP2 {

	Ship ship;
	final String kFleet1 = "5/XWing:12/AWing:3/YWing:2/XWing";
	final String kFleet2 = "40/XWing:10/AWing:30/YWing:25/XWing:35/TIEFighter:55/TIEBomber:45/TIEInterceptor:100/AWing";
	final String kFleet3 = "40/XWing:10/AWing:30/YWing:25/AWing:35/XWing:55/TIEBomber:45/TIEBomber:100/AWing"+
			               ":23/XWing:10/YWing:30/TIEBomber:25/AWing:15/XWing:27/TIEBomber:45/AWing:10/AWing";

	final String kFleet21 = "45/XWing:103/AWing:30/YWing:1/TIEBomber:45/TIEInterceptor";
	final String kFleet22 = "65/XWing:110/AWing:30/YWing:35/TIEFighter:55/TIEBomber:45/TIEInterceptor";
	final String kToString1 = "Ship [Tydirium 0/0] 7/XWing:12/AWing:3/YWing";
	final String kToString2 = "Ship [Tydirium 30/45] 65/XWing:110/AWing:30/YWing:35/TIEFighter:55/TIEBomber:45/TIEInterceptor";
	final String kToString3 = "Ship [Tydirium 0/0] 113/XWing:215/AWing:40/YWing:157/TIEBomber";
	final String kShow = "(XWing 1 REBEL null {110,100,-120}) (X)\n" + 
			"(XWing 2 REBEL null {110,100,80})\n" + 
			"(XWing 3 REBEL null {110,100,80})\n" + 
			"(XWing 4 REBEL null {110,100,80})\n" + 
			"(XWing 5 REBEL null {110,100,-120}) (X)\n" + 
			"(AWing 6 REBEL null {140,85,30})\n" + 
			"(AWing 7 REBEL null {140,85,30})\n" + 
			"(AWing 8 REBEL null {140,85,30})\n" + 
			"(AWing 9 REBEL null {140,85,-170}) (X)\n" + 
			"(AWing 10 REBEL null {140,85,30})\n" + 
			"(AWing 11 REBEL null {140,85,-170}) (X)\n" + 
			"(AWing 12 REBEL null {140,85,30})\n" + 
			"(AWing 13 REBEL null {140,85,30})\n" + 
			"(AWing 14 REBEL null {140,85,30})\n" + 
			"(AWing 15 REBEL null {140,85,30})\n" + 
			"(AWing 16 REBEL null {140,85,30})\n" + 
			"(AWing 17 REBEL null {140,85,30})\n" + 
			"(YWing 18 REBEL null {80,70,110})\n" + 
			"(YWing 19 REBEL null {80,70,-90}) (X)\n" + 
			"(YWing 20 REBEL null {80,70,110})\n" + 
			"(XWing 21 REBEL null {110,100,-120}) (X)\n" + 
			"(XWing 22 REBEL null {110,100,-120}) (X)\n";
	
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
		Fighter f = FighterFactory.createFighter("AWing",ship);
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

	
	/* Comprueba addFighter con una gran cantidad de fighters" 
	 * "40/XWing:10/AWing:30/YWing:25/XWing:35/TIEFighter:55/TIEBomber:45/TIEInterceptor:100/AWing"*/
	@Test
	public void testAddFighters3() {
		
		ship.addFighters(kFleet2);
		 
		List<Fighter> lfleet = (List<Fighter>) ship.getFleetTest();
		String type="";
		assertEquals (340, lfleet.size());
		for (int i=0; i<lfleet.size(); i++) {	
			if (i<40) type = "XWing"; //
			else if (i<50) type = "AWing"; 
			else if (i<80) type = "YWing";
			else if (i<105) type = "XWing";
			else if (i<140) type = "TIEFighter"; 
			else if (i<195) type = "TIEBomber";
			else if (i<240) type = "TIEInterceptor";
			else if (i<340) type = "AWing";
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
		ship.updateResults(-1);
		assertEquals(1, ship.getLosses());
		assertEquals(0, ship.getWins());
	}
	
	
	/* Se comprueba que UpdateResults(2) no modifica ni wins ni losses*/
	@Test
	public void testUpdateResults() {
		ship.updateResults(2);
		assertEquals(0, ship.getLosses());
		assertEquals(0, ship.getWins());
	}

	
	/* Comprueba que getFirstAvailableFighter devuelve null en una nave vacía */
	@Test(expected=NoFighterAvailableException.class)
	public void testGetFirstAvailableFighter1() throws NoFighterAvailableException {
		assertNull(ship.getFirstAvailableFighter("XWing"));
	}

	
	/* Comprueba que getFirstAvailableFighter con un tipo que no existe devuelve null */
	@Test(expected=NoFighterAvailableException.class)
	public void testGetFirstAvailableFighter2() throws NoFighterAvailableException {
		ship.addFighters(kFleet1);
		assertNull(ship.getFirstAvailableFighter("ZWing"));
	}
	
	
	/* Comprueba que getFirstAvailableFighter devuelve null en una nave cuyos cazas
	 * están todos destruídos.
	 */
	@Test(expected=NoFighterAvailableException.class)
	public void testGetFirstAvailableFighter3() throws NoFighterAvailableException {
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
	public void testGetFirstAvailableFighter4() throws NoFighterAvailableException {
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
	@Test(expected=NoFighterAvailableException.class)
	public void testGetFirstAvailableFighter5() throws NoFighterAvailableException {
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
	public void testGetFirstAvailableFighter6() throws NoFighterAvailableException {
		ship.addFighters(kFleet1);
		destroy("XWing",6);
		Fighter fighter = ship.getFirstAvailableFighter("XWing");
		assertNotNull(fighter);
		assertEquals("XWing",fighter.getType());
		 
		List<Fighter> lfleet = (List<Fighter>) ship.getFleetTest();
		assertEquals(lfleet.get(21), fighter);
		assertSame(lfleet.get(21), fighter);
	}
	
	
	//@DisplayName ("getFighterAvailable whose fighters are all destroyed except the middle one")
	/* Se crean los cazas de la constante kFleet1 en una nave. Se destruyen el primero y el tercero
	 * de los cazas 'YWing'. Se obtiene el primer caza 'YWing' no destruído de la nave. 
	 * Se comprueba que devuelve justo el segundo de los 'YWing'.
	 */
	@Test
	public void testGetFirstAvailableFighter7() throws NoFighterAvailableException {
		ship.addFighters(kFleet1);
		 
		List<Fighter> lfleet = (List<Fighter>) ship.getFleetTest();
		lfleet.get(17).addShield(-200);
		lfleet.get(19).addShield(-200);
		Fighter fighter = ship.getFirstAvailableFighter("YWing");
		assertNotNull(fighter);
		assertEquals("YWing",fighter.getType());
		assertEquals(lfleet.get(18), fighter);
		assertSame(lfleet.get(18), fighter);
	}
	
	//"5/XWing:12/AWing:3/YWing:2/XWing"
	//@DisplayName ("getFighterAvailable with the first fighter not destroyed")
	/* Se crean los cazas de la constante kFleet1 en una nave. Se destruyen todos
	 * excepto los dos últimos ('XWing') Se comprueba que getFirstAvailableFighter
	 * devuelve el primero de los dos últimos.
	 */ 
	@Test
	public void testGetFirstAvailableFighter8() throws NoFighterAvailableException {
		ship.addFighters(kFleet1);
		destroy("XWing",5);
		destroy("AWing",12);
		destroy("YWing",3);
		assertNotNull(ship.getFirstAvailableFighter(""));
		 
		List<Fighter> lfleet = (List<Fighter>) ship.getFleetTest();
		assertEquals(lfleet.get(20), ship.getFirstAvailableFighter(""));
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
		destroy("TIEFighter",35);
		destroy("TIEBomber",54);
		destroy("TIEShuttle",35);
		destroy("AWing",100);
		
		ship.purgeFleet();
		//Comprobamos total Fighters en ship
		 
		List<Fighter> auxFleet = ship.getFleetTest();
		assertEquals(124, auxFleet.size());
		//Comprobamos que coinciden las cantidades con cada tipo
		assertEquals(45, numberOfFightersOk("XWing"));
		assertEquals (3, numberOfFightersOk("AWing"));
		assertEquals (30, numberOfFightersOk("YWing"));
		assertEquals (0, numberOfFightersOk("TIEFighter"));
		assertEquals (1, numberOfFightersOk("TIEBomber"));
		assertEquals (45, numberOfFightersOk ("TIEInterceptor"));
		//assertEquals (0, numberOfFightersOk ("ZWing"));
	}
	
	 
	/* Crea cazas en un Ship,  destruye algunos cazas y comprueba que PurgeFleet ha eliminado
	 * solo los destruídos.
	 */
	@Test
	public void testPurgeFleet3() {
		ship.addFighters(kFleet1);
		
		List<Fighter> list = (List<Fighter>) ship.getFleetTest();
		(list.get(0)).addShield(-200);
		(list.get(4)).addShield(-200);
		(list.get(8)).addShield(-200);
		(list.get(10)).addShield(-200);
		(list.get(18)).addShield(-200);
		(list.get(20)).addShield(-200);
		(list.get(21)).addShield(-200);
		ship.purgeFleet();
		//Se comprueba el total Fighters en ship
		assertEquals(15, ship.getFleetTest().size());
		//Se comprueba que coinciden las cantidades con cada tipo
		assertEquals(3, numberOfFightersOk("XWing"));
		assertEquals (10, numberOfFightersOk("AWing"));
		assertEquals (2, numberOfFightersOk("YWing"));
	}
	
	//@DisplayName ("PurgeFleet with all fighters destroyed")
	/* Crea cazas en un Ship,  destruye todos los cazas y comprueba que PurgeFleet ha eliminado
	 * todos.
	 */
	@Test
	public void testPurgeFleet4() {
		ship.addFighters(kFleet1);
		destroy("", 0);			
		ship.purgeFleet();
		//Comprobamos total Fighters en ship
		
		List<Fighter> list = (List<Fighter>) ship.getFleetTest();
		assertEquals(0, list.size());
		
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
		compareLines(kShow,ship.showFleet());
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
		compareLines(kFleet22, ship.myFleet());	
	}
	//"65/XWing:110/AWing:30/YWing:35/TIEFighter:55/TIEBomber:45/TIEInterceptor";
	/* Crea cazas en una nave. Destrúyelos todos y comprueba que showFleet devuelve
	 * la cadena vacía.
	 */
	@Test
	public void testMyFleet3() {
		ship.addFighters(kFleet2);
		destroy("XWing", 65);
		destroy("AWing",110);
		destroy("YWing",30);
		destroy("TIEFighter",35);
		destroy("TIEBomber",55);
		destroy("TIEInterceptor",45);
		//destroy("ZWing",100);
		assertTrue(ship.myFleet().isEmpty());
	}
	
	//@DisplayName ("Check myFleet() in a not empty fleet with many fighters destroyed")
	/* Crea cazas en una nave. Destruye muchos y comprueba que showFleet solo devuelve
	 * una cadena con los no destruídos.
	 */
	@Test
	public void testMyFleet4() {
		ship.addFighters(kFleet2);
		destroy("XWing", 20);
		destroy("AWing",7);
		destroy("TIEFighter",35);
		destroy("TIEBomber",54);
		destroy("TIEShuttle",35);
		destroy("ZWing",100);
		compareLines(kFleet21,ship.myFleet());
	}

	/* Comprueba toString para una nave sin cazas */
	@Test
	public void testToString1() {
		compareLines ("Ship [Tydirium 0/0] ", ship.toString());
	}
	
	/* En una nave crea cazas. Comprueba que la salida con toString es correcta.
	 */
	@Test
	public void testToString2() {
		
		ship.addFighters(kFleet1);
		compareLines (kToString1, ship.toString());
	}
	
	/* En una nave crea cazas y  modifica el número de luchas ganadas y perdidas de
	 * sus cazas. Comprueba que la salida con toString es correcta.
	 */
	@Test
	public void testToString3() {
		for (int i=0; i<30; i++) ship.updateResults(1);
		for (int i=0; i<45; i++) ship.updateResults(-1);
		ship.addFighters(kFleet2);
		compareLines (kToString2, ship.toString());
	}
	
	/* En una nave crea cazas con muchas repeticiones del tipo.  
	 * Comprueba que la salida con toString es correcta.
	 */
	@Test
	public void testToString4() {
		
		ship.addFighters(kFleet3);
		compareLines (kToString3, ship.toString());
	}
	
	/* Test de comprobación de los parámetros null en Ship */
	@Test
	public void testRequireNonNull() throws NoFighterAvailableException {
		
		try {
			new Ship(null, Side.REBEL);
			fail("ERROR: Debió lanzar NullPointerException");
		}catch (NullPointerException e) {}
		try {
			new Ship("Tydirium", null);
			fail("ERROR: Debió lanzar NullPointerException");
		}catch (NullPointerException e) {}
		
		try {
			ship.addFighters(null);
			fail("ERROR: Debió lanzar NullPointerException");
		}catch (NullPointerException e) {}
		try {
			ship.getFirstAvailableFighter(null);
			fail("ERROR: Debió lanzar NullPointerException");
		}catch (NullPointerException e) {}
	}

	/*************************************/
	//METODOS AUXILIARES PARA LOS TESTS
	/*************************************/
/*	Object getValue(Ship auxShip, String sfield) {
		
		
		Class<?> cship=null;
		try {
			cship = Class.forName("model.Ship");
		 	Field field=null;
			field = cship.getDeclaredField(sfield);
			field.setAccessible(true);	
			return (field.get(auxShip));
		} catch (IllegalArgumentException | IllegalAccessException 
				| ClassNotFoundException | NoSuchFieldException 
				| SecurityException e) {
			e.printStackTrace();
			fail();
			return null;
		}	
	}*/
	
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
	
	//Compara dos Strings línea a línea
		private void  compareLines(String expected, String result) {
			String exp[]=expected.split("\n");
			String res[]=result.split("\n");
			boolean iguales = true;
			if (exp.length!=res.length) 
				fail("Cadena esperada de tamaño ("+exp.length+") distinto a la resultante ("+res.length+")");
			for (int i=0; i<exp.length && iguales; i++) {
				 if (! exp[i].contains("Action by")) {
					 assertEquals("linea "+i, exp[i].trim(),res[i].trim());
				 }
			}
		}

}
