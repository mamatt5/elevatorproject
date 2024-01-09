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
		assertTrue(elevator.isGoingUp());
		assertEquals(0, elevator.getCurrentFloor());
		assertTrue(elevator.getPeopleInside().isEmpty());
	}
	
	@Test
	void elevator_load_one_person() throws InterruptedException {
		Person person = new Person(0,1);
		elevator.addPersonToLoad(person);
		elevator.LoadPeople();
		
		assertEquals(person, elevator.getPeopleInside().get(0));
	}
	
	@Test
	void elevator_go_up_to_floor_10() {
		try {
			elevator.GoToFloor(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(10, elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_go_to_same_floor() {
		try {
			elevator.GoToFloor(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(0, elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_go_down_to_ground_floor() {
		try {
			elevator.GoToFloor(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			elevator.GoToFloor(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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
		elevator.GoToFloor(person.getSrcFloor());
		elevator.LoadPeople();
		
		assertEquals(1, elevator.getPeopleInside().size());
		assertTrue(elevator.getFloorsToGo().contains(10));
	}
	
	@Test
	void elevator_loads_a_person_then_goes_to_floor() throws InterruptedException {
		Person person = new Person(0,10);
		elevator.addPersonToLoad(person);
		elevator.GoToFloor(person.getSrcFloor());
		elevator.LoadPeople();
		elevator.operateElevator();
		
		assertEquals(10,elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_loads_a_person_then_unloads() throws InterruptedException {
		Person person = new Person(0,10);
		elevator.addPersonToLoad(person);
		elevator.GoToFloor(person.getSrcFloor());
		elevator.LoadPeople();
		elevator.operateElevator();
		
	    assertTrue(elevator.getPeopleInside().isEmpty());
	    assertEquals(10, elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_loads_a_person_then_unloads_then_check_if_idle() throws InterruptedException {
		Person person = new Person(0,10);
		elevator.addPersonToLoad(person);
		elevator.GoToFloor(person.getSrcFloor());
		elevator.LoadPeople();
		elevator.operateElevator();
		
	    assertTrue(elevator.getPeopleInside().isEmpty());
	    assertTrue(elevator.getPeopleOutsideToLoad().isEmpty());
	    assertTrue(elevator.getFloorsToGo().isEmpty());
	    assertEquals(10, elevator.getCurrentFloor());
	    assertTrue(elevator.isIdle());
	}
	
	@Test
	void elevator_loads_multiple_people_then_unloads_everyone() throws InterruptedException {
		Person person1 = new Person(0,10);
		Person person2 = new Person(2,5);
		Person person3 = new Person(6,11);
		Person person4 = new Person(14,0);
		elevator.addPersonToLoad(person1);
		elevator.addPersonToLoad(person2);
		elevator.addPersonToLoad(person3);
		elevator.addPersonToLoad(person4);
		System.out.println(elevator.getFloorsToGo().size()+ " floors to visit.");
		System.out.println(elevator.getPeopleOutsideToLoad().size() + " people waiting.");
		System.out.println(elevator.getPeopleInside().size() + " people inside elevator.");
		elevator.operateElevator();

		
	    assertTrue(elevator.getPeopleInside().isEmpty());
	}

}
