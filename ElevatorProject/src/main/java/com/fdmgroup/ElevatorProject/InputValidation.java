package com.fdmgroup.ElevatorProject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class InputValidation {

    // fixme temporary min and max values
    // todo @Jefferson minFloor and maxFloor should be from config (?)
    int minFloor = 0;
    int maxFloor = 10;
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    public boolean isValidFloor(int floor) {
        return floor >= minFloor && floor <= maxFloor;
    }

    // expected format is "src:dest"
    public boolean isValidInput(@NotNull String input) {
        input = input.replaceAll(" ", "");

        String[] floors = input.split(":");		// two and only two parts:
        if (floors.length != 2) { 						// i.e. src and dest
            return false;
        }

        try {
            int src = Integer.parseInt(floors[0]);
            int dest = Integer.parseInt(floors[1]);

            // floor validity check
            return isValidFloor(src)
                    && isValidFloor(dest);
        }
        catch (NumberFormatException e) {
        	LOGGER.error("Invalid input");
            return false;
        }
    }
}
