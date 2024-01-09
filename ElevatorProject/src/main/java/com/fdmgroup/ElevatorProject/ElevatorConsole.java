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
			System.out.println("Error occurred when reading configuration file. Please double check the "
					+ "configuration file and relaunch the program");
			return;
		}
		
		int numElevators = configs.getNumOfElevators();
		
		// fixme: unused variables
//		int maxFloor = configs.getMaxFloor();
//		int minFloor = configs.getMinFloor();
		
		// TODO: use config to create number of elevators
		System.out.println("Creating elevators...");
		
		Elevator elevator;
		ArrayList<Elevator> elevators = new ArrayList<>();
		for (int i = 0; i < numElevators; i++) {
			elevator = new Elevator();
			elevators.add(elevator);
		}
	
        Scheduler scheduler = new Scheduler(elevators);
		Controller controller = new Controller(scheduler);
		
		// Start elevator threads
		System.out.println("Starting elevators...");
		scheduler.runElevators();
		
		Scanner myObj = new Scanner(System.in);
		InputValidation inputValidation = new InputValidation();
	    System.out.println("Enter your commands: ");
	    
	    String input ="";
	    
	    while(true) {
	    	
	    	input = myObj.nextLine();
	    	
	    	if (input.equals("q")) {
	    		break;
	    	}
	    	
//	    	(inputValidation.isValidRequest(InputTo2DArray))
    		String[] people = input.split(",");
    		
    		for (String person: people) {
    			String[] floors = person.split(":");
    			int srcFloor = Integer.parseInt(floors[0]);
    			int dstFloor = Integer.parseInt(floors[1]);
    			
    			controller.addPersonToQueue(new Person(srcFloor, dstFloor));
    		}
    		// Assign elevators to the people in the queue
	        try {
	            controller.assignElevator();
	        }
			catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    	
	    }
		
	    myObj.close();
	}

}
