package model;

/**
 * Class that represents a fighter
 * 
 * @author Javier Rodriguez Perez - 24435270R
 *
 */
public class Fighter {

	static final int DEF_VALOCITY = 100;
	static final int DEF_ATTACK = 80;
	static final int DEF_SHIELD = 80;

	/**
	 * id counter
	 */
	private static int nextId = 1;

	/**
	 * type of fighter
	 */
	private String type;
	/**
	 * velocity
	 */
	private int velocity;
	/**
	 * attack
	 */
	private int attack;
	/**
	 * shield (life)
	 */
	private int shield;
	/**
	 * id
	 */
	private int id;
	/**
	 * mother ship
	 */
	private Ship motherShip;
	/**
	 * position on the board
	 */
	private Coordinate position;

	/**
	 * Resets the id counter
	 */
	public static void resetNextId() {
		nextId = 1;
	}

	/**
	 * Creates a new fighter
	 * 
	 * @param type   The fighter type
	 * @param mother The fighter mother ship
	 */
	Fighter(String type, Ship mother) {
		this.type = type;
		this.motherShip = mother;
		this.velocity = DEF_VALOCITY;
		this.attack = DEF_ATTACK;
		this.shield = DEF_SHIELD;
		this.id = nextId++;
		this.position = null;
	}

	/**
	 * Creates a copy object
	 * 
	 * @param f The fighter to copy
	 */
	public Fighter(Fighter f) {
		this.type = f.type;
		this.velocity = f.velocity;
		this.attack = f.attack;
		this.shield = f.shield;
		this.id = f.id;
		this.motherShip = f.motherShip;
		this.position = f.position;
	}

	/**
	 * Computes the damage to deal to the given enemy
	 * 
	 * @param n     A random number
	 * @param enemy The enemy
	 * @return The damage to deal
	 */
	public int getDamage(int n, Fighter enemy) {
		return (n * getAttack()) / 300;
	}

	/**
	 * Fights to the death the given enemy
	 * 
	 * @param enemy The enemy
	 * @return 0 if both fighters are destroyed, 1 if this fighter wins, -1 if the
	 *         enemy wins
	 */
	public int fight(Fighter enemy) {
		if (isDestroyed() || enemy.isDestroyed())
			return 0;
		int umbral = (100 * getVelocity()) / (getVelocity() + enemy.getVelocity());
		do {
			int n = RandomNumber.newRandomNumber(100);
			if (umbral <= n) {
				enemy.addShield(-getDamage(n, enemy));
			} else {
				addShield(-enemy.getDamage(100 - n, this));
			}
		} while (!isDestroyed() && !enemy.isDestroyed());
		return isDestroyed() ? -1 : 1;
	}

	/**
	 * Computes the hashCode of this fighter
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/**
	 * Compares this fighter to the given one
	 * 
	 * @param obj The other fighter
	 * 
	 * @return true if both fighters have the same id
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Fighter)) {
			return false;
		}
		Fighter other = (Fighter) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	/**
	 * @return A string representation of this fighter
	 */
	@Override
	public String toString() {
		return String.format("(%s %d %s %s {%d,%d,%d})", getType(), getId(), getSide().toString(), getPosition(),
				getVelocity(), getAttack(), getShield());
	}

	/**
	 * @return The side of the mother ship of the fighter
	 */
	public Side getSide() {
		return getMotherShip().getSide();
	}

	/**
	 * Checks if the fighter is destroyed
	 * 
	 * @return true if the shield is 0 or lower
	 */
	public boolean isDestroyed() {
		return getShield() <= 0;
	}

	/**
	 * @return the position
	 */
	public Coordinate getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Coordinate position) {
		this.position = position;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the velocity
	 */
	public int getVelocity() {
		return velocity;
	}

	/**
	 * Adds velocity to the fighter
	 * 
	 * @param v the velocity to add
	 */
	public void addVelocity(int v) {
		this.velocity += v;
	}

	/**
	 * @return the attack
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * Adds attack to the fighter
	 * 
	 * @param attack the attack to add
	 */
	public void addAttack(int attack) {
		this.attack += attack;
	}

	/**
	 * @return the shield
	 */
	public int getShield() {
		return shield;
	}

	/**
	 * Adds shield to the fighter
	 * 
	 * @param shield the shield to add
	 */
	public void addShield(int shield) {
		this.shield += shield;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the motherShip
	 */
	public Ship getMotherShip() {
		return motherShip;
	}

}