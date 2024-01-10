package com.fdmgroup.ElevatorProject;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenerateCommands implements Runnable {
	
	private static final Logger LOGGER = LogManager.getLogger(ReadConfiguration.class);
	private int maxFloor;
	private int minFloor;
	private int interval;
	private Random random = new Random();
	Controller controller;
	
	public GenerateCommands(int maxFloor, int minFloor, int interval, Controller controller) {
		this.maxFloor = maxFloor;
		this.minFloor = minFloor;
		this.interval = interval;
		this.controller = controller;
	}
	
	public void setInterval(int i) {
		this.interval = i;
	}
	
	public void generateCommands() {
		
		int source = random.nextInt(minFloor, maxFloor + 1);
		int destination = random.nextInt(minFloor, maxFloor + 1);
		LOGGER.info("Person going from " + source + "to " + destination);
		controller.addPersonToQueue(new Person(source, destination));
		return;
	}

	@Override
	public void run() {
		generateCommands();
	}
}
