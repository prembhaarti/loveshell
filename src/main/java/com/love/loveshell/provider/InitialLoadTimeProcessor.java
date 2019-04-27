package com.love.loveshell.provider;

import com.love.loveshell.executor.service.ExecutorServiceProvider;
import com.love.loveshell.executor.HostsInFilePopulatable;
import com.love.loveshell.models.config.Host;
import com.love.loveshell.models.config.HostsProviderInfo;
import com.love.loveshell.utils.InputTextReplacerUtil;

import javax.swing.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class InitialLoadTimeProcessor {

	private DefaultListModel<String> jList;
	private CommandConfigProvider commandConfigProvider;
	private InputTextReplacerUtil inputTextReplacerUtil;

	public InitialLoadTimeProcessor(DefaultListModel<String> jList, CommandConfigProvider commandConfigProvider,
			InputTextReplacerUtil inputTextReplacerUtil) {
		this.jList = jList;
		this.commandConfigProvider = commandConfigProvider;
		this.inputTextReplacerUtil = inputTextReplacerUtil;
	}

	public void processInitialTasks(){
		populateInitialHosts(jList, commandConfigProvider);
		populateHostsInFile();
	}

	public void populateHostsInFile(){
		Collection<Host> hosts = commandConfigProvider.getHosts().values();
		for(Host host : hosts){
			HostsProviderInfo hostsProviderInfo = host.getHostsProviderInfo();
			HostsInFilePopulatable
					hostsInFilePopulatable = new HostsInFilePopulatable(hostsProviderInfo, inputTextReplacerUtil);
			ExecutorServiceProvider.executorService.submit(hostsInFilePopulatable);
		}
	}

	public void populateInitialHosts(DefaultListModel<String> jList, CommandConfigProvider commandConfigProvider){
		Map<String,List<String>> hostCommandsMap = commandConfigProvider.getHostCommands("localhost");
		jList.clear();
		for(String command : hostCommandsMap.keySet()){
			jList.addElement(command);
		}
	}
}
