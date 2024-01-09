package com.fdmgroup.ElevatorProject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Scheduler implements Serializable
{
	private ArrayList<Elevator> elevators = new ArrayList<Elevator>();

	public Scheduler(ArrayList<Elevator> elevators)
	{
		this.elevators = elevators;
	}

	public ArrayList<Elevator> getElevators()
	{
		return elevators;
	}

	public void addElevator(Elevator elevator)
	{
		this.elevators.add(elevator);
	}
	
	
	/**
	 * Determines the best elevator to respond to a person's request based on current elevators' states.
	 *
	 * @param person The person requesting an elevator.
	 * @return The most appropriate Elevator object to respond to the request.
	 */
	public Elevator callElevator(Person person) {
	    Elevator bestElevator = null;
	    Elevator closestIdleElevator = null;
	    int minIdleDistance = Integer.MAX_VALUE;
	    int minDistance = Integer.MAX_VALUE;

	    for (Elevator elevator : elevators) {
	        int distance = Math.abs(elevator.getCurrentFloor() - person.getSrcFloor());

	        // Gets nearest idle Elevator first, meaning Elevator does not have people inside
	        if (elevator.isIdle() && distance < minIdleDistance) {
	            closestIdleElevator = elevator;
	            minIdleDistance = distance;
	        }

	        // If no idle Elevator, gets minimum distance between Elevator and person, then checks if same direction
	        if (distance < minDistance && (elevator.isIdle() || (person.isGoingUp() == elevator.isGoingUp()) ) ) {
	            bestElevator = elevator;
	            minDistance = distance;
	        }
	    }

	    return bestElevator != null ? bestElevator : closestIdleElevator;
	}
	
	/**
	 * Starts threads for each elevator for independent function.
	 */
	public void runElevators() {
		for (Elevator elevator : elevators) {
			new Thread(elevator).start();
		}
	}
	
	/**
	 * Serializes the current state of the scheduler into a file.
	 *
	 * @param fileName The name of the file to serialize the scheduler state into.
	 */
	public void serializeSystemState(String fileName) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
			output.writeObject(this);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Deserializes and retrieves the scheduler state from a file.
	 *
	 * @param filename The name of the file to deserialize the scheduler state from.
	 * @return The Scheduler object retrieved from the file.
	 */
	public Scheduler deserializeSchedulerState(String filename) {
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename))) {
			return (Scheduler) input.readObject();
		}
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
