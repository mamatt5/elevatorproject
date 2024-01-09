package com.fdmgroup.ElevatorProject;

import java.io.Serializable;

/**
 * This object simulates requests for the Elevator. It only has two states, going up or going down.
 */

@SuppressWarnings("serial")
public class Person implements Serializable{
	private int srcFloor;
	private int destFloor;
	private final boolean goingUp;
	
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
		this.goingUp = (srcFloor - destFloor) < 0;
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
	
	
	// fixme: methods unused - not called anywhere
//	public void setSrcFloor(int srcFloor) {
//		this.srcFloor = srcFloor;
//	}
//
//	public void setDestFloor(int destFloor) {
//		this.destFloor = destFloor;
//	}
	
	/**
	 * @return True if the person is going up, false if going down.
	 */
	public boolean isGoingUp() {
		return goingUp;
	}

}
