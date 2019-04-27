package com.love.loveshell.models;

import com.love.loveshell.provider.ChannelProvider;
import com.love.loveshell.provider.SessionCreater;
import com.love.loveshell.provider.UserConfigProvider;
import lombok.Data;

@Data
public class BackgroundWorkerRequest {
	private UserConfigProvider userConfigProvider;
	private String host;
	private SessionCreater sessionCreater;
	private ChannelProvider channelProvider;
}
