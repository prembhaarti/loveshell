package com.love.loveshell.executor.task;

import com.love.loveshell.models.config.UserConfig;
import com.love.loveshell.provider.SessionCreater;
import com.love.loveshell.utils.CommandOutputDecorator;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class CommandExecutableTask implements Runnable {
	private static final String CHANNEL_TYPE = "exec";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String BOLD = "\033[0;1m";

	private CommandOutputDecorator commandOutputDecorator;

	private List<String> commands;
	private String host;
	private OutputStream outputStream;
	private SessionCreater sessionCreater;
	private UserConfig userConfig;
	private Session session;
	private String inputText;

	public CommandExecutableTask(List<String> commands, String host, OutputStream outputStream, UserConfig userConfig,
			String inputText) {
		this.commands = commands;
		this.host = host;
		this.outputStream = outputStream;
		this.sessionCreater = new SessionCreater();
		this.userConfig = userConfig;
		this.inputText = inputText;
		this.commandOutputDecorator = new CommandOutputDecorator();
	}

	@Override public void run() {
		try {
			this.session = sessionCreater.createSession(userConfig, host.trim());
			executeCommand();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void executeCommand() {
		Channel channel = null;
		String output = StringUtils.EMPTY;
		try {
			channel = session.openChannel(CHANNEL_TYPE);
			ChannelExec execChannel = (ChannelExec) channel;
			for (String command : commands) {
				execChannel.setCommand(command);

				channel.setInputStream(null);
				execChannel.setErrStream(System.err);
				execChannel.setOutputStream(outputStream);

				InputStream in = channel.getInputStream();

				channel.connect();

				byte[] tmp = new byte[1024];
				while (true) {
					while (in.available() > 0) {
						int i = in.read(tmp, 0, 1024);
						if (i < 0)
							break;
						output += new String(tmp, 0, i);

					}
					if (channel.isClosed()) {
						if (in.available() > 0)
							continue;
						break;
					}
				}
			}
			channel.disconnect();
			session.disconnect();
			if (StringUtils.isNotEmpty(output)) {
				System.out.println();
				System.out.println(BOLD + ANSI_GREEN + " " + host + commands + " " + ANSI_RESET);
				output = commandOutputDecorator.decorateText(output, inputText);
				System.out.println(output);
			}
			else {
				System.out.print("->");
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}


	private void executeStreamCommand() {
		Channel channel = null;
		try {
			channel = session.openChannel(CHANNEL_TYPE);
			ChannelExec execChannel = (ChannelExec) channel;
			for (String command : commands) {
				execChannel.setCommand(command);

				channel.setInputStream(null);
				execChannel.setErrStream(System.err);
				execChannel.setOutputStream(outputStream);

				InputStream in = channel.getInputStream();

				channel.connect(userConfig.getChannelConnectionTimeout());

				byte[] tmp = new byte[256];
				while (true) {
					while (in.available() > 0) {
						int i = in.read(tmp, 0, 256);
						if (i < 0)
							break;
						System.out.println(new String(tmp, 0, i));

					}
					if (channel.isClosed()) {
						if (in.available() > 0)
							continue;
						break;
					}
				}
			}
			channel.disconnect();
			session.disconnect();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
