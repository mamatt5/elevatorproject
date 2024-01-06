package com.fdmgroup.ElevatorProject;

import java.util.ArrayList;

public class Controller {
	private ArrayList<Person> peopleQueue = new ArrayList<Person>();
	private Scheduler scheduler;

	public Controller( Scheduler scheduler ) {
		this.scheduler = scheduler;
	}
	
	public void handlePeopleWaiting(String input) {
		
		// sample input: "1:4,12:3,5:13", where "src:dest" notation
		
		// input will be broken down to a peopleList: {1:4, 12:3, 5:13}
		String[] peopleList = input.split(",");
		
		// for each "src:dest" string in peopleList
		for ( int i = 0 ; i <= peopleList.length-1 ; i++ ) {
			
			// each element in personString is {src, dest}, then converted into Person object
			String[] personString = peopleList[i].split(":");
			int srcFloor = Integer.parseInt(personString[0]);
			int destFloor = Integer.parseInt(personString[1]);
			
			Person person = new Person(srcFloor, destFloor);
			peopleQueue.add(person);
		}
		
	}
	
	public void assignElevator() {
		for (Person person : peopleQueue ) {
			scheduler.callElevator(person).loadPerson(person);
		}
	}
	
}
