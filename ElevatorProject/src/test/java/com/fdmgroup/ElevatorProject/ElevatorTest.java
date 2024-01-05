package com.fdmgroup.ElevatorProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElevatorTest {
	
	Elevator elevator;
	
	@BeforeEach
	void setup() {
		elevator = new Elevator();
	}
	
	@Test
	void default_elevator_instantiated() {
		
		assertTrue(elevator.isIdle());
		assertFalse(elevator.isGoingUp());
		assertEquals(0, elevator.getCurrentFloor());
		assertTrue(elevator.getPeopleInside().isEmpty());
	}
	
	@Test
	void elevator_load_one_person() {
		Person person = new Person(0,1);
		elevator.loadPerson(person);
		
		assertEquals(person, elevator.getPeopleInside().get(0));
	}

}
