package com.fdmgroup.ElevatorProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ReadConfiguration {
	
	private static File file;
	private static final Logger LOGGER = LogManager.getLogger(ReadConfiguration.class);
	
	public static void readFile(String fileName) throws FileNotFoundException {
		
		File newFile = new File(fileName);
		
		if (!newFile.exists()) {
			throw new FileNotFoundException("Configuration File not found");
		} else {
			file = newFile;
		}
		
	}
	
	public static Configurations getConfiguration() {
		
		ObjectMapper objectMapper = new ObjectMapper();
		Configurations config = new Configurations();
		

		try {
			config = objectMapper.readValue(file, Configurations.class);
		} catch (JsonParseException e) {
			
			LOGGER.info("Invalid JSON format");
			
		} catch (InvalidFormatException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		return config;
	}
	
}
