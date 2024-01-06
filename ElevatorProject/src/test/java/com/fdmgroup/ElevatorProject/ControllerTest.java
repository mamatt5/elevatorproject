package com.fdmgroup.ElevatorProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ControllerTest {
	Controller controller;
	
	@BeforeEach
	void setup() {
		Elevator elevator1 = new Elevator();
		Elevator elevator2 = new Elevator();
		Elevator elevator3 = new Elevator();
		ArrayList<Elevator> elevators = new ArrayList<Elevator>();
		
		elevators.add(elevator1);
		elevators.add(elevator2);
		elevators.add(elevator3);
		Scheduler scheduler = new Scheduler(elevators);
		
		controller = new Controller(scheduler);
		
	}
	
	@Test
	void convert_string_to_Person_object() {
		Person person1 = new Person(0,10);
		
		String stringPerson = "0:10";
		Person person2 = controller.StringToPerson(stringPerson);
		
		assertEquals(person1.getSrcFloor(), person2.getSrcFloor());
		assertEquals(person1.getDestFloor(), person2.getDestFloor());
	}
	
	@Test
	void add_person_to_queue() {
		Person person1 = new Person(0,10);
		
		controller.addPersonToQueue(person1);
		
		assertEquals(1, controller.getPeopleQueue().size());
	}
	
	@Test
	void add_multiple_person_to_queue() {
		Person person1 = new Person(0,10);
		Person person2 = new Person(0,5);
		Person person3 = new Person(0,1);
		
		controller.addPersonToQueue(person1);
		controller.addPersonToQueue(person2);
		controller.addPersonToQueue(person3);
		
		assertEquals(3, controller.getPeopleQueue().size());
	}
	
	@Test
	void assign_elevator_through_controller() {
		Person person1 = new Person(0,1);
		
		controller.addPersonToQueue(person1);
		controller.assignElevator();
		
		assertTrue(controller.FindElevatorWithPerson(person1).getPeopleInside().contains(person1));
	}

}
