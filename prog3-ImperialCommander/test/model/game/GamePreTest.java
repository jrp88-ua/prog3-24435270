package model.game;

import static org.junit.Assert.*;

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

import model.Coordinate;
import model.Fighter;
import model.RandomNumber;
import model.Side;
import model.exceptions.FighterAlreadyInBoardException;
import model.exceptions.FighterNotInBoardException;
import model.exceptions.InvalidSizeException;
import model.exceptions.OutOfBoundsException;
import model.game.exceptions.WrongFighterIdException;

public class GamePreTest {
	PlayerFile plfRebel, plfImperial;
	PlayerRandom plrRebel, plrImperial;
	Reader stringReader;
	Game game;
	ByteArrayOutputStream baos;
	PrintStream ps, old;
	
	@Before
	public void setUp() throws Exception {
		 
		String inputImp = "2/TIEInterceptor\nlaunch 1 1\nlaunch 2 2\npatrol 2\nexit\n";
		stringReader = new StringReader(inputImp);
		BufferedReader br = new BufferedReader(stringReader);
		plfImperial = new PlayerFile(Side.IMPERIAL, br);
		
		String inputReb = "2/YWing\nlaunch 1 2\nlaunch 2 3\nexit\n";
		stringReader = new StringReader(inputReb);
		br = new BufferedReader(stringReader);	
		plfRebel = new PlayerFile(Side.REBEL, br);	
		
        game = new Game(plfImperial,plfRebel);
        RandomNumber.resetRandomCounter();
        Fighter.resetNextId();
	}

	/* Comprueba solo que el tamaño del tablero es 10 */
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
	

	/* Game con ships de los Players vacíos.
	 * Se crean players con naves sin cazas, para ello se crea un string con tipos de cazas erróneos. 
	 * Se comprueba que el que gana es el player REBEL y que la salida de play() coincide con el resultado
	 * solución que está en el fichero "files/testPlayEmptyShips.out"
	 */
	@Test
	public void testPlayEmptyShips() {
		String inputImp = "2/TIEInterceptores\nlaunch 1 1";
		stringReader = new StringReader(inputImp);
		BufferedReader br = new BufferedReader(stringReader);
		plfImperial = new PlayerFile(Side.IMPERIAL, br);
		
		String inputReb = "2/ZWing\nlaunch 1 2";
		stringReader = new StringReader(inputReb);
		br = new BufferedReader(stringReader);
		plfRebel = new PlayerFile(Side.REBEL, br);
		game = new Game(plfImperial,plfRebel);
		
		standardIO2Stream(); //Cambia salida standard a un Stream
		Side winner = game.play();
		String sout = Stream2StandardIO(); //Cambia salida de Stream a la consola
		assertEquals(Side.REBEL, winner);
		String solution = readSolutionFromFile("files/testPlayEmptyShips.out");
		compareLines(solution, sout, false);
	}
	
	/* Game con la nave REBEL inicialmente vacía.
	 * Crea el player REBEL con su nave vacía y la IMPERIAL con cazas. Comprueba que 
	 * el que gana es IMPERIAL y que la salida de play coincide con el resultado solución que
	 * está en el fichero "files/testPlayEmptyRebelShip.out"
	 */
	//TODO
	@Test
	public void testPlayEmptyRebelShip() {
		String inputImp = "1/TIEInterceptor\nlaunch 1 1";
		stringReader = new StringReader(inputImp);
		BufferedReader br = new BufferedReader(stringReader);
		plfImperial = new PlayerFile(Side.IMPERIAL, br);
		
		fail("Termina el test");
	}
	
	/* Game del MainP4min.
	 * Se redireciona la salida estandar a un stream. Se llama a play que reproduce
	 * la misma partida del MainP4min. La iniciación de los players y los datos de entrada
	 *  están en el setUp() */
	@Test
	public void testPlayMain1() {
		standardIO2Stream();
		Side winner = game.play();
		
		String sout = Stream2StandardIO(); //Cambia salida standard a un Stream
		assertEquals(Side.IMPERIAL, winner);
		String solution = readSolutionFromFile("files/testPlayMain1.out"); //Cambia salida de Stream a la consola
		compareLines(solution, sout, false);
	}
	
	/* Game del MainP4.
	 * Crea los players Imperial y Rebel igual que en el MainP4.
	 * Redireciona la salida standard a un stream y llama a play para que reproduzca
	 * la misma partida del MainP4.
	 * Coteja tu salida con la del fichero solución "files/testPlayMain2.out"
	 */
	//TODO
	@Test
	public void testPlayMain2() {
		fail("Realiza el test del MainP4");
	}
	
	/*Test como MainP4min, IMPERIAL hace exit 
	 */
	@Test
	public void testPlayMainImpExit() {
		String inputImp = "2/TIEInterceptor\nlaunch 1 1\nlaunch 2 2\nexit\n";
		stringReader = new StringReader(inputImp);
		BufferedReader br = new BufferedReader(stringReader);
		plfImperial = new PlayerFile(Side.IMPERIAL, br);

		String inputReb = "2/YWing\nlaunch 1 2\nlaunch 2 3\nexit\n";
		stringReader = new StringReader(inputReb);
		br = new BufferedReader(stringReader);
		plfRebel = new PlayerFile(Side.REBEL, br);
		game = new Game(plfImperial,plfRebel);
		standardIO2Stream();
		Side winner = game.play();
		String sout = Stream2StandardIO();
		assertEquals(Side.REBEL, winner);
		String solution = readSolutionFromFile("files/testPlayMainImpExit.out");
		compareLines(solution, sout, false);
	}


	/***************************
	 * METODOS DE APOYO
	 ***************************/
	/* Compara dos String línea a línea.
	 * Parámetros: la cadena esperada, la cadena resultado y true si queremos que considere los espacios 
	 * interiores de la cadena o false en caso contrario. 
	 */
		private void  compareLines(String expected, String result, boolean spaces) {
			String exp[]=expected.split("\n");
			String res[]=result.split("\n");
			boolean iguales = true;
			if (exp.length!=res.length) 
				fail("Cadena esperada de tamaño ("+exp.length+") distinto a la resultante ("+res.length+")");
			for (int i=0; i<exp.length && iguales; i++) {
				if (! exp[i].contains("ERROR:")) {
					if (spaces)
						assertEquals("linea "+i, exp[i].trim(),res[i].trim());
					else
						assertEquals("linea "+i, exp[i].replace(" ",""), res[i].replace(" ","")); 
				} 
				else if (! res[i].contains("ERROR:"))
						fail("Error: el resultado esperado debía contener en la línea "+i+" la cadena 'ERROR:'");
			}
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
		
	//Volver a restaurar la salida standard a la consola devolviendo lo recogido en el stream
	private String Stream2StandardIO(){
		System.out.flush();
		System.setOut(old); //Reestablecemos la salida standard
		return (baos.toString());
	}
		
	
	//Lee la solución de un fichero y la devuelve en un String	
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
