package com.fdmgroup.ElevatorProject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Manages the queue of people and elevators' assignments.
 */
public class Controller {
	private ArrayList<Person> peopleQueue = new ArrayList<Person>();
	private Scheduler scheduler;
	
	// fixme: method unused - not called anywhere
//	private static final Logger LOGGER = LogManager.getLogger(Controller.class);
	
	/**
	 * @param scheduler The scheduler to be used for managing elevator assignments.
	 */
	public Controller( Scheduler scheduler ) {
		this.scheduler = scheduler;
	}
	
	/**
	 * @return The ArrayList representing the queue of people.
	 */
	public ArrayList<Person> getPeopleQueue() {
		return peopleQueue;
	}
	
	// fixme: method unused - not called anywhere
//	public ArrayList<Elevator> getElevators() {
//		return scheduler.getElevators();
//	}
	
	
	// fixme: method unused - not called anywhere except in ControllerTest
	// Might have to move this method to Person object
	public Person StringToPerson(@NotNull String input) {
		String[] personString = input.split(":");
		int srcFloor = Integer.parseInt(personString[0]);
		int destFloor = Integer.parseInt(personString[1]);
		return new Person(srcFloor, destFloor);
	}
	
	/**
	 * Adds a Person to the queue of people waiting for an elevator.
	 *
	 * @param person The Person object to be added to the queue of people waiting for an elevator.
	 */
	public void addPersonToQueue(Person person) {
		this.peopleQueue.add(person);
	}
	
	
	/**
	 * Assigns elevators to people in the queue, loading them into the elevators.
	 *
	 * @throws InterruptedException If interrupted during elevator assignment.
	 */
	public void assignElevator() throws InterruptedException {
		
		// Since this is modifying the peopleQueue list, use for-loop with indices
		// but do not increment i unless person object is not removed.
		for ( int i = 0 ; i < peopleQueue.size() ; ) {
			Person person = peopleQueue.get(i);
			scheduler.callElevator(person).loadPerson(person);
//			LOGGER.info("Assigning person from floor " + person.getSrcFloor()
//			+ " to floor " + person.getDestFloor() + " to an elevator.");
			peopleQueue.remove(i);
		}
	}
	
	/**
	 * Finds the elevator containing a specific Person.
	 *
	 * @param person The Person object to search for in elevators.
	 * @return The Elevator object containing the specified Person, or null if not found.
	 */
	// For testing purposes
	public Elevator FindElevatorWithPerson(Person person) {
		for ( Elevator elevator : scheduler.getElevators() ) {
			if ( elevator.getPeopleInside().contains(person) ) {
				return elevator;
			}
		}
		
		return null; // if no person exists inside any of the elevators
	}
}
