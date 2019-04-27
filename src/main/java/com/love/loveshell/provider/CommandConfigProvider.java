package com.love.loveshell.provider;

import com.love.loveshell.models.config.CommandConfig;
import com.love.loveshell.models.config.Host;
import com.love.loveshell.utils.FileUtils;
import com.love.loveshell.utils.JsonUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class CommandConfigProvider {
	private String basePath;
	private CommandConfig commandConfig;
	private String commandConfigJson;

	public CommandConfigProvider(String basePath) {
		this.basePath = basePath;
		getReloadedCommandConfigMap();
	}

	public Map<String, Map<String, List<String>>> getCommandConfigMap() {
		Map<String, Map<String, List<String>>> commandConfigMap = new HashMap<>();
		for (Host host : commandConfig.getHosts()) {
			commandConfigMap.put(host.getHostName(), host.getCommands());
		}
		return commandConfigMap;
	}

	public Map<String, Map<String, List<String>>> getReloadedCommandConfigMap() {
		try {
			String commandConfigJson = FileUtils.getFileContent(basePath + "/" + "commandConfigFile");
			this.commandConfigJson=commandConfigJson;
			loadCommandConfig();
		}
		catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Exception Occured while reading file : commandConfigFile" + e.getMessage(), 1);
		}
		return getCommandConfigMap();
	}

	public Map<String,List<String>> getHostCommands(String host){
		Map<String, Map<String, List<String>>> commandConfigMap = getCommandConfigMap();
		return commandConfigMap.get(host);
	}

	public void loadCommandConfig() {
		CommandConfig commandConfig = JsonUtils.deser(this.commandConfigJson, CommandConfig.class);
		setCommandConfig(commandConfig);
	}

	public Map<String,Host> getHosts(){
		List<Host> hosts = commandConfig.getHosts();
		return hosts.stream().collect(
				Collectors.toMap(host -> host.getHostName(), host -> host));
	}

	public List<String> getMachinesByHost(String host) throws IOException {
		Host hostInstance = getHosts().get(host);
		List<String> hosts = null;
		try {
			if (Objects.nonNull(hostInstance) && StringUtils.isNotEmpty(hostInstance.getHostsProviderInfo().getHostsFilePath())) {
				String hostString = FileUtils.getFileContent(hostInstance.getHostsProviderInfo().getHostsFilePath());
				hosts = Arrays.asList(hostString.split("\n"));
			}
		}
		catch (Exception e){
			System.out.println("Unable to get hosts");
		}
		return hosts;
	}
}
