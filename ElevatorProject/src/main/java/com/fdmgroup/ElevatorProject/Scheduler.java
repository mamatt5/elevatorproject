package com.fdmgroup.ElevatorProject;

import java.util.ArrayList;


public class Scheduler {
	private ArrayList<Elevator> elevators;
	
	public Elevator callElevator(Controller controller) {
		Elevator bestElevator = null;
		int minDistance = Integer.MAX_VALUE;
		Person person = controller.getPerson();
		
		for ( Elevator elevator : elevators ) {
			int distance = Math.abs(elevator.getCurrentFloor() - person.getSrcFloor());
			
			if ( distance < minDistance ) {
				minDistance = distance;
				
				if ( elevator.isIdle() || person.isGoingUp() == elevator.isGoingUp() ) {
					bestElevator = elevator;
				}
			}
		}
		
		return bestElevator;
	}

}
