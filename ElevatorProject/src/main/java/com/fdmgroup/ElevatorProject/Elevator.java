package com.fdmgroup.ElevatorProject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("serial")
public class Elevator implements Runnable, Serializable {
	private boolean goingUp = false;
	private boolean isIdle = true;
	private int currentFloor = 0;
	private ArrayList<Person> peopleInside = new ArrayList<>();
	private static int nextID = 0;
	private final String ELEVATORID;

	private static final Logger LOGGER = LogManager.getLogger(Elevator.class);

	public Elevator() {
		this.ELEVATORID = "Elevator" +nextID++;
	}

	// Getters and Setters
	
	public String getElevatorID() {
		return ELEVATORID;
	}
	
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

	public String getELEVATORID() {
		return this.ELEVATORID;
	}

	// Elevator methods

	public void doorsOpenClose() throws InterruptedException {
		Thread.sleep(100);
	}

	public void movesFloor() throws InterruptedException {
		Thread.sleep(100);
	}

	public void GoToFloor(int floor) throws InterruptedException {
		int srcFloor = this.currentFloor;
		this.isIdle = false;

		if ( currentFloor < floor ) {
			this.goingUp = true;

			for ( int i = srcFloor ; i < floor ; i++ ) {
				this.currentFloor++;
				movesFloor();
			}

		} else if ( currentFloor > floor ) {
			this.goingUp = false;

			for ( int i = srcFloor ; i > floor ; i-- ) {
				this.currentFloor--;
				movesFloor();
			}
		}

	}

	public void LoadPerson(Person person) throws InterruptedException {
		GoToFloor(person.getSrcFloor());
		LOGGER.info("Person entered " + this.ELEVATORID + " on floor " + this.getCurrentFloor() + " to get to floor " + person.getDestFloor());
		this.peopleInside.add(person);
		doorsOpenClose();
	}
	
	// Improve this logic for sorting people. It's weird.
	public void sortPeopleInside() {
		if (this.goingUp) {
			Collections.sort(peopleInside);
			
		} else if (!this.goingUp) {
			Collections.sort(peopleInside, Collections.reverseOrder());
			
		}
	}

	// Might have to change peopleInside list to something sorted. otherwise it will go up and down, test for now
	public void unloadPeople() {
		while (!peopleInside.isEmpty()) {
			sortPeopleInside();
			isIdle = false;
			Person person = peopleInside.get(0);
			try {
				LOGGER.info(this.ELEVATORID + " moving to floor " + person.getDestFloor());
				this.GoToFloor(person.getDestFloor());
				LOGGER.info(this.ELEVATORID + " arrived at floor " + this.getCurrentFloor());
				doorsOpenClose();
				peopleInside.remove(0);
				LOGGER.info("Person unloaded from " + this.ELEVATORID + " at floor " + this.getCurrentFloor());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.isIdle = true;
	}

	@Override
	public void run() {
		unloadPeople();
	}

}
