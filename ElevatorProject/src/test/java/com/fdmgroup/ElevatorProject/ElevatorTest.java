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
		assertTrue(elevator.getPeopleInsideToUnload().isEmpty());
	}
	
	@Test
	void elevator_load_one_person() throws InterruptedException {
		Person person = new Person(0,1);
<<<<<<< HEAD
		elevator.addPersonToLoad(person);
		elevator.loadPeople();
=======
		try {
			elevator.loadPerson(person);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
>>>>>>> 2604b9ba4b2a86f1dd9a2d63ae2cbf20fb77cdb2
		
		assertEquals(person, elevator.getPeopleInsideToUnload().get(0));
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
	void add_person_to_load_into_elevator() {
		Person person = new Person(0,10);
<<<<<<< HEAD
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
=======
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
>>>>>>> 2604b9ba4b2a86f1dd9a2d63ae2cbf20fb77cdb2
		
		assertEquals(10,elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_loads_a_person_then_unloads() throws InterruptedException {
		Person person = new Person(0,10);
<<<<<<< HEAD
		elevator.addPersonToLoad(person);
		elevator.goToFloor(person.getSrcFloor());
		elevator.loadPeople();
		elevator.operateElevator();
=======
		elevator.loadPerson(person);
		elevator.unloadPeople();
>>>>>>> 2604b9ba4b2a86f1dd9a2d63ae2cbf20fb77cdb2
		
	    assertTrue(elevator.getPeopleInsideToUnload().isEmpty());
	    assertEquals(10, elevator.getCurrentFloor());
	}
	
	@Test
	void elevator_loads_a_person_then_unloads_then_check_if_idle() throws InterruptedException {
		Person person = new Person(0,10);
<<<<<<< HEAD
		elevator.addPersonToLoad(person);
		elevator.goToFloor(person.getSrcFloor());
		elevator.loadPeople();
		elevator.operateElevator();
=======
		elevator.loadPerson(person);
		elevator.unloadPeople();
>>>>>>> 2604b9ba4b2a86f1dd9a2d63ae2cbf20fb77cdb2
		
	    assertTrue(elevator.getPeopleInsideToUnload().isEmpty());
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
<<<<<<< HEAD
		elevator.addPersonToLoad(person1);
		elevator.addPersonToLoad(person2);
		elevator.addPersonToLoad(person3);
		elevator.addPersonToLoad(person4);
		System.out.println(elevator.getFloorsToGo().size()+ " floors to visit.");
		System.out.println(elevator.getPeopleOutsideToLoad().size() + " people waiting.");
		System.out.println(elevator.getPeopleInsideToUnload().size() + " people inside elevator.");
		elevator.operateElevator();
		System.out.println(elevator.getFloorsToGo().size()+ " floors to visit.");
		System.out.println(elevator.getPeopleOutsideToLoad().size() + " people waiting.");
		System.out.println(elevator.getPeopleInsideToUnload().size() + " people inside elevator.");

=======
		elevator.loadPerson(person1);
		elevator.loadPerson(person2);
		elevator.loadPerson(person3);
		elevator.loadPerson(person4);
		elevator.unloadPeople();
>>>>>>> 2604b9ba4b2a86f1dd9a2d63ae2cbf20fb77cdb2
		
	    assertTrue(elevator.getPeopleInsideToUnload().isEmpty());
	    assertTrue(elevator.isIdle());
	}
	
	
	@Test
	void OPERATE_elevator_loads_a_person_then_unloads_then_check_if_idle() throws InterruptedException {
		Person person = new Person(0,10);
		elevator.addPersonToLoad(person);
		elevator.operateElevator();
		
	    assertTrue(elevator.getPeopleInsideToUnload().isEmpty());
	    assertTrue(elevator.getPeopleOutsideToLoad().isEmpty());
	    assertTrue(elevator.getFloorsToGo().isEmpty());
	    assertEquals(10, elevator.getCurrentFloor());
	    assertTrue(elevator.isIdle());
	}

}
