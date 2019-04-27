package com.love.loveshell.ui;

import com.love.loveshell.excution.logic.ExecutionLogicProvider;
import com.love.loveshell.models.BackgroundWorkerRequest;
import com.love.loveshell.provider.ChannelProvider;
import com.love.loveshell.provider.SessionCreater;
import com.love.loveshell.provider.UserConfigProvider;

import javax.swing.*;

public class HostConnector {

	private SessionCreater sessionCreater;
	private ChannelProvider channelProvider;
	private UserConfigProvider userConfigProvider;
	private String host;

	public HostConnector(SessionCreater sessionCreater, ChannelProvider channelProvider,
			UserConfigProvider userConfigProvider, String host) {
		this.sessionCreater = sessionCreater;
		this.channelProvider = channelProvider;
		this.userConfigProvider = userConfigProvider;
		this.host = host;
	}

	public void connectHost() {
		try {
			BackgroundWorkerRequest backgroundWorkerRequest = new BackgroundWorkerRequest();
			backgroundWorkerRequest.setSessionCreater(sessionCreater);
			backgroundWorkerRequest.setChannelProvider(channelProvider);
			backgroundWorkerRequest.setUserConfigProvider(userConfigProvider);
			backgroundWorkerRequest.setHost(host);
			SwingBackGroundWorker backGroundWorker =
					new SwingBackGroundWorker(backgroundWorkerRequest, ExecutionLogicProvider.sessionCreater);
			backGroundWorker.doInBackground();
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Exception Occured" + e.getMessage(), 1);
		}
	}

}
