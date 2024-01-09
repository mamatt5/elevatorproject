package com.fdmgroup.ElevatorProject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This object controls the flow of Person objects into the Elevators. It needs the Scheduler object to choose which Elevator
 * the Person is to be loaded. It handles all the requests and processes it into the Elevator objects. It takes a Scheduler
 * to be instantiated and it holds a list of requests to be passed on to the Elevators.
 */

public class Controller {
	private ArrayList<Person> peopleQueue = new ArrayList<Person>();
	private Scheduler scheduler;
	
	private static final Logger LOGGER = LogManager.getLogger(Controller.class);
	
	
	public Controller( Scheduler scheduler ) {
		this.scheduler = scheduler;
	}
	
	public ArrayList<Person> getPeopleQueue() {
		return peopleQueue;
	}
	
	public ArrayList<Elevator> getElevators() {
		return scheduler.getElevators();
	}
	
	// Adds a Person object to the peopleQueue list
	public void addPersonToQueue(Person person) {
		this.peopleQueue.add(person);
	}

	
	/**
	 * Method to simulate a Person calling an Elevator.
	 * 
	 * This method assigns an Elevator to a Person object. It calls the Scheduler to choose the appropriate Elevator to
	 * where the Person is to be loaded. It then calls the addPersonToLoad() method of the chosen Elevator to load the
	 * Person object into the Elevator's peopleOutside list. Note that it will keep running until the peopleQueue list
	 * is empty.
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void assignElevator() throws InterruptedException {
		while (!peopleQueue.isEmpty()) {
			Person person = peopleQueue.get(0);
			scheduler.CallElevator(person).addPersonToLoad(person);
//			LOGGER.info("Assigning person from floor " + person.getSrcFloor() + " to floor " + person.getDestFloor() + " to an elevator.");
			peopleQueue.remove(0);
		}
	}

	// FOR TESTING PURPOSES
	public Elevator FindElevatorWithPerson(Person person) {
		for ( Elevator elevator : scheduler.getElevators() ) {
			if ( elevator.getPeopleInside().contains(person) ) {
				return elevator;
			}
		}
		
		return null; // if no person exists inside any of the elevators
	}
	
}
