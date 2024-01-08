package com.fdmgroup.ElevatorProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
		// Use the first elevator since all 3 start at same position
		assertEquals(assignedElevator.getElevatorID(), "Elevator0"); 
	}
	
	@Test
	void  scheduler_can_assign_different_elevators() {
		Person person1 = new Person(0,10);
		

		Elevator assignedElevator1 = scheduler.CallElevator(person1);
		
		Person person2 = new Person(0,10);
		Elevator assignedElevator2 = scheduler.CallElevator(person2);
		
		assertEquals(assignedElevator1.getElevatorID(), "Elevator0"); 
		assertEquals(assignedElevator2.getElevatorID(), "Elevator1");
	}

}
