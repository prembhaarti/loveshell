package com.love.loveshell.listeners;

import com.love.loveshell.provider.CommandConfigProvider;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;
import java.util.Map;

public class CommandListSelectionListener implements ListSelectionListener {
	private JList<String> selectCommandList;
	private JComboBox<String> hostComboBox;
	private CommandConfigProvider commandConfigProvider;
	private JTextArea resultSpaceTextArea;

	public CommandListSelectionListener(JList<String> selectCommandList, JComboBox<String> hostComboBox,
			CommandConfigProvider commandConfigProvider, JTextArea resultSpaceTextArea) {
		this.selectCommandList = selectCommandList;
		this.hostComboBox = hostComboBox;
		this.commandConfigProvider = commandConfigProvider;
		this.resultSpaceTextArea = resultSpaceTextArea;
	}

	@Override public void valueChanged(ListSelectionEvent e) {
		int index = selectCommandList.getSelectedIndex();
		String host = hostComboBox.getSelectedItem().toString().trim();
		String commandName = selectCommandList.getModel().getElementAt(index);
		Map<String, Map<String, List<String>>> commandConfigMap = commandConfigProvider.getCommandConfigMap();
		List<String> commands = commandConfigMap.get(host).get(commandName);
		resultSpaceTextArea.setText("");
		for (String command : commands) {
			resultSpaceTextArea.setText(resultSpaceTextArea.getText() + "\n" + command);
		}
	}
}
