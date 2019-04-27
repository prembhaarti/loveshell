package com.love.loveshell.listeners;

import com.love.loveshell.executor.CmdExecutor;
import com.love.loveshell.executor.MultiHostCommandExecutor;
import com.love.loveshell.executor.SingleHostCommandExecutor;
import com.love.loveshell.models.config.UserConfig;
import com.love.loveshell.provider.ChannelProvider;
import com.love.loveshell.provider.CommandConfigProvider;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputBoxKeyListener implements KeyListener {

	private JComboBox<String> jComboBox;
	private CmdExecutor commandExecutor;
	private ChannelProvider channelProvider;
	private JTextArea commandHistoryTextArea;
	private OutputStream outputStream;
	private UserConfig userConfig;
	private CommandConfigProvider commandConfigProvider;
	private JComboBox<String> hostComboBox;
	public InputBoxKeyListener(JComboBox<String> comboBox, JComboBox<String> hostComboBox, CommandConfigProvider commandConfigProvider, ChannelProvider channelProvider,
			JTextArea commandHistoryTextArea, OutputStream outputStream, UserConfig userConfig) {
		this.jComboBox = comboBox;
		this.channelProvider = channelProvider;
		this.commandHistoryTextArea = commandHistoryTextArea;
		this.outputStream = outputStream;
		this.userConfig = userConfig;
		this.commandConfigProvider = commandConfigProvider;
		this.hostComboBox = hostComboBox;
	}

	@Override public void keyTyped(KeyEvent e) {
	}

	@Override public void keyPressed(KeyEvent e) {
	}

	@Override public void keyReleased(KeyEvent event) {
		if (event.getKeyChar() == KeyEvent.VK_ENTER) {
			JTextField jTextField = (JTextField) jComboBox.getEditor().getEditorComponent();
			String command = jTextField.getText();

			if(StringUtils.isEmpty(command)){
				return;
			}
			commandExecutor = new SingleHostCommandExecutor(Arrays.asList(command), channelProvider.getChannel(), outputStream);
			commandHistoryTextArea.setText(command + "\n" +commandHistoryTextArea.getText());

			try {
				commandExecutor.execute();
			}
			catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e, "Exception Occured" + e.getMessage(), 1);
			}
		}
		else if(event.getKeyCode() == KeyEvent.VK_ENTER && ((event.getModifiers() & KeyEvent.SHIFT_MASK) != 0)){
			JTextField jTextField = (JTextField) jComboBox.getEditor().getEditorComponent();
			String command = jTextField.getText();

			List<String> machines = new ArrayList<>();
			if(StringUtils.isEmpty(command)){
				return;
			}
			try {
				machines = commandConfigProvider.getMachinesByHost(hostComboBox.getSelectedItem().toString().trim());
			}
			catch (IOException e) {
				System.out.println("Unable to get machines");
			}
			commandExecutor = new MultiHostCommandExecutor(Arrays.asList(command), machines, outputStream, userConfig, StringUtils.EMPTY);
			commandHistoryTextArea.setText(command + "\n" +commandHistoryTextArea.getText());

			try {
				commandExecutor.execute();
			}
			catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e, "Exception Occured" + e.getMessage(), 1);
			}
		}
		else if(event.getKeyCode() == KeyEvent.VK_RIGHT && ((event.getModifiers() & KeyEvent.ALT_MASK) != 0)){
			JTextField jTextField = (JTextField) jComboBox.getEditor().getEditorComponent();
			jTextField.setCaretPosition(jTextField.getText().length());
		}
		else if(event.getKeyCode() == KeyEvent.VK_LEFT && ((event.getModifiers() & KeyEvent.ALT_MASK) != 0)){
			JTextField jTextField = (JTextField) jComboBox.getEditor().getEditorComponent();
			jTextField.setCaretPosition(0);
		}

	}
}
