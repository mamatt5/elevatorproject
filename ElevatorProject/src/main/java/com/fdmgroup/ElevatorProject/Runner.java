package com.fdmgroup.ElevatorProject;

import java.util.ArrayList;


public class Runner
{
	public static void main(String[] args)
	{
		// Create elevators
		System.out.println("Creating elevators...");
		Elevator elevator1 = new Elevator();
		Elevator elevator2 = new Elevator();
		Elevator elevator3 = new Elevator();
		Elevator elevator4 = new Elevator();
		Elevator elevator5 = new Elevator();


		// Add elevators to a list
		ArrayList<Elevator> elevators = new ArrayList<>();
		elevators.add(elevator1);
		elevators.add(elevator2);
		elevators.add(elevator3);
		elevators.add(elevator4);
		elevators.add(elevator5);

		// Create a Scheduler and assign the elevators to it
		Scheduler scheduler = new Scheduler(elevators);

		// Create a Controller and assign the Scheduler to it
		Controller controller = new Controller(scheduler);

		// Start elevator threads
		System.out.println("Starting elevators...");
		scheduler.runElevators();

        // Simulate some elevator requests
        System.out.println("Simulating elevator requests...");
        
        controller.addPersonToQueue(new Person(1, 4));
        System.out.println("Person 1 to 4 created");
        
        controller.addPersonToQueue(new Person(3, 7));
        System.out.println("Person 3 to 7 created");
        
        controller.addPersonToQueue(new Person(6, 2));
        System.out.println("Person 6 to 2 created");
        
        controller.addPersonToQueue(new Person(0, 5));
        System.out.println("Person 0 to 5 created");
        
        controller.addPersonToQueue(new Person(2, 10));
        System.out.println("Person 2 to 10 created");
        
        controller.addPersonToQueue(new Person(10, 4));
        System.out.println("Person 10 to 4 created");
        
        controller.addPersonToQueue(new Person(1, 4));
        System.out.println("Person 1 to 4 created");
        
        controller.addPersonToQueue(new Person(3, 7));
        System.out.println("Person 3 to 7 created");
        
        controller.addPersonToQueue(new Person(6, 2));
        System.out.println("Person 6 to 2 created");
        
        controller.addPersonToQueue(new Person(4, 4));
        System.out.println("Person 4 to 4 created");
        
        controller.addPersonToQueue(new Person(15, 3));
        System.out.println("Person 15 to 3 created");
        
        controller.addPersonToQueue(new Person(4, 10));
        System.out.println("Person 4 to 10 created");
        
        controller.addPersonToQueue(new Person(1, 2));
        System.out.println("Person 1 to 2 created");
        
        controller.addPersonToQueue(new Person(15, 0));
        System.out.println("Person 15 to 0 created");
        
        controller.addPersonToQueue(new Person(8, 12));
        System.out.println("Person 8 to 12 created");
        
        controller.addPersonToQueue(new Person(13, 15));
        System.out.println("Person 13 to 15 created");
        
        controller.addPersonToQueue(new Person(5, 8));
        System.out.println("Person 5 to 8 created");
        
        controller.addPersonToQueue(new Person(6, 1));
        System.out.println("Person 6 to 1 created");
        
        controller.addPersonToQueue(new Person(6, 2));
        System.out.println("Person 6 to 2 created");
        
        controller.addPersonToQueue(new Person(2, 9));
        System.out.println("Person 2 to 9 created");

		// Assign elevators to the people in the queue
		try
		{
			System.out.println("Assigning elevators to queued requests...");
			controller.assignElevator();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}


		// TODO: we want to know if all the elevators were utilized for all three people
		//  because theoretically, it should use all elevators.

		scheduler.serializeSystemState("elevatorSystemState.ser");

		// Deserialise to restart/reboot
		//Scheduler rebootScheduler = scheduler.deserializeSchedulerState("elevatorSystemState.ser");

	}
}
