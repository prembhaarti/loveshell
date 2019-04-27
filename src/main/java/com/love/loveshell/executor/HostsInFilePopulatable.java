package com.love.loveshell.executor;

import com.love.loveshell.models.config.HostsProviderInfo;
import com.love.loveshell.utils.FileUtils;
import com.love.loveshell.utils.InputTextReplacerUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class HostsInFilePopulatable implements Runnable {

	private CmdExecutor commandExecutor;
	private InputTextReplacerUtil inputTextReplacerUtil;
	private HostsProviderInfo hostsProviderInfo;

	public HostsInFilePopulatable(HostsProviderInfo hostsProviderInfo,
			InputTextReplacerUtil inputTextReplacerUtil) {
		this.hostsProviderInfo = hostsProviderInfo;
		this.inputTextReplacerUtil = inputTextReplacerUtil;
	}

	@Override public void run() {
		String fileName = hostsProviderInfo.getHostsFilePath();
		List<String> commands = hostsProviderInfo.getHostsLoaderCommands();

		if(StringUtils.isEmpty(fileName) || CollectionUtils.isEmpty(commands)){
			return;
		}

		if(!FileUtils.fileExists(fileName)){
			FileUtils.writeContentIntoFile(fileName, StringUtils.EMPTY);
		}
		commands = inputTextReplacerUtil.populateFinalCommand(commands, fileName);
		commandExecutor = new LocalHostCommandExecutor(commands);
		commandExecutor.execute();
	}
}
