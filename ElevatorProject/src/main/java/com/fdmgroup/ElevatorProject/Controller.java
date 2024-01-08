package com.fdmgroup.ElevatorProject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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


	// Might have to move this method to Person object
	public Person StringToPerson(@NotNull String input) {
		String[] personString = input.split(":");
		int srcFloor = Integer.parseInt(personString[0]);
		int destFloor = Integer.parseInt(personString[1]);
		return new Person(srcFloor, destFloor);
	}
	
	public void addPersonToQueue(Person person) {
		this.peopleQueue.add(person);
	}
	

	public void assignElevator() throws InterruptedException {
		

		while (!peopleQueue.isEmpty()) {
			Person person = peopleQueue.get(0);
			scheduler.CallElevator(person).addPersonToLoad(person);
//			LOGGER.info("Assigning person from floor " + person.getSrcFloor() + " to floor " + person.getDestFloor() + " to an elevator.");
			peopleQueue.remove(0);
		}
	}

	// For testing purposes
	public Elevator FindElevatorWithPerson(Person person) {
		for ( Elevator elevator : scheduler.getElevators() ) {
			if ( elevator.getPeopleInside().contains(person) ) {
				return elevator;
			}
		}
		
		return null; // if no person exists inside any of the elevators
	}



//	todo: This is to convert string input into People object, hold testing for now!
//	public void handlePeopleWaiting(String input) {
//		
//		// sample input: "1:4,12:3,5:13", where "src:dest" notation
//		
//		// input will be broken down to a peopleList: {1:4, 12:3, 5:13}
//		String[] peopleList = input.split(",");
//		
//		// for each "src:dest" string in peopleList
//		for ( int i = 0 ; i <= peopleList.length-1 ; i++ ) {
//			
//			// each element in personString is {src, dest}, then converted into Person object
//			Person person = StringToPerson(peopleList[i]);
//			peopleQueue.add(person);
//		}
//		
//	}
	
}
