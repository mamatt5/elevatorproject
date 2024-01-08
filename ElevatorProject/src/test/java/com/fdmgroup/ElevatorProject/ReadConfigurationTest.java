package com.fdmgroup.ElevatorProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ReadConfigurationTest {
	
	ReadConfiguration configReader;

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
	void valid_json_format() {
		
		String fileName = "src/test/resources/ValidJsonFormatConfigurationTest.txt";
	
		Configurations config = ReadConfiguration.getConfiguration(fileName);
			
		assertEquals(4, config.getMaxFloor());
		assertEquals(1, config.getMinFloor());
		assertEquals(3, config.getNumOfElevators());

		
	}
	

	
}


