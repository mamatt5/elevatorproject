package com.fdmgroup.ElevatorProject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class InputValidation {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);
    private final int minFloor;
    private final int maxFloor;
    private final int ERROR_FLOOR = -1;
    
    // ------------ constructor ------------ //
    
    public InputValidation(int minFloor, int maxFloor) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
    }
    
    // -------------------------------------------------------------------- //
    /**
     * Converts String user input into a 2D array containing source and destination floors.
     * @param userInput The input string in the format "src:dest,src:dest,src:dest..."
     * @return A 2D array representing source and destination floors
     */
    public int[][] InputTo2DArray (String userInput) {
        userInput = userInput.replaceAll(" ", "");
        String[] requests = userInput.split(",");     // splits on comma (,) delimiter for separate src:dest requests
        int[][] jobs = new int[requests.length][2];
        int validRequestCounter = 0;
        
        for (int i = 0; i < requests.length; i++) {
            String[] job = requests[i].split(":");
            
            if (isValidRequest(requests[i])) {
                
                jobs[i][0] = getSrcFloor(job);
                jobs[i][1] = getDestFloor(job);
                validRequestCounter++;
            }
            else {
                LOGGER.error("Invalid request: " + Arrays.toString(job));
            }
        }
        
        if (validRequestCounter != jobs.length) {
            return resizeArray(jobs, validRequestCounter);
        }
        
        return jobs;
    }
    
    // ------------ helper methods (private -- public only for testing purposes) ------------ //
    
    /**
     * Checks if the given input array has the expected src:dest format.
     * @param input The job request array "src:dest" to validate
     * @return True if the input array has a length of 2; otherwise, false
     */
    public boolean isValidSrcDestFormat(String[] input) {
        return input.length == 2;   // expected job format has 2 parts "src:dest" after : split
    }
    
    /**
     * Converts the first element of the input array into an integer, representing the source floor.
     * @param input An array in the expected src:dest format, where the source floor is the first element
     * @return The integer value of the source floor
     */
    public int getSrcFloor(String[] input) {
        try {
            return Integer.parseInt(input[0]);              // converts source floor from String to int
        }
        catch (NumberFormatException e) {
            LOGGER.error("Source floor format error-- " + input[0]);
            return ERROR_FLOOR;
        }
    }
    
    /**
     * Converts the second element of the input array into an integer, representing the destination floor.
     * @param input An array in the expected src:dest format, where the destination floor is the second element
     * @return The integer value of the destination floor
     */
    public int getDestFloor(String[] input) {
        try {
            return Integer.parseInt(input[1]);                     // converts destination floor from String to int
        }
        catch (NumberFormatException e) {
            LOGGER.error("Destination floor format error-- " + input[1]);
            return ERROR_FLOOR;
        }
    }
    
    /**
     * Validates the input request for an elevator trip:
     * Checks if: the input format is correct,
     *            the floors are within the given range,
     *            the floors are different
     * @param input The input string representing the trip request in "src:dest" format
     * @return True if the request is valid; otherwise, false
     */
    public boolean isValidRequest(String input) {
        input = input.replaceAll(" ", "");
        
        String[] trip = input.split(":");
        if (!isValidSrcDestFormat(trip)) return false;
        
        // get requested source and destination floors from String
        int src = getSrcFloor(trip);
        int dest = getDestFloor(trip);
        
        try {
            validateTrip(src, dest);
        }
        catch (InvalidInputException e) {
            LOGGER.error("Invalid request-- src: " + src + ", dest: " + dest);
            return false;
        }
        
        return true;
    }
    
    /**
     * Resizes the given 2D array of jobs based on the valid request counter.
     * @param jobs The initial 2D array of jobs
     * @param validRequestCounter The counter for valid, non-null requests
     * @return A resized 2D array containing only valid job requests
     */
    public int[][] resizeArray(int[][] jobs, int validRequestCounter) {
        
        int[][] resizedJobs = new int[validRequestCounter][2];
        int index = 0;
        
        for (int[] job : jobs) {
            if (job[0] != 0 || job[1] != 0) {
                resizedJobs[index][0] = job[0];
                resizedJobs[index][1] = job[1];
                index++;
            }
        }
        
        return resizedJobs;
    }
    
    // ------------ validation methods ------------ //
    
    /**
     * Custom class for Exception handling
     */
     public static class InvalidInputException extends Exception {
        public InvalidInputException(String message) {
            super(message);
        }
    }

    /**
     * Validates the trip based on source and destination floors.
     *
     * @param src  The source floor.
     * @param dest The destination floor.
     * @throws InvalidInputException Thrown when the input doesn't meet validation criteria.
     */
    public void validateTrip(int src, int dest) throws InvalidInputException {
        if (!floorInRange(src)) {
            throw new InvalidInputException("Floor out of range-- " + src);
        }
        if (!floorInRange(dest)) {
            throw new InvalidInputException("Floor out of range-- " + dest);
        }
        if (!sameFloor(src, dest)) {
            throw new InvalidInputException("No travel required-- " + src + ", " + dest);
        }
    }
    
    /**
     * Checks if the given floor is within the valid building range.
     * @param floor The floor number to check
     * @return True if the floor is within the building range, otherwise false
     */
    public boolean floorInRange(int floor) {
        return floor >= minFloor && floor <= maxFloor;        // floor has to exist within building
    }
    
    /**
     * Checks if the source and destination floors are different.
     * @param src The source floor
     * @param dest The destination floor
     * @return True if the floors are different, otherwise false
     */
    public boolean sameFloor(int src, int dest) {
        return src != dest;                                     // only need elevator if there's movement
    }
}