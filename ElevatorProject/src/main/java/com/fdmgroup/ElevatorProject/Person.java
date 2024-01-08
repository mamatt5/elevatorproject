package com.fdmgroup.ElevatorProject;

import java.io.Serializable;

public class Person implements Serializable{
	private int srcFloor;
	private int destFloor;
	private boolean goingUp;
	
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
