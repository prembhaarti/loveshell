package com.love.loveshell.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InputTextReplacerUtil {

	private static final String REPLACE_TOKEN = "##";

	public List<String> populateFinalCommand(List<String> commands, String textToPut){
		List<String> commandsClone = new ArrayList<>(commands);
		for(int i=0;i<commandsClone.size();i++){
			String command = commandsClone.get(i);
			if(command.contains(REPLACE_TOKEN)){
				command = command.replace(REPLACE_TOKEN, textToPut);
				commandsClone.set(i,command);
			}
		}
		return commandsClone;
	}

	public boolean isReplaceRequired(List<String> commands){
		List<String> filteredCommands = commands.stream()
				.filter(command -> command.contains(REPLACE_TOKEN))
				.collect(Collectors.toList());
		return filteredCommands.size()>0;
	}
}
