package com.fdmgroup.ElevatorProject;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadConfigurationTest {
	
	ReadConfiguration configReader;


	
	
	@BeforeEach
	void setup() {
	
	}
	
	
	@Test 
	void invalid_file_name() {
		String fileName = "Invalid";
		assertThrows(FileNotFoundException.class, () -> ReadConfiguration.readFile(fileName));

	}
	
	@Test 
	void invalid_json_format() {
		String fileName = "src/test/resources/ConfigurationTest1.txt";
		
		try {
			ReadConfiguration.readFile(fileName);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		ReadConfiguration.getConfiguration();
		
		
	}
	
	
}
