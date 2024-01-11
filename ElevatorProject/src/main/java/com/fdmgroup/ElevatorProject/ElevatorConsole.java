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
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
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
	    System.out.println("'save=[filename].ser': saves the state of the current elevators");
	    System.out.println("'load=[filename].ser': loads the state of the saved elevators");
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

    	boolean setSrcOn = false;
    	boolean setDstOn = false;

	    while(true) {
	    	input = myObj.nextLine();
	    	if (input.equals("q")) {        // user termination command
	    		break;
	    	}
	    
	    	
	    	// Turn off the set source floor
	    	if (input.equals("setsource=off")) {
	    		srcFloor = -1;
	    		setSrcOn = false;
	    	}
	    	// Turn off the set destination floor
	    	if (input.equals("setdestination=off")) {
	    		dstFloor = -1;
				setDstOn = false;
			}

			
	    	// Turn on the command generation
	    	if (input.equals("commandgeneration=on")) {
	    		generateCommands = true;
	    		
	    		if (generator == null) {
	    			generator = new GenerateCommands(maxFloor, minFloor, interval, controller);
	    		}
	    		
				generator.run();
	    	}
	    	
	    	// Turn off the command generation
	    	if (input.equals("commandgeneration=off")) {
	    		generateCommands = false;
	    		if (generator != null) {
	    			generator.kill();
	    		}
	    			
	    	}
	    	
	    	// Command to set source floor to a particular number
	    	if (input.matches("setsource=-?[0-9]\\d*")) {
	    		int floor = Integer.parseInt(input.split("=")[1]);
	    		if (floor > minFloor - 1 && floor < maxFloor + 1) {
	    			srcFloor = floor;
					setSrcOn = true;
					
	    			if (generateCommands) {
	    				generator.kill();
	    				generator.setMinFloor(srcFloor);
	    				generator.run();
	    			}
	    			
	    				
	    		} else {
	    			System.out.println("Invalid floor number");
	    		}
	    			
	    	}
	    	
	    	// Command to set destination floor to a particular number
	    	if (input.matches("setdestination=-?[0-9]\\d*")) {
	    		
	    		int floor = Integer.parseInt(input.split("=")[1]);
	    		if (floor > minFloor - 1 && floor < maxFloor + 1) {
	    			dstFloor = floor;
					setDstOn = true;

	    			if (generateCommands) {
	    				generator.kill();
	    				generator.setMaxFloor(dstFloor);
	    				generator.run();
	    			}
	    				
	    		} else {

	    			System.out.println("Invalid floor number");
	    		}
	    			
	    	}
	    	

	    	// Command to an interval to commands to generate
	    	if (input.matches("setinterval=-?[0-9]\\d*")) {
	    		int intervalCommand = Integer.parseInt(input.split("=")[1]);

	    		if (generateCommands) {
	    			if (intervalCommand > 0) {
	    				generator.kill();
	    				generator.setInterval(intervalCommand);
	    			} else {
	    				System.out.println("Invalid Interval");
	    			} 

	    		} else {
	    			System.out.println("You have not enabled command generation");
	    		}
	    		
	    	}
	    
	    	// saving the state of the system to a file
	    	if (input.matches("save=[aA-zZ0-9]*\\.ser")) {
	    	
	    		String fileName = input.split("=")[1];
	    		scheduler.serializeSystemState(fileName);
	    		System.out.println("Saved current system state to file " + fileName);
	    	}
	    	
	    	// loads a state of the system
	    	if (input.matches("load=[aA-zZ0-9]*\\.ser")) {
		    	
	    		String fileName = input.split("=")[1];
	    		Scheduler newScheduler = scheduler.deserializeSchedulerState(fileName);
	    		GUI.close();
	  
	    		scheduler = newScheduler;
	    		controller = new Controller(scheduler);
	    		
	    		scheduler.runElevators();
	    		GUI = new FrameView(minFloor, maxFloor, scheduler.getElevators().size(), scheduler.getElevators());
	    	    GUI.run();
	    		System.out.println("Loaded system state from file " + fileName);
	    		
	    	}
	    	
			// add Person objects to the elevator queue
    		int[][] requests = inputValidation.InputTo2DArray(input);
    		
		    for (int[] request : requests) {
		    	
		    	// If there hasn't been a source/destination floor set use input values
		    	if (setSrcOn == false)
		    		srcFloor = request[0];
		    	
		    	if (setDstOn == false)
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
	    
	    for (Elevator e : elevators) {
	    	e.kill();
	    }
	    
	    GUI.close();
	    myObj.close();
	    
	}

}
