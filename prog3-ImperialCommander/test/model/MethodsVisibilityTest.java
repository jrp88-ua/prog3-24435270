package model;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class MethodsVisibilityTest {

	@Before
	public void setUp() throws Exception {
		
	}
	
	/*
	 * Estos tests prueban que los métodos públicos que deberían ser públicos lo son. Si aparece algún otro método 
	 * se emite un aviso por si se tratara de un error (no lo es si son métodos privados, evidentemente)
	 * 
	 * No se prueban las subclases de Fighter, son muy sencillas y ya se han probado indirectamente en la práctica 3
	 * 
	 * No se prueban los constructores, ni los atributos (pero se podría hacer de forma similar)
	 */
	
	@Test
	public final void testBoard() throws ClassNotFoundException {
		Class c = Class.forName("model.Board");
		
		HashMap<String,String> methodVisibility = new HashMap<>();
		methodVisibility.put("getSize","public");
		methodVisibility.put("patrol","public");
		methodVisibility.put("getNeighborhood","public");
		methodVisibility.put("removeFighter","public");
		methodVisibility.put("getFighter","public");
		methodVisibility.put("launch","public");
		methodVisibility.put("inside","public");

		
		Method[] methods = c.getDeclaredMethods();
		Pattern pattern = Pattern.compile("[ ][^ (]*[(]");
		for (Method method: methods) {
			String full_name = method.toString();
			
			Matcher m = pattern.matcher(full_name);
			m.find();
            String name = full_name.substring(m.start() + 1, m.end() - 1);
            String sn[] = name.split("\\.");
            String simplename = sn[sn.length-1];
			
			String modifier = Modifier.toString(method.getModifiers());
			
			String vis = methodVisibility.get(simplename); 
			if (vis != null)
				assertEquals(vis,modifier);
			else
				System.out.println("AVISO: método'"+name+"' no incluido en la especificación");
		}
	}
	
	@Test
	public final void testCoordinate() throws ClassNotFoundException {
		Class c = Class.forName("model.Coordinate");
		
		HashMap<String,String> methodVisibility = new HashMap<>();
		methodVisibility.put("add","public");
		methodVisibility.put("add","public");
		methodVisibility.put("equals","public");
		methodVisibility.put("toString","public");
		methodVisibility.put("hashCode","public");
		methodVisibility.put("getNeighborhood","public");
		methodVisibility.put("getX","public");
		methodVisibility.put("getY","public");

		
		Method[] methods = c.getDeclaredMethods();
		Pattern pattern = Pattern.compile("[ ][^ (]*[(]");
		for (Method method: methods) {
			String full_name = method.toString();
			
			Matcher m = pattern.matcher(full_name);
			m.find();
            String name = full_name.substring(m.start() + 1, m.end() - 1);
            String sn[] = name.split("\\.");
            String simplename = sn[sn.length-1];

            if (simplename.contentEquals("compareTo")) continue;   // aparece dos veces por el interface, lo ignoramos
            
			String modifier = Modifier.toString(method.getModifiers());
			
			String vis = methodVisibility.get(simplename); 
			if (vis != null)
				assertEquals(vis,modifier);
			else
				System.out.println("AVISO: método'"+name+"' no incluido en la especificación");
		}
	}
		

	@Test
	public final void testFighter() throws ClassNotFoundException {
		Class c = Class.forName("model.Fighter");
		
		HashMap<String,String> methodVisibility = new HashMap<>();
		methodVisibility.put("equals","public");
		methodVisibility.put("toString","public");
		methodVisibility.put("hashCode","public");
		methodVisibility.put("getId","public");
		methodVisibility.put("isDestroyed","public");
		methodVisibility.put("copy","public abstract");
		methodVisibility.put("getType","public");
		methodVisibility.put("getSymbol","public abstract");
		methodVisibility.put("resetNextId","public static");
		methodVisibility.put("getVelocity","public");
		methodVisibility.put("getAttack","public");
		methodVisibility.put("getShield","public");
		methodVisibility.put("getPosition","public");
		methodVisibility.put("setPosition","public");
		methodVisibility.put("getSide","public");
		methodVisibility.put("getMotherShip","public");
		methodVisibility.put("addAttack","public");
		methodVisibility.put("addVelocity","public");
		methodVisibility.put("addShield","public");
		methodVisibility.put("fight","public");
		methodVisibility.put("getDamage","public");

		
		Method[] methods = c.getDeclaredMethods();
		Pattern pattern = Pattern.compile("[ ][^ (]*[(]");
		for (Method method: methods) {
			String full_name = method.toString();
			
			Matcher m = pattern.matcher(full_name);
			m.find();
            String name = full_name.substring(m.start() + 1, m.end() - 1);
            String sn[] = name.split("\\.");
            String simplename = sn[sn.length-1];
			
			String modifier = Modifier.toString(method.getModifiers());
			
			String vis = methodVisibility.get(simplename); 
			if (vis != null)
				assertEquals(vis,modifier);
			else
				System.out.println("AVISO: método'"+name+"' no incluido en la especificación");
		}
	}

	@Test
	public final void testFighterFactory() throws ClassNotFoundException {
		Class c = Class.forName("model.FighterFactory");
		
		HashMap<String,String> methodVisibility = new HashMap<>();
		methodVisibility.put("createFighter","public static");

		
		Method[] methods = c.getDeclaredMethods();
		Pattern pattern = Pattern.compile("[ ][^ (]*[(]");
		for (Method method: methods) {
			String full_name = method.toString();
			
			Matcher m = pattern.matcher(full_name);
			m.find();
            String name = full_name.substring(m.start() + 1, m.end() - 1);
            String sn[] = name.split("\\.");
            String simplename = sn[sn.length-1];
			
			String modifier = Modifier.toString(method.getModifiers());
			
			String vis = methodVisibility.get(simplename); 
			if (vis != null)
				assertEquals(vis,modifier);
			else
				System.out.println("AVISO: método'"+name+"' no incluido en la especificación");
		}
	}

	@Test
	public final void testShip() throws ClassNotFoundException {
		Class c = Class.forName("model.Ship");
		
		HashMap<String,String> methodVisibility = new HashMap<>();
		methodVisibility.put("toString","public");
		methodVisibility.put("getName","public");
		methodVisibility.put("getSide","public");
		methodVisibility.put("getWins","public");
		methodVisibility.put("getLosses","public");
		methodVisibility.put("getFleetTest","public");
		methodVisibility.put("addFighters","public");
		methodVisibility.put("updateResults","public");
		methodVisibility.put("getFirstAvailableFighter","public");
		methodVisibility.put("purgeFleet","public");
		methodVisibility.put("showFleet","public");
		methodVisibility.put("myFleet","public");

		
		Method[] methods = c.getDeclaredMethods();
		Pattern pattern = Pattern.compile("[ ][^ (]*[(]");
		for (Method method: methods) {
			String full_name = method.toString();
			
			Matcher m = pattern.matcher(full_name);
			m.find();
            String name = full_name.substring(m.start() + 1, m.end() - 1);
            String sn[] = name.split("\\.");
            String simplename = sn[sn.length-1];
			
            if (simplename.startsWith("lambda")) continue;   // alguna solución puede incluir una función lambda
            
			String modifier = Modifier.toString(method.getModifiers());
			
			String vis = methodVisibility.get(simplename); 
			if (vis != null)
				assertEquals(vis,modifier);
			else
				System.out.println("AVISO: método'"+name+"' no incluido en la especificación");
		}
	}

	@Test
	public final void testGame() throws ClassNotFoundException {
		Class c = Class.forName("model.game.Game");
		
		HashMap<String,String> methodVisibility = new HashMap<>();
		methodVisibility.put("play","public");
		methodVisibility.put("getGameBoard","public");

		
		Method[] methods = c.getDeclaredMethods();
		Pattern pattern = Pattern.compile("[ ][^ (]*[(]");
		for (Method method: methods) {
			String full_name = method.toString();
			
			Matcher m = pattern.matcher(full_name);
			m.find();
            String name = full_name.substring(m.start() + 1, m.end() - 1);
            String sn[] = name.split("\\.");
            String simplename = sn[sn.length-1];
			
			String modifier = Modifier.toString(method.getModifiers());
			
			String vis = methodVisibility.get(simplename); 
			if (vis != null)
				assertEquals(vis,modifier);
			else
				System.out.println("AVISO: método'"+name+"' no incluido en la especificación");
		}
	}

	@Test
	public final void testGameShip() throws ClassNotFoundException {
		Class c = Class.forName("model.game.GameShip");
		
		HashMap<String,String> methodVisibility = new HashMap<>();
		methodVisibility.put("patrol","public");
		methodVisibility.put("getFighter","private");
		methodVisibility.put("launch","public");
		methodVisibility.put("isFleetDestroyed","public");
		methodVisibility.put("getFightersId","public");
		methodVisibility.put("improveFighter","public");

		
		Method[] methods = c.getDeclaredMethods();
		Pattern pattern = Pattern.compile("[ ][^ (]*[(]");
		for (Method method: methods) {
			String full_name = method.toString();
			
			Matcher m = pattern.matcher(full_name);
			m.find();
            String name = full_name.substring(m.start() + 1, m.end() - 1);
            String sn[] = name.split("\\.");
            String simplename = sn[sn.length-1];
			
			String modifier = Modifier.toString(method.getModifiers());
			
			String vis = methodVisibility.get(simplename); 
			if (vis != null)
				assertEquals(vis,modifier);
			else
				System.out.println("AVISO: método'"+name+"' no incluido en la especificación");
		}
	}
	
	@Test
	public final void testGameBoard() throws ClassNotFoundException {
		Class c = Class.forName("model.game.GameBoard");
		
		HashMap<String,String> methodVisibility = new HashMap<>();
		methodVisibility.put("toString","public");
		methodVisibility.put("numFighters","public");
		
		Method[] methods = c.getDeclaredMethods();
		Pattern pattern = Pattern.compile("[ ][^ (]*[(]");
		for (Method method: methods) {
			String full_name = method.toString();
			
			Matcher m = pattern.matcher(full_name);
			m.find();
            String name = full_name.substring(m.start() + 1, m.end() - 1);
            String sn[] = name.split("\\.");
            String simplename = sn[sn.length-1];
			
			String modifier = Modifier.toString(method.getModifiers());
			
			String vis = methodVisibility.get(simplename); 
			if (vis != null)
				assertEquals(vis,modifier);
			else
				System.out.println("AVISO: método'"+name+"' no incluido en la especificación");
		}
	}

	@Test
	public final void testPlayerFile() throws ClassNotFoundException {
		Class c = Class.forName("model.game.PlayerFile");
		
		HashMap<String,String> methodVisibility = new HashMap<>();
		methodVisibility.put("purgeFleet","public");
		methodVisibility.put("isFleetDestroyed","public");
		methodVisibility.put("setBoard","public");
		methodVisibility.put("showShip","public");
		methodVisibility.put("initFighters","public");
		methodVisibility.put("nextPlay","public");
		methodVisibility.put("getGameShip","public");
		
		Method[] methods = c.getDeclaredMethods();
		Pattern pattern = Pattern.compile("[ ][^ (]*[(]");
		for (Method method: methods) {
			String full_name = method.toString();
			
			Matcher m = pattern.matcher(full_name);
			m.find();
            String name = full_name.substring(m.start() + 1, m.end() - 1);
            String sn[] = name.split("\\.");
            String simplename = sn[sn.length-1];
			
			String modifier = Modifier.toString(method.getModifiers());
			
			String vis = methodVisibility.get(simplename); 
			if (vis != null)
				assertEquals(vis,modifier);
			else
				System.out.println("AVISO: método'"+name+"' no incluido en la especificación");
		}
	}

	@Test
	public final void testPlayerRandom() throws ClassNotFoundException {
		Class c = Class.forName("model.game.PlayerRandom");
		
		HashMap<String,String> methodVisibility = new HashMap<>();
		methodVisibility.put("purgeFleet","public");
		methodVisibility.put("isFleetDestroyed","public");
		methodVisibility.put("setBoard","public");
		methodVisibility.put("showShip","public");
		methodVisibility.put("initFighters","public");
		methodVisibility.put("nextPlay","public");
		methodVisibility.put("getGameShip","public");
		
		Method[] methods = c.getDeclaredMethods();
		Pattern pattern = Pattern.compile("[ ][^ (]*[(]");
		for (Method method: methods) {
			String full_name = method.toString();
			
			Matcher m = pattern.matcher(full_name);
			m.find();
            String name = full_name.substring(m.start() + 1, m.end() - 1);
            String sn[] = name.split("\\.");
            String simplename = sn[sn.length-1];
			
			String modifier = Modifier.toString(method.getModifiers());
			
			String vis = methodVisibility.get(simplename); 
			if (vis != null)
				assertEquals(vis,modifier);
			else
				System.out.println("AVISO: método'"+name+"' no incluido en la especificación");
		}
	}
}
