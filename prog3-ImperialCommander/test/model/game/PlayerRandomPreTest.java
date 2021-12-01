package model.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Coordinate;
import model.Fighter;
import model.RandomNumber;
import model.Side;
import model.exceptions.FighterAlreadyInBoardException;
import model.exceptions.FighterNotInBoardException;
import model.exceptions.InvalidSizeException;
import model.exceptions.OutOfBoundsException;
import model.game.exceptions.WrongFighterIdException;

public class PlayerRandomPreTest {

	IPlayer playerRandom;
	GameShip gs;
	GameBoard gb;
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
	
	@Before
	public void setUp() throws Exception {
		playerRandom = new PlayerRandom(Side.REBEL,10);
		gb = new GameBoard(10);
		RandomNumber.resetRandomCounter();
		Fighter.resetNextId();
	}

	/* 
	 * Se crea un PlayerRandom (en el setUp) y se comprueba que el GameShip que se crea
	 * es del bando REBEL y que el nombre es correcto. 
	 * Comprueba que el objeto playerRandom es una instancia de IPlayer.
	 */
	//TODO
	@Test
	public void testPlayerRandom() {
		gs = playerRandom.getGameShip();
		assertEquals(Side.REBEL,gs.getSide());
		assertEquals("PlayerRandom REBEL Ship", gs.getName());
		fail("Comprueba que el objeto playerRandom es una instancia de IPlayer");
	}


	/* Se comprueba initFighters() para playerRandom. Para ello se constata que el fleet del GameShip asociado 
	 * tiene 20 cazas y que el ship es correcto (coincide con kREBELGAMESHIP)
	 */
	@Test
	public void testInitFightersRebel() {
		playerRandom.initFighters();
		gs = playerRandom.getGameShip();
		assertEquals(20,gs.getFleetTest().size());
		compareLines(kREBELGAMESHIP, playerRandom.getGameShip().toString());
	}
	
	/* Crea un PlayerRandom Imperial, con numSize de 500. Invoca a initFighters() y
	 * comprueba que el fleet del GameShip asociado tiene 920 cazas y
	 * que el ship es correcto (coincide con kIMPERIALGAMESHIP)
	 */
	//TODO
	@Test
	public void testInitFightersImperial() {
		fail("Realiza el test");
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
	 * Destruye todos los cazas. Ejecuta purgeFleet(). Comprueba que ahora
	 * no existe ningún caza en la nave.
	 */
	//TODO
	@Test
	public void testPurgeFleet() {
		playerRandom.initFighters();
		assertEquals(20,playerRandom.getGameShip().getFleetTest().size());
		fail("Termina el test");
	}

	/* Se inicia playerRandom con cazas en su nave. Se le añade un tablero. 
	 * Se invoca a nextPlay() varias veces de tal forma que entra en todas
	 * las opciones (excepto exit). 
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
	
	/* Realiza el test de comprobación de los parámetros null en PlayerRandom del constructor y de setBoard */
	//TODO
	@Test
	public void testRequireNonNull()  {
		
		fail("Realiza el test");
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
			 
				 assertEquals("linea "+i, exp[i].trim(),res[i].trim());
		}
	}
	
	//Comprueba que el Fighter con id n está en el tablero en la coordenada c
	private void checkFighterOnBoard(Coordinate c, int n) {
		Fighter fighter = gb.getFighter(c);
		assertNotNull(fighter);
		assertEquals(n, fighter.getId());
	}
}
