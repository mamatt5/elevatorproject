package com.fdmgroup.ElevatorProject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
public class ConsoleTest
{

	@Test
	public void test_console_running_then_immediately_terminate() throws IOException, InterruptedException {
		
		String[] args = null;
		InputStream sysInBackup = System.in; // backup System.in to restore it later
		
		// Get the user input
		ByteArrayInputStream in = new ByteArrayInputStream("q".getBytes());
		System.setIn(in);
		
		// Run console
		ElevatorConsole.main(args);
		
		System.setIn(sysInBackup);
	}
	
	@Test
	public void test_console_running_and_using_valid_input() throws IOException, InterruptedException {
		
		String[] args = null;
		InputStream sysInBackup = System.in;
		
		// Get the user input
		ByteArrayInputStream in = new ByteArrayInputStream("1:4\nq".getBytes());
		System.setIn(in);
		
		// Run console
		ElevatorConsole.main(args);
		
		System.setIn(sysInBackup);
	}
	
	@Test
	public void test_console_running_and_fixing_source_and_destination_floors() throws IOException, InterruptedException {
		
		String[] args = null;
		InputStream sysInBackup = System.in;
		
		// Get the user input
		ByteArrayInputStream in = new ByteArrayInputStream("setsource=0\nq".getBytes());
		System.setIn(in);
		
		// Run console
		ElevatorConsole.main(args);
		
		ByteArrayInputStream in2 = new ByteArrayInputStream("setdestination=3\nq".getBytes());
		System.setIn(in2);
		
		// Run console
		ElevatorConsole.main(args);
		
		System.setIn(sysInBackup);
	}
	
	@Test
	public void test_console_running_set_source_off() throws IOException, InterruptedException {
		
		String[] args = null;
		InputStream sysInBackup = System.in;
		
		// Get the user input
		ByteArrayInputStream in = new ByteArrayInputStream("setsource=off\nq".getBytes());
		System.setIn(in);
		
		// Run console
		ElevatorConsole.main(args);
		
		System.setIn(sysInBackup);
	}
	
	@Test
	public void test_console_running_set_destination_off() throws IOException, InterruptedException {
		
		String[] args = null;
		InputStream sysInBackup = System.in;
		
		// Get the user input
		ByteArrayInputStream in = new ByteArrayInputStream("setdestination=off\nq".getBytes());
		System.setIn(in);
		
		// Run console
		ElevatorConsole.main(args);
		
		System.setIn(sysInBackup);
	}
	
	@Test
	public void test_console_running_and_turning_on_and_off_command_generation() throws IOException, InterruptedException {
		
		String[] args = null;
		InputStream sysInBackup = System.in;
		
		// Get the user input
		ByteArrayInputStream in = new ByteArrayInputStream("commandgeneration=on\ncommandgeneration=off\nq".getBytes());
		System.setIn(in);
			
		// Run console
		ElevatorConsole.main(args);
		
		System.setIn(sysInBackup);
	}
	
	
	@Test
	public void test_console_running_and_setting_time_interval() throws IOException, InterruptedException {
		
		String[] args = null;
		InputStream sysInBackup = System.in;
		
		// Get the user input
		ByteArrayInputStream in = new ByteArrayInputStream("setinterval=3\nq".getBytes());
		System.setIn(in);
		
		// Run console
		ElevatorConsole.main(args);
		
		System.setIn(sysInBackup);
	}
	
	@Test
	public void test_console_running_and_saving_and_loading_states() throws IOException, InterruptedException {
		
		String[] args = null;
		InputStream sysInBackup = System.in;
		
		// Get the user input
		ByteArrayInputStream in = new ByteArrayInputStream("save=testconsole.ser\nload=testconsole.ser\nq".getBytes());
		System.setIn(in);
		
		// Run console
		ElevatorConsole.main(args);
		
		System.setIn(sysInBackup);
	}
}
