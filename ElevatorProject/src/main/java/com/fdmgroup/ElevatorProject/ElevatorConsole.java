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
		scheduler.RunElevators();
		
		Scanner myObj = new Scanner(System.in);
		InputValidation inputValidation = new InputValidation();
	    System.out.println("Enter your commands: ");
	    
	    String input ="";
	    
	    FrameView GUI = new FrameView(minFloor, maxFloor, numElevators, elevators);
	    Thread t = new Thread(GUI);
	    t.run();
	    
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
    			
    			controller.addPersonToQueue(new Person(srcFloor, dstFloor));
    		}
    		// Assign elevators to the people in the queue
	        try
	        {
	            controller.assignElevator();
	            
	        } catch (InterruptedException e)
	        {
	            e.printStackTrace();
	        }
	    	
	    }
	    
	    myObj.close();
	}

}
