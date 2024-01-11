package com.fdmgroup.ElevatorProject;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This object allow for the automatic generation of commands depending on a time interval.
 */
public class GenerateCommands extends Thread {
	
	private static final Logger LOGGER = LogManager.getLogger(GenerateCommands.class);
	private int maxFloor;
	private int minFloor;
	private int interval;
	private Random random = new Random();
	private Controller controller;
	private Timer timer;
	
	public GenerateCommands(int maxFloor, int minFloor, int interval, Controller controller) {
		this.maxFloor = maxFloor;
		this.minFloor = minFloor;
		this.interval = interval;
		this.controller = controller;
	}

	/**
	 * A setter for interval which allows it to be changed.
	 */
	public synchronized void setInterval(int i) {
		
		interval = i;
	}
	
	/**
	 * A setter for maxFloor which allows it to be changed.
	 */
	public synchronized void setMaxFloor(int i) {
		
		maxFloor = i;
	}

	
	/**
	 * A setter for minFloor which allows it be changed.
	 */
	public synchronized void setMinFloor(int i) {
		
		minFloor = i;
	}
	
	
	/**
	 * A getter for minFloor.
	 */
	public int getMinFloor() {
		
		return minFloor;
	}
	
	/**
	 * A getter for maxFloor
	 */
	public int getMaxFloor() {
		
		return maxFloor;
	}
	
	/**
	 * A getter for interval
	 */
	public int getInterval() {
		
		return interval;
	}
	
	/**
	 * A TimerTask that allows for command generation.
	 */
	public class generateCommands extends TimerTask {
		
		@Override
		public void run() {
			int source = random.nextInt(maxFloor - minFloor + 1) + minFloor;
			int destination = random.nextInt(maxFloor - minFloor + 1) + minFloor;
			
			System.out.println(source + ":" + destination);
			LOGGER.info("Person going from " + source + " to " + destination);
			
			try {
				controller.addPersonToQueue(new Person(source, destination));
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * A TimerTask that is used to wrap assignElevator.
	 */
	public class runElevator extends TimerTask {
		
		@Override
		public void run() {
			try {
				controller.assignElevator();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

	}

	/**
	 * A kill function which stops the automatic generation of commands.
	 */
	public void kill() {
		timer.cancel();
	}
	
	@Override
	public synchronized void run() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new generateCommands(), 0, interval * 1000);
		timer.scheduleAtFixedRate(new runElevator(), 0, interval * 1010);
	}
}
