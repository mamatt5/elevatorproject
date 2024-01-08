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
	
	public static Configurations getConfiguration(String fileName) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		Configurations config = new Configurations();
		File newFile = new File(fileName);
		
		if (!newFile.exists()) {
			
			LOGGER.info("Configuration-File-Not-Found");
			return null;
			
		} else {
			file = newFile;
		}
		
		
		try {
			
			config = objectMapper.readValue(file, Configurations.class);
		} catch (IOException e) {
		
			LOGGER.info("Invalid-Configuration-File");
			e.printStackTrace();
		} 
		
		
		return config;
	}
	
	
	
}
