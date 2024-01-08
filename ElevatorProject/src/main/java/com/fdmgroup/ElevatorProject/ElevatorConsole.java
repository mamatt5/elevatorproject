package com.fdmgroup.ElevatorProject;

import java.util.ArrayList;
import java.util.Scanner;

public class ElevatorConsole
{

	public static void main(String[] args)
	{
		// TODO: use config to create number of elevators
		System.out.println("Creating elevators...");
        Elevator elevator1 = new Elevator();
        Elevator elevator2 = new Elevator();
        Elevator elevator3 = new Elevator();

        // Add elevators to a list
        ArrayList<Elevator> elevators = new ArrayList<>();
        elevators.add(elevator1);
        elevators.add(elevator2);
        elevators.add(elevator3);
        
        Scheduler scheduler = new Scheduler(elevators);
		
		Controller controller = new Controller(scheduler);
        
		Scanner myObj = new Scanner(System.in);
		InputValidation inputValidation = new InputValidation();
	    System.out.println("Enter your commands: ");
	    
	    String input ="";
	    
	    while(true) {
	    	
	    	input = myObj.nextLine();
	    	
	    	if (input.equals("q")) {
	    		break;
	    	}
	    	
	    	if (inputValidation.isValidInput(input)) {
	    		String[] people = input.split(",");
	    		
	    		for (String person: people) {
	    			
	    			String[] floors = person.split(":");
	    			int srcFloor = Integer.parseInt(floors[0]);
	    			int dstFloor = Integer.parseInt(floors[1]);
	    			
	    			controller.addPersonToQueue(new Person(srcFloor, dstFloor));
	    		}
	    	}
	    }
		
	    myObj.close();
	}

}
