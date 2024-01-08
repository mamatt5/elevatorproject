package com.fdmgroup.ElevatorProject;

import java.util.ArrayList;

public class Elevator implements Runnable {
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
	
	public void GoToFloor(int floor) throws InterruptedException {
		int srcFloor = this.currentFloor;
		this.isIdle = false;
		
		if ( currentFloor < floor ) {
			this.goingUp = true;
			
			for ( int i = srcFloor ; i < floor ; i++ ) {
				this.currentFloor++;
				Thread.sleep(1000);
			}
			
		} else if ( currentFloor > floor ) {
			this.goingUp = false;
			
			for ( int i = srcFloor ; i > floor ; i-- ) {
				this.currentFloor--;
				Thread.sleep(1000);
			}
		}
		
	}
	
	public void LoadPerson(Person person) throws InterruptedException {
		this.peopleInside.add(person);
		Thread.sleep(1000);
	}

	@Override
	public void run() {
		while ( !Thread.interrupted() ) {
			
			if (!peopleInside.isEmpty()) {
				isIdle = false;
				
				// Since this is modifying the peopleInside list, use for-loop with indices but do not increment i unless person object is not removed.
				for ( int i = 0; i < peopleInside.size() ; ) {
					Person person = peopleInside.get(i);
					try {
						System.out.println("Elevator moving to floor " + person.getDestFloor());
						this.GoToFloor(person.getDestFloor());
						System.out.println("Elevator arrived at floor " + this.getCurrentFloor());
						peopleInside.remove(i);
						System.out.println("Person unloaded at floor " + this.getCurrentFloor());
						
					} catch (InterruptedException e) {
						e.printStackTrace();
						i++;
					}
				}
			} else {
				isIdle = true;
			}
		}
		
	}
	
}
