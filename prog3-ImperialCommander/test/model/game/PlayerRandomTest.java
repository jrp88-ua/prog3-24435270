package model.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.Coordinate;
import model.Fighter;
import model.RandomNumber;
import model.Side;

public class PlayerRandomTest {

	IPlayer playerRandom;
	GameShip gs;
	GameBoard gb;
	final String kEMPTYGAMESHIP = "Ship [PlayerRandom REBEL Ship 0/0]";
	final String kREBELGAMESHIP = "Ship [PlayerRandom REBEL Ship 0/0] 5/XWing:8/YWing:7/AWing";
	final String kIMPERIALGAMESHIP = "Ship [PlayerRandom IMPERIAL Ship 0/0] 485/TIEFighter:88/TIEBomber:347/TIEInterceptor";
	final String kSHOWSHIP ="Ship [PlayerRandom REBEL Ship 0/0] 5/XWing:8/YWing:7/AWing\n" + 
			"(XWing 1 REBEL null {110,100,80})\n" + 
			"(XWing 2 REBEL null {110,100,80})\n" + 
			"(XWing 3 REBEL null {110,100,80})\n" + 
			"(XWing 4 REBEL null {110,100,80})\n" + 
			"(XWing 5 REBEL null {110,100,80})\n" + 
			"(YWing 6 REBEL null {80,70,110})\n" + 
			"(YWing 7 REBEL null {80,70,110})\n" + 
			"(YWing 8 REBEL null {80,70,110})\n" + 
			"(YWing 9 REBEL null {80,70,110})\n" + 
			"(YWing 10 REBEL null {80,70,110})\n" + 
			"(YWing 11 REBEL null {80,70,110})\n" + 
			"(YWing 12 REBEL null {80,70,110})\n" + 
			"(YWing 13 REBEL null {80,70,110})\n" + 
			"(AWing 14 REBEL null {140,85,30})\n" + 
			"(AWing 15 REBEL null {140,85,30})\n" + 
			"(AWing 16 REBEL null {140,85,30})\n" + 
			"(AWing 17 REBEL null {140,85,30})\n" + 
			"(AWing 18 REBEL null {140,85,30})\n" + 
			"(AWing 19 REBEL null {140,85,30})\n" + 
			"(AWing 20 REBEL null {140,85,30})";
	final String kNEXTPLAYMANYTIMES1  =   "0123456789\n" + 
			"			  ----------\n" + 
			"			  0|    A    Y\n" + 
			"			  1|          \n" + 
			"			  2|  A  X A Y\n" + 
			"			  3|         A\n" + 
			"			  4|          \n" + 
			"			  5|      YXXA\n" + 
			"			  6| A   Y   Y\n" + 
			"			  7|  Y      X\n" + 
			"			  8|     A  Y \n" + 
			"			  9|  Y    X  ";
	final String kNEXTPLAYMANYTIMES2 = "  0123456789\n" + 
			"  ----------\n" + 
			"0|    ff   b\n" + 
			"1|        i \n" + 
			"2| if ff f f\n" + 
			"3| b      ii\n" + 
			"4|     i    \n" + 
			"5|  if  ffff\n" + 
			"6|ff  fbi  i\n" + 
			"7|  b   if  \n" + 
			"8|     i  fb\n" + 
			"9|f i  fffi ";
	@Before
	public void setUp() throws Exception {
		playerRandom = new PlayerRandom(Side.REBEL,10);
		gb = new GameBoard(10);
		RandomNumber.resetRandomCounter();
		Fighter.resetNextId();
	}

	/* 
	 * Se crea un PlayerRandom (en el setUp) y se comprueba que el GameShip que se crea
	 * es del bando REBEL y que el nombre es correcto. Además se comprueba que el objeto
	 * playerRandom es una instancia de IPlayer.
	 */
	@Test
	public void testPlayerRandom() {
		gs = playerRandom.getGameShip();
		assertEquals(Side.REBEL,gs.getSide());
		assertEquals("PlayerRandom REBEL Ship", gs.getName());
		assertTrue (playerRandom instanceof IPlayer);
	}

	
	@Test
	public void testInitFightersWithoutFighters() {
		playerRandom = new PlayerRandom(Side.REBEL,1);
		playerRandom.initFighters();
		gs = playerRandom.getGameShip();
		assertEquals(0,gs.getFleetTest().size());
		compareLines(kEMPTYGAMESHIP, playerRandom.getGameShip().toString());
	}

	/* Se comprueba initFighters() para playerRandom. Para ello se comprueba que el fleet del GameShip asociado 
	 * tiene 20 cazas y que el ship es correcto (coincide con kREBELGAMESHIP)
	 */
	@Test
	public void testInitFightersRebel() {
		playerRandom.initFighters();
		gs = playerRandom.getGameShip();
		assertEquals(20,gs.getFleetTest().size());
		compareLines(kREBELGAMESHIP, playerRandom.getGameShip().toString());
	}
	
	/* Se crea un PlayerRandom Imperial, con numSize de 500. Se invoca a initFighters() y
	 * se comprueba que el fleet del GameShip asociado tiene 920 cazas y
	 * que el ship es correcto (coincide con kIMPERIALGAMESHIP)
	 */
	@Test
	public void testInitFightersImperial() {
		playerRandom = new PlayerRandom(Side.IMPERIAL,500);
		playerRandom.initFighters();
		gs = playerRandom.getGameShip();
		assertEquals(920,gs.getFleetTest().size());
		compareLines(kIMPERIALGAMESHIP, playerRandom.getGameShip().toString());
	}

	/* Para un PlayerRandom sin iniciar (sin cazas en la nave) se comprueba que isFleetDestroyed es true
	 * Tras iniciar la flota, se comprueba ahora que es false. Se destruyen todos y se comprueba que
	 * devuelve true. 
	 */
	@Test
	public void testIsFleetDestroyed() {
		assertTrue (playerRandom.isFleetDestroyed());
		playerRandom.initFighters();
		assertFalse (playerRandom.isFleetDestroyed());
		for (Fighter f: playerRandom.getGameShip().getFleetTest()) {
			f.addShield(-300);
		}
		assertTrue (playerRandom.isFleetDestroyed());
	}

	/* Se comprueba showShip para playerRandom sin iniciarlo. Tras iniciarlo se comprueba
	 * que ahora coincide con kSHOWSHIP
	 */
	@Test
	public void testShowShip() {
		compareLines("Ship [PlayerRandom REBEL Ship 0/0] ",playerRandom.showShip());
		playerRandom.initFighters();
		compareLines(kSHOWSHIP, playerRandom.showShip());
	}

	/* Comprueba purgeFleet() con un playerRandom con toda la flota de su nave intacta.
	 * El número de cazas debe coincidir con el que tenía antes de hacer purgeFleet.
	 * Se destruyen todos los cazas. Se ejecuta purgeFleet(). Se comprueba que ahora
	 * no existe ningún caza en la nave.
	 */
	@Test
	public void testPurgeFleet() {
		playerRandom.initFighters();
		assertEquals(20,playerRandom.getGameShip().getFleetTest().size());
		playerRandom.purgeFleet();
		assertEquals(20,playerRandom.getGameShip().getFleetTest().size());
		
		for (Fighter f: playerRandom.getGameShip().getFleetTest()) {
			f.addShield(-300);
		}
		playerRandom.purgeFleet();
		assertEquals(0,playerRandom.getGameShip().getFleetTest().size());	
	}

	/* Se inicia playerRandom con cazas en su nave. Se le añade un tablero. 
	 * Se invoca a nextPlay() varias veces de tal forma que entra en todas
	 * las opciones (excepto exit). 
	 * 
	 */
	@Test
	public void testNextPlaySeveralTimes() {
		playerRandom.initFighters();
		playerRandom.setBoard(gb);
		
		assertTrue (playerRandom.nextPlay()); //Intenta poner a patrullar a un caza
		assertTrue (playerRandom.nextPlay()); //Pone un caza en el tablero (id=5)
		checkFighterOnBoard(new Coordinate(4,6), 5);
	
		assertTrue (playerRandom.nextPlay()); //Pone un caza en el tablero (id=20)
		checkFighterOnBoard(new Coordinate(9,3), 20);
		
		assertTrue (playerRandom.nextPlay()); //Pone un caza a patrullar (id=5)
		assertTrue (playerRandom.nextPlay()); //Pone un caza en el tablero (id=18)
		checkFighterOnBoard(new Coordinate(2,2), 18);
	
		assertTrue (playerRandom.nextPlay()); //Intenta mejorar un caza que no está en el tablero
	}
	
	/* Tras iniciar playerRandom con cazas y asignarles un tablero de 10x10, se invoca repetidamente nextPlay() hasta que
	 * retorne false. Se comprueba que el número de veces que se ejecutó es 105. Y además que el tablero con sus cazas
	 * coincide con el de kNEXTPLAYMANYTIMES1
	 */
	@Test
	public void testNextPlayManyTimes1() {
		playerRandom.initFighters();
		playerRandom.setBoard(gb);
		int n=0;
		while (playerRandom.nextPlay())
			n++;
		assertEquals(105,n);
		compareLines(kNEXTPLAYMANYTIMES1,gb.toString() );
	}
	
	/* Tras crear un playerRandom imperial con numSize de 500, e iniciarlo con cazas y asignarles un tablero de 10x10, 
	 * se invoca repetidamente nextPlay() hasta que retorne false. Se comprueba que el número de veces que se ejecutó 
	 * es 83. Y además que el tablero con sus cazas coincide con el de kNEXTPLAYMANYTIMES2
	 */
	@Test
	public void testNextPlayManyTimes2() {
		playerRandom = new PlayerRandom(Side.IMPERIAL,500);
		playerRandom.initFighters();
		playerRandom.setBoard(gb);
		int n=0;
		while (playerRandom.nextPlay())
			n++;
		assertEquals(83,n);
		compareLines(kNEXTPLAYMANYTIMES2,gb.toString() );
	}
	
	/* Test de comprobación de los parámetros null en PlayerRandom */
	@Test
	public void testRequireNonNull()  {
		
		try {
			new PlayerRandom(null,10);
			fail("ERROR: Debió lanzar NullPointerException");
		}catch (NullPointerException e) {}
		try {
			playerRandom.setBoard(null);
		}catch (NullPointerException e) {}
	}

	/***************************
	 * METODOS DE APOYO
	 ***************************/
	//Compara dos String línea a línea
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
	
	private void checkFighterOnBoard(Coordinate c, int n) {
		Fighter fighter = gb.getFighter(c);
		assertNotNull(fighter);
		assertEquals(n, fighter.getId());
	}
}
