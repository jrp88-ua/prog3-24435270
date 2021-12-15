package model.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import model.Board;
import model.Coordinate;
import model.Fighter;
import model.RandomNumber;
import model.Ship;
import model.Side;
import model.exceptions.FighterAlreadyInBoardException;
import model.exceptions.InvalidSizeException;
import model.exceptions.OutOfBoundsException;

public class GameBoardTest {

	GameBoard gameBoard;
	GameShip gameShip;
	final String kEXAMPLEBOARD = "  0123456789\n" + 
								 "  ----------\n" + 
								 "0|   A    AX\n" + 
								 "1|          \n" + 
								 "2|    X     \n" + 
								 "3|       A  \n" + 
								 "4|          \n" + 
								 "5|   A      \n" + 
								 "6| i Y      \n" + 
								 "7|    Y b X \n" + 
								 "8|          \n" + 
								 "9|          ";
	final String kEMPTYBOARD = "   0123456789\n" + 
							   "   ----------\n" + 
							   " 0|          \n" + 
							   " 1|          \n" + 
							   " 2|          \n" + 
							   " 3|          \n" + 
							   " 4|          \n" + 
							   " 5|          \n" + 
							   " 6|          \n" + 
							   " 7|          \n" + 
							   " 8|          \n" + 
							   " 9|          ";
	final String kNOTEMPTYBOARD = "  0123456789\n" + 
								  "  ----------\n" + 
								  "0|          \n" + 
								  "1|          \n" + 
								  "2|  b   ii  \n" + 
								  "3|       AiX\n" + 
								  "4|  b A     \n" + 
								  "5|          \n" + 
								  "6|    X     \n" + 
								  "7|          \n" + 
								  "8|     A  X \n" + 
								  "9|i   i b   ";
								  
	@Before
	public void setUp() throws Exception {
		gameBoard = new GameBoard(10);
		gameShip = new GameShip("Tydirium", Side.REBEL);
		Fighter.resetNextId();
	}

	/* Se comprueba que el constructor copia de GameBoard es correcto y que
	   GameBoard es una clase derivada de Board.
	 */
	@Test
	public void testGameBoard() {
		assertEquals (10, gameBoard.getSize());
		assertTrue(gameBoard instanceof Board );
	}

	/* Se comprueba que en un tablero vacío el número de Fighters de ambos bandos
	 * es 0
	 */
	@Test
	public void testNumFightersInEmptyBoard() {
		gameShip.addFighters("10/AWing:35/XWing:5/YWing");
		assertEquals(0,gameBoard.numFighters(Side.IMPERIAL));
		assertEquals(0,gameBoard.numFighters(Side.REBEL));
	}

	/* Se añaden a un GameShip varios cazas rebeldes. Todos ellos se posicionan sobre un 
	 * tablero. Se comprueba que numFighters para los rebeldes coincide con el número de
	 * cazas del GameShip.
	 */
	@Test
	public void testNumFighters1() throws FighterAlreadyInBoardException, OutOfBoundsException {
		gameShip.addFighters("7/AWing:6/XWing:2/YWing");
		int i=0;
		int j=0;
		for (Fighter f : gameShip.getFleetTest()) {
			if (i==gameBoard.getSize()) {
				i=0; j+=3;
			}
			gameBoard.launch(new Coordinate(i,j), f);
			i++;
			
		}
		assertEquals(15,gameBoard.numFighters(Side.REBEL));
	}
	
	/* Se añaden cazas rebeldes a un GameShip rebelde y cazas imperiales a un GameShip imperial.
	 * Se posiciona una parte de los cazas rebeldes e imperiales en un tablero. 
	 * Se comprueba que el numFighers para los cazas rebeldes e imperiales coinciden respectivamente
	 * con los que están en el tablero.
	 */
	@Test
	public void testNumFighters2() throws FighterAlreadyInBoardException, OutOfBoundsException {
		gameShip.addFighters("7/AWing:6/XWing:2/YWing");
		GameShip gameImperialShip = new GameShip("Lanzadera T-4a", Side.IMPERIAL);
		gameImperialShip.addFighters("3/TIEBomber:9/TIEInterceptor:2/TIEFighter");
		addFightersOnBoard(gameShip, gameImperialShip);
		
		assertEquals(8,gameBoard.numFighters(Side.IMPERIAL));
		assertEquals(6,gameBoard.numFighters(Side.REBEL));
	}
	
	/* Se prueba toString para un tablero de 15x15 vacío
	 * 
	 */
	@Test
	public void testToStringAnEmptyBoard()  {
		
		compareLines(kEMPTYBOARD, gameBoard.toString());
		System.out.println(gameBoard.toString());
	}
	
	/* Se añaden cazas imperiales y rebeledes a un tablero de 10x10 y se comprueba que la salida es
	 * correcta.
	 */
	@Test
	public void testToString() throws FighterAlreadyInBoardException, OutOfBoundsException {
		
		gameShip.addFighters("7/AWing:6/XWing:2/YWing");
		GameShip gameImperialShip = new GameShip("Lanzadera T-4a", Side.IMPERIAL);
		gameImperialShip.addFighters("3/TIEBomber:9/TIEInterceptor:2/TIEFighter");
		addFightersOnBoard(gameShip, gameImperialShip);
		compareLines(kNOTEMPTYBOARD, gameBoard.toString());
		System.out.println(gameBoard.toString());
	}
	
	/* Se añaden los cazas del ejemplo del enunciado en un tablero 10x10. Se comprueba que la salida
	 * es correcta.
	 */
	@Test
	public void testToStringExample() throws FighterAlreadyInBoardException, OutOfBoundsException, InvalidSizeException {
		
		gameShip.addFighters("7/AWing:6/XWing:2/YWing");
		GameShip gameImperialShip = new GameShip("Lanzadera T-4a", Side.IMPERIAL);
		gameImperialShip.addFighters("3/TIEBomber:9/TIEInterceptor:2/TIEFighter");
		addFightersOnBoardExample(gameShip, gameImperialShip);
		compareLines(kEXAMPLEBOARD, gameBoard.toString());
		System.out.println(gameBoard.toString());
	}

	/*************************
	 * FUNCIONES AUXILIARES
	 * 
	 *************************/
	
	void addFightersOnBoard(Ship rebelShip, Ship imperialShip) throws FighterAlreadyInBoardException, OutOfBoundsException  {
		RandomNumber.resetRandomCounter();
		Set<Coordinate> s = new HashSet<Coordinate>();
		Coordinate c;	
		int k=2;
		for (int i=0; i<6; i++) {
			c = new Coordinate(RandomNumber.newRandomNumber(10), RandomNumber.newRandomNumber(10));
			if (! s.contains(c)) {
				gameBoard.launch(c, rebelShip.getFleetTest().get(k));
				s.add(c);
				k+=2;
			}
		}
		k=0;
		for (int i=0; i<9; i++) {
			c = new Coordinate(RandomNumber.newRandomNumber(10), RandomNumber.newRandomNumber(10));
			if (! s.contains(c)) {
				gameBoard.launch(c, imperialShip.getFleetTest().get(k));
				s.add(c);
				k+=1;
			}
			
		}
	}
	void addFightersOnBoardExample(Ship rebelShip, Ship imperialShip) throws FighterAlreadyInBoardException, OutOfBoundsException  {
		//Añade cazas rebeldes
		Fighter f = rebelShip.getFleetTest().get(0);
		gameBoard.launch(new Coordinate(3,0), f);
		f = rebelShip.getFleetTest().get(1);
		gameBoard.launch(new Coordinate(8,0), f);
		f = rebelShip.getFleetTest().get(7);
		gameBoard.launch(new Coordinate(9,0), f);
		f = rebelShip.getFleetTest().get(8);
		gameBoard.launch(new Coordinate(4,2), f);
		f = rebelShip.getFleetTest().get(2);
		gameBoard.launch(new Coordinate(7,3), f);
		f = rebelShip.getFleetTest().get(3);
		gameBoard.launch(new Coordinate(3,5), f);
		f = rebelShip.getFleetTest().get(13);
		gameBoard.launch(new Coordinate(3,6), f);
		f = rebelShip.getFleetTest().get(14);
		gameBoard.launch(new Coordinate(4,7), f);
		f = rebelShip.getFleetTest().get(9);
		gameBoard.launch(new Coordinate(8,7), f);
		//Añade cazas imperiales
		f = imperialShip.getFleetTest().get(3);
		gameBoard.launch(new Coordinate(1,6), f);
		f = imperialShip.getFleetTest().get(2);
		gameBoard.launch(new Coordinate(6,7), f);
	}
	
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
}
