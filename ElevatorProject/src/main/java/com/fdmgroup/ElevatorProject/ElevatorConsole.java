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
		ElevatorConsole console = new ElevatorConsole();
		
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
		
		// elevators
		System.out.println("Creating elevators...");		// elevator generation
		ArrayList<Elevator> elevators = new ArrayList<>();
		console.createElevators(elevators, numElevators, minFloor);

        Scheduler scheduler = new Scheduler(elevators);		// instantiate
		Controller controller = new Controller(scheduler);	// elevator managers

		System.out.println("Starting elevators...");		// start elevator threads
		scheduler.runElevators();
		
		// user interaction
		Scanner myObj = new Scanner(System.in);
		console.userOptions();
	    String input ="";
	    
	    // if command generation is set to true in the config file
		GenerateCommands generator = null;
	    if (generateCommands) {
			generator = new GenerateCommands(maxFloor, minFloor, interval, controller);
			generator.run();
		}
		
		// runs the GUI
	    FrameView GUI = new FrameView(minFloor, maxFloor, numElevators, elevators);
	    GUI.run();

		InputValidation inputValidation = new InputValidation(minFloor, maxFloor);
	    
		// container to hold user input for elevator request creation
		UserToggleOptions toggleOptions = new UserToggleOptions(-1, -1, false, false, generateCommands);

	    while(true) {
			// ------------ user input handling ------------ //
	    	input = myObj.nextLine();

			if (input.equals("q")) {    // user termination command
				break;
			}

			console.processUserOptions(input, toggleOptions, generator,
					minFloor, maxFloor, controller, interval, scheduler, GUI);

			// ------------ elevator job requests ------------ //
			// create a group of people to use the elevator
    		int[][] requests = inputValidation.InputTo2DArray(input);
    		
		    for (int[] request : requests) {
		    	
		    	// If there hasn't been a source/destination floor set use input values
		    	if (!toggleOptions.isSetSrcOn())
		    		toggleOptions.setSrcFloor(request[0]);
		    	
		    	if (!toggleOptions.isSetDstOn())
		    		toggleOptions.setDstFloor(request[1]);
		    	
		    	controller.addPersonToQueue(
						new Person(toggleOptions.getSrcFloor(), toggleOptions.getDstFloor()));
		    }

			// ------------ elevator job assignments ------------ //
			// assign available elevators to people in queue
	        try {
	            controller.assignElevator();
	        }
			catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

		console.terminateProgram(elevators, GUI, myObj);
	}

	// ------------ helper methods ------------ //

	/**
	 * Displays available commands to the user.
	 */
	void userOptions() {
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
	}

	/**
	 * Process user options:
	 *		'setsource=off' to toggle off set source floor.
	 * 		'setdestination=off' to toggle off set destination floor.
	 * 		'commandgeneration=on' to enable command generation.
	 * 		'commandgeneration=off' to disable command generation.
	 * 		'setsource=' to set source floor.
	 * 		'setdestination=' to set destination floor.
	 * 		'setinterval=' to set the interval for command generation.
	 * 		'save=' to save, or
	 * 		'load=' to load.
	 */
	private void processUserOptions(
			String input, UserToggleOptions toggleOptions, GenerateCommands generator,
			int minFloor, int maxFloor, Controller controller,
			int interval, Scheduler scheduler, FrameView GUI) {

		// Turn off the set source floor
		if (input.equals("setsource=off")) {
			toggleOptions.setSrcFloor(-1);
			toggleOptions.setSetSrcOn(false);
		}

		// Turn off the set destination floor
		if (input.equals("setdestination=off")) {
			toggleOptions.setDstFloor(-1);
			toggleOptions.setSetDstOn(false);
		}

		// Turn on the command generation
		if (input.equals("commandgeneration=on")) {
			toggleOptions.setGenerateCommands(true);

			if (generator == null) {
				generator = new GenerateCommands(maxFloor, minFloor, interval, controller);
			}

			generator.run();
		}

		// Turn off the command generation
		if (input.equals("commandgeneration=off")) {
			toggleOptions.setGenerateCommands(false);
			if (generator != null) {
				generator.kill();
			}
		}

		// Command to set source floor to a particular number
		if (input.matches("setsource=-?[0-9]\\d*")) {
			int floor = Integer.parseInt(input.split("=")[1]);
			if (floor > minFloor - 1 && floor < maxFloor + 1) {
				toggleOptions.setSrcFloor(floor);
				toggleOptions.setSetSrcOn(true);

				if (toggleOptions.isGenerateCommands())
					generator.setMinFloor(toggleOptions.getSrcFloor());
			}
			else {
				System.out.println("Invalid floor number");
			}
		}

		// Command to set destination floor to a particular number
		if (input.matches("setdestination=-?[0-9]\\d*")) {
			int floor = Integer.parseInt(input.split("=")[1]);
			if (floor > minFloor - 1 && floor < maxFloor + 1) {
				toggleOptions.setDstFloor(floor);
				toggleOptions.setSetDstOn(true);

				if (toggleOptions.isGenerateCommands())
					generator.setMaxFloor(toggleOptions.getDstFloor());
			} else {

				System.out.println("Invalid floor number");
			}

		}

		// Command to an interval to commands to generate
		if (input.matches("setinterval=-?[0-9]\\d*")) {
			int intervalCommand = Integer.parseInt(input.split("=")[1]);

			if (toggleOptions.isGenerateCommands()) {
				if (intervalCommand > 0) {
					generator.setInterval(intervalCommand);
				}
				else {
					System.out.println("Invalid Interval");
				}
			}
			else {
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
	}

	/**
	 * Creates a specified number of elevators and initializes their current floor.
	 *
	 * @param elevators    The list to store the created elevators.
	 * @param numElevators The number of elevators to create.
	 * @param minFloor     The initial floor for all elevators.
	 */
	void createElevators(ArrayList<Elevator> elevators, int numElevators, int minFloor) {
		for (int i = 0; i < numElevators; i++) {
			Elevator elevator = new Elevator();
			elevator.setCurrentFloor(minFloor);
			elevators.add(elevator);
		}
	}

	/**
	 * Terminates the program by stopping elevator threads, closing the GUI, and closing the scanner.
	 */
	void terminateProgram(ArrayList<Elevator> elevators, FrameView GUI, Scanner myObj) {
		for (Elevator e : elevators) {
			e.kill();						// kill threads
		}
		GUI.close();						// close FrameView
		myObj.close();						// close scanner
	}
}
