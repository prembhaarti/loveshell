package com.love.loveshell.models.config;

import lombok.Data;


@Data
public class UserConfig {
	private String username;
	private String passPhrase;
	private String knowHostFilePath;
	private String privateKeyFilePath;
	private String strictHostKeyChecking;
	private String channel;
	private int sessionConnectionTimeout;
	private int channelConnectionTimeout;
	private String commandConfigFilePath;
	private String commandHistoryFilePath;
	private String userConfigFilePath;
}
