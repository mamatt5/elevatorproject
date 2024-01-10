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
		int interval = configs.getIntervalBetweenCommands();
		
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
	    System.out.println("Available commands: ");
	    System.out.println("'q': exit program");
	    System.out.println("'srcFloor:dstFloor': move elevator");
	    System.out.println("'setsource=(int)': set a source floor for all people");
	    System.out.println("'setdestination=(int)': set a destination floor for all people");
	    System.out.println("'setsource=off': turn off set source floor");
	    System.out.println("'setdestination=off': turn off set destination floor");
	    System.out.println("'setinterval=(int)': set a time interval for commands to generate");
	    System.out.println("'commandgeneration=on/off': set a time interval for commands to generate");
	    String input ="";
	    
	    GenerateCommands generator = null;
	    
	    // if command generation is set to true in the config file
	    if (generateCommands) {
			generator = new GenerateCommands(maxFloor, minFloor, interval, controller);
			generator.run();
		}
	    
		InputValidation inputValidation = new InputValidation(minFloor, maxFloor);
		
		// runs the GUI
	    FrameView GUI = new FrameView(minFloor, maxFloor, numElevators, elevators);
	    GUI.run();
	    
		// handle user input for elevator requests and manages elevator assignments
	    int srcFloor = -1;
    	int dstFloor = -1;
    	
	    while(true) {
	    	input = myObj.nextLine();
	    	input.replaceAll(" ", "");
	    	input = input.toLowerCase();
	    	if (input.equals("q")) {        // user termination command
	    		break;
	    	}
	    	
	    	
	    	// Turn off the set source floor
	    	if (input.equals("setsource=off"))
	    		srcFloor = -1;
	    	
	    	// Turn off the set destination floor
	    	if (input.equals("setdestination=off"))
	    		dstFloor = -1;
	    	
	    	// Turn on the command generation
	    	if (input.equals("commandgeneration=on")) {
	    		generateCommands = true;
	    		
	    		if (generator == null) {
	    			System.out.println("beans");
	    			generator = new GenerateCommands(maxFloor, minFloor, interval, controller);
	    		}
	    		
				generator.run();
	    	}
	    	
	    	// Turn off the command generation
	    	if (input.equals("commandgeneration=off")) {
	    		generateCommands = false;
	    		generator.kill();
	    	}
	    		
	    	
	    	// Command to set source floor to a particular number
	    	if (input.matches("setsource=-?[0-9]\\d*")) {
	    		int floor = Integer.parseInt(input.split("=")[1]);
	    		if (floor > minFloor - 1 && floor < maxFloor + 1) {
	    			srcFloor = floor;
	    			if (generateCommands)
	    				generator.setMinFloor(srcFloor);
	    				
	    		} else {
	    			System.out.println("Invalid floor number");
	    		}
	    			
	    	}
	    	
	    	// Command to set destination floor to a particular number
	    	if (input.matches("setdestination=-?[0-9]\\d*")) {
	    		int floor = Integer.parseInt(input.split("=")[1]);
	    		if (floor > minFloor - 1 && floor < maxFloor + 1) {
	    			dstFloor = floor;
	    			if (generateCommands)
	    				generator.setMaxFloor(dstFloor);
	    		} else {
	    			System.out.println("Invalid floor number");
	    		}
	    			
	    	}
	    	

	    	// Command to an interval to commands to generate
	    	if (input.matches("setinterval=-?[0-9]\\d*")) {
	    		int intervalCommand = Integer.parseInt(input.split("=")[1]);

	    		if (generateCommands) {
	    			if (intervalCommand > 0) {
	    		
	    				generator.setInterval(intervalCommand);
	    			} else {
	    				System.out.println("Invalid Interval");
	    			} 

	    		} else {
	    			System.out.println("You have not enabled command generation");
	    		}
	    		
	    	}
	    	
			// add Person objects to the elevator queue
    		int[][] requests = inputValidation.InputTo2DArray(input);
    		
		    for (int[] request : requests) {
		    	
		    	// If there hasn't been a source/destination floor set use input values
		    	if (srcFloor == -1)
		    		srcFloor = request[0];
		    	
		    	if (dstFloor == -1)
		    		dstFloor = request[1];
		    	
				controller.addPersonToQueue(new Person(srcFloor, dstFloor));
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
