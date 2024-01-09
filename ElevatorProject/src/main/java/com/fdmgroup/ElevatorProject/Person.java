package com.fdmgroup.ElevatorProject;

import java.io.Serializable;

/**
 * This object simulates requests for the Elevator. It only has two states, going up or going down.
 */

@SuppressWarnings("serial")
public class Person implements Serializable{
	private int srcFloor;
	private int destFloor;
	private boolean goingUp;
	
	// Note that the direction of the Person is derived from the srcFloor and destFloor attribute.
	public Person(int srcFloor, int destFloor) {
		super();
		this.srcFloor = srcFloor;
		this.destFloor = destFloor;
		this.goingUp = (srcFloor - destFloor) < 0 ? true:false;
	}

	public int getSrcFloor() {
		return srcFloor;
	}

	public void setSrcFloor(int srcFloor) {
		this.srcFloor = srcFloor;
	}

	public int getDestFloor() {
		return destFloor;
	}

	public void setDestFloor(int destFloor) {
		this.destFloor = destFloor;
	}

	public boolean isGoingUp() {
		return goingUp;
	}

}
