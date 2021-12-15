package model;

import java.util.Objects;

import model.exceptions.FighterIsDestroyedException;

/**
 * Class that represents a fighter
 * 
 * @author Javier Rodriguez Perez - 24435270R
 *
 */
public abstract class Fighter {

	/**
	 * Default velocity
	 */
	static final int DEF_VELOCITY = 100;
	/**
	 * Default attack
	 */
	static final int DEF_ATTACK = 80;
	/**
	 * Default shield
	 */
	static final int DEF_SHIELD = 80;

	/**
	 * id counter
	 */
	private static int nextId = 1;

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
	 * Creates a new fighter
	 * 
	 * @param mother The fighter mother ship
	 */
	protected Fighter(Ship mother) {
		Objects.requireNonNull(mother);
		this.motherShip = mother;
		this.velocity = DEF_VELOCITY;
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
	protected Fighter(Fighter f) {
		Objects.requireNonNull(f);
		this.velocity = f.velocity;
		this.attack = f.attack;
		this.shield = f.shield;
		this.id = f.id;
		this.motherShip = f.motherShip;
		this.position = f.position;
	}

	/**
	 * Resets the id counter
	 */
	public static void resetNextId() {
		nextId = 1;
	}

	/**
	 * @return A copy of this fighter
	 */
	public abstract Fighter copy();

	/**
	 * @return A char that represents this fighter
	 */
	public abstract char getSymbol();

	/**
	 * @return the value of this fighter
	 */
	public int getValue() {
		return getVelocity() + getAttack();
	}

	/**
	 * Computes the damage to deal to the given enemy
	 * 
	 * @param n     A random number
	 * @param enemy The enemy
	 * @return The damage to deal
	 */
	public int getDamage(int n, Fighter enemy) {
		Objects.requireNonNull(enemy);
		return (n * getAttack()) / 300;
	}

	/**
	 * Fights to the death the given enemy
	 * 
	 * @param enemy The enemy
	 * @return 1 if this fighter wins, -1 if the enemy wins
	 * @throws FighterIsDestroyedException if this fighter or the enemy is destroyed
	 */
	public int fight(Fighter enemy) throws FighterIsDestroyedException {
		Objects.requireNonNull(enemy);
		if (isDestroyed())
			throw new FighterIsDestroyedException(this);
		if (enemy.isDestroyed())
			throw new FighterIsDestroyedException(enemy);
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
		if (obj == null)
			return false;
		if (!(obj instanceof Fighter)) {
			return false;
		}
		if (getClass() != obj.getClass())
			return false;
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
		return new StringBuilder("(").append(getType()).append(' ').append(getId()).append(' ').append(getSide())
				.append(' ').append(getPosition()).append(" {").append(getVelocity()).append(',').append(getAttack())
				.append(',').append(getShield()).append("})").toString();
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
		return getClass().getSimpleName();
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
		if (velocity < 0)
			velocity = 0;
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
		if (this.attack < 0)
			this.attack = 0;
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
