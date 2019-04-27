package com.love.loveshell.executor;

import java.util.List;

public abstract class CmdExecutor {

	protected List<String> commands;

	public CmdExecutor(List<String> commands) {
		this.commands = commands;
	}

	public abstract void execute();
	public abstract void interrupt();
}
