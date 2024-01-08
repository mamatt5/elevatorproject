package com.fdmgroup.ElevatorProject;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SchedulerSerializationTest
{
	private static final String TEST_FILE_NAME = "testSchedulerSerialization.ser";
	private Scheduler scheduler;

	@BeforeEach
	void setUp()
	{
		Elevator elevator1 = new Elevator();
		Elevator elevator2 = new Elevator();
		Elevator elevator3 = new Elevator();
		ArrayList<Elevator> elevators = new ArrayList<>();
		elevators.add(elevator1);
		elevators.add(elevator2);
		elevators.add(elevator3);
		scheduler = new Scheduler(elevators);
	}

	@AfterEach
	void tearDown()
	{
		// Delete the test file after each test
		File file = new File(TEST_FILE_NAME);
		if (file.exists())
		{
			file.delete();
		}
	}

	@Test
	void scheduler_serialize_test()
	{
		scheduler.serializeSystemState(TEST_FILE_NAME);

		File serializedFile = new File(TEST_FILE_NAME);
		File fakeFile = new File("FakeFile");

		assertTrue(serializedFile.exists());
		assertFalse(fakeFile.exists());
	}

	@Test
	void scheduler_deserialize_test()
	{
		scheduler.serializeSystemState(TEST_FILE_NAME);

		Scheduler deserializedScheduler = scheduler.deserializeSchedulerState(TEST_FILE_NAME);

		assertNotNull(deserializedScheduler);
		assertEquals(scheduler.getElevators().size(), deserializedScheduler.getElevators().size());
	}
}
