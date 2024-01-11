package com.fdmgroup.ElevatorProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InputValidationTest {

    InputValidation inputValidation;
    
    @BeforeEach
    void setup() {
        int minFloor = 0;           // hardcoded min and max values so that the
        int maxFloor = 25;          // test results do not fluctuate with config updates
        inputValidation = new InputValidation(minFloor, maxFloor);
    }
    
    @Nested
    class TestInputSplitColonDelim {
        @Test
        void Test_Two_Parts_Colon_Delimiter_True() {
            String[] validInputs = {
                    "G : 3",
                    "9 : 5,5",
                    "1.2 : 1",
                    "a : e",
                    " : "
            };
            
            for (String input : validInputs) {
                String[] splitInput = input.split(":");
                
                assertTrue(inputValidation.isValidSrcDestFormat(splitInput));
            }
        }
        
        @Test
        void Test_Two_Parts_Colon_Delimiter_False() {
            String[] invalidInputs = {
                    "G | 3",
                    "9 ; 5",
                    "1211",
                    ":",
                    "a : b : c",
                    ""
            };
            
            for (String input : invalidInputs) {
                String[] splitInput = input.split(":");
                
                assertFalse(inputValidation.isValidSrcDestFormat(splitInput));
            }
        }
    }
    
    @Nested
    class TestValidRequest {
        String[] inputs;
        String[][] splitInputs;
        int[] expectedSrcFloors;
        int[] expectedDestFloors;
        @BeforeEach
        void setup() {
            inputs = new String[]{
                    "3:7",
                    "0:5",
                    "10:2",
                    "1:9",
                    "8:0"
            };
            
            splitInputs = new String[inputs.length][2];
            expectedSrcFloors = new int[]{3, 0, 10, 1, 8};
            expectedDestFloors = new int[]{7, 5, 2, 9, 0};
            
            for (int i = 0; i < inputs.length; i++) {
                splitInputs[i][0] = String.valueOf(expectedSrcFloors[i]);
                splitInputs[i][1] = String.valueOf(expectedDestFloors[i]);
            }
        }
        
        @Test
        void Test_GetSrcFloor_Valid() {
            
            for (int i = 0; i < inputs.length; i++) {
                int actualSrcFloor = inputValidation.getSrcFloor(splitInputs[i]);
                assertEquals(expectedSrcFloors[i], actualSrcFloor);
            }
        }
        
        @Test
        void Test_GetDestFloor_Valid() {
            
            for (int i = 0; i < inputs.length; i++) {
                int actualDestFloor = inputValidation.getDestFloor(splitInputs[i]);
                assertEquals(expectedDestFloors[i], actualDestFloor);
            }
        }
        
        @Test
        void Test_IsValid_Valid() {
            String[] normalInputs = new String[]{
                    "3:7",
                    "15:5",
                    "10:2",
                    "1:9"
            };
            
            for (String input : normalInputs) {
                assertTrue(inputValidation.isValidRequest(input));
            }
            
            String[] whitespaceInputs = {
                    "3 : 7",
                    "15 :5",
                    "1 0:2",
                    "1: 9"
            };

            for (String input : whitespaceInputs) {
                assertTrue(inputValidation.isValidRequest(input));
            }
        }
    }
    
    @Nested
    class TestInvalidRequest {
        String[] inputs;
        String[][] splitInputs;
        int[] expectedSrcFloors;
        int[] expectedDestFloors;
        @BeforeEach
        void setup() {
            inputs = new String[]{
                    "G:3",
                    "9:5,5",
                    "1.2:1",
                    "a:e",
                    " : "
            };
            
            splitInputs = new String[inputs.length][2];
            expectedSrcFloors = new int[]{-1, 9, -1, -1, -1};
            expectedDestFloors = new int[]{3, 5, 1, -1, -1};
            
            for (int i = 0; i < inputs.length; i++) {
                splitInputs[i][0] = String.valueOf(expectedSrcFloors[i]);
                splitInputs[i][1] = String.valueOf(expectedDestFloors[i]);
            }
        }
        
        @Test
        void Test_GetSrcFloor_Invalid() {
            
            for (int i = 0; i < inputs.length; i++) {
                int actualSrcFloor = inputValidation.getSrcFloor(splitInputs[i]);
                assertEquals(expectedSrcFloors[i], actualSrcFloor);
            }
        }
        
        @Test
        void Test_GetDestFloor_Invalid() {
            
            for (int i = 0; i < inputs.length; i++) {
                int actualDestFloor = inputValidation.getDestFloor(splitInputs[i]);
                assertEquals(expectedDestFloors[i], actualDestFloor);
            }
        }
        
        @Test
        void Test_IsValid_Same_Floor() {
            String[] inputs = {
                    "3:3",
                    "5:5",
                    "1:1",
                    "9:9"
            };
            
            for (String input : inputs) {
                assertFalse(inputValidation.isValidRequest(input));
            }
        }
        
        @Test
        void Test_IsValid_Null_Floor() {
            String[] inputs = {
                    ":3",
                    "5:",
                    ":",
                    " : "
            };
            
            for (String input : inputs) {
                assertFalse(inputValidation.isValidRequest(input));
            }
        }
        
        @Test
        void Test_Nonnumerical_Input() {
            
            String[] inputs = {
                    "G : 3",        // invalid: letter G
                    "9 : 5,5",      // invalid: comma 5,5
                    "1.2 : 1",      // invalid: decimal 1.2
                    "a : e",        // invalid: letters a,e
                    " : "           // invalid: empty values
            };
            
            for (String input : inputs) {
                assertFalse(inputValidation.isValidRequest(input));
            }
        }
    }
    
    @Nested
    class Range {
        String configFilePath;
        Configurations CONFIGS;
        int min;
        int max;

        @BeforeEach
        void setup() {
            min = 0;
            max = 30;
        }

        @Test
        void Test_OutOfRange() {
            String[] outOfRangeInputs = {
                    (min - 1) + ":7",
                    "0:" + (min - 5),
                    (max + 1) + ":2",
                    "1:" + (max + 5)
            };

            for (String input : outOfRangeInputs) {
                assertFalse(inputValidation.isValidRequest(input));
            }
        }
    }

    @Nested
    class Resize2dArray {
        @Test
        void Test_NoResizeRequired() {
            int[][] initialArray = {
                    {1, 2},
                    {3, 4},
                    {5, 6},
                    {7, 8}
            };
            int validRequestCounter = initialArray.length;

            int[][] resizedArray = inputValidation.resizeArray(initialArray, validRequestCounter);

            assertArrayEquals(initialArray, resizedArray);
        }

        @Test
        void Test_ResizeRequired_DataAtBeginning() {
            int[][] initialArray = {
                    {1, 2},
                    {3, 4},
                    {0, 0},
                    {0, 0}
            };
            int validRequestCounter = 2;

            int[][] resizedArray = inputValidation.resizeArray(initialArray, validRequestCounter);
            int[][] expectedResizedArray = {
                    {1, 2},
                    {3, 4},
            };
            assertArrayEquals(expectedResizedArray, resizedArray);
        }

        @Test
        void Test_ResizeRequired_DataScattered() {
            int[][] initialArray = {
                    {1, 2},
                    {0, 0},
                    {3, 4},
                    {0, 0}
            };
            int validRequestCounter = 2;

            int[][] resizedArray = inputValidation.resizeArray(initialArray, validRequestCounter);
            int[][] expectedResizedArray = {
                    {1, 2},
                    {3, 4},
            };
            assertArrayEquals(expectedResizedArray, resizedArray);
        }
    }

    @Nested
    class TestInputTo2DArray {
        @Test
        void Test_InputTo2DArray_Single() {
            String userInput = "1:2";

            int[][] result = inputValidation.InputTo2DArray(userInput);
            int[][] expected = {
                    {1, 2}
            };

            assertArrayEquals(expected, result);
        }

        @Test
        void Test_InputTo2DArray_None() {
            String userInput = "";

            int[][] result = inputValidation.InputTo2DArray(userInput);
            int[][] expected = {}; // Expecting an empty array

            assertArrayEquals(expected, result);
        }

        @Test
        void Test_InputTo2DArray_AllValid() {
            String userInput = "1:2, 4:3, 5:4, 6:5, 7:6";

            int[][] result = inputValidation.InputTo2DArray(userInput);
            int[][] expected = {
                    {1, 2},
                    {4, 3},
                    {5, 4},
                    {6, 5},
                    {7, 6}
            };

            assertArrayEquals(expected, result);
        }

        @Test
        void Test_InputTo2DArray_AllInvalid() {
            String userInput = "G:3, 1.2:1, a:e, :";

            int[][] result = inputValidation.InputTo2DArray(userInput);
            int[][] expected = {}; // Expecting an empty array

            assertArrayEquals(expected, result);
        }

        @Test
        void Test_InputTo2DArray_MixedValidInvalid() {
            String userInput = "1:3, 9:5,5, 5:9, a:e, G:3 ,2:4";

            int[][] result = inputValidation.InputTo2DArray(userInput);
            int[][] expected = {
                    {1, 3},
                    {9, 5},
                    {5, 9},
                    {2, 4},
            };

            assertArrayEquals(expected, result);
        }

        @Test
        void Test_InputTo2DArray_MixedLongRequest1() {
            String userInput = " 0:2, 1:4,  9:1, 6: 4, 8 8 :9, 64:7; 92:  1 00, 5 :9, 8: 7 ,";

            int[][] result = inputValidation.InputTo2DArray(userInput);
            int[][] expected = {
                    {0,2},
                    {1,4},
                    {9,1},
                    {6,4},
                    {5,9},
                    {8,7}
            };

            assertArrayEquals(expected, result);
        }

        @Test
        void Test_InputTo2DArray_MixedLongRequest2() {
            String userInput = "1:21,G :3 ,2:4,32:125:8,2:3,6:7,2:c,5:d,e:7,3:7,1 :3,9 5 ,5, 5 :9,16:25,,26:24,";

            int[][] result = inputValidation.InputTo2DArray(userInput);
            int[][] expected = {
                    {1, 21},
                    {2, 4},
                    {2, 3},
                    {6, 7},
                    {3, 7},
                    {1, 3},
                    {5, 9},
                    {16, 25},
            };

            assertArrayEquals(expected, result);
        }
    }

}
