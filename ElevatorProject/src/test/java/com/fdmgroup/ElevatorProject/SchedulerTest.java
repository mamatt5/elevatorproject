package com.fdmgroup.ElevatorProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SchedulerTest {
	Scheduler scheduler;
	
	@BeforeEach
	void setup() {
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

}
