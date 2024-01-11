package com.fdmgroup.ElevatorProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorTest {
	
	Elevator elevator;

	@BeforeEach
	void setup() {
		elevator = new Elevator();
	}

	@Test
	void default_elevator_instantiated() {
		// Elevator should start idle and at ground floor
        assertSame(elevator.state, Direction.IDLE);
		assertEquals(0, elevator.getCurrentFloor());
		assertTrue(elevator.getPeopleInsideToUnload().isEmpty());
	}
	
	@Test
	void elevator_load_one_person() throws InterruptedException {
		Person person = new Person(0,1);
		elevator.addPersonToLoad(person);
		elevator.loadPeople();
		
		assertEquals(person, elevator.getPeopleInsideToUnload().get(0));
	}
	
	@Test
	void elevator_go_up_to_floor_10() {
		try {
			elevator.goToFloor(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertEquals(10, elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_go_to_same_floor() {
		try {
			elevator.goToFloor(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertEquals(0, elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_go_down_to_ground_floor() {
		try {
			elevator.goToFloor(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			elevator.goToFloor(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertEquals(1, elevator.getCurrentFloor());
	}
	
	@Test
	void add_person_to_load_into_elevator() {
		Person person = new Person(0,10);
		elevator.addPersonToLoad(person);
		
		assertEquals(1, elevator.getPeopleOutsideToLoad().size());
	}
	
	@Test
	void floor_list_gets_updated_after_adding_person_to_load() {
		Person person = new Person(0,10);
		elevator.addPersonToLoad(person);
		
		assertEquals(1, elevator.getFloorsToGo().size());
	}
	
	@Test
	void floor_list_gets_updated_after_loading_person_inside_elevator() throws InterruptedException {
		Person person = new Person(0,10);
		elevator.addPersonToLoad(person);
		elevator.goToFloor(person.getSrcFloor());
		elevator.loadPeople();
		
		// Should be 1 person inside waiting to get off
		assertEquals(1, elevator.getPeopleInsideToUnload().size());
		assertTrue(elevator.getFloorsToGo().contains(10));
	}
	
	@Test
	void elevator_loads_a_person_then_goes_to_floor() throws InterruptedException {
		Person person = new Person(0,10);
		elevator.addPersonToLoad(person);
		elevator.goToFloor(person.getSrcFloor());
		elevator.loadPeople();
		elevator.operateElevator();
		
		assertEquals(10,elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_loads_a_person_then_unloads() throws InterruptedException {
		Person person = new Person(0,10);
		// Person gets on at floor 0
		elevator.addPersonToLoad(person);
		
		// Person gets off at floor 10
		elevator.goToFloor(person.getSrcFloor());
		elevator.loadPeople();
		elevator.operateElevator();
		
		// There should be no person left inside and elevator is now on floor 10
	    assertTrue(elevator.getPeopleInsideToUnload().isEmpty());
	    assertEquals(10, elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_loads_a_person_then_unloads_then_check_if_idle() throws InterruptedException {
		Person person = new Person(0,10);
		// Load a person on floor 0 and unload on floor 10
		elevator.addPersonToLoad(person);
		elevator.goToFloor(person.getSrcFloor());
		elevator.loadPeople();
		elevator.operateElevator();
		
		// Check if there are no more people inside elevator
	    assertTrue(elevator.getPeopleInsideToUnload().isEmpty());
	    
	    // Check if there are any more people waiting for elevator
	    assertTrue(elevator.getPeopleOutsideToLoad().isEmpty());
	    
	    // Check if there are no more requests to go to a floor
	    assertTrue(elevator.getFloorsToGo().isEmpty());
	    
	    // Elevator should be idle on floor 10
	    assertEquals(10, elevator.getCurrentFloor());
	    assertTrue(elevator.state == Direction.IDLE);
	}
	
	@Test
	void elevator_loads_multiple_people_then_unloads_everyone() throws InterruptedException {
		// Create 4 different people
		Person person1 = new Person(0,10);
		Person person2 = new Person(2,5);
		Person person3 = new Person(6,11);
		Person person4 = new Person(14,0);
		
		// Load the requests onto an elevator
		elevator.addPersonToLoad(person1);
		elevator.addPersonToLoad(person2);
		elevator.addPersonToLoad(person3);
		elevator.addPersonToLoad(person4);
		
		// There should 4 floors to go to
		assertEquals(4, elevator.getFloorsToGo().size());
		
		// There should be 4 people waiting for elevator
		assertEquals(4, elevator.getPeopleOutsideToLoad().size());
		
		// There should be 0 people inside elevator
		assertEquals(0, elevator.getPeopleInsideToUnload().size());

		elevator.operateElevator();

		// There should 0 floors to go to
		assertEquals(0, elevator.getFloorsToGo().size());
		
		// There should be 0 people inside the elevator and elevator should be idle
	    assertTrue(elevator.getPeopleInsideToUnload().isEmpty());
	    assertTrue(elevator.state == Direction.IDLE);
	}
	
	// Tests for lift capacity functionality
	
	@Test
	void elevator_only_loads_up_to_lift_capacity() throws InterruptedException {
		Person person1 = new Person(0,10);
		Person person2 = new Person(0,15);
		Person person3 = new Person(0,20);
		Person person4 = new Person(0,25);
		Person person5 = new Person(0,10);
		Person person6 = new Person(0,15);
		Person person7 = new Person(0,20);
		Person person8 = new Person(0,25);
		Person person9 = new Person(0,30);
		
		elevator.addPersonToLoad(person1);
		elevator.addPersonToLoad(person2);
		elevator.addPersonToLoad(person3);
		elevator.addPersonToLoad(person4);
		elevator.addPersonToLoad(person5);
		elevator.addPersonToLoad(person6);
		elevator.addPersonToLoad(person7);
		elevator.addPersonToLoad(person8);
		
		// Last person should not be loaded
		elevator.addPersonToLoad(person9);
		
		elevator.loadPeople();
		
		// Elevator should only load 3 people
		assertEquals(8, elevator.getPeopleInsideToUnload().size());
		
	}
	
	@Test
	void elevator_only_loads_up_to_specified_lift_capacity() throws InterruptedException {
		Person person1 = new Person(0,10);
		Person person2 = new Person(0,15);
		Person person3 = new Person(0,20);
		Person person4 = new Person(0,25);
		
		elevator.addPersonToLoad(person1);
		elevator.addPersonToLoad(person2);
		elevator.addPersonToLoad(person3);
		
		// Last person should not be loaded
		elevator.addPersonToLoad(person4);
		
		// Change the capacity to 3
		elevator.setCapacity(3);
		
		elevator.loadPeople();
		
		// Elevator should only load 3 people
		assertEquals(3, elevator.getPeopleInsideToUnload().size());
		
	}

}
