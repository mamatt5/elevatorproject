package com.fdmgroup.ElevatorProject;

public enum ElevatorState {
	UP(1),
	IDLE(0),
	DOWN(-1);
	
	private final int value;
	
	ElevatorState(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
