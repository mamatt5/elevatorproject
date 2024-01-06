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
		elevator.LoadPerson(person);
		
		assertEquals(person, elevator.getPeopleInside().get(0));
	}
	
	@Test
	void elevator_go_up_to_floor_10() {
		elevator.GoToFloor(10);
		
		assertEquals(10, elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_go_to_same_floor() {
		elevator.GoToFloor(0);
		
		assertEquals(0, elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_loads_a_person_then_goes_to_floor() {
		Person person = new Person(0,10);
		elevator.LoadPerson(person);
		
		elevator.GoToFloor(elevator.getPeopleInside().get(0).getDestFloor());
		
		assertEquals(10,elevator.getCurrentFloor());
	}

}
