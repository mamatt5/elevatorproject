package com.fdmgroup.ElevatorProject;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenerateCommands implements Runnable {
	
	private static final Logger LOGGER = LogManager.getLogger(ReadConfiguration.class);
	private static int maxFloor;
	private static int minFloor;
	private int interval;
	private static Random random = new Random();
	static Controller controller;
	private Timer timer = new Timer();
	
	public GenerateCommands(int maxFloor, int minFloor, int interval, Controller controller) {
		GenerateCommands.maxFloor = maxFloor;
		GenerateCommands.minFloor = minFloor;
		this.interval = interval;
		GenerateCommands.controller = controller;
	}
	
	public synchronized void setInterval(int i) {
		System.out.println("BEANS");
		this.interval = i;
		run();
	}
	
	static class generateCommands extends TimerTask {
		
		@Override
		public void run() {
			int source = random.nextInt(minFloor, maxFloor + 1);
			int destination = random.nextInt(minFloor, maxFloor + 1);
			System.out.println("Person going from " + source + " to " + destination);
			LOGGER.info("Person going from " + source + " to " + destination);
			
			try {
				controller.addPersonToQueue(new Person(source, destination));
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		
	}
	
	static class runElevator extends TimerTask {
		
		@Override
		public void run() {
			try {
				controller.assignElevator();
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		}
		
		
	}

	@Override
	public synchronized void run() {
		timer.scheduleAtFixedRate(new generateCommands(), 0, interval * 1000);
		timer.scheduleAtFixedRate(new runElevator(), 0, interval * 1010);
	}
}
