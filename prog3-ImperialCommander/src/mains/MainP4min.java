package mains;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

import model.Side;
import model.game.Game;
import model.game.PlayerFile;

public class MainP4min {

	public static void main(String[] args) {
		String inputImp = "2/TIEInterceptor\nlaunch 1 1\nlaunch 2 2\npatrol 2\nexit\n";
		Reader stringReaderImp = new StringReader(inputImp);
		BufferedReader brI = new BufferedReader(stringReaderImp);
		PlayerFile plimperial = new PlayerFile(Side.IMPERIAL, brI);

		String inputReb = "2/YWing\nlaunch 1 2\nlaunch 2 3\nexit\n";
		Reader stringReaderReb = new StringReader(inputReb);
		BufferedReader brR = new BufferedReader(stringReaderReb);
		PlayerFile plrebel = new PlayerFile(Side.REBEL, brR);

		Game g = new Game(plimperial, plrebel);

		Side winner = g.play();
		System.out.println("And the winner is " + winner);
	}

}