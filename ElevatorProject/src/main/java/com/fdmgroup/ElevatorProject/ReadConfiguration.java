package com.fdmgroup.ElevatorProject;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ReadConfiguration {
	
	private static final Logger LOGGER = LogManager.getLogger(ReadConfiguration.class);
	
	/**
	 * Reads and parses the configuration details from the specified file,
	 * validates the configuration parameters, and deserializes it into an
	 * instance of the Configurations object.
	 *
	 * @param fileName the name of the file containing the configuration details
	 * @return a Configurations object parsed from the file, or
	 *  null if the file is invalid or not found
	 */
	public static Configurations getConfiguration(String fileName) {
		ObjectMapper objectMapper = new ObjectMapper();
		Configurations config = new Configurations();
		File newFile = new File(fileName);
		
		File file;
		if (!newFile.exists()) {
			LOGGER.info("Configuration-File-Not-Found");
			return null;	
		}
		else {
			file = newFile;
		}
		
		try {
			config = objectMapper.readValue(file, Configurations.class);
			
			if (config.getMaxFloor() < 0 || config.getMinFloor() < 0 || config.getNumOfElevators() < 0 ||
					config.getMinFloor() > config.getMaxFloor()) {
				
				LOGGER.info("Invalid-Configuration-File-Field");
				return null;
			}
		}
		catch (IOException e) {
			LOGGER.info("Invalid-Configuration-File");
			return null;

		}
		
		return config;
	}

}
