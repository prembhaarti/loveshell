package com.love.loveshell.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TerminalCommandTest {
	public static void main(String[] args) throws IOException, InterruptedException {

		String command = "ls";

		Process proc = Runtime.getRuntime().exec(command);

		// Read the output

		BufferedReader reader =
				new BufferedReader(new InputStreamReader(proc.getInputStream()));

		String line = "";
		while((line = reader.readLine()) != null) {
			System.out.print(line + "\n");
		}

		proc.waitFor();

	}

}
