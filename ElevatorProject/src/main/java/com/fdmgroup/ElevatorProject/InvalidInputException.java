package com.fdmgroup.ElevatorProject;


/**
 * Custom exception class -- handles cases of invalid user input scenarios.
 */
public class InvalidInputException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidInputException(String message) {
        super(message);
    }
}