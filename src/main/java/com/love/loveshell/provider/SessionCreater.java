package com.love.loveshell.provider;

import com.love.loveshell.models.config.UserConfig;
import com.love.loveshell.exception.ShellException;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.File;

@Getter public class SessionCreater {

	private Session session;

	public Session createSession(UserConfig userConfig, String host) throws JSchException, ShellException {
		JSch jsch = new JSch();
		if (StringUtils.isNotEmpty(userConfig.getKnowHostFilePath()) && new File(userConfig.getKnowHostFilePath()).exists()) {
			jsch.setKnownHosts(userConfig.getKnowHostFilePath());
		}
		else {
			throw new ShellException("KnownHostFilePath doesn't exist");
		}
		if (StringUtils.isNotEmpty(userConfig.getPrivateKeyFilePath()) && new File(userConfig.getPrivateKeyFilePath()).exists()) {
			jsch.addIdentity(userConfig.getPrivateKeyFilePath(), userConfig.getPassPhrase());
		}
		else {
			throw new ShellException("Private key file path doesn't exist");
		}

		Session session = jsch.getSession(userConfig.getUsername(), host, 22);

		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", userConfig.getStrictHostKeyChecking());
		session.setConfig(config);

		UserInfo ui = new UserInfoProvider() {
			public void showMessage(String message) {
				JOptionPane.showMessageDialog(null, message);
			}

			public boolean promptYesNo(String message) {
				return false;
			}
		};

		session.setUserInfo(ui);
		session.connect(userConfig.getSessionConnectionTimeout());   // making a connection with timeout.
		return session;
	}

	public Session createGlobalSession(UserConfig userConfig, String host) throws JSchException {
		Session session = null;
		try {
			session = createSession(userConfig, host);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, e.getMessage(), 1);
		}
		this.session = session;
		return session;
	}

}
