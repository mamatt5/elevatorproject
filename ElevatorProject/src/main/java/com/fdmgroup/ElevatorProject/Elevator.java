package com.fdmgroup.ElevatorProject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

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
	
	// fixme: change to 1000 before submission
	private static final int SLEEP_TIME = 100;

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
	
	
	// fixme: method unused - not called anywhere
//	public void setGoingUp(boolean goingUp) {
//		this.goingUp = goingUp;
//	}

	
	public boolean isIdle() {
		return isIdle;
	}
	
	// fixme: method unused - not called anywhere
//	public void setIdle(boolean isIdle) {
//		this.isIdle = isIdle;
//	}

	public int getCurrentFloor() {
		return currentFloor;
	}
	
	// fixme: method unused - not called anywhere
//	public void setCurrentFloor(int currentFloor) {
//		this.currentFloor = currentFloor;
//	}

	public ArrayList<Person> getPeopleInside() {
		return peopleInside;
	}

	// Elevator methods

	public void doorsOpenClose() throws InterruptedException {
		Thread.sleep(SLEEP_TIME);
	}

	public void movesFloor() throws InterruptedException {
		Thread.sleep(SLEEP_TIME);
	}
	
	/**
	 * Moves the elevator to the specified floor.
	 *
	 * @param floor The destination floor the elevator will move to.
	 * @throws InterruptedException If interrupted during the movement process.
	 */
	public void goToFloor(int floor) throws InterruptedException {
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
	
	/**
	 * Loads a person into the elevator, moving the elevator to the person's source floor.
	 *
	 * @param person The Person object to be loaded into the elevator.
	 * @throws InterruptedException If interrupted during the loading process.
	 */
	public void loadPerson(Person person) throws InterruptedException {
		goToFloor(person.getSrcFloor());
		LOGGER.info("Person entered " + this.ELEVATORID + " on floor " + this.getCurrentFloor() + " to get to floor " + person.getDestFloor());
		this.peopleInside.add(person);
		doorsOpenClose();
	}
	
	/**
	 * Sorts the people inside the elevator based on their destination floors.
	 * If the elevator is going up, sorts in ascending order;
	 *      else, sorts in descending order.
	 */
	// todo: Improve this logic for sorting people. It's weird.
	public void sortPeopleInside() {
		if (this.goingUp) {
			Collections.sort(peopleInside);
			
		}
		else {
			Collections.sort(peopleInside, Collections.reverseOrder());
			
		}
	}
	
	/**
	 * Unloads people from the elevator sequentially, in the order of their destination floors.
	 */
	// todo: Might have to change peopleInside list to something sorted. otherwise it will go up and down, test for now
	public void unloadPeople() {
		while (!peopleInside.isEmpty()) {
			sortPeopleInside();
			isIdle = false;
			Person person = peopleInside.get(0);
			try {
				LOGGER.info(this.ELEVATORID + " moving to floor " + person.getDestFloor());
				this.goToFloor(person.getDestFloor());
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
