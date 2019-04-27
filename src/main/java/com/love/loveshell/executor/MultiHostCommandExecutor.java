package com.love.loveshell.executor;

import com.love.loveshell.executor.service.ExecutorServiceProvider;
import com.love.loveshell.executor.task.CommandExecutableTask;
import com.love.loveshell.models.config.UserConfig;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MultiHostCommandExecutor extends CmdExecutor {

	private List<String> hosts;
	private OutputStream outputStream;
	private UserConfig userConfig;
	private String inputText;
	private List<Future<?>> futureList;

	public MultiHostCommandExecutor(List<String> commands, List<String> hosts, OutputStream outputStream,
			UserConfig userConfig, String inputText) {
		super(commands);
		this.hosts = hosts;
		this.outputStream = outputStream;
		this.userConfig = userConfig;
		this.inputText = inputText;
		this.futureList = new ArrayList<>();
	}

	@Override
	public void execute() {
		for(String host: hosts) {
			futureList.add(ExecutorServiceProvider.executorService.submit(new CommandExecutableTask(commands, host, outputStream, userConfig, inputText)));
		}
	}

	@Override
	public void interrupt(){
		for(Future<?> future : futureList){
			System.out.println(future.cancel(true));
		}
		try {
			ExecutorServiceProvider.executorService.awaitTermination(1, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
