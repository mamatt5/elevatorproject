package com.fdmgroup.ElevatorProject;

import java.util.ArrayList;

public class Controller {
	private ArrayList<Person> peopleQueue = new ArrayList<Person>();
	private Scheduler scheduler;

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
	public Person StringToPerson(String input) {
		String[] personString = input.split(":");
		int srcFloor = Integer.parseInt(personString[0]);
		int destFloor = Integer.parseInt(personString[1]);
		return new Person(srcFloor, destFloor);
	}
	
	public void addPersonToQueue(Person person) {
		this.peopleQueue.add(person);
	}
	
	
	public void assignElevator() {
		for (Person person : peopleQueue ) {
			scheduler.CallElevator(person).LoadPerson(person);
			peopleQueue.remove(person);
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
	
//	// This is to convert string input into People object, hold testing for now!
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
