package com.fdmgroup.ElevatorProject;

public class GenerateCommands implements Runnable {

	private int maxFloor;
	private int minFloor;
	private int interval;
	
	public GenerateCommands(int maxFloor, int minFloor, int interval) {
		this.maxFloor = maxFloor;
		this.minFloor = minFloor;
		this.interval = interval;
	}
	
	public static void generateCommands() {
		return;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		generateCommands();
	}
}
