package com.love.loveshell.executor;

import com.jcraft.jsch.Channel;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

@Slf4j
public class SingleHostCommandExecutor extends CmdExecutor {

	private Channel channel;
	private OutputStream outputStream;
	private static final String INTERRUPT_SIGNAL = "2";

	public SingleHostCommandExecutor(List<String> commands, Channel channel, OutputStream outputStream) {
		super(commands);
		this.channel = channel;
		this.outputStream = outputStream;
	}

	@Override public void execute() {
		try {
			for (String command : commands) {
				OutputStream inputstream_for_the_channel = channel.getOutputStream();
				PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
				channel.setOutputStream(outputStream);
				commander.println(command);
			}
		}
		catch (Exception e) {
			log.error("Exception occured:{}",e);
			JOptionPane.showMessageDialog(null, e, "Exception Occured" + e.getMessage(), 1);
		}
	}

	@Override public void interrupt() {
		try {
			channel.sendSignal(INTERRUPT_SIGNAL);
		}
		catch (Exception e){
			log.error("Unable to interrupt", e);
		}
	}
}
