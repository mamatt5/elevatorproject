package com.fdmgroup.ElevatorProject;

public class InvalidInputException extends Exception {

    private final InputValidation validation = new InputValidation();

    public InvalidInputException() {
    }

    public InvalidInputException(String message) {
        super(message);
    }

    // invalid floor inputs throw InvalidInputException
    public void validateFloor(int floor) throws InvalidInputException {
        if (!validation.isValidFloor(floor)) {
            throw new InvalidInputException("Input floor value `" + floor + "` is invalid");
        }
    }

}
