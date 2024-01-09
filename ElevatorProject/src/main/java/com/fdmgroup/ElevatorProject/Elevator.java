package com.fdmgroup.ElevatorProject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents the main object controlling the behavior of an elevator in the system.
 *
 * This object manages the state, movement, and handling of people inside and outside the elevator.
 * It simulates movement, loading, and unloading of people, depending on the current floors
 * and requests made to the elevator system.
 */

public class Elevator implements Runnable, Serializable {
	private ConcurrentSkipListSet<Integer> floorsToGo = new ConcurrentSkipListSet<>();
	// ConcurrentSkipListSet permits concurrent modifications when loading/unloading people and moving floors,
	// preventing ConcurrentModificationException experienced with other collections like TreeSet or HashSet.
	
	private ArrayList<Person> peopleInsideToUnload = new ArrayList<>();
	// 'peopleInside' holds Person objects currently inside the Elevator, ready for unloading.
	
	private ArrayList<Person> peopleOutsideToLoad = new ArrayList<>();
	// 'peopleOutsideToLoad' holds Person objects currently outside the Elevator, ready for loading.

	private static final Logger LOGGER = LogManager.getLogger(Elevator.class);
	
	// ------------ Elevator Attributes ------------ //
	private final String ELEVATOR_ID;
	private static int nextID = 0;
	private boolean goingUp = true;
	private boolean isIdle = true;
	private int currentFloor = 0;
	private final int SLEEP_TIME = 1000;
	
	// ------------ Elevator Constructor ------------ //
	public Elevator() {
		this.ELEVATOR_ID = "Elevator" +nextID++;
	}
	
	// ------------ States ------------ //
	
	public boolean isGoingUp() {
		return goingUp;
	}
	
	public boolean isIdle() {
		return isIdle;
	}
	
	// ------------ Getters and Setters ------------ //

	public String getElevatorID() {
		return ELEVATOR_ID;
	}
	
	public void setGoingUp(boolean state) {
		this.goingUp = state;
	}
	
	public void setIdle(boolean state) {
		this.isIdle = state;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int floor) {
		this.currentFloor = floor;
	}

	public ArrayList<Person> getPeopleInsideToUnload() {
		return peopleInsideToUnload;
	}
	
	public ArrayList<Person> getPeopleOutsideToLoad() {
		return peopleOutsideToLoad;
	}
	
	public ConcurrentSkipListSet<Integer> getFloorsToGo() {
		return floorsToGo;
	}
	
	// ------------ Main Elevator method -- operateElevator ------------ //
	
	/**
	 * Simulates elevator behaviour by moving between floors based on the floorsToGo set.
	 * Handles loading/unloading people, changes direction when reaching the end of the floors set,
	 * and updates the floorsToGo set accordingly. If the set is empty, the elevator goes idle.
	 *
	 * @throws InterruptedException if the thread is interrupted while waiting.
	 */
	public void operateElevator() throws InterruptedException {
	    if (floorsToGo.isEmpty()) {
	        this.isIdle = true;
	        return;
	    }

	    this.isIdle = false;
	    ArrayList<Integer> floorsVisited = new ArrayList<>();
		// floorsVisited - stores the floors at which the Elevator have already visited.
		
	    Iterable<Integer> floorsIterable = goingUp ? floorsToGo : floorsToGo.descendingSet();
		// floorsIterable - makes the floorsToGo set to be iterable, integrating a for loop
		// and allowing descending order. The order depends on the direction of the Elevator
		
	    for (Integer floor : floorsIterable) {
			
	        goToFloor(floor);                   // simulate elevator behaviour with
	        loadPeople();                       // movement and loading/unloading
	        unloadPeople();                     // of Person objects
	        floorsVisited.add(floor);
	        
	        // Checks the end of the floorsToGo set and changes direction accordingly.
	        if (this.currentFloor == floorsToGo.last() && goingUp) {
	            this.goingUp = false;
	            break; // Exit the loop as the direction has changed
	        }
	        
	        if (this.currentFloor == floorsToGo.first() && !goingUp) {
	        	this.goingUp = true;
	        	break; // Exit the loop as the direction has changed
	        }
	    }

	    updateFloors();
	    floorsToGo.removeAll(floorsVisited);
		
		// recursively call the method to check if it received any more requests and the elevator is not idle
	    if (!floorsToGo.isEmpty() && !this.isIdle) {
	        operateElevator(); // Recursively call the method
	    }
		else {
	        this.isIdle = true;
	    }
	    
	    // todo: Maybe add Thread.wait() or Thread.lock()?
	}
	
	// ------------ person handling ------------ //
	/**
	 * Processes elevator requests by adding a Person object to the elevator's waiting list.
	 * Additionally, updates the floors to visit in the `floorsToGo` set accordingly.
	 *
	 * @param person the Person object requesting to use the elevator
	 */
	public void addPersonToLoad(Person person) {
		this.peopleOutsideToLoad.add(person);
		updateFloors();
	}

	// ------------ helper methods ------------ //
	/**
	 * Moves the elevator to the specified floor.
	 *
	 * @param floor The destination floor the elevator will move to.
	 * @throws InterruptedException If interrupted during the movement process.
	 */
	// fixme: CHANGE THIS TO PRIVATE METHOD BEFORE SUBMITTING
	public void goToFloor(int floor) throws InterruptedException {
		int currentFloor = getCurrentFloor();
		int numFloors = floor - currentFloor;
		
		movesFloor(numFloors);
	}
	
	/**
	 * Moves the elevator by the specified number of floors, and Elevator states `isIdle` and `goingUp`
	 *
	 * @param numFloors the number of floors the elevator will move
	 * @throws InterruptedException if the thread is interrupted while moving
	 */
	private void movesFloor(int numFloors) throws InterruptedException {
		int absNumFloors = numFloors;
		setIdle(false);
		setGoingUp(true);
		
		if (numFloors < 0) {        // determine if elevator is to
			setGoingUp(false);      // move up (positive numFloors)
			absNumFloors *= -1;     // or down (negative numFloors)
		}
		
		for (int i = 0; i < absNumFloors; i++) {
			if (numFloors > 0) {
				setCurrentFloor(getCurrentFloor()+1);    // Elevator moves
			}                                            // incrementally
			else {                                       // to simulate movement
				setCurrentFloor(getCurrentFloor()-1);    // through each floor
			}
			
			pauseOnFloorMove();
		}
	}
	
	/**
	 * Updates `floorsToGo` (list of floors to visit), based on the people associated with the elevator.
	 * `floorsToGo` contains the src floors of people waiting outside and the dest floors of people inside.
	 */
	// fixme: CHANGE TO PRIVATE METHOD BEFORE SUBMITTING
	public void updateFloors() {
		for (Person person : peopleOutsideToLoad ) {
			floorsToGo.add(person.getSrcFloor());
		}
		
		for (Person person : peopleInsideToUnload) {
			floorsToGo.add(person.getDestFloor());
		}
	}
	
	/**
	 * Loads eligible Person objects into the Elevator from outside if they are at the Elevator's current floor.
	 *
	 * @throws InterruptedException if the thread is interrupted while pausing during the door open/close.
	 */
	// fixme: CHANGE TO PRIVATE METHOD BEFORE SUBMITTING
	public void loadPeople() throws InterruptedException {
		ArrayList<Person> peopleToBeLoaded = new ArrayList<Person>();
		for ( Person person : peopleOutsideToLoad ) {
			if ( person.getSrcFloor() == this.currentFloor ) {
				this.peopleInsideToUnload.add(person);
				peopleToBeLoaded.add(person);
				LOGGER.info("Person entered " + this.ELEVATOR_ID + " on floor " + this.getCurrentFloor() + " to get to floor " + person.getDestFloor());
			}
		}
		
		peopleOutsideToLoad.removeAll(peopleToBeLoaded);
		updateFloors();
		pauseOnDoorOpenClose();
	}
	
	/**
	 * Unloads Person objects from the Elevator at the current floor where destination matches current floor.
	 *
	 * @throws InterruptedException if the thread is interrupted while pausing during the door open/close.
	 */
	// fixme: CHANGE TO PRIVATE METHOD BEFORE SUBMITTING
	public void unloadPeople() throws InterruptedException {
		ArrayList<Person> peopleToBeUnloaded = new ArrayList<Person>() ;
		for (Person person : peopleInsideToUnload) {
			if ( person.getDestFloor() == this.currentFloor ) {
				peopleToBeUnloaded.add(person);
				LOGGER.info("Person unloaded from " + this.ELEVATOR_ID + " at floor " + this.getCurrentFloor());
			}
		}
		
		peopleInsideToUnload.removeAll(peopleToBeUnloaded);
		updateFloors();
		pauseOnDoorOpenClose();
	}
	
	// ------------ pauses ------------ //
	
	/**
	 * Pauses the execution for door opening and closing simulation.
	 *
	 * @throws InterruptedException if the thread is interrupted while pausing
	 */
	public void pauseOnDoorOpenClose() throws InterruptedException {
		Thread.sleep(SLEEP_TIME);
	}
	
	/**
	 * Pauses the execution for simulating the movement of the elevator between floors.
	 *
	 * @throws InterruptedException if the thread is interrupted while pausing
	 */
	public void pauseOnFloorMove() throws InterruptedException {
		Thread.sleep(SLEEP_TIME);
	}
	
	// ------------ For: Scheduler ------------ //
	// This allows the Elevator to be called as a Thread. It must only be called by the Scheduler object.
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
