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

	public Scheduler(ArrayList<Elevator> elevators)
	{
		this.elevators = elevators;
	}

	public ArrayList<Elevator> getElevators()
	{
		return elevators;
	}

	// Adds an instantiated Elevator into its list.
	public void AddElevator(Elevator elevator)
	{
		this.elevators.add(elevator);
	}

	/**
	 * This method allocates an Elevator object to be assigned to a Person object. It is called by the Controller object.
	 * 
	 * It works by first checking if there are any idle Elevators available, then if all Elevators are active, it will choose
	 * the nearest Elevator going in the same direction as the Person. If nothing else, it will choose the nearest Elevator.
	 * It will return an Elevator object. To avoid errors due to synchronization, it will recursively call itself if it returns
	 * a null.
	 * 
	 * TRY IMPROVE THIS LOGIC BEFORE SUBMITTING OTHERWISE IT WILL THROW STACKOVERFLOW ERROR. TRY TO AVOID CALLING THIS RECURSIVELY
	 * BY MODIFYING LOGIC WHEN NO ELEVATOR IS IDLE, AND NONE IS GOING IN THE SAME DIRECTION AS THE PERSON.
	 * @param person
	 * @return Elevator
	 */
	public synchronized Elevator CallElevator(Person person) {
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
	    	CallElevator(person);
	    }

	    return bestElevator != null ? bestElevator : closestIdleElevator;
	}


	// Starts the elevator objects as Threads.
	public void RunElevators()
	{
		for (Elevator elevator : elevators)
		{
			new Thread(elevator).start();
		}
	}

	public void serializeSystemState(String fileName)
	{
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName)))
		{
			output.writeObject(this);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public Scheduler deserializeSchedulerState(String filename)
	{
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename)))
		{
			return (Scheduler) input.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
	}

}
