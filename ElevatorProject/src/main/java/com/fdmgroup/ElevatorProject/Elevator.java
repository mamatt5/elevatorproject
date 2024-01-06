package com.fdmgroup.ElevatorProject;

import java.util.ArrayList;

public class Elevator implements Runnable {
	private boolean goingUp = false;
	private boolean isIdle = true;
	private int currentFloor = 0;
	private ArrayList<Person> peopleInside = new ArrayList<>();
	private int minFloor = 0;
	private int maxFloor = 0;
	
	
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
	
	public void GoToFloor(int floor) {
		int srcFloor = this.currentFloor;
		this.isIdle = false;
		
		if ( currentFloor < floor ) {
			this.goingUp = true;
		}
		
		for ( int i = srcFloor ; i < floor ; i++ ) {
			this.currentFloor++;
		}
	}
	
	public void LoadPerson(Person person) {
		this.peopleInside.add(person);
	}

	@Override
	public void run() {
		while ( !Thread.interrupted() ) {
			
			if (!peopleInside.isEmpty()) {
				isIdle = false;
				
				for ( Person person : peopleInside ) {
					this.GoToFloor(person.getDestFloor());
					peopleInside.remove(person);
				}
			} else {
				isIdle = true;
			}
		}
		
	}
	
}
