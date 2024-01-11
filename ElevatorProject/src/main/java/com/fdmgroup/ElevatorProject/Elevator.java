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

@SuppressWarnings("serial")
public class Elevator implements Runnable, Serializable {
	private ConcurrentSkipListSet<Integer> floorsToGo = new ConcurrentSkipListSet<>();
	// ConcurrentSkipListSet is thread-safe (built-in concurrency control) ensuring data integrity
	// during simultaneous access e.g. during loading/unloading people and moving floors
	
	/**
	 * holds Person objects currently inside the Elevator, ready for unloading.
	 */
	private ArrayList<Person> peopleInsideToUnload = new ArrayList<>();

	/**
	 * holds Person objects currently outside the Elevator, ready for loading.
	 */
	private ArrayList<Person> peopleOutsideToLoad = new ArrayList<>();

	private static final Logger LOGGER = LogManager.getLogger(Elevator.class);
	
	// ------------ Elevator Attributes ------------ //
	private final String ELEVATOR_ID;
	private static int nextID = 0;
	Direction state = Direction.IDLE;
	private int currentFloor = 0;
	private final int SLEEP_TIME = 100;
	private boolean running = true;

	// ------------ Elevator Constructor ------------ //
	public Elevator() {
		this.ELEVATOR_ID = "Elevator" +nextID++;
	}

	// ------------ Getters and Setters ------------ //

	public String getElevatorID() {
		return ELEVATOR_ID;
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
	 *
	 * @throws InterruptedException if the thread is interrupted while waiting.
	 */
	public void operateElevator() throws InterruptedException {
		// base case: elevator stops operating when there are no more travel requests
	    if (floorsToGo.isEmpty()) {
			this.state = Direction.IDLE;
	        return;
	    }
		
		// recursive case: elevator keeps operating if there are remaining requests
	    ArrayList<Integer> floorsVisited = new ArrayList<>();

		Iterable<Integer> floorsIterable = switch (state) {
            case UP -> floorsToGo;							// Iterable<Integer> permits conversion
            case DOWN -> floorsToGo.descendingSet();		// between ascending/descending order
            default -> floorsToGo;
        };

        for (Integer floor : floorsIterable) {
	        goToFloor(floor);                   // simulate elevator behaviour with
	        loadPeople();                       // movement and loading/unloading
	        unloadPeople();                     // of Person objects
	        floorsVisited.add(floor);           // and updates floorsToGo set accordingly
	        
	        
		    if (shouldChangeDirection()) {
                switch (state) {
                    case UP -> this.state = Direction.DOWN;
                    case DOWN -> this.state = Direction.UP;
                }

				break;
	        }
	    }
		
	    updateFloorRequests();                  // refresh the list of all floors
		floorsToGo.removeAll(floorsVisited);    // to be travelled to, and
		operateElevator();                      // continue operating elevator
		
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
		updateFloorRequests();
	}

	// ------------ helper methods ------------ //
	/**
	 * Determines whether the elevator should change its direction based on the current floor and direction.
	 *
	 * @return true if the elevator should change direction; otherwise false.
	 */
	private boolean shouldChangeDirection() {
		return (this.currentFloor == floorsToGo.last() && this.state == Direction.UP) ||
				(this.currentFloor == floorsToGo.first() && this.state == Direction.DOWN);
	}

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
		LOGGER.info(this.getElevatorID() + " moving to floor " +floor);
		
		movesFloor(numFloors);
		LOGGER.info(this.getElevatorID() + " arrived at floor " +this.currentFloor);
	}
	
	/**
	 * Moves the elevator by the specified number of floors, and Elevator states `isIdle` and `goingUp`
	 *
	 * @param numFloors the number of floors the elevator will move
	 * @throws InterruptedException if the thread is interrupted while moving
	 */
	private void movesFloor(int numFloors) throws InterruptedException {

		this.state = Direction.UP;				// determine if elevator is to
		if (numFloors < 0) {        			// move UP (positive numFloors)
			this.state = Direction.DOWN;   		// or DOWN (negative numFloors)
		}
		
		for (int i = 0; i < Math.abs(numFloors); i++) {
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
	public void updateFloorRequests() {
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
		updateFloorRequests();
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
		updateFloorRequests();
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
		while(running) {
			try {
				operateElevator();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	public void kill() {
		this.running = false;
	}
	
	

}
