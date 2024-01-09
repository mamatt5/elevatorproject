package com.fdmgroup.ElevatorProject;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Person implements Serializable , Comparable<Person>{
	private int srcFloor;
	private int destFloor;
	private boolean goingUp;
	
	/**
	 * Constructor for creating a Person object with source and destination floors.
	 *
	 * @param srcFloor  The source floor of the person.
	 * @param destFloor The destination floor of the person.
	 */
	public Person(int srcFloor, int destFloor) {
		super();
		this.srcFloor = srcFloor;
		this.destFloor = destFloor;
		this.goingUp = (srcFloor - destFloor) < 0 ? true:false;
	}

	public int getSrcFloor() {
		return srcFloor;
	}

	public int getDestFloor() {
		return destFloor;
	}
	
	/**
	 * Checks if the person is going up (true) or down (false).
	 *
	 * @return True if the person is going up, false if going down.
	 */
	public boolean isGoingUp() {
		return goingUp;
	}
	
	/**
	 * Compares the destination floors of two Person objects.
	 *
	 * @param other The other Person object to compare to.
	 * @return A negative value if calling person's destination floor is less than the other;
	 *         Zero if both destination floors are equal;
	 *         A positive value if calling person's destination floor is greater than the other.
	 */
	@Override
	public int compareTo(Person other) {
		return Integer.compare(this.destFloor, other.destFloor);
	}

}
