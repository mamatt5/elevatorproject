package com.fdmgroup.ElevatorProject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("serial")
public class Elevator implements Runnable, Serializable {
	private boolean goingUp = true;
	private boolean isIdle = true;
	
	private int currentFloor = 0;
	private ConcurrentSkipListSet<Integer> floorsToGo = new ConcurrentSkipListSet<>();
	
	private ArrayList<Person> peopleInside = new ArrayList<>();
	private ArrayList<Person> peopleOutsideToLoad = new ArrayList<>();

	private static final Logger LOGGER = LogManager.getLogger(Elevator.class);
	private static int nextID = 0;
	private final String ELEVATORID;

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
	
	public ArrayList<Person> getPeopleOutsideToLoad() {
		return peopleOutsideToLoad;
	}
	
	public ConcurrentSkipListSet<Integer> getFloorsToGo() {
		return floorsToGo;
	}

	public String getELEVATORID() {
		return this.ELEVATORID;
	}

	// Elevator methods

	public void doorsOpenClose() throws InterruptedException {
		Thread.sleep(10);
	}

	public void movesFloor() throws InterruptedException {
		Thread.sleep(10);
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
	
	public void updateFloors() {
		for (Person person : peopleOutsideToLoad ) {
			floorsToGo.add(person.getSrcFloor());
		}
		
		for (Person person : peopleInside ) {
			floorsToGo.add(person.getDestFloor());
		}
	}
	
	// Person handling
	
	// Adds Person object to peopleToLoad list
	public void addPersonToLoad(Person person) {
		this.peopleOutsideToLoad.add(person);
		updateFloors();
	}

	public void LoadPeople() throws InterruptedException {
		ArrayList<Person> peopleToBeLoaded = new ArrayList<Person>();
		for ( Person person : peopleOutsideToLoad ) {
			if ( person.getSrcFloor() == this.currentFloor ) {
				this.peopleInside.add(person);
				peopleToBeLoaded.add(person);
				LOGGER.info("Person entered " + this.ELEVATORID + " on floor " + this.getCurrentFloor() + " to get to floor " + person.getDestFloor());
			}
		}
		
		peopleOutsideToLoad.removeAll(peopleToBeLoaded);
		updateFloors();
		doorsOpenClose();
	}

	public void unloadPeople() throws InterruptedException {
		ArrayList<Person> peopleToBeUnloaded = new ArrayList<Person>() ;
		for (Person person : peopleInside ) {
			if ( person.getDestFloor() == this.currentFloor ) {
				peopleToBeUnloaded.add(person);
				LOGGER.info("Person unloaded from " + this.ELEVATORID + " at floor " + this.getCurrentFloor());
			}
		}
		
		peopleInside.removeAll(peopleToBeUnloaded);
		updateFloors();
		doorsOpenClose();
		
	}
	
	// This will move the Elevator over the floorsToGo list
	public void operateElevator() throws InterruptedException {
	    if (floorsToGo.isEmpty()) {
	        this.isIdle = true;
	        return;
	    }

	    this.isIdle = false;
	    ArrayList<Integer> floorsVisited = new ArrayList<>();
	    Iterable<Integer> floorsIterable = goingUp ? floorsToGo : floorsToGo.descendingSet();

	    for (Integer floor : floorsIterable) {
	        GoToFloor(floor);
	        LoadPeople();
	        unloadPeople();
	        floorsVisited.add(floor);
	        
	        // Check if the current floor is the highest floor in floorsToGo
	        if (this.currentFloor == floorsToGo.last()) {
	            this.goingUp = false;
	            break; // Exit the loop as the direction has changed
	        }
	    }

	    updateFloors();
	    floorsToGo.removeAll(floorsVisited);

	    // Check if there are more floors to visit and the elevator is not idle
	    if (!floorsToGo.isEmpty() && !this.isIdle) {
	        operateElevator(); // Recursively call the method
	    } else {
	        this.isIdle = true;
	    }
	}

	

	@Override
	public void run() {
		while(!Thread.interrupted()) {
			try {
				operateElevator();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
