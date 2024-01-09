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
		try {
			elevator.loadPerson(person);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(person, elevator.getPeopleInside().get(0));
	}
	
	@Test
	void elevator_go_up_to_floor_10() {
		try {
			elevator.goToFloor(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(10, elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_go_to_same_floor() {
		try {
			elevator.goToFloor(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(0, elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_go_down_to_ground_floor() {
		try {
			elevator.goToFloor(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			elevator.goToFloor(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(1, elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_loads_a_person_then_goes_to_floor() {
		Person person = new Person(0,10);
		try {
			elevator.loadPerson(person);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			elevator.goToFloor(elevator.getPeopleInside().get(0).getDestFloor());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(10,elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_loads_a_person_then_unloads() throws InterruptedException {
		Person person = new Person(0,10);
		elevator.loadPerson(person);
		elevator.unloadPeople();
		
	    assertTrue(elevator.getPeopleInside().isEmpty());
	    assertEquals(10, elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_loads_a_person_then_unloads_then_check_if_idle() throws InterruptedException {
		Person person = new Person(0,10);
		elevator.loadPerson(person);
		elevator.unloadPeople();
		
	    assertTrue(elevator.getPeopleInside().isEmpty());
	    assertEquals(10, elevator.getCurrentFloor());
	    assertTrue(elevator.isIdle());
	}
	
	// Weird test for now, because it initially loads all Person objects into the elevator, basically the elevator is teleporting. Then unloads.
	@Test
	void elevator_loads_multiple_people_then_unloads_everyone() throws InterruptedException {
		Person person1 = new Person(0,10);
		Person person2 = new Person(2,5);
		Person person3 = new Person(6,11);
		Person person4 = new Person(14,0);
		elevator.loadPerson(person1);
		elevator.loadPerson(person2);
		elevator.loadPerson(person3);
		elevator.loadPerson(person4);
		elevator.unloadPeople();
		
	    assertTrue(elevator.getPeopleInside().isEmpty());
	}

}
