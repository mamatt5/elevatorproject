package com.fdmgroup.ElevatorProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InputValidationTest {

    InputValidation inputValidation;

    @BeforeEach
    void setup() {
        inputValidation = new InputValidation();
    }

    @Test
    void Test_Valid() {
        String[] normalInputs = {
                "3:7",
                "0:5",
                "10:2",
                "1:9"
        };

        for (String input : normalInputs) {
            assertTrue(inputValidation.isValidRequest(input),
                    "failed: " + input);
        }

        String[] whitespaceInputs = {
                "3 : 7",
                "0 :5",
                "1 0:2",
                "1: 9"
        };

        for (String input : whitespaceInputs) {
            assertTrue(inputValidation.isValidRequest(input),
                    "failed: " + input);
        }
    }

    @Test
    void Test_Same_Floor() {
        String[] inputs = {
                "3:3",
                "5:5",
                "1:1",
                "9:9"
        };

        for (String input : inputs) {
            assertFalse(inputValidation.isValidRequest(input),
                    "failed: " + input);
        }
    }

    @Test
    void Test_OutOfRange() {
        int max = inputValidation.getMaxFloor();
        int min = inputValidation.getMinFloor();

        String[] inputs = {
                (min-1)+ ":7",
                "0:" +(min-5),
                (max+1)+ ":2",
                "1:" +(max+5)
        };

        for (String input : inputs) {
            assertFalse(inputValidation.isValidRequest(input),
                    "failed: " + input);
        }
    }

    @Test
    void Test_Nonnumerical_Input() {

        String[] inputs = {
                "G : 3",
                "9 : 5,5",
                "1.2 : 1",
                "a : e"
        };

        for (String input : inputs) {
            assertFalse(inputValidation.isValidRequest(input),
                    "failed: " + input);
        }
    }

}
