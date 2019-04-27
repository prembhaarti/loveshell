package com.love.loveshell.provider;

import com.love.loveshell.models.config.UserConfig;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.Getter;

@Getter
public class ChannelProvider {
	private Channel channel;

	public Channel createChannel(Session session, UserConfig userConfig) throws JSchException {
		this.channel = session.openChannel(userConfig.getChannel());
		return channel;
	}
}
