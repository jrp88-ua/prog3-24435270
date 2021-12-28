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

public class GameNewTest {
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

	/*Test como MainP4min, IMPERIAL juega y pierde
	 */
	@Test
	public void testPlayMainImpLoss() {
		String inputImp = "1/TIEBomber\nimprove 1 10\nlaunch 2 2\npatrol 1\nexit\n";
		plfImperial=makePlayerFile(inputImp,Side.IMPERIAL);
		String inputReb = "2/AWing\nlaunch 1 2\nlaunch 2 3\nexit\n";
		plfRebel = makePlayerFile(inputReb, Side.REBEL);
		game = new Game(plfImperial,plfRebel);
		standardIO2Stream();
		Side winner = game.play();
		String sout = Stream2StandardIO();
		assertEquals(Side.REBEL, winner);
		String solution = readSolutionFromFile("files/testPlayMainImpLoss.out");
		compareLines(solution, sout);
	}

	/*Test como MainP4min, REBEL juega y pierde
	 */
	@Test
	public void testPlayMainRebLoss() {
		String inputImp = "2/TIEInterceptor\nlaunch 1 1\nlaunch 2 2\nexit\n";
		plfImperial=makePlayerFile(inputImp,Side.IMPERIAL);
		String inputReb = "1/YWing\nlaunch 1 2\npatrol 3\nexit\n";
		plfRebel = makePlayerFile(inputReb, Side.REBEL);
		game = new Game(plfImperial,plfRebel);
		standardIO2Stream();
		Side winner = game.play();
		String sout = Stream2StandardIO();
		assertEquals(Side.IMPERIAL, winner);
		String solution = readSolutionFromFile("files/testPlayMainRebLoss.out");
		compareLines(solution, sout);
	}
	
	/*Test como MainP4min, REBEL juega y gana
	 */
	@Test
	public void testPlayMainRebWin() {
		String inputImp = "2/TIEBomber\nimprove 1 10\nlaunch 1 1\nlaunch 2 2\nexit\n";
		plfImperial=makePlayerFile(inputImp,Side.IMPERIAL);
		String inputReb = "2/AWing\nlaunch 1 2\nlaunch 2 3\npatrol 3\nexit\n";
		plfRebel = makePlayerFile(inputReb, Side.REBEL);
		game = new Game(plfImperial,plfRebel);
		standardIO2Stream();
		Side winner = game.play();
		String sout = Stream2StandardIO();
		assertEquals(Side.REBEL, winner);
		String solution = readSolutionFromFile("files/testPlayMainRebWin.out");
		compareLines(solution, sout);
	}
	

	/* Game del MainP4, pero con 4 fighters
	 * 
	 */
	@Test
	public void testPlayMainRandom4() {
		plrImperial = new PlayerRandom(Side.IMPERIAL,4);
		plrRebel = new PlayerRandom(Side.REBEL,4);	
		game = new Game(plrImperial,plrRebel);
	
		standardIO2Stream();	
		Side winner = game.play();
		String sout = Stream2StandardIO();
		
		assertEquals(Side.IMPERIAL, winner);
		System.out.println(sout);
		String solution = readSolutionFromFile("files/testPlayMainRandom4.out");
		compareLines(solution, sout);
	}
	
	/* Game del MainP4, pero con 9 fighters
	 * 
	 */
	@Test
	public void testPlayMainRandom9() {
		plrImperial = new PlayerRandom(Side.IMPERIAL,9);
		plrRebel = new PlayerRandom(Side.REBEL,9);	
		game = new Game(plrImperial,plrRebel);
	
		standardIO2Stream();	
		Side winner = game.play();
		String sout = Stream2StandardIO();
		
		assertEquals(Side.REBEL, winner);
		System.out.println(sout);
		String solution = readSolutionFromFile("files/testPlayMainRandom9.out");
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
