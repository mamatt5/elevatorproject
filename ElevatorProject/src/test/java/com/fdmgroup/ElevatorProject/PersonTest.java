package com.fdmgroup.ElevatorProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PersonTest {
	
	@Test
	void check_person_instantation() {
		Person person = new Person(0,1);
		
		assertEquals(0, person.getSrcFloor());
		assertEquals(1, person.getDestFloor());
	}
	
	@Test
	void check_person_if_going_up() {
		Person person = new Person(0,1);
		
		assertTrue(person.isGoingUp());
	}
	
	@Test
	void check_person_if_going_down() {
		Person person = new Person(5,0);
		
		assertFalse(person.isGoingUp());
	}

}
