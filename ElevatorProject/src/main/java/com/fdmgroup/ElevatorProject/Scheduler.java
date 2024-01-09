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

	public void AddElevator(Elevator elevator)
	{
		this.elevators.add(elevator);
	}

	public Elevator CallElevator(Person person) {
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
