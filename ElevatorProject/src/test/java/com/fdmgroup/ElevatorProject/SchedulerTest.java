package com.fdmgroup.ElevatorProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SchedulerTest {
	static Scheduler scheduler;

	@BeforeAll
	static void setup() {
		
		// These elevators instantiated at floor 0
		Elevator elevator1 = new Elevator();
		Elevator elevator2 = new Elevator();
		Elevator elevator3 = new Elevator();

		ArrayList<Elevator> elevators = new ArrayList<Elevator>();

		elevators.add(elevator1);
		elevators.add(elevator2);
		elevators.add(elevator3);

		scheduler = new Scheduler(elevators);
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
		List<String> validElevatorIDs = Arrays.asList("Elevator0","Elevator1","Elevator2");
		
		Elevator assignedElevator1 = scheduler.callElevator(person1);
		assertTrue(validElevatorIDs.contains(assignedElevator1.getElevatorID())); 

		// Bring first assigned Elevator to floor 4
		try
		{
			assignedElevator1.goToFloor(4);
			assignedElevator1.setIdle(true);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		Elevator assignedElevator2 = scheduler.callElevator(person2);

		assertEquals("Elevator1",assignedElevator2.getElevatorID());
	}

	@Test
	void scheduler_assign_elevators_with_more_people_added() throws InterruptedException {  
		Person person1 = new Person(0,3);
		Person person2 = new Person(10,14);
		Person person3 = new Person(2,4);


		Elevator assignedElevator1 = scheduler.callElevator(person1);
		assertEquals("Elevator0", assignedElevator1.getElevatorID());


		try {
			assignedElevator1.goToFloor(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Elevator assignedElevator2 = scheduler.callElevator(person2);

		assertEquals("Elevator0", assignedElevator2.getElevatorID());
		try
		{
			assignedElevator2.goToFloor(14);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		assertFalse(assignedElevator1.isIdle());
		assertFalse(assignedElevator2.isIdle());
		assertTrue(assignedElevator2.getPeopleInsideToUnload().isEmpty());


		Elevator assignedElevator3 = scheduler.callElevator(person3);

		assertEquals("Elevator1", assignedElevator3.getElevatorID());
		try
		{
			assignedElevator3.goToFloor(3);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		assertFalse(assignedElevator3.isIdle());

	}

	@Test
	void scheduler_assign_elevators_with_more_people_and_elevator_added() throws InterruptedException { 
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
		
		// Set all elevators to idle
		for ( Elevator elevator: scheduler.getElevators() ) {
			elevator.setIdle(true);
		}

		Person person1 = new Person(0,3);
		Person person2 = new Person(10,14);
		Person person3 = new Person(2,4);
		Person person4 = new Person(0,3);
		Person person5 = new Person(1,2);
		Person person6 = new Person(5,4);
		Person person7 = new Person(2,4);


		// Elevators for all persons except person2 and person6
		List<String> validElevatorIDs = Arrays.asList("Elevator0","Elevator1","Elevator2");
		
		Elevator assignedElevator1 = scheduler.callElevator(person1);
		assertTrue(validElevatorIDs.contains(assignedElevator1.getElevatorID()));

		Elevator assignedElevator2 = scheduler.callElevator(person2);
		assertEquals(elevator4.getElevatorID(), assignedElevator2.getElevatorID());

		Elevator assignedElevator3 = scheduler.callElevator(person3);
		assertTrue(validElevatorIDs.contains(assignedElevator3.getElevatorID()));

		Elevator assignedElevator4 = scheduler.callElevator(person4);
		assertTrue(validElevatorIDs.contains(assignedElevator4.getElevatorID()));

		Elevator assignedElevator5 = scheduler.callElevator(person5);
		assertTrue(validElevatorIDs.contains(assignedElevator5.getElevatorID()));

		Elevator assignedElevator6 = scheduler.callElevator(person6);
		assertEquals(elevator7.getElevatorID(), assignedElevator6.getElevatorID());

		Elevator assignedElevator7 = scheduler.callElevator(person7);
		assertTrue(validElevatorIDs.contains(assignedElevator7.getElevatorID()));


	}

}
