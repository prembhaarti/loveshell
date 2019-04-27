package com.love.loveshell.executor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
public class LocalHostCommandExecutor extends CmdExecutor {

	public LocalHostCommandExecutor(List<String> commands) {
		super(commands);
	}

	@Override public void execute() {
		try {
			for (String command : commands) {
				Process proc = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
				BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				String line = StringUtils.EMPTY;
				while ((line = reader.readLine()) != null) {
					System.out.print(line + "\n");
				}
				proc.waitFor();
			}
		}
		catch (Exception e) {
			log.error("Exception occured:{}", e);
		}
	}

	@Override public void interrupt() {

	}
}
