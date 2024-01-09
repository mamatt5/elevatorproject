package com.fdmgroup.ElevatorProject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main object that handles the Person object. This object can have various states depending on the
 * goingUp and isIdle attribute, simulating the direction of movement and if it is in motion or not.
 * Loading and unloading a Person object counts as it being active. It will only go idle if there 
 * are no more requests or any Person object inside of it. This object cannot be instantiated with 
 * preconfigured attributes as in reality, it starts off at ground floor and with an idle state. Once
 * it gets any requests, it will do its methods. This object would also be ran as a Thread.
 */

@SuppressWarnings("serial")
public class Elevator implements Runnable, Serializable {
	
	// Identifies the state of the Elevator for direction and activity
	private boolean goingUp = true;
	private boolean isIdle = true;
	
	// Identifies where the Elevator currently is
	private int currentFloor = 0;
	
	// This set commands the Elevator to which floors it should go to based on the Person objects loaded
	// and about to be loaded. The use of ConcurrentSkipListSet allows concurrent modification of the set
	// whenever the Elevator loads/unloads a Person and moves to a floor. Through testing, if a TreeSet,
	// HashSet, or other collections was used, it will throw a ConcurrentModificationException when accessed.
	private ConcurrentSkipListSet<Integer> floorsToGo = new ConcurrentSkipListSet<>();
	
	// These two lists contain the Person objects to be loaded and unloaded along the floors of the building
	// with peopleInside as the Person objects present inside the Elevator to be unloaded around, while
	// peopleOutsideToLoad are the Person objects waiting to be loaded inside the Elevator.
	private ArrayList<Person> peopleInside = new ArrayList<>();
	private ArrayList<Person> peopleOutsideToLoad = new ArrayList<>();

	// These are identifiers for the Elevator.
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
	
	public ArrayList<Person> getPeopleOutsideToLoad() {
		return peopleOutsideToLoad;
	}
	
	public ConcurrentSkipListSet<Integer> getFloorsToGo() {
		return floorsToGo;
	}

	// Elevator methods

	// These methods allow for the simulation of opening and closing doors, and the actual movement
	// of the Elevator in between floors. CHANGE THESE METHODS TO PRIVATE AND 1000ms
	public void doorsOpenClose() throws InterruptedException {
		Thread.sleep(1000);
	}

	public void movesFloor() throws InterruptedException {
		Thread.sleep(1000);
	}
	
	
	/**
	 * This method tells the Elevator to move to a specified floor. This method also changes the state of the
	 * Elevator:
	 * 1. isIdle would become false when this is ran
	 * 2. goingUp depends on its currentFloor and the floor to which this method is called for
	 * 
	 * It moves incrementally to simulate movement of the Elevator floor by floor.
	 * @param floor
	 * @throws InterruptedException
	 */
	
	// CHANGE THIS TO PRIVATE METHOD BEFORE SUBMITTING
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
	
	/**
	 * This method updates the floorsToGo set. It will be called whenever a Person object is loaded or unloaded into
	 * or out of the Elevator object.
	 */
	
	// CHANGE TO PRIVATE METHOD BEFORE SUBMITTING
	public void updateFloors() {
		for (Person person : peopleOutsideToLoad ) {
			floorsToGo.add(person.getSrcFloor());
		}
		
		for (Person person : peopleInside ) {
			floorsToGo.add(person.getDestFloor());
		}
	}
	
	
	// Person handling
	
	/**
	 * Method to simulate requests for the elevator.
	 * 
	 * This method adds a Person object to the peopleOutsideToLoad list. It would also call the updateFloors() method
	 * to update the floorsToGo set. This is the main method that can be called by the Controller object to process
	 * requests to the Elevator object.
	 * @param person
	 */
	public void addPersonToLoad(Person person) {
		this.peopleOutsideToLoad.add(person);
		updateFloors();
	}

	
	/**
	 * Method to simulate People entering the Elevator.
	 * 
	 * This method adds a Person object to the peopleInside list of the Elevator and removes the same object from the
	 * outside list. It would also call the updateFloors() method to update the floorsToGo set. It would also simulate
	 * doors opening and closing through doorsOpenClose(). 
	 * 
	 * Any Person object to be removed will be first stored into another list, peopleToBeLoaded. After iterating
	 * through the outside list, it will use the other list to remove the Person objects from the outside list.
	 * @throws InterruptedException
	 */
	
	// CHANGE TO PRIVATE METHOD BEFORE SUBMITTING
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

	/**
	 * Method to simulate People leaving the Elevator.
	 * 
	 * This method removes a Person object from the peopleInside list. It would also call the updateFloors() method
	 * to update the floorsToGo set. It would also simulate doors opening and closing through doorsOpenClose(). 
	 * 
	 * Any Person object to be removed will be first stored into another list. After iterating through the peopleInside
	 * list, it will use the other list to remove the Person objects from the peopleInsidelist.
	 * @throws InterruptedException
	 */
	
	// CHANGE TO PRIVATE METHOD BEFORE SUBMITTING
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
	
	// Main Elevator method to incorporate all methods
	
	/**
	 * Method to simulate the actual behavior of elevators: moving floors, loading and unloading people, and setting the Elevator's state.
	 * 
	 * This method depends on the floorsToGo set for running. It will first check if the set is empty, setting the state to idle. If the set
	 * is not empty, it will change the state of the elevator to active. It also utilizes two lists inherent only to this method.
	 * 1. floorsVisited, this stores the floors at which the Elevator have already visited.
	 * 2. floorsIterable, makes the floorsToGo set to be iterable, integrating a for loop and allowing descending order. The order depends on
	 * the direction the Elevator is moving through the goingUp attribute.
	 * 
	 * It will iterate through the iterable list, and run GoToFloor() to simulate Elevator movement, Load/unloadPeople to simulate People
	 * moving, and then lists the visited floor. Once it reaches the end, it will change its direction, or goingUp attribute, then recursively
	 * calls the method to check if it received any more requests. This property is what made ConcurrentSkipListSet applicable as it allows
	 * modification of the set while the method is running.
	 * 
	 * Once the floorsToGo set is empty, the Elevator will then be set to idle.
	 * @throws InterruptedException
	 */
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

	    // Check if there are more floors to visit and the elevator is not idle
	    if (!floorsToGo.isEmpty() && !this.isIdle) {
	        operateElevator(); // Recursively call the method
	    } else {
	        this.isIdle = true;
	    }
	    
	    // Maybe add Thread.wait() or Thread.lock()?
	}

	

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
