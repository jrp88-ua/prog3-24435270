package mains;

import model.Side;
import model.game.Game;
import model.game.PlayerRandom;

public class MainP4 {

	public static void main(String[] args) {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  // lee de la consola
		
		
		// partida con dos jugadores Random
		
		PlayerRandom plimperial = new PlayerRandom(Side.IMPERIAL,3);
		//PlayerFile plimperial = new PlayerFile(Side.IMPERIAL, br);

		PlayerRandom plrebel = new PlayerRandom(Side.REBEL,3);
		//PlayerFile plrebel = new PlayerFile(Side.REBEL, br);
		
		Game g = new Game(plimperial,plrebel);

		Side winner = g.play();
		System.out.println("And the winner is "+winner);
	}

}