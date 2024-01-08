package com.fdmgroup.ElevatorProject;

import java.util.ArrayList;
import java.util.Scanner;

public class ElevatorConsole
{

	private final static String configFilePath = "../ElevatorProject/src/main/resources/Configurations.txt";
		
	public static void main(String[] args)
	{
		
		Configurations configs = ReadConfiguration.getConfiguration(configFilePath);
		
		if (configs == null) {
			System.out.println("Error occured when reading configuration file. Please double check the "
					+ "configuration file and relauch the program");
			return;
		}
		
		int numElevators = configs.getNumOfElevators();
		int maxFloor = configs.getMaxFloor();
		int minFloor = configs.getMinFloor();
		
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
	    	
	    	// Assign elevators to the people in the queue
	        try
	        {
	            System.out.println("Assigning elevators to queued requests...");
	            controller.assignElevator();
	        } catch (InterruptedException e)
	        {
	            e.printStackTrace();
	        }
	    }
		
	    myObj.close();
	}

}
