package com.fdmgroup.ElevatorProject;

public class InvalidInputException extends Exception {

    private final InputValidation validation = new InputValidation();

    public InvalidInputException() {
    }

    public InvalidInputException(String message) {
        super(message);
    }

    // invalid floor inputs throw InvalidInputException
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
