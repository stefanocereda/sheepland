package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

//A sheep
public class Sheep extends Animal {
	// the age of the sheep, used for advanced rules
	private int age;
	// the type of the sheep, used for advanced rules
	private TypeOfSheep typeOfSheep;

	// complete constructor for advanced rules
	public Sheep(int age, TypeOfSheep type) {
		this.age = age;
		typeOfSheep = type;
	}

	// constructor for basic rules
	public Sheep() {
		this(0, TypeOfSheep.NORMALSHEEP);
	}

	// get the sheep's age
	public int getAge() {
		return age;
	}

	// increment the age of the sheep
	public void ageIcrement() {
		age++;
	}

	// get the type of the sheep
	public TypeOfSheep getTypeOfSheep() {
		return typeOfSheep;
	}

	// random modify the type of sheep, used for advanced rules
	public void setNewRandomTypeOfSheep() {
		typeOfSheep = TypeOfSheep.getRandomAndvancedTypeOfSheep();
	}
}
