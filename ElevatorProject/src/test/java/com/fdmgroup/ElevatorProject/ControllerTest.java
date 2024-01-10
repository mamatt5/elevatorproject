package com.fdmgroup.ElevatorProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ControllerTest
{
	Controller controller;

	@BeforeEach
	void setup()
	{
		Elevator elevator1 = new Elevator();
		Elevator elevator2 = new Elevator();
		Elevator elevator3 = new Elevator();
		ArrayList<Elevator> elevators = new ArrayList<Elevator>();

		elevators.add(elevator1);
		elevators.add(elevator2);
		elevators.add(elevator3);
		Scheduler scheduler = new Scheduler(elevators);

		controller = new Controller(scheduler);

	}

	@Test
	void assign_elevator_with_empty_queue() {
		assertEquals(0, controller.getPeopleQueue().size());
		
		try { 
			controller.assignElevator();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertEquals(0, controller.getPeopleQueue().size());
	}

	@Test
	void add_person_to_queue_from_ground_floor_going_up()
	{
		Person person1 = new Person(0, 10);

		controller.addPersonToQueue(person1);

		assertEquals(1, controller.getPeopleQueue().size());
	}
	
	@Test
	void add_person_to_queue_from_different_floor_going_up()
	{
		Person person1 = new Person(3, 10);

		controller.addPersonToQueue(person1);

		assertEquals(1, controller.getPeopleQueue().size());
	}
	
	@Test
	void add_person_to_queue_from_going_down_to_different_floor()
	{
		Person person1 = new Person(10, 3);

		controller.addPersonToQueue(person1);

		assertEquals(1, controller.getPeopleQueue().size());
	}
	
	@Test
	void add_person_to_queue_from_going_down_to_ground_floor()
	{
		Person person1 = new Person(6, 0);

		controller.addPersonToQueue(person1);

		assertEquals(1, controller.getPeopleQueue().size());
	}

	@Test
	void add_multiple_person_to_queue_from_ground_floor()
	{
		Person person1 = new Person(0, 10);
		Person person2 = new Person(0, 5);
		Person person3 = new Person(0, 1);

		controller.addPersonToQueue(person1);
		controller.addPersonToQueue(person2);
		controller.addPersonToQueue(person3);

		assertEquals(3, controller.getPeopleQueue().size());
	}
	
	@Test
	void add_multiple_person_to_queue_from_different_floors_going_up()
	{
		Person person1 = new Person(3, 10);
		Person person2 = new Person(2, 5);
		Person person3 = new Person(0, 1);

		controller.addPersonToQueue(person1);
		controller.addPersonToQueue(person2);
		controller.addPersonToQueue(person3);

		assertEquals(3, controller.getPeopleQueue().size());
	}
	
	@Test
	void add_multiple_person_to_queue_from_different_floors_going_down()
	{
		Person person1 = new Person(10, 2);
		Person person2 = new Person(7, 1);
		Person person3 = new Person(4, 3);

		controller.addPersonToQueue(person1);
		controller.addPersonToQueue(person2);
		controller.addPersonToQueue(person3);

		assertEquals(3, controller.getPeopleQueue().size());
	}

	@Test
	void assign_elevator_with_1_person_ground_floor_going_up_to_load() throws InterruptedException
	{
		Person person1 = new Person(0, 1);

		controller.addPersonToQueue(person1);
		try
		{
			controller.assignElevator();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		assertTrue(controller.FindElevatorWithPerson(person1).getPeopleOutsideToLoad().contains(person1));
	}
	
	@Test
	void assign_elevator_3_person_ground_floor_going_up_to_load()
	{
		Person person1 = new Person(0, 10);
		Person person2 = new Person(0, 5);
		Person person3 = new Person(0, 1);

		controller.addPersonToQueue(person1);
		controller.addPersonToQueue(person2);
		controller.addPersonToQueue(person3);
		try
		{
			controller.assignElevator();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		assertTrue(controller.FindElevatorWithPerson(person1).getPeopleOutsideToLoad().contains(person1));
		assertTrue(controller.FindElevatorWithPerson(person2).getPeopleOutsideToLoad().contains(person2));
		assertTrue(controller.FindElevatorWithPerson(person3).getPeopleOutsideToLoad().contains(person3));
	}
	
	@Test
	void assign_elevator_3_person_different_floor_going_up_to_load()
	{
		Person person1 = new Person(3, 10);
		Person person2 = new Person(2, 5);
		Person person3 = new Person(6, 8);

		controller.addPersonToQueue(person1);
		controller.addPersonToQueue(person2);
		controller.addPersonToQueue(person3);
		try
		{
			controller.assignElevator();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		assertTrue(controller.FindElevatorWithPerson(person1).getPeopleOutsideToLoad().contains(person1));
		assertTrue(controller.FindElevatorWithPerson(person2).getPeopleOutsideToLoad().contains(person2));
		assertTrue(controller.FindElevatorWithPerson(person3).getPeopleOutsideToLoad().contains(person3));
	}
	
	@Test
	void assign_elevator_1_person_going_down_ground_floor_to_load()
	{
		Person person1 = new Person(3, 0);
		Person person2 = new Person(2, 0);
		Person person3 = new Person(6, 0);

		controller.addPersonToQueue(person1);
		controller.addPersonToQueue(person2);
		controller.addPersonToQueue(person3);
		try
		{
			controller.assignElevator();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		assertTrue(controller.FindElevatorWithPerson(person1).getPeopleOutsideToLoad().contains(person1));
		assertTrue(controller.FindElevatorWithPerson(person2).getPeopleOutsideToLoad().contains(person2));
		assertTrue(controller.FindElevatorWithPerson(person3).getPeopleOutsideToLoad().contains(person3));
	}
	
	@Test
	void assign_elevator_1_person_going_down_different_floor_to_load()
	{
		Person person1 = new Person(3, 1);
		Person person2 = new Person(7, 2);
		Person person3 = new Person(6, 3);

		controller.addPersonToQueue(person1);
		controller.addPersonToQueue(person2);
		controller.addPersonToQueue(person3);
		try
		{
			controller.assignElevator();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		assertTrue(controller.FindElevatorWithPerson(person1).getPeopleOutsideToLoad().contains(person1));
		assertTrue(controller.FindElevatorWithPerson(person2).getPeopleOutsideToLoad().contains(person2));
		assertTrue(controller.FindElevatorWithPerson(person3).getPeopleOutsideToLoad().contains(person3));
	}
	

}
