package com.fdmgroup.ElevatorProject;

public class Controller {
	private Person person;
	private Scheduler scheduler;
	private int minFloor;
	private int maxFloor;
	
	
	public Person getPerson() {
		return person;
	}

	public Controller(Person person) {
		super();
		this.person = person;
	}
	
}
