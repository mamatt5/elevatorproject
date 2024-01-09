package com.fdmgroup.ElevatorProject;

@SuppressWarnings("serial")
public class InvalidInputException extends Exception {
    
    /**
     * Custom exception class -- handles cases of invalid user input scenarios.
     */
    private final InputValidation validation = new InputValidation();

    public InvalidInputException() {
    }

    public InvalidInputException(String message) {
        super(message);
    }
    
    /**
     * Validates the trip based on source and destination floors.
     *
     * @param src  The source floor.
     * @param dest The destination floor.
     * @throws InvalidInputException Thrown when the input doesn't meet validation criteria.
     */
    public void validateTrip(int src, int dest) throws InvalidInputException {
        if (!validation.floorInRange(src)) {
            throw new InvalidInputException("Floor out of range-- " + src);
        }
        if (!validation.floorInRange(dest)) {
            throw new InvalidInputException("Floor out of range-- " + dest);
        }
        if (!validation.sameFloor(src, dest)) {
            throw new InvalidInputException("No travel required-- " + src + ", " + dest);
        }
    }

}
