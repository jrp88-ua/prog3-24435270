package model.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

import model.Coordinate;
import model.Fighter;
import model.Side;
import model.exceptions.InvalidSizeException;

public class PlayerFileTest {
	PlayerFile playerFile;
	GameShip gs;
	GameBoard gb;
	String inputFighter;
	BufferedReader br;
	Reader stringReader;
	PrintStream ps, old;
	ByteArrayOutputStream baos;
	final String kREBELGAMESHIP = "Ship [PlayerFile REBEL Ship 0/0] 10/AWing:25/XWing:7/YWing";
	final String kIMPERIALGAMESHIP = "Ship [PlayerFile IMPERIAL Ship 0/0] 155/TIEInterceptor:210/TIEBomber:170/TIEFighter";
	final String kSHOWSHIP ="Ship [PlayerFile REBEL Ship 0/0] 10/AWing:25/XWing:7/YWing\n" + 
			"(AWing 1 REBEL null {140,85,30})\n" + 
			"(AWing 2 REBEL null {140,85,30})\n" + 
			"(AWing 3 REBEL null {140,85,30})\n" + 
			"(AWing 4 REBEL null {140,85,30})\n" + 
			"(AWing 5 REBEL null {140,85,30})\n" + 
			"(AWing 6 REBEL null {140,85,30})\n" + 
			"(AWing 7 REBEL null {140,85,30})\n" + 
			"(AWing 8 REBEL null {140,85,30})\n" + 
			"(AWing 9 REBEL null {140,85,30})\n" + 
			"(AWing 10 REBEL null {140,85,30})\n" + 
			"(XWing 11 REBEL null {110,100,80})\n" + 
			"(XWing 12 REBEL null {110,100,80})\n" + 
			"(XWing 13 REBEL null {110,100,80})\n" + 
			"(XWing 14 REBEL null {110,100,80})\n" + 
			"(XWing 15 REBEL null {110,100,80})\n" + 
			"(XWing 16 REBEL null {110,100,80})\n" + 
			"(XWing 17 REBEL null {110,100,80})\n" + 
			"(XWing 18 REBEL null {110,100,80})\n" + 
			"(XWing 19 REBEL null {110,100,80})\n" + 
			"(XWing 20 REBEL null {110,100,80})\n" + 
			"(XWing 21 REBEL null {110,100,80})\n" + 
			"(XWing 22 REBEL null {110,100,80})\n" + 
			"(XWing 23 REBEL null {110,100,80})\n" + 
			"(XWing 24 REBEL null {110,100,80})\n" + 
			"(XWing 25 REBEL null {110,100,80})\n" + 
			"(XWing 26 REBEL null {110,100,80})\n" + 
			"(XWing 27 REBEL null {110,100,80})\n" + 
			"(XWing 28 REBEL null {110,100,80})\n" + 
			"(XWing 29 REBEL null {110,100,80})\n" + 
			"(XWing 30 REBEL null {110,100,80})\n" + 
			"(XWing 31 REBEL null {110,100,80})\n" + 
			"(XWing 32 REBEL null {110,100,80})\n" + 
			"(XWing 33 REBEL null {110,100,80})\n" + 
			"(XWing 34 REBEL null {110,100,80})\n" + 
			"(XWing 35 REBEL null {110,100,80})\n" + 
			"(YWing 36 REBEL null {80,70,110})\n" + 
			"(YWing 37 REBEL null {80,70,110})\n" + 
			"(YWing 38 REBEL null {80,70,110})\n" + 
			"(YWing 39 REBEL null {80,70,110})\n" + 
			"(YWing 40 REBEL null {80,70,110})\n" + 
			"(YWing 41 REBEL null {80,70,110})\n" + 
			"(YWing 42 REBEL null {80,70,110})";
	
	@Before
	public void setUp() throws Exception {
		inputFighter = "10/AWing:25/XWing:7/YWing\nlaunch 4 6\nlaunch 9 3 26\nlaunch 6 1 YWing\npatrol 26\nimprove 1 99\nexit"; 
		makePlayerFile(inputFighter,Side.REBEL);
		Fighter.resetNextId();
		/*stringReader = new StringReader(inputFighter);
		br = new BufferedReader(stringReader);
		playerFile = new PlayerFile(Side.REBEL, br);
		gb = new GameBoard(10);
		gs = playerFile.getGameShip();*/
	}

	/* 
	 * Se crea un PlayerFile (en el setUp) y se comprueba que el GameShip que se crea
	 * es del bando REBEL y que el nombre es correcto. Además se comprueba que el objeto
	 * playerRandom es una instancia de IPlayer.
	 */
	@Test
	public void testPlayerFile() {
		gs = playerFile.getGameShip();
		assertEquals(Side.REBEL,gs.getSide());
		assertEquals("PlayerFile REBEL Ship", gs.getName());
		assertTrue (playerFile instanceof IPlayer);
	}

	/* Se comprueba initFighters() para playerFile. Para ello se comprueba que el fleet del GameShip asociado 
	 * tiene 42 cazas y que el ship es correcto (coincide con kREBELGAMESHIP)
	 */
	
	@Test
	public void testInitFightersRebel() {
		playerFile.initFighters();
		gs = playerFile.getGameShip();
		assertEquals(42,gs.getFleetTest().size());
		compareLines(kREBELGAMESHIP, playerFile.getGameShip().toString());
		//System.out.println(playerFile.getGameShip().toString());
	}
	
	/* Se crea un PlayerFile Imperial. Se invoca a initFighters() y
	 * se comprueba que el fleet del GameShip asociado tiene 535 cazas y
	 * que el ship es correcto (coincide con kIMPERIALGAMESHIP)
	 */
	@Test
	public void testInitFightersImperial() {
		inputFighter = "155/TIEInterceptor:210/TIEBomber:170/TIEFighter\nlaunch 1 5\nlaunch 2 2\npatrol 2\nexit\n";
		makePlayerFile(inputFighter, Side.IMPERIAL);
		playerFile.initFighters();
		assertEquals(535,gs.getFleetTest().size());
		compareLines(kIMPERIALGAMESHIP, playerFile.getGameShip().toString());
		//System.out.println(playerFile.getGameShip().toString());
	}

	
	/* Para un PlayerFile sin iniciar (sin cazas en la nave) se comprueba que isFleetDestroyed es true
	 * Tras iniciar la flota, se comprueba ahora que es false. Se destruyen todos y se comprueba que
	 * devuelve true. 
	 */
	@Test
	public void testIsFleetDestroyed() {
		assertTrue (playerFile.isFleetDestroyed());
		playerFile.initFighters();
		assertFalse (playerFile.isFleetDestroyed());
		for (Fighter f: playerFile.getGameShip().getFleetTest()) {
			f.addShield(-300);
		}
		assertTrue (playerFile.isFleetDestroyed());
	}
	

	/* Se comprueba showShip para playerFile sin iniciarlo. Tras iniciarlo se comprueba
	 * que ahora coincide con kSHOWSHIP
	 */
	@Test
	public void testShowShip() {
		compareLines("Ship [PlayerFile REBEL Ship 0/0] ",playerFile.showShip());
		playerFile.initFighters();
		compareLines(kSHOWSHIP, playerFile.showShip());
	}

	/* Comprueba purgeFleet() con un PlayeFile con toda la flota de su nave intacta.
	 * El número de cazas debe coincidir con el que tenía antes de hacer purgeFleet.
	 * Se destruyen todos los cazas. Se ejecuta purgeFleet(). Se comprueba que ahora
	 * no existe ningún caza en la nave.
	 */
	@Test
	public void testPurgeFleet() {
		playerFile.initFighters();
		assertEquals(42,playerFile.getGameShip().getFleetTest().size());
		playerFile.purgeFleet();
		assertEquals(42,playerFile.getGameShip().getFleetTest().size());
		
		for (Fighter f: playerFile.getGameShip().getFleetTest()) {
			f.addShield(-300);
		}
		playerFile.purgeFleet();
		assertEquals(0,playerFile.getGameShip().getFleetTest().size());	
	}
	
	/* Se inicia playerFile con cazas en su nave. Se le añade un tablero. 
	 * Se invoca, para ese player, a nextPlay() una vez de tal forma que se añade un caza en el tablero.
	 * Se comprueba que está en el tablero en esa posición. 
	 * Se vuelve a invocar a nextPlay() se lee exit y se comprueba que devuelve false.
	 */
	@Test
	public void testNextPlay() {
		String s = "10/AWing:25/XWing\nlaunch 4 6\nexit";
		makePlayerFile(s,Side.REBEL);
		playerFile.initFighters();
		playerFile.setBoard(gb);
		gs = playerFile.getGameShip();
		assertTrue (playerFile.nextPlay()); //Pone un caza en el tablero (id=1)
		assertFalse (playerFile.nextPlay()); //Sale con exit	
		checkFighterOnBoard(new Coordinate(4,6), 1);
	}

	/* Se inicia playerFile con cazas en su nave. Se le añade un tablero. 
	 * Se invoca, para ese player, a nextPlay() varias veces de tal forma que:
	 *  -añade 3 cazas en el tablero (con cada una de las 3 posibilidades) y comprueba que así ha sido.
	 *  -mejora uno de ellos. Comprueba que la mejora se ha efectuado.
	 *  -pone a patrullar a uno en el tablero
	 *  -abandona la partida
	 * 10/AWing:25/XWing:7/YWing\nlaunch 4 6\nlaunch 9 3 26\nlaunch 6 1 YWing\npatrol 26\nimprove 1 99\nexit\n"
	 */
	@Test
	public void testNextPlay1() {
		playerFile.initFighters();
		playerFile.setBoard(gb);
		gs = playerFile.getGameShip();
		assertTrue (playerFile.nextPlay()); //Pone un caza en el tablero (id=1)
		assertTrue (playerFile.nextPlay()); //Pone un caza en el tablero (id=26)
		assertTrue (playerFile.nextPlay()); //Pone un caza en el tablero (id=36)
		checkFighterOnBoard(new Coordinate(4,6), 1);
		checkFighterOnBoard(new Coordinate(9,3), 26);
		checkFighterOnBoard(new Coordinate(6,1), 36);
		assertTrue (playerFile.nextPlay()); //Pone a patrullar al caza con id=26
		assertTrue (playerFile.nextPlay()); //Mejora uno de los cazas del tablero (id=1)
		Fighter f = gb.getFighter(new Coordinate(4,6));
		assertNull(f);
		f=gs.getFleetTest().get(0);
		assertEquals(134,f.getAttack());
		assertEquals(80,f.getShield());
		assertFalse (playerFile.nextPlay()); //Debe de salir
	}
	
	/* Tras iniciar playerFile con cazas y asignarles un tablero de 10x10, se invoca 7 veces a nextPlay() 
	 * que debe ejecutar 1 instrucción bien y 6 instrucciones launch erróneas:
	 *  - pone un caza en el tablero
	 *  - con 1 número
	 *  - con 4 números 
	 *  - con un caza erróneo.
	 *  - con una coordenada fuera del tablero
	 *  - con un tipo de caza que no está en la nave
	 *  - con un caza que ya está en el tablero(el que se puso al principio)
	 *  
	 * Se comprueba que se emitieron los mensajes de error correspondientes y que éstos empiezan con la subcadena "ERROR:".
	 */
	@Test
	public void testNextPlayWithErrorsInLaunch() {
		
		String s = "10/AWing:25/XWing\nlaunch 2 2 5\nlaunch 2 \nlaunch 9 3 26 31\nlaunch 6 1 ZWing\nlaunch 10 5 5\nlaunch 1 4 YWing\nlaunch 3 3 5";
		makePlayerFile(s,Side.REBEL);
		playerFile.initFighters();
		playerFile.setBoard(gb);
		standardIO2Stream(); //Cambiamos la salida estandard a un nuevo stream
		for (int i=0; i<7; i++) 
			assertTrue (playerFile.nextPlay()); 
		String serr = Stream2StandardIO(); //Restauramos la salida estandar a la consola.
		errorsControl(serr,6);
	}
	
	/* Tras iniciar playerFile con cazas y asignarles un tablero de 10x10, se invoca cuatro veces a nextPlay() 
	 * que debe ejecutar tres instrucciones launch erróneas:
	 *  - con 1 número 
	 *  - con 3 números
	 *  - con qty no menor que 100
	 *  - con un id inexistente.
	 * Se comprueba que se emitieron los mensajes de error y que éstos empiezan con la subcadena "ERROR:".
	 */
	@Test
	public void testNextPlayWithErrorsInImprove() {
		
		String s = "10/AWing:25/XWing:7/YWing\nlaunch 2 \nimprove 9\nimprove 4 3 26\nimprove 4 100\nimprove 43 50";
		makePlayerFile(s,Side.REBEL);
		playerFile.initFighters();
		playerFile.setBoard(gb);
		standardIO2Stream(); //Cambiamos la salida estandard a un nuevo stream
		assertTrue (playerFile.nextPlay()); 
		assertTrue (playerFile.nextPlay()); 
		assertTrue (playerFile.nextPlay());
		assertTrue (playerFile.nextPlay());
		String serr = Stream2StandardIO(); //Restauramos la salida estandar a la consola.
		errorsControl(serr,4);
	}

	/* Tras iniciar playerFile con cazas y asignarles un tablero de 10x10, se invoca cuatro veces a nextPlay() 
	 * que debe ejecutar tres instrucciones launch erróneas:
	 *  - con 1 número 
	 *  - con 3 números
	 *  - con qty no menor que 100
	 *  - con un id inexistente.
	 * Se comprueba que se emitieron los mensajes de error y que éstos empiezan con la subcadena "ERROR:".
	 */
	@Test
	public void testNextPlayWithErrorsInPatrol() {
		
		String s = "10/AWing:25/XWing:7/YWing\npatrol \npatrol 4 26\npatrol 4\npatrol 43";
		makePlayerFile(s,Side.REBEL);
		playerFile.initFighters();
		playerFile.setBoard(gb);
		standardIO2Stream(); //Cambiamos la salida estandard a un nuevo stream
		assertTrue (playerFile.nextPlay()); 
		assertTrue (playerFile.nextPlay()); 
		assertTrue (playerFile.nextPlay());
		assertTrue (playerFile.nextPlay());
		String serr = Stream2StandardIO(); //Restauramos la salida estandar a la consola.
		errorsControl(serr,4);
	}
	
	/* Test de comprobación de los parámetros null en PlayerFile */
	@Test
	public void testRequireNonNull()  {
		
		try {
			new PlayerFile(null, br);
			fail("ERROR: Debió lanzar NullPointerException");
		}catch (NullPointerException e) {}
		try {
			new PlayerFile(Side.REBEL, null);
			fail("ERROR: Debió lanzar NullPointerException");
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
	
	// Comprueba si existe un Figther con id n, en la coordenada c del tablero bg
	private void checkFighterOnBoard(Coordinate c, int n) {
		Fighter fighter = gb.getFighter(c);
		assertNotNull(fighter);
		assertEquals(n, fighter.getId());
	}
	
	private void makePlayerFile(String inputFighter,Side side) { 
		stringReader = new StringReader(inputFighter);
		br = new BufferedReader(stringReader);
		playerFile = new PlayerFile(side, br);
		try {
			gb = new GameBoard(10);
		} catch (InvalidSizeException e) {
			System.out.println(e.getMessage());
		}
		gs = playerFile.getGameShip();
	}
	
	//Redirección de la salida standard de la consola a un Stream	
	private  void standardIO2Stream(){
		baos = new ByteArrayOutputStream();
		ps = new PrintStream(baos);
		// Guarda el antiguo System.out!
		old = System.out;
		// Cambiamos en Java la salida standard al nuevo stream
		System.setOut(ps);   
	}
		
	//Volver a restaurar la salida standard a la consola
	private String Stream2StandardIO(){
		System.out.flush();
		System.setOut(old); //Reestablecemos la salida standard
		return (baos.toString());
	}
		
	//Se comprueba si serr contiene nlines que empiezan con "ERROR:"
	private void errorsControl(String serr, int nlines) {
		String verr[] = serr.split("\n");
		if (nlines != verr.length) fail ("ERROR: Número de líneas de error incorrectas "+verr.length);
		for (int i=0; i<nlines; i++) {
			assertTrue("Error en la línea "+i,verr[i].startsWith("ERROR:"));
		}
	}
	
}
