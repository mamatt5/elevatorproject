package com.fdmgroup.ElevatorProject;

import org.jetbrains.annotations.NotNull;

public class InputValidation {

    // fixme temporary min and max values
    // todo @Jefferson minFloor and maxFloor should be from config (?)
    private final int MIN_FLOOR = 0;
    private final int MAX_FLOOR = 10;

    // getters
    public int getMinFloor() {
        return MIN_FLOOR;
    }

    public int getMaxFloor() {
        return MAX_FLOOR;
    }


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

            if (src == dest) {                          // there's no need for an elevator
                return false;                           // if there's no need for movement
            }

            // floor validity check
            return isValidFloor(src)                    // both floors have to be valid
                    && isValidFloor(dest);              // for the elevator to travel
        }
        catch (NumberFormatException e) {
            // todo @Jasper to log error
            return false;
        }
    }

    // expected input format is "src:dest,src:dest,src:dest..."
    public boolean isValidInput(@NotNull String input) {
        String[] requests = input.split(",");

        for (String request : requests) {

            if (!isValidRequest(request)) {
                return false;
            }
        }

        return true;
    }
}
