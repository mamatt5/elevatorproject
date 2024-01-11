package com.fdmgroup.ElevatorProject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        assertSame(person.DIRECTION, Direction.UP);
	}
	
	@Test
	void check_person_if_going_down() {
		Person person = new Person(5,0);

        assertNotSame(person.DIRECTION, Direction.UP);
	}

}
