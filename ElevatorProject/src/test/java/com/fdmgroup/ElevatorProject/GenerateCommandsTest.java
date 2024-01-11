package com.fdmgroup.ElevatorProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GenerateCommandsTest {
	
	ArrayList<Elevator> elevators;
	Scheduler scheduler;
	Controller controller;
	
	@BeforeEach
	void setup() {
		elevators = new ArrayList<>();
		scheduler = new Scheduler(elevators);
		controller = new Controller(scheduler);
	}
	
	@Test
	void test_getting_values() {
		GenerateCommands generator = new GenerateCommands(10, 0, 1, controller);
		
		assertEquals(0, generator.getMinFloor());
		assertEquals(10, generator.getMaxFloor());
		assertEquals(1, generator.getInterval());
	}
	
	@Test
	void test_setting_values() {
		GenerateCommands generator = new GenerateCommands(10, 0, 1, controller);
		
		generator.setMinFloor(20);
		generator.setMaxFloor(57);
		generator.setInterval(14);
		
		assertEquals(20, generator.getMinFloor());
		assertEquals(57, generator.getMaxFloor());
		assertEquals(14, generator.getInterval());
	}
}
