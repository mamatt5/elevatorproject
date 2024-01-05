package com.fdmgroup.ElevatorProject;

import java.util.ArrayList;

public class Elevator {
	private boolean goingUp = false;
	private boolean isIdle = true;
	private int currentFloor = 0;
	private ArrayList<Person> peopleInside = new ArrayList<>();
	
	
	// Getters and Setters
	
	public boolean isGoingUp() {
		return goingUp;
	}

	public void setGoingUp(boolean goingUp) {
		this.goingUp = goingUp;
	}

	public boolean isIdle() {
		return isIdle;
	}

	public void setIdle(boolean isIdle) {
		this.isIdle = isIdle;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}
	
	public ArrayList<Person> getPeopleInside() {
		return peopleInside;
	}

	
	
	
	// Elevator methods
	
	public void goToFloor(int floor) {
		this.isIdle = false;
		
		if ( currentFloor < floor ) {
			this.goingUp = true;
		}
		this.currentFloor = floor;
	}
	
	public void loadPerson(Person person) {
		this.peopleInside.add(person);
	}
	
}
