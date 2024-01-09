package com.fdmgroup.ElevatorProject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This object chooses to which Elevator from an array of instantiated Elevator objects is best for the Person object to be
 * loaded into. Note that it only chooses which Elevator but does not directly load the Person into the Elevator. This object
 * can be instantiated by default with an empty ArrayList of Elevators.
 */

@SuppressWarnings("serial")
public class Scheduler implements Serializable
{
	private ArrayList<Elevator> elevators = new ArrayList<Elevator>();

	public Scheduler(ArrayList<Elevator> elevators) {
		this.elevators = elevators;
	}

	public ArrayList<Elevator> getElevators() {
		return elevators;
	}
	
	// Adds an instantiated Elevator into its list.
	public void addElevator(Elevator elevator) {
		this.elevators.add(elevator);
	}
	
	/**
	 * Determines the best elevator to respond to a person's request based on current elevators' states.
	 *
	 * @param person The person requesting an elevator.
	 * @return The most appropriate Elevator object to respond to the request.
	 */

	
	public synchronized Elevator callElevator(Person person) {
		//	It works by first checking if there are any idle Elevators available, then if all Elevators are active,
		//	it will choose the nearest Elevator going in the same direction as the Person. If nothing else, it will
		//	choose the nearest Elevator. It will return an Elevator object. To avoid errors due to synchronization,
		//	it will recursively call itself if it returns a null.
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
	    
	    if (bestElevator == null && closestIdleElevator == null ) {
	    	callElevator(person);
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
