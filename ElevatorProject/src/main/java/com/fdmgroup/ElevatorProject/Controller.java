package com.fdmgroup.ElevatorProject;

import java.util.ArrayList;

/**
 * Manages the queue of people and passes requests to Elevators, handling Elevator assignments.
 */
public class Controller {
	private ArrayList<Person> peopleQueue = new ArrayList<Person>();
	private Scheduler scheduler;
	
	
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
	
	/**
	 * Adds a Person to the queue of people waiting for an elevator.
	 *
	 * @param person The Person object to be added to the peopleQueue list.
	 */
	public void addPersonToQueue(Person person) {
		this.peopleQueue.add(person);
	}

	/**
	 * Assigns elevators to people in the queue, loading them into the elevators.
	 * Note that it will keep running until the peopleQueue list is empty.
	 *
	 * @throws InterruptedException If interrupted during elevator assignment.
	 */
	public synchronized void assignElevator() throws InterruptedException {
		while (!peopleQueue.isEmpty()) {
			Person person = peopleQueue.get(0);
			scheduler.callElevator(person).addPersonToLoad(person);
//			LOGGER.info("Assigning person from floor " + person.getSrcFloor() + " to floor " + person.getDestFloor() + " to an elevator.");
			peopleQueue.remove(0);
		}
	}

	// FOR TESTING PURPOSES
	/**
	 * Finds the elevator assigned to a specific Person.
	 *
	 * @param person The Person object to search for in elevators.
	 * @return The Elevator object containing the specified Person, or null if not found.
	 */

	// For testing purposes
	public Elevator FindElevatorWithPerson(Person person) {
		for ( Elevator elevator : scheduler.getElevators() ) {
			if ( elevator.getPeopleOutsideToLoad().contains(person)) {
				return elevator;
			}
		}
		
		return null; // if no person exists inside any of the elevators
	}
}
