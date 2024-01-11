package com.fdmgroup.ElevatorProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SchedulerTest {
	static Scheduler scheduler;

	@BeforeAll
	static void setup() {
		
		// These elevators instantiated at ground floor
		Elevator elevator1 = new Elevator();
		Elevator elevator2 = new Elevator();
		Elevator elevator3 = new Elevator();

		ArrayList<Elevator> elevators = new ArrayList<Elevator>();

		elevators.add(elevator1);
		elevators.add(elevator2);
		elevators.add(elevator3);

		scheduler = new Scheduler(elevators);
		
	}
	
	@AfterEach
	void cleanup() {
		scheduler.getElevators().clear();
		
		// Refresh the Elevator array after each test
		Elevator elevator1 = new Elevator();
		Elevator elevator2 = new Elevator();
		Elevator elevator3 = new Elevator();
		
		scheduler.getElevators().add(elevator1);
		scheduler.getElevators().add(elevator2);
		scheduler.getElevators().add(elevator3);
	}

	@Test
	void scheduler_can_add_elevator() {
		Elevator elevator4 = new Elevator();
		scheduler.addElevator(elevator4);

		assertEquals(4, scheduler.getElevators().size());
	}

	@Test
	void scheduler_can_assign_elevator_to_a_person() throws InterruptedException {
		Person person = new Person(0,10);
		Elevator assignedElevator = scheduler.callElevator(person);

		assertNotNull(assignedElevator);
		assertTrue(scheduler.getElevators().contains(assignedElevator));
	}

	@Test
	void  scheduler_can_assign_different_elevators() throws InterruptedException {
		Person person1 = new Person(0,10);
		Person person2 = new Person(0,10);
		
		// Grabs IDs of Elevators at ground floor
		List<String> elevatorsOnGroundFloor = new ArrayList<>();
		for ( Elevator elevator : scheduler.getElevators() ) {
			if ( elevator.getCurrentFloor() == 0 ) {
				elevatorsOnGroundFloor.add(elevator.getElevatorID());
				
			}
		}
		
		Elevator assignedElevator1 = scheduler.callElevator(person1);
		assertTrue(elevatorsOnGroundFloor.contains(assignedElevator1.getElevatorID())); 

		
		// Bring first assigned Elevator to floor 4, making it an invalid choice for the second person
		try
		{
			assignedElevator1.goToFloor(4);
			assignedElevator1.state = Direction.IDLE; // To simulate if the Elevator unloaded at floor 4
			elevatorsOnGroundFloor.remove(assignedElevator1.getElevatorID());
			
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		Elevator assignedElevator2 = scheduler.callElevator(person2);
		assertTrue(elevatorsOnGroundFloor.contains(assignedElevator2.getElevatorID()));
		
	}

	@Test
	void scheduler_assign_elevators_with_more_people_added() throws InterruptedException {  
		Person person1 = new Person(0,3);
		Person person2 = new Person(10,14);
		Person person3 = new Person(2,4);
		
		// Grabs IDs of Elevators at ground floor
		List<String> elevatorsOnGroundFloor = new ArrayList<>();
		for ( Elevator elevator : scheduler.getElevators() ) {
			if ( elevator.getCurrentFloor() == 0 ) {
				elevatorsOnGroundFloor.add(elevator.getElevatorID());
				
			}
		}


		Elevator assignedElevator1 = scheduler.callElevator(person1);
		assertTrue(elevatorsOnGroundFloor.contains(assignedElevator1.getElevatorID()));


		try {
			assignedElevator1.goToFloor(3); // Assume person unloaded at floor 3
			assignedElevator1.state = Direction.IDLE; // Elevator idle
			elevatorsOnGroundFloor.remove(assignedElevator1.getElevatorID()); // Since Elevator is not in ground floor anymore
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Elevator assignedElevator2 = scheduler.callElevator(person2);
		
		// Should be the same Elevator as the first since the other Elevators are on ground floor 
		assertEquals(assignedElevator2, assignedElevator1); 
		
		
		try
		{
			assignedElevator2.goToFloor(14); // person unloaded at floor 14
			assignedElevator2.state = Direction.IDLE; // Elevator idle
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		// Should grab any elevator from ground floor
		Elevator assignedElevator3 = scheduler.callElevator(person3);
		assertTrue(elevatorsOnGroundFloor.contains(assignedElevator3.getElevatorID()));

	}

	@Test
	void scheduler_assign_elevators_with_more_people_and_elevator_added_but_different_starting_position() throws InterruptedException { 
		Elevator elevator4 = new Elevator();
		elevator4.goToFloor(10);

		Elevator elevator5 = new Elevator();
		elevator5.goToFloor(25);

		Elevator elevator6 = new Elevator();
		elevator6.goToFloor(30);

		Elevator elevator7 = new Elevator();
		elevator7.goToFloor(5);

		scheduler.addElevator(elevator4);
		scheduler.addElevator(elevator5);
		scheduler.addElevator(elevator6);
		scheduler.addElevator(elevator7);
		
		// List of elevators that is on ground floor
		List<String> elevatorsOnGroundFloor = new ArrayList<>();
		for ( Elevator elevator : scheduler.getElevators() ) {
			if ( elevator.getCurrentFloor() == 0 ) {
				elevatorsOnGroundFloor.add(elevator.getElevatorID());
				
			}
		}
		
		
		// Set all elevators to idle to simulate different idle positions
		for ( Elevator elevator: scheduler.getElevators() ) {
			elevator.state = Direction.IDLE;
		}

		// In theory, only person2 and person6 would be assigned a different elevator from different floor
		// person2 would be assigned elevator4, while person6 would be assigned elevator7
		Person person1 = new Person(0,3);
		Person person2 = new Person(10,14);
		Person person3 = new Person(2,4);
		Person person4 = new Person(0,3);
		Person person5 = new Person(1,2);
		Person person6 = new Person(5,4);
		Person person7 = new Person(2,4);

		
		Elevator assignedElevator1 = scheduler.callElevator(person1);
		assertTrue(elevatorsOnGroundFloor.contains(assignedElevator1.getElevatorID()));

		Elevator assignedElevator2 = scheduler.callElevator(person2);
		assertEquals(elevator4.getElevatorID(), assignedElevator2.getElevatorID());

		Elevator assignedElevator3 = scheduler.callElevator(person3);
		assertTrue(elevatorsOnGroundFloor.contains(assignedElevator3.getElevatorID()));

		Elevator assignedElevator4 = scheduler.callElevator(person4);
		assertTrue(elevatorsOnGroundFloor.contains(assignedElevator4.getElevatorID()));

		Elevator assignedElevator5 = scheduler.callElevator(person5);
		assertTrue(elevatorsOnGroundFloor.contains(assignedElevator5.getElevatorID()));

		Elevator assignedElevator6 = scheduler.callElevator(person6);
		assertEquals(elevator7.getElevatorID(), assignedElevator6.getElevatorID());

		Elevator assignedElevator7 = scheduler.callElevator(person7);
		assertTrue(elevatorsOnGroundFloor.contains(assignedElevator7.getElevatorID()));


	}
	

}
