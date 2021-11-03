package mains;

import model.Board;
import model.Coordinate;
import model.Fighter;
import model.RandomNumber;
import model.Ship;
import model.Side;
import model.exceptions.FighterAlreadyInBoardException;
import model.exceptions.FighterNotInBoardException;
import model.exceptions.InvalidSizeException;
import model.exceptions.NoFighterAvailableException;
import model.exceptions.OutOfBoundsException;

public class MainP3 {

	public static void main(String[] args) {
		Board b;
		try {
			b = new Board(10);
			Ship imperialCommander = new Ship("Imperial Commander 1", Side.IMPERIAL);
			Ship rebel = new Ship("Corellian Cruiser", Side.REBEL);

			imperialCommander.addFighters("8/TIEFighter:8/TIEBomber:5/TIEInterceptor:2/TIEFighter");
			rebel.addFighters("10/XWing:7/YWing:4/AWing:1/YWing");

			System.out.println("Random numbers=" + RandomNumber.getRandomNumberList());
			Fighter fi = imperialCommander.getFirstAvailableFighter("TIEFighter");
			try {
				b.launch(new Coordinate(1, 1), fi);
			} catch (OutOfBoundsException e) {
				e.printStackTrace(); // error de ejecuciÃ³n
			} catch (FighterAlreadyInBoardException e) {
				e.printStackTrace(); // error de ejecuciÃ³n
			}

			Fighter fr = rebel.getFirstAvailableFighter("");
			try {
				b.launch(new Coordinate(0, 1), fr);
			} catch (OutOfBoundsException e) {
				e.printStackTrace();
			} catch (FighterAlreadyInBoardException e) {
				e.printStackTrace();
			}

			System.out.println(imperialCommander + "\n" + imperialCommander.showFleet());
			System.out.println(rebel + "\n" + rebel.showFleet());
			System.out.println("Random numbers=" + RandomNumber.getRandomNumberList());

			System.out.println("Patrol " + fr);
			b.patrol(fr);

			System.out.println(imperialCommander + "\n" + imperialCommander.showFleet());
			System.out.println(rebel + "\n" + rebel.showFleet());
			System.out.println("Random numbers=" + RandomNumber.getRandomNumberList());
		} catch (InvalidSizeException | NoFighterAvailableException | FighterNotInBoardException e1) {
			e1.printStackTrace(); // error de ejecuciÃ³n
		}
	}

}