package com.fdmgroup.ElevatorProject;

public class Configurations {
	
	private int maxFloor;
	private int minFloor;
	private int numOfElevators;
	private boolean generateCommands;
	private int intervalBetweenCommands;
	
	/**
	 * @return The maximum floor value as allowed in the configuration.
	 */
	public int getMaxFloor() {
		return maxFloor;
	}
	
	/**
	 * @return The minimum floor value as allowed in the configuration.
	 */
	public int getMinFloor() {
		return minFloor;
	}
	
	/**
	 * @return The number of elevators as configured in the system.
	 */
	public int getNumOfElevators() {
		return numOfElevators;
	}
	
	/**
	 * @return The number of elevators as configured in the system.
	 */
	public boolean getGenerateCommands() {
		return generateCommands;
	}
	
	/**
	 * @return The number of elevators as configured in the system.
	 */
	public int getIntervalBetweenCommands() {
		return intervalBetweenCommands;
	}
	
	
}