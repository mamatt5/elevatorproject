package com.fdmgroup.ElevatorProject;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main class controlling the Elevator system through the console interface.
 * Reads configurations, initializes elevators, and manages user input to interact with the Elevator system.
 */
public class ElevatorConsole {
	private final static String configFilePath = "../ElevatorProject/src/main/resources/Configurations.txt";
	
	/**
	 * Main method initiating the Elevator system through the console.
	 */
	public static void main(String[] args) {
		Configurations configs = ReadConfiguration.getConfiguration(configFilePath);
		
		if (configs == null) {
			System.out.println("Error occurred when reading configuration file. Please double check the "
					+ "configuration file and relaunch the program");
			return;
		}
		
		int numElevators = configs.getNumOfElevators();
		
		int minFloor = configs.getMinFloor();
		int maxFloor = configs.getMaxFloor();
		
		System.out.println("Creating elevators...");
		
		Elevator elevator;
		ArrayList<Elevator> elevators = new ArrayList<>();
		for (int i = 0; i < numElevators; i++) {
			elevator = new Elevator();
			elevator.setCurrentFloor(minFloor);
			elevators.add(elevator);
		}
	
        Scheduler scheduler = new Scheduler(elevators);
		Controller controller = new Controller(scheduler);
		
		// Start elevator threads
		System.out.println("Starting elevators...");
		scheduler.runElevators();
		
		Scanner myObj = new Scanner(System.in);
		
		InputValidation inputValidation = new InputValidation(minFloor, maxFloor);
	    System.out.println("Enter your commands: ");
	    
	    String input ="";
	    
	    FrameView GUI = new FrameView(minFloor, maxFloor, numElevators, elevators);
	    
	    //Thread t = new Thread(GUI);
	    //t.run();
	    GUI.run();
	    
	    while(true) {
	    	input = myObj.nextLine();
	    	input = input.replaceAll(" ", "");
	    	
	    	if (input.equals("q")) {
	    		break;
	    	}
			
    		String[] people = input.split(",");
    		int[][] requests = inputValidation.InputTo2DArray(input);
    		
    		for (int i = 0; i < people.length; i++) {
    			
    			int srcFloor = requests[i][0];
    			int dstFloor = requests[i][1];
    			if (srcFloor != -1 && dstFloor != -1)
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
	    
	    GUI.close();
	    myObj.close();
	}

}
