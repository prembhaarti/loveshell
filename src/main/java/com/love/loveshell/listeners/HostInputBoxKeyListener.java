package com.love.loveshell.listeners;

import com.love.loveshell.provider.ChannelProvider;
import com.love.loveshell.provider.CommandConfigProvider;
import com.love.loveshell.provider.SessionCreater;
import com.love.loveshell.provider.UserConfigProvider;
import com.love.loveshell.ui.HostConnector;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HostInputBoxKeyListener implements KeyListener {

	private JComboBox<String> jComboBox;
	private SessionCreater sessionCreater;
	private UserConfigProvider userConfigProvider;
	private ChannelProvider channelProvider;
	private DefaultListModel<String> jListModel;
	private CommandConfigProvider commandConfigProvider;

	public HostInputBoxKeyListener(JComboBox<String> comboBox, SessionCreater sessionCreater,
			ChannelProvider channelProvider, UserConfigProvider userConfigProvider, DefaultListModel<String> jListModel,
			CommandConfigProvider commandConfigProvider) {
		this.jComboBox = comboBox;
		this.sessionCreater = sessionCreater;
		this.channelProvider = channelProvider;
		this.userConfigProvider = userConfigProvider;
		this.jListModel = jListModel;
		this.commandConfigProvider = commandConfigProvider;
	}
	//combobox editor text

	@Override public void keyTyped(KeyEvent e) {

	}

	@Override public void keyPressed(KeyEvent e) {

	}

	@Override public void keyReleased(KeyEvent event) {
		if (event.getKeyChar() == KeyEvent.VK_ENTER) {
			String hostText = jComboBox.getSelectedItem().toString().trim();
			if (!hostText.contains(":")) {
				return;
			}
			String[] hostSplit = hostText.split(":");

			try {
				String domain = hostSplit[0].trim();
				String host = hostSplit[1].trim();
				Map<String, List<String>> commandConfigMap = commandConfigProvider.getCommandConfigMap().get(domain);
				jListModel.clear();
				List<String> list = new ArrayList<>(commandConfigMap.keySet());
				for (int i = 0; i < list.size(); i++) {
					jListModel.add(i, list.get(i));
				}
				HostConnector hostConnector =
						new HostConnector(sessionCreater, channelProvider, userConfigProvider, host);
				hostConnector.connectHost();
			}
			catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e, "Exception Occured" + e.getMessage(), 1);
			}
		}
	}
}
