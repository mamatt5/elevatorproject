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
		
		// configs attributes
		int numElevators = configs.getNumOfElevators();
		int minFloor = configs.getMinFloor();
		int maxFloor = configs.getMaxFloor();
		boolean generateCommands = configs.getGenerateCommands();
		
		// elevator generation
		System.out.println("Creating elevators...");
		Elevator elevator;
		ArrayList<Elevator> elevators = new ArrayList<>();
		for (int i = 0; i < numElevators; i++) {
			elevator = new Elevator();
			elevator.setCurrentFloor(minFloor);
			elevators.add(elevator);
		}
	
		// elevator management
        Scheduler scheduler = new Scheduler(elevators);
		Controller controller = new Controller(scheduler);
		
		// start elevator threads
		System.out.println("Starting elevators...");
		scheduler.runElevators();
		
		// user interaction
		Scanner myObj = new Scanner(System.in);
	    System.out.println("Enter your commands: ");
	    String input ="";
	    
		if (generateCommands) {
			GenerateCommands generator = new GenerateCommands(minFloor, maxFloor);
		}
		InputValidation inputValidation = new InputValidation(minFloor, maxFloor);
	    FrameView GUI = new FrameView(minFloor, maxFloor, numElevators, elevators);
	    //Thread t = new Thread(GUI);
	    //t.run();
	    GUI.run();
		
		// handle user input for elevator requests and manages elevator assignments
	    while(true) {
	    	input = myObj.nextLine();
	    	
	    	if (input.equals("q")) {        // user termination command
	    		break;
	    	}
			
			// add Person objects to the elevator queue
    		int[][] requests = inputValidation.InputTo2DArray(input);
    		
    		if (configs.getGenerateCommands()) {
    			
    		}
    		
		    for (int[] request : requests) {
				controller.addPersonToQueue(new Person(request[0], request[1]));
		    }
			
    		// assign available elevators to people in queue
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
