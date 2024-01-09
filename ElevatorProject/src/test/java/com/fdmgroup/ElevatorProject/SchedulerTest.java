package com.fdmgroup.ElevatorProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
		scheduler.addElevator(elevator4);

		assertEquals(4, scheduler.getElevators().size());
	}
	
	@Test
	void scheduler_can_assign_elevator_to_a_person() {
		Person person = new Person(0,10);
		Elevator assignedElevator = scheduler.callElevator(person);

		assertNotNull(assignedElevator);
	}
	
	@Test
	void  scheduler_can_assign_different_elevators() {
		Person person1 = new Person(0,10);
		Person person2 = new Person(0,10);

		Elevator assignedElevator1 = scheduler.callElevator(person1);
		assertEquals(assignedElevator1.getElevatorID(), "Elevator0"); 
		
		// Bring Elevator0 to floor 2
		try
		{
			assignedElevator1.goToFloor(2);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		// Since Elevator0 is further than Elevator1/2 it will use 
		// of them in order of ID.
		Elevator assignedElevator2 = scheduler.callElevator(person2);
		
		assertEquals(assignedElevator2.getElevatorID(), "Elevator1");
	}

}
