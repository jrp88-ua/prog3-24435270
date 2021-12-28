package model.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import model.Fighter;
import model.RandomNumber;
import model.Side;

public class GameTest {
	PlayerFile plfRebel, plfImperial;
	PlayerRandom plrRebel, plrImperial;
	Reader stringReader;
	Game game;
	ByteArrayOutputStream baos;
	PrintStream ps, old;
	@Before
	public void setUp() throws Exception {
		 
		String inputImp = "2/TIEInterceptor\nlaunch 1 1\nlaunch 2 2\npatrol 2\nexit\n";
		plfImperial=makePlayerFile(inputImp,Side.IMPERIAL);
		
		String inputReb = "2/YWing\nlaunch 1 2\nlaunch 2 3\nexit\n";
		plfRebel = makePlayerFile(inputReb, Side.REBEL);	
        game = new Game(plfImperial,plfRebel);
        RandomNumber.resetRandomCounter();
        Fighter.resetNextId();
	}

	/* Comprueba que el tamaño del tablero es 10 */
	@Test
	public void testGame() {
		GameBoard gb = game.getGameBoard();
		assertEquals(10,gb.getSize());
		
	}
	
	/* Test de comprobación de los parámetros null en el constructor de Game */
	@Test
	public void testRequireNonNull() {
		
		try {
			new Game(null, plfRebel);
			fail("ERROR: Debió lanzar NullPointerException");
		}catch (NullPointerException e) {}
		try {
			new Game(plfImperial, null);
			fail("ERROR: Debió lanzar NullPointerException");
		}catch (NullPointerException e) {}
	}
	
	/*Test con comandos iniciales de exit en IMPERIAL y REBEL. Debe ganar REBEL 
	 */
	@Test
	public void testPlayPlayersExit() {
		String inputImp = "2/TIEInterceptores\nexit";
		plfImperial=makePlayerFile(inputImp,Side.IMPERIAL);
		String inputReb = "2/YWing\nexit";
		plfRebel = makePlayerFile(inputReb, Side.REBEL);
		game = new Game(plfImperial,plfRebel);
		standardIO2Stream();
		Side winner = game.play();
		String sout = Stream2StandardIO();
		assertEquals(Side.REBEL, winner);
		String solution = readSolutionFromFile("files/testPlayPlayersExit.out");
		compareLines(solution, sout);
	}

	/* Game con ships de los PlayerFile vacíos.
	 * Se crean players con naves sin cazas. Se comprueba que el que gana es REBEL y
	 * que la salida de play coincide con la resultado
	 */
	@Test
	public void testPlayEmptyShipsInPlayerFile() {
		String inputImp = "2/TIEInterceptores\nimprove 1 80";
		plfImperial=makePlayerFile(inputImp,Side.IMPERIAL);
		String inputReb = "2/ZWing\nimprove 1 80";
		plfRebel = makePlayerFile(inputReb, Side.REBEL);
		game = new Game(plfImperial,plfRebel);
		standardIO2Stream();
		Side winner = game.play();
		String sout = Stream2StandardIO();
		assertEquals(Side.REBEL, winner);
		String solution = readSolutionFromFile("files/testPlayEmptyShipsInPlayerFile.out");
		compareLines(solution, sout);
	}
	
	/* Game con ships de los PlayerRandom vacíos.
	 * Se crean players con naves sin cazas. Se comprueba que el que gana es REBEL y
	 * que la salida de play coincide con la resultado
	 */
	@Test
	public void testPlayEmptyShipsInPlayerRandom() {
		plrRebel = new PlayerRandom(Side.REBEL,1);		
		plrImperial = new PlayerRandom(Side.IMPERIAL,1);	
		game = new Game(plrImperial,plrRebel);
	
		standardIO2Stream();	
		Side winner = game.play();
		String sout = Stream2StandardIO();
		
		assertEquals(Side.REBEL, winner);
		System.out.println(sout);
		String solution = readSolutionFromFile("files/testPlayEmptyShipsInPlayerRandom.out");
		compareLines(solution, sout);
	}
	
	
	/* Game con la nave REBEL inicialmente vacía.
	 * Se crea el player REBEL con su nave vacía y la IMPERIAL con cazas. Se comprueba que 
	 * el que gana es IMPERIAL y que la salida de play coincide con la resultado.
	 */
	@Test
	public void testPlayEmptyRebelShip() {
		String inputImp = "1/TIEInterceptor\nlaunch 1 1";
		plfImperial=makePlayerFile(inputImp,Side.IMPERIAL);
		String inputReb = "1/zWing\nlaunch 1 2";
		plfRebel = makePlayerFile(inputReb, Side.REBEL);
		game = new Game(plfImperial,plfRebel);
		standardIO2Stream();
		Side winner = game.play();
		String sout = Stream2StandardIO();
		assertEquals(Side.IMPERIAL, winner);
		String solution = readSolutionFromFile("files/testPlayEmptyRebelShip.out");
		compareLines(solution, sout);
	}
	
	/* Game del MainP4min.
	 * Se redireciona la salida estandar a un stream. Se llama a play que reproduce
	 * la misma partida del MainP4min */
	@Test
	public void testPlayMain1() {
		standardIO2Stream();
		Side winner = game.play();
		String sout = Stream2StandardIO();
		assertEquals(Side.IMPERIAL, winner);
		String solution = readSolutionFromFile("files/testPlayMain1.out");
		compareLines(solution, sout);
	}
	
	/* Game del MainP4.
	 * Se redireciona la salida estandar a un stream. Se llama a play que reproduce
	 * la misma partida del MainP4 */
	@Test
	public void testPlayMain2() {
		plrImperial = new PlayerRandom(Side.IMPERIAL,3);
		plrRebel = new PlayerRandom(Side.REBEL,3);	
		game = new Game(plrImperial,plrRebel);
	
		standardIO2Stream();	
		Side winner = game.play();
		String sout = Stream2StandardIO();
		
		assertEquals(Side.IMPERIAL, winner);
		System.out.println(sout);
		String solution = readSolutionFromFile("files/testPlayMain2.out");
		compareLines(solution, sout);
	}

	/*
	 * En el juego hay 6 formas de terminar una partida:
	 *  - IMP exit (testPlayMainImpExit)
	 *  - IMP juega y pierde (testPlayMainImpLoss)
	 *  - IMP juega y gana  (testPlayMain1 , de MainP4min)
	 *  - REB exit (testPlayMain2, de MainP4)
	 *  - REB juega y pierde (testPlayMainRebLoss)
	 *  - REB juega y gana (testPlayMainRebWin)
	 */

	/*Test como MainP4min, IMPERIAL hace exit 
	 */
	@Test
	public void testPlayMainImpExit() {
		String inputImp = "2/TIEInterceptor\nlaunch 1 1\nlaunch 2 2\nexit\n";
		plfImperial=makePlayerFile(inputImp,Side.IMPERIAL);
		String inputReb = "2/YWing\nlaunch 1 2\nlaunch 2 3\nexit\n";
		plfRebel = makePlayerFile(inputReb, Side.REBEL);
		game = new Game(plfImperial,plfRebel);
		standardIO2Stream();
		Side winner = game.play();
		String sout = Stream2StandardIO();
		assertEquals(Side.REBEL, winner);
		String solution = readSolutionFromFile("files/testPlayMainImpExit.out");
		compareLines(solution, sout);
	}
	
	/* Game con dos PlayerRandom con 11 fighters cada uno
	 * gana REBEL porque IMPERIAL hace 'exit'
	 */
	@Test
	public void testPlayRandom11() {
		
		PlayerRandom plimperial = new PlayerRandom(Side.IMPERIAL,11);
		PlayerRandom plrebel = new PlayerRandom(Side.REBEL,11);

		game = new Game(plimperial,plrebel);
	
		standardIO2Stream();
		Side winner = game.play();
		
		String sout = Stream2StandardIO(); //Cambia salida standard a un Stream
		assertEquals(Side.REBEL, winner);
		String solution = readSolutionFromFile("files/testPlayRandom11.out"); //Cambia salida de Stream a la consola
		compareLines(solution, sout);
	}
	
	/* Misma partida que la anterior, pero con PlayerFile
	 */
	@Test
	public void testPlayFile11() {
		
		String inputImp = "4/TIEFighter:6/TIEBomber:8/TIEInterceptor\nlaunch 8 8 11\nlaunch 2 2 6\nlaunch 0 9 17\nlaunch 5 4 3\nlaunch 9 0 5\nlaunch 7 2 4\nlaunch 1 6 1\npatrol 5\nlaunch 7 2 18\nlaunch 8 5 18\npatrol 11\nlaunch 4 8 10\nlaunch 3 7 8\nlaunch 3 0 12\nexit\n";
		stringReader = new StringReader(inputImp);
		BufferedReader br = new BufferedReader(stringReader);
		plfImperial = new PlayerFile(Side.IMPERIAL, br);
		
		String inputReb = "8/XWing:9/YWing:2/AWing\nlaunch 7 3 29\nimprove 21 96\nlaunch 8 3 37\npatrol 29\nlaunch 5 0 36\npatrol 29\nlaunch 6 8 19\nlaunch 6 5 24\nlaunch 7 8 23\nlaunch 5 9 35\nlaunch 1 6 21\nlaunch 7 7 34\nlaunch 5 0 31\nlaunch 6 1 30\nexit\n";
		stringReader = new StringReader(inputReb);
		br = new BufferedReader(stringReader);	
		plfRebel = new PlayerFile(Side.REBEL, br);	
		
        game = new Game(plfImperial,plfRebel);
		standardIO2Stream();
		Side winner = game.play();
		
		String sout = Stream2StandardIO(); //Cambia salida standard a un Stream
		assertEquals(Side.REBEL, winner);
		String solution = readSolutionFromFile("files/testPlayFile11.out"); //Cambia salida de Stream a la consola
		compareLines(solution, sout);
	}

	
	
	/***************************
	 * METODOS DE APOYO
	 ***************************/
	/* Compara dos String línea a línea.
	 * Parámetros: la cadena esperada, la cadena resultado.
	 */
		private void  compareLines(String expected, String result) {
			expected=expected.replaceAll("\n+","\n");
			result=result.replaceAll("\n+","\n");
			String exp[]=expected.split("\n");
			String res[]=result.split("\n");
			boolean iguales = true;
			if (exp.length!=res.length) 
				fail("Cadena esperada de tamaño ("+exp.length+") distinto a la resultante ("+res.length+")");
			for (int i=0; i<exp.length && iguales; i++) {
				if (! exp[i].contains("ERROR:")) {
					res[i]=res[i].trim();
					if (res[i].length()>1 && (res[i].charAt(1)=='|')) //Es un Board
						assertEquals("linea "+i, exp[i].trim(),res[i]);
					else
						assertEquals("linea "+i, exp[i].replaceAll(" ",""), res[i].replaceAll(" ","")); 
				} 
				else if (! res[i].contains("ERROR:"))
						fail("Error: el resultado esperado debía contener en la línea "+i+" la cadena 'ERROR:'");
			}
		}
	
	
		
	private PlayerFile makePlayerFile(String inputFighter,Side side) { 
		stringReader = new StringReader(inputFighter);
		BufferedReader br = new BufferedReader(stringReader);
		PlayerFile playerFile = new PlayerFile(side, br);
		return playerFile;

	}
	
	//Redirección de la salida estandard de la consola a un Stream	
	private  void standardIO2Stream(){
		baos = new ByteArrayOutputStream();
		ps = new PrintStream(baos);
		// IMPORTANTE: Guarda el antiguo System.out!
		old = System.out;
		// Cambiamos en Java la salida estandar al nuevo stream
		System.setOut(ps);   
	}
		
	//Volver a restaurar la salida standard a la consola
	private String Stream2StandardIO(){
		System.out.flush();
		System.setOut(old); //Reestablecemos la salida standard
		return (baos.toString());
	}
		
	
	//Lee la solución de un fichero y la devuelve en un StringBuilder	
	private String readSolutionFromFile(String file) {
			Scanner sc=null;
			try {
					sc = new Scanner(new File(file));
			} catch (FileNotFoundException e) {
					System.out.println("File "+file+" not found");
					fail("Fichero "+file+" no encontrado");
			}
			StringBuilder sb = new StringBuilder();
			while (sc.hasNext()) 
				sb.append(sc.nextLine()+"\n");			
			sc.close();
			return (sb.toString());
		}
	
}
