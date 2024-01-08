package com.fdmgroup.ElevatorProject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class InputValidation {
    private final Configurations CONFIGS = new Configurations();
    private final int MIN_FLOOR = CONFIGS.getMinFloor();
    private final int MAX_FLOOR = CONFIGS.getMaxFloor();
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);


    // methods
    public boolean isValidFloor(int floor) {
        return floor >= MIN_FLOOR && floor <= MAX_FLOOR;
    }

    // expected floor-to-floor request format is "src:dest"
    public boolean isValidRequest(@NotNull String input) {
        input = input.replaceAll(" ", "");

        String[] floors = input.split(":");		// two and only two parts:
        if (floors.length != 2) { 						// i.e. src and dest
            return false;
        }

        try {
            int src = Integer.parseInt(floors[0]);
            int dest = Integer.parseInt(floors[1]);

            // fixme: [check] do I need to declare the exception [InvalidInputException validityCheck]?
            InvalidInputException validityCheck = new InvalidInputException();
            validityCheck.validateFloor(src);
            validityCheck.validateFloor(dest);

            if (src == dest) {                          // there's no need for an elevator
                return false;                           // if there's no need for movement
            }

            // floor validity check
            return isValidFloor(src)                    // both floors have to be valid
                    && isValidFloor(dest);              // for the elevator to travel
        }
        catch (InvalidInputException e) {
            LOGGER.error("Invalid input: floor out of range");
            return false;
        }
        catch (NumberFormatException e) {
        	LOGGER.error("Invalid input: floor format error");
            return false;
        }
    }

    // expected input format is "src:dest,src:dest,src:dest..."
    // splits on comma (,) delimiter for separate src:dest requests
    public void passInputToController (@NotNull String input) {
        String[] requests = input.split(",");

        for (String request : requests) {

            if (isValidRequest(request)) {
                // todo: add job into queue
            }
        }
    }
}
