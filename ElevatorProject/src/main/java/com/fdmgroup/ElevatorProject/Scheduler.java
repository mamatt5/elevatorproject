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
	 * This method allocates an Elevator object to be assigned to a Person object. It is called by the Controller object.
	 * 
	 * It works by first checking if there are any idle Elevators available, then if all Elevators are active, it will choose
	 * the nearest Elevator going in the same direction as the Person. If nothing else, it will choose the nearest Elevator. 
	 * On both occasions, if the floorsToGo set of the elevator is greater than 10, it will not choose the Elevator as other 
	 * Elevators will be underutilized. In this case, if there are no other Elevators available with these conditions, it will
	 * wait for 1 second, then check again with the conditions until an Elevator is chosen.
	 * 
	 * @param person
	 * @return Elevator
	 * @throws InterruptedException 
	 */
	public synchronized Elevator callElevator(Person person) throws InterruptedException {
		Elevator bestElevator = null;
		Elevator closestIdleElevator = null;
		int minIdleDistance = Integer.MAX_VALUE;
		int minDistance = Integer.MAX_VALUE;

		while (bestElevator == null && closestIdleElevator == null) {
			for (Elevator elevator : elevators) {
				int distance = Math.abs(elevator.getCurrentFloor() - person.getSrcFloor());

				// Gets nearest idle Elevator first, meaning Elevator does not have people inside
				if (elevator.isIdle() && distance < minIdleDistance 
						&& elevator.getFloorsToGo().size() <= 5) {
					closestIdleElevator = elevator;
					minIdleDistance = distance;
				}

				// If no idle Elevator, gets minimum distance between Elevator and person, then checks if same direction
				if (distance < minDistance && (elevator.isIdle() || (person.isGoingUp() == elevator.isGoingUp()))
						&& elevator.getFloorsToGo().size() <= 5) {
					bestElevator = elevator;
					minDistance = distance;
				}
			}

			if (bestElevator == null && closestIdleElevator == null ) {
				wait(1000);
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
