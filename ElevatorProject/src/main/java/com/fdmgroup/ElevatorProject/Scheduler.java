package com.fdmgroup.ElevatorProject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class Scheduler implements Serializable {
	private ArrayList<Elevator> elevators = new ArrayList<Elevator>();
	
	public Scheduler(ArrayList<Elevator> elevators) {
		this.elevators = elevators;
	}
	
	public ArrayList<Elevator> getElevators() {
		return elevators;
	}

	public void AddElevator(Elevator elevator) {
		this.elevators.add(elevator);
	}

	public Elevator CallElevator(Person person) {
		Elevator bestElevator = null;
		Elevator closestElevator = null;
		int minDistance = Integer.MAX_VALUE;
		
		for ( Elevator elevator : elevators ) {
			int distance = Math.abs(elevator.getCurrentFloor() - person.getSrcFloor());
			
			if ( distance < minDistance ) {
				minDistance = distance;
				closestElevator = elevator;
				
				if ( elevator.isIdle() || ( person.isGoingUp() == elevator.isGoingUp() ) ) {
					bestElevator = elevator;
				}
			}
		}
		
		return bestElevator != null ? bestElevator : closestElevator;
	}
	
	public void RunElevators() {
		for ( Elevator elevator : elevators ) {
			new Thread(elevator).start();
		}
	}

	public void serializeSystemState(String fileName)
	{
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))){
			output.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Scheduler deserializeSchedulerState(String filename) {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename))) {
            return (Scheduler) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}
