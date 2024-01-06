package com.fdmgroup.ElevatorProject;

import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) {
        // Create elevators
        Elevator elevator1 = new Elevator();
        Elevator elevator2 = new Elevator();
        Elevator elevator3 = new Elevator();

        // Add elevators to a list
        ArrayList<Elevator> elevators = new ArrayList<>();
        elevators.add(elevator1);
        elevators.add(elevator2);
        elevators.add(elevator3);

        // Create a Scheduler and assign the elevators to it
        Scheduler scheduler = new Scheduler(elevators);

        // Create a Controller and assign the Scheduler to it
        Controller controller = new Controller(scheduler);

        // Start elevator threads
        scheduler.RunElevators();

        // Simulate some elevator requests
        controller.addPersonToQueue(new Person(1, 4));
        controller.addPersonToQueue(new Person(3, 7));
        controller.addPersonToQueue(new Person(6, 2));

        // Assign elevators to the people in the queue
        controller.assignElevator();


    }
}
