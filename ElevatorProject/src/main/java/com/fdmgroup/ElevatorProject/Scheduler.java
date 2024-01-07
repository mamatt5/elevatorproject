package com.fdmgroup.ElevatorProject;

import java.util.ArrayList;


public class Scheduler {
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

}
