package com.fdmgroup.ElevatorProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;




public class ReadConfigurationTest {
	
	ReadConfiguration configReader;

	/**
	 * Function that is used to verify certain log messages were produced in order
	 * to test if getConfiguration is working properly. It reads the last token of of the
	 * last line of the LogTesting.log file to see if the lastest logger message produced is correct. 
	 * @return String the last token of the last line
	 */
	private String readLogFile() {
		
		String logErrorToken = "";
		String data = "";
		
		try {
			
			File myObj = new File("../ElevatorProject/src/test/resources/LogTesting.log");
			Scanner myReader = new Scanner(myObj);
	
			while (myReader.hasNextLine()) {
				data = myReader.nextLine();
			}
			
			logErrorToken = data.substring(data.lastIndexOf(" ") + 1);
			myReader.close();
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}

		return logErrorToken;
	}

	
	@Test 
	void invalid_file_name() {
		
		String fileName = "Invalid";
	
		assertNull(ReadConfiguration.getConfiguration(fileName));
		assertEquals("Configuration-File-Not-Found", readLogFile());
		
	}
	
	@Test 
	void invalid_json_format() {
		
		String fileName = "src/test/resources/InvalidJsonFormatConfigurationTest.txt";

		assertNull(ReadConfiguration.getConfiguration(fileName));			
		assertEquals("Invalid-Configuration-File", readLogFile());
		
	}
	
	@Test 
	void invalid_maxFloor_field() {
		
		String fileName = "src/test/resources/InvalidMaxFloorTest.txt";
	
		assertNull(ReadConfiguration.getConfiguration(fileName));
		assertEquals("Invalid-Configuration-File-Field", readLogFile());

	}
	
	@Test 
	void invalid_minFloor_field() {
		
		String fileName = "src/test/resources/InvalidMinFloorTest.txt";
	
		assertNull(ReadConfiguration.getConfiguration(fileName));
		assertEquals("Invalid-Configuration-File-Field", readLogFile());

	}
	
	@Test 
	void invalid_numOfElevators_field() {
		
		String fileName = "src/test/resources/InvalidNumOfElevatorsTest.txt";
	
		assertNull(ReadConfiguration.getConfiguration(fileName));
		assertEquals("Invalid-Configuration-File-Field", readLogFile());

	}

	@Test 
	void invalid_generateCommands_field() {
		
		String fileName = "src/test/resources/InvalidGenerateCommandsTest.txt";
	
		assertNull(ReadConfiguration.getConfiguration(fileName));
		assertEquals("Invalid-Configuration-File", readLogFile());

	}
	
	@Test 
	void invalid_invalidInterval_field() {
		
		String fileName = "src/test/resources/InvalidIntervalBetweenCommands.txt";
	
		assertNull(ReadConfiguration.getConfiguration(fileName));
		assertEquals("Invalid-Configuration-File-Field", readLogFile());

	}
	
	@Test 
	void invalid_min_and_max_floor_field() {
		
		String fileName = "src/test/resources/InvalidMinAndMaxFloorTest.txt";
	
		assertNull(ReadConfiguration.getConfiguration(fileName));
		assertEquals("Invalid-Configuration-File-Field", readLogFile());

	}
	
	@Test 
	void invalid_lift_capacity_field() {
		
		String fileName = "src/test/resources/InvalidLiftCapacityTest.txt";
	
		assertNull(ReadConfiguration.getConfiguration(fileName));
		assertEquals("Invalid-Configuration-File-Field", readLogFile());

	}
	
	@Test 
	void valid_json_format() {
		
		String fileName = "src/test/resources/ValidJsonFormatConfigurationTest.txt";
		Configurations config = ReadConfiguration.getConfiguration(fileName);
			
		assertEquals(4, config.getMaxFloor());
		assertEquals(1, config.getMinFloor());
		assertEquals(3, config.getNumOfElevators());
		assertFalse(config.getGenerateCommands());
		assertEquals(8, config.getIntervalBetweenCommands());
		assertEquals(8, config.getLiftCapacity());

	}
	
	@Test 
	void valid_json_format_big_fields() {
		
		String fileName = "src/test/resources/ValidJsonFormatBigFieldsConfigurationTest.txt";
		Configurations config = ReadConfiguration.getConfiguration(fileName);
			
		assertEquals(4987324, config.getMaxFloor());
		assertEquals(23494, config.getMinFloor());
		assertEquals(987345, config.getNumOfElevators());
		assertTrue(config.getGenerateCommands());
		assertEquals(85657, config.getIntervalBetweenCommands());
		assertEquals(3728948, config.getLiftCapacity());

	}

	
}


