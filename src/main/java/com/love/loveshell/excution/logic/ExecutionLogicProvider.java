package com.love.loveshell.excution.logic;

import com.love.loveshell.models.BackgroundWorkerRequest;
import com.love.loveshell.models.BackgroundWorkerResponse;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.function.Function;

public class ExecutionLogicProvider {

	public static Function<BackgroundWorkerRequest, BackgroundWorkerResponse> sessionCreater =
			(backgroundWorkerRequest -> {
				Session session = null;
				Channel channel = null;
				try {
					session = backgroundWorkerRequest.getSessionCreater()
							.createGlobalSession(backgroundWorkerRequest.getUserConfigProvider().getUserConfig(),
									backgroundWorkerRequest.getHost());
					channel = backgroundWorkerRequest.getChannelProvider()
							.createChannel(session, backgroundWorkerRequest.getUserConfigProvider().getUserConfig());
					BackgroundWorkerResponse backgroundWorkerResponse = new BackgroundWorkerResponse();
					backgroundWorkerResponse.setChannel(channel);
					channel.connect(backgroundWorkerRequest.getUserConfigProvider().getUserConfig()
							.getChannelConnectionTimeout());

					return backgroundWorkerResponse;
				}
				catch (JSchException e) {
					e.printStackTrace();
					return null;
				}
			});
}
