package com.fdmgroup.ElevatorProject;

import java.io.Serializable;

/**
 * This object simulates requests for the Elevator. It only has two states, going up or going down.
 */

@SuppressWarnings("serial")
public class Person implements Serializable{
	private final int srcFloor;
	private final int destFloor;
	final Direction direction;
	
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

		if ((srcFloor - destFloor) < 0) {
			direction = Direction.UP;
		}
		else {
			direction = Direction.DOWN;
		}
	}
	
	/**
	 * @return the source floor of the person's journey
	 */
	public int getSrcFloor() {
		return srcFloor;
	}
	
	/**
	 * @return the destination floor of the person's journey
	 */
	public int getDestFloor() {
		return destFloor;
	}
}
