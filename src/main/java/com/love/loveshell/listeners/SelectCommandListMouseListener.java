package com.love.loveshell.listeners;

import com.love.loveshell.exception.ShellException;
import com.love.loveshell.executor.CommandExecutorProvider;
import com.love.loveshell.executor.MultiHostCommandExecutor;
import com.love.loveshell.executor.SingleHostCommandExecutor;
import com.love.loveshell.models.config.UserConfig;
import com.love.loveshell.provider.ChannelProvider;
import com.love.loveshell.provider.CommandConfigProvider;
import com.love.loveshell.provider.ConnectingHostProvider;
import com.love.loveshell.utils.InputTextReplacerUtil;
import com.love.loveshell.executor.LocalHostCommandExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.OutputStream;
import java.util.*;

@Slf4j
public class SelectCommandListMouseListener implements MouseListener{
	private static final Integer DOUBLE_CLICK = 2;
	private static final Integer QUADRAPLE_CLICK = 3;
	private JComboBox<String> hostComboBox;
	private CommandConfigProvider commandConfigProvider;
	private ChannelProvider channelProvider;
	private JTextArea commandHistoryTextArea;
	private JComboBox inputComboBox;
	private OutputStream terminalOutputStream;
	private InputTextReplacerUtil inputTextReplacerUtil = new InputTextReplacerUtil();
	private ConnectingHostProvider connectingHostProvider = new ConnectingHostProvider();
	private UserConfig userConfig;


	public SelectCommandListMouseListener(JComboBox<String> hostComboBox,
			CommandConfigProvider commandConfigProvider, ChannelProvider channelProvider, JTextArea commandHistoryTextArea,
	JComboBox inputComboBox, OutputStream outputStream, UserConfig userConfig) {
		this.hostComboBox = hostComboBox;
		this.commandConfigProvider = commandConfigProvider;
		this.channelProvider = channelProvider;
		this.commandHistoryTextArea = commandHistoryTextArea;
		this.inputComboBox = inputComboBox;
		this.terminalOutputStream = outputStream;
		this.userConfig = userConfig;
	}

	@Override public void mouseClicked(MouseEvent evt) {
		JList list = (JList) evt.getSource();

		if(evt.getClickCount() == QUADRAPLE_CLICK){
			String host= hostComboBox.getSelectedItem().toString().trim();
			List<String> machines = null;
			try {
				machines = commandConfigProvider.getMachinesByHost(host);
				if(CollectionUtils.isEmpty(machines)){
					throw new ShellException("no host machines found for given host");
				}

				List<String> commands = new ArrayList<>(commandConfigProvider.getHostCommands(host).get(list.getSelectedValue()));
				for (int i = 0; i < commands.size(); i++) {
					String command = commands.get(0);
					JTextField jTextField = (JTextField) inputComboBox.getEditor().getEditorComponent();
					command = command.replace("##", jTextField.getText());
					commands.set(i, command);
				}
				CommandExecutorProvider.commandExecutor  = new MultiHostCommandExecutor(commands, machines, terminalOutputStream, userConfig,
						inputComboBox.getSelectedItem().toString());
				System.out.println("Looking into multiple hosts. ");
				CommandExecutorProvider.commandExecutor .execute();
			}
			catch (Exception e){
				log.error("Exception occured: {}",e);
			}
		}
		else if (evt.getClickCount() == DOUBLE_CLICK) {
			int index = list.locationToIndex(evt.getPoint());
			String host = connectingHostProvider.fetchHost(hostComboBox.getSelectedItem().toString().trim());
			String commandName = (String) list.getModel().getElementAt(index);
			Map<String, Map<String, List<String>>> commandConfigMap = commandConfigProvider.getCommandConfigMap();
			List<String> commands = commandConfigMap.get(host).get(commandName);
			String txt = inputComboBox.getSelectedItem().toString().trim();
			if(inputTextReplacerUtil.isReplaceRequired(commands)) {
				if(StringUtils.isEmpty(txt)){
					JOptionPane.showMessageDialog(null, "Please add input for command");
					return;
				}
				commands = inputTextReplacerUtil.populateFinalCommand(commands, txt);
			}
			for(String command : commands){
				commandHistoryTextArea.setText(command + "\n" + commandHistoryTextArea.getText());
			}
			if(host.equalsIgnoreCase("localhost")){
				System.out.println("Looking into localhost. ");
				CommandExecutorProvider.commandExecutor = new LocalHostCommandExecutor(commands);
				CommandExecutorProvider.commandExecutor .execute();
			}
			else {
				CommandExecutorProvider.commandExecutor  = new SingleHostCommandExecutor(commands, channelProvider.getChannel(), terminalOutputStream);
				System.out.println("Looking into single host. ");
				CommandExecutorProvider.commandExecutor .execute();
			}
		}
	}

	@Override public void mousePressed(MouseEvent e) {

	}

	@Override public void mouseReleased(MouseEvent e) {

	}

	@Override public void mouseEntered(MouseEvent e) {

	}

	@Override public void mouseExited(MouseEvent e) {

	}
}
