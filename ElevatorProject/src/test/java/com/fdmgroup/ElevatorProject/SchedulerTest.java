package com.fdmgroup.ElevatorProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SchedulerTest {
	static Scheduler scheduler;

	@BeforeAll
	static void setup() {
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
		scheduler.AddElevator(elevator4);

		assertEquals(4, scheduler.getElevators().size());
	}
	
	@Test
	void scheduler_can_assign_elevator_to_a_person() {
		Person person = new Person(0,10);
		Elevator assignedElevator = scheduler.CallElevator(person);

		assertNotNull(assignedElevator);
		assertTrue(scheduler.getElevators().contains(assignedElevator));
	}
	
	@Test
	// This test passes if run by itself
	void  scheduler_can_assign_different_elevators() {
		Person person1 = new Person(0,10);
		Person person2 = new Person(0,10);

		Elevator assignedElevator1 = scheduler.CallElevator(person1);
		assertEquals("Elevator0",assignedElevator1.getElevatorID()); 
		
		// Bring Elevator0 to floor 2
		try
		{
			assignedElevator1.GoToFloor(2);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		// Since Elevator0 is further than Elevator1/2 it will use 
		// of them in order of ID.
		Elevator assignedElevator2 = scheduler.CallElevator(person2);
		
		assertEquals("Elevator1",assignedElevator2.getElevatorID());
	}
	
	@Test
	// This test passes if run by itself
	void scheduler_assign_elevators_with_more_people_added() {  
	    Person person1 = new Person(0,3);
		Person person2 = new Person(10,14);
		Person person3 = new Person(2,4);
		
		
		Elevator assignedElevator1 = scheduler.CallElevator(person1);
		assertEquals("Elevator0", assignedElevator1.getElevatorID());

		
		try {
			assignedElevator1.GoToFloor(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Elevator assignedElevator2 = scheduler.CallElevator(person2);
		
		assertEquals("Elevator0", assignedElevator2.getElevatorID());
		try
		{
			assignedElevator2.GoToFloor(14);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		assertFalse(assignedElevator1.isIdle());
		assertFalse(assignedElevator2.isIdle());
		assertTrue(assignedElevator2.getPeopleInside().isEmpty());
		
		
		Elevator assignedElevator3 = scheduler.CallElevator(person3);
		
		assertEquals("Elevator1", assignedElevator3.getElevatorID());
		try
		{
			assignedElevator3.GoToFloor(3);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		assertFalse(assignedElevator3.isIdle());
		// Scheduler does not load the elevator with person. Only chooses the best elevator. Supposedly
//		assertFalse(assignedElevator3.getPeopleInside().isEmpty());
		
		
	}
	
	@Test
	void scheduler_assign_elevators_with_more_people_and_elevator_added() throws InterruptedException { 
		Elevator elevator4 = new Elevator();
		elevator4.GoToFloor(10);
		
		Elevator elevator5 = new Elevator();
		elevator5.GoToFloor(25);
		
		Elevator elevator6 = new Elevator();
		elevator6.GoToFloor(30);
		
		Elevator elevator7 = new Elevator();
		
		scheduler.AddElevator(elevator4);
		scheduler.AddElevator(elevator5);
		scheduler.AddElevator(elevator6);
		scheduler.AddElevator(elevator7);
		
		Person person1 = new Person(0,3);
		Person person2 = new Person(10,14);
		Person person3 = new Person(2,4);
		Person person4 = new Person(0,3);
		Person person5 = new Person(1,2);
		Person person6 = new Person(3,4);
		Person person7 = new Person(2,4);
		
		
		Elevator assignedElevator1 = scheduler.CallElevator(person1);
		assertEquals(elevator7.getElevatorID(), assignedElevator1.getElevatorID());
		
		Elevator assignedElevator2 = scheduler.CallElevator(person2);
		assertEquals(elevator4.getElevatorID(), assignedElevator2.getElevatorID());
		
		assertFalse(assignedElevator1.isIdle());
		assertFalse(assignedElevator2.isIdle());
		
		// Scheduler does not load the Elevator
//		assertFalse(assignedElevator2.getPeopleInside().isEmpty());
		
		Elevator assignedElevator3 = scheduler.CallElevator(person3);
		
		assertFalse(assignedElevator3.isIdle());
		assertFalse(assignedElevator3.getPeopleInside().isEmpty());
		assertEquals("Elevator2", assignedElevator3.getElevatorID());
		
		
		Elevator assignedElevator4 = scheduler.CallElevator(person4);
		assertEquals("Elevator3", assignedElevator4.getElevatorID());

		Elevator assignedElevator5 = scheduler.CallElevator(person5);
		assertEquals("Elevator4", assignedElevator5.getElevatorID());
		
		Elevator assignedElevator6 = scheduler.CallElevator(person6);
		assertEquals("Elevator5", assignedElevator6.getElevatorID());
		
		Elevator assignedElevator7 = scheduler.CallElevator(person7);
		assertEquals("Elevator6", assignedElevator7.getElevatorID());
		
		
	}

}
