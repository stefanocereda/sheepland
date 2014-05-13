package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

/**
 * A sheep is an animal, for advanced rules it also has gender and age
 * 
 * @author Stefano
 * 
 */
public class Sheep extends Animal {
	/** The age of the sheep (for advanced rules) */
	private int age;
	/** The type (gender) of the sheep (for advanced rules) */
	private TypeOfSheep typeOfSheep;

	/**
	 * Complete constructor
	 * 
	 * @param age
	 *            The starting age of the sheep
	 * @param type
	 *            The starting type (gender) of the sheep
	 */
	public Sheep(int age, TypeOfSheep type) {
		this.age = age;
		typeOfSheep = type;
	}

	/** Default constructor, creates a normal sheep with age=0 */
	public Sheep() {
		this(0, TypeOfSheep.NORMALSHEEP);
	}

	/** Get the age of the sheep */
	public int getAge() {
		return age;
	}

	/**
	 * Increment by 1 the sheep's age, without checking if it has to be modified
	 * to a new type of sheep
	 */
	public void ageIcrement() {
		age++;
	}

	/** Get the type (gender) of the sheep */
	public TypeOfSheep getTypeOfSheep() {
		return typeOfSheep;
	}

	/** Randomly modify the type of sheep to an advanced one (advanced rules) */
	public void setNewRandomAndvancedTypeOfSheep() {
		typeOfSheep = TypeOfSheep.getRandomAndvancedTypeOfSheep();
	}
}
