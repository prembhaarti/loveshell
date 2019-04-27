package com.love.loveshell.ui;

import com.love.loveshell.exception.ShellException;
import com.love.loveshell.executor.CmdExecutor;
import com.love.loveshell.executor.CommandExecutorProvider;
import com.love.loveshell.executor.MultiHostCommandExecutor;
import com.love.loveshell.listeners.HostInputBoxKeyListener;
import com.love.loveshell.listeners.InputBoxKeyListener;
import com.love.loveshell.listeners.SelectCommandListMouseListener;
import com.love.loveshell.models.config.Host;
import com.love.loveshell.models.config.UserConfig;
import com.love.loveshell.utils.FileUtils;
import com.love.loveshell.utils.InputTextReplacerUtil;
import com.love.loveshell.utils.JsonUtils;
import com.love.loveshell.provider.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.OutputStream;
import java.util.*;

/**
 * @author prem.b
 */
@Slf4j
public class LoveShell extends javax.swing.JFrame {

	private UserConfigProvider userConfigProvider;
	private SessionCreater sessionCreater;
	private ChannelProvider channelProvider;
	private CommandConfigProvider commandConfigProvider;
	private DefaultListModel<String> jListModel = new DefaultListModel<>();
	private DefaultComboBoxModel<String> defaultHostComboBoxModel = new DefaultComboBoxModel<>();
	private InputTextReplacerUtil inputTextReplacerUtil = new InputTextReplacerUtil();
	private String basePath;
	private Set<String> hosts;
	private OutputStream outputStream = System.out;
	private ConnectingHostProvider connectingHostProvider = new ConnectingHostProvider();
	private InitialLoadTimeProcessor initialHostProvider;

	public LoveShell(String basePath) {
		this.basePath = basePath;
		userConfigProvider = new UserConfigProvider(basePath);//passing basePath
		commandConfigProvider = new CommandConfigProvider(basePath);
		sessionCreater = new SessionCreater();
		channelProvider = new ChannelProvider();
		String userConfigJson = userConfigProvider.loadUserConfig();
		String commandsJson = commandConfigProvider.getCommandConfigJson();
		Map<String, Map<String, List<String>>> hostCommandsMap = commandConfigProvider.getReloadedCommandConfigMap();
		hosts = hostCommandsMap.keySet();
		initComponents();
		initialHostProvider = new InitialLoadTimeProcessor(jListModel,commandConfigProvider, inputTextReplacerUtil);
		initialHostProvider.processInitialTasks();
//		initialHostProvider.populateInitialHosts(jListModel, commandConfigProvider);
		userConfigTextArea.setText(userConfigJson);
		hostCommandTextArea.setText(commandsJson);
	}

	public void display() {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(LoveShell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(LoveShell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(LoveShell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(LoveShell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new LoveShell(basePath).setVisible(true);
			}
		});
	}

	private void initComponents() {
		commandConfigTextArea = new javax.swing.JTabbedPane();
		jPanel1 = new javax.swing.JPanel();
		jScrollPane6 = new javax.swing.JScrollPane();
		resultSpaceTextArea = new javax.swing.JTextArea();
		jPanel2 = new javax.swing.JPanel();
		jScrollPane5 = new javax.swing.JScrollPane();
		hostCommandTextArea = new javax.swing.JTextArea();
		hostCommandSaveButton = new javax.swing.JButton();
		hostCommandFetchButton = new javax.swing.JButton();
		hostCommandCreateButton = new javax.swing.JButton();
		jPanel3 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		userConfigTextArea = new javax.swing.JTextArea();
		userConfigSaveButton = new javax.swing.JButton();
		userConfigFetchButton = new javax.swing.JButton();
		jButton11 = new javax.swing.JButton();
		jButton12 = new javax.swing.JButton();
		userConfigCreateButton = new javax.swing.JButton();
		jPanel4 = new javax.swing.JPanel();
		jScrollPane3 = new javax.swing.JScrollPane();
		historyTextArea = new javax.swing.JTextArea();
		jPanel5 = new javax.swing.JPanel();
		jScrollPane4 = new javax.swing.JScrollPane();
		relativeDirectoryTextArea = new javax.swing.JTextArea();
		relativeDirectorySaveButton = new javax.swing.JButton();
		relativeDirectoryFetchButton = new javax.swing.JButton();
		relativeDirectoryCreateButton = new javax.swing.JButton();
		jPanel7 = new javax.swing.JPanel();
		jScrollPane8 = new javax.swing.JScrollPane();
		workSpaceHistoryTextArea = new javax.swing.JTextArea();
		workSpaceHistorySaveButton = new javax.swing.JButton();
		workSpaceHistoryFetchButton = new javax.swing.JButton();
		workSpaceHistoryCreateButton = new javax.swing.JButton();
		inputComboBox = new javax.swing.JComboBox<>();
		hostComboBox = new javax.swing.JComboBox<>(defaultHostComboBoxModel);
		jScrollPane1 = new javax.swing.JScrollPane();
		selectCommandList = new javax.swing.JList<>(jListModel);
		executeButton = new javax.swing.JButton();
		interruptMultiHostButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Love Shell");

		resultSpaceTextArea.setBackground(new java.awt.Color(0, 0, 0));
		resultSpaceTextArea.setColumns(20);
		resultSpaceTextArea.setForeground(new java.awt.Color(63, 255, 0));
		resultSpaceTextArea.setRows(5);
		jScrollPane6.setViewportView(resultSpaceTextArea);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1012, Short.MAX_VALUE)
						.addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE));

		commandConfigTextArea.addTab("WorkSpace", jPanel1);

		hostCommandTextArea.setBackground(new java.awt.Color(0, 0, 0));
		hostCommandTextArea.setColumns(20);
		hostCommandTextArea.setForeground(new java.awt.Color(255, 255, 255));
		hostCommandTextArea.setRows(5);
		jScrollPane5.setViewportView(hostCommandTextArea);

		hostCommandSaveButton.setText("Save");
		hostCommandSaveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				hostCommandSaveButtonActionPerformed(evt);
			}
		});

		hostCommandFetchButton.setText("Fetch");
		hostCommandFetchButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				hostCommandFetchButtonActionPerformed(evt);
			}
		});

		hostCommandCreateButton.setText("Create");
		hostCommandCreateButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				hostCommandCreateButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addGroup(
						jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1012, Short.MAX_VALUE)
								.addGroup(jPanel2Layout.createSequentialGroup().addComponent(hostCommandSaveButton)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(hostCommandFetchButton)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(hostCommandCreateButton).addGap(0, 0, Short.MAX_VALUE)))
						.addContainerGap()));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup()
						.addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
								jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(hostCommandFetchButton).addComponent(hostCommandSaveButton)
										.addComponent(hostCommandCreateButton))));

		commandConfigTextArea.addTab("Host Commands", jPanel2);

		userConfigTextArea.setBackground(new java.awt.Color(0, 0, 0));
		userConfigTextArea.setColumns(20);
		userConfigTextArea.setForeground(new java.awt.Color(255, 255, 255));
		userConfigTextArea.setRows(5);
		userConfigTextArea.setText("{\n" + "  \"username\": \"prem.b\",\n" + "  \"passPhrase\": \"flipkart\",\n"
				+ "  \"knowHostFilePath\": \"/Users/prem.b/.ssh/known_hosts\",\n"
				+ "  \"privateKeyFilePath\": \"/Users/prem.b/.ssh/id_rsa\",\n"
				+ "  \"commandConfigFilePath\": \"/Users/prem.b/commandConfigFile\",\n"
				+ "  \"commandHistoryFilePath\": \"/Users/prem.b/commandHistoryFile\",\n"
				+ "  \"userConfigFilePath\": \"/Users/prem.b/userConfigFile\",\n"
				+ "  \"strictHostKeyChecking\": \"no\",\n" + "  \"channel\": \"shell\",\n"
				+ "  \"sessionConnectionTimeout\": 30000,\n" + "  \"channelConnectionTimeout\": 3000\n" + "}");

		jScrollPane2.setViewportView(userConfigTextArea);

		userConfigSaveButton.setText("Save");
		userConfigSaveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				userConfigSaveButtonActionPerformed(evt);
			}
		});

		userConfigFetchButton.setText("Fetch");
		userConfigFetchButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				userConfigFetchButtonActionPerformed(evt);
			}
		});

		jButton11.setText("Create");
		jButton12.setText("Create");

		userConfigCreateButton.setText("Create");
		userConfigCreateButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				userConfigCreateButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addGroup(
						jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane2).addGroup(
								jPanel3Layout.createSequentialGroup().addComponent(userConfigSaveButton)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(userConfigFetchButton)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(userConfigCreateButton).addGap(0, 763, Short.MAX_VALUE)))
						.addContainerGap()).addGroup(
						jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
								jPanel3Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
										.addComponent(jButton11).addGap(0, 0, Short.MAX_VALUE))).addGroup(
						jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
								jPanel3Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
										.addComponent(jButton12).addGap(0, 0, Short.MAX_VALUE))));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup()
						.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
								jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(userConfigFetchButton).addComponent(userConfigSaveButton)
										.addComponent(userConfigCreateButton)).addContainerGap()).addGroup(
						jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
								jPanel3Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
										.addComponent(jButton11).addGap(0, 0, Short.MAX_VALUE))).addGroup(
						jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
								jPanel3Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
										.addComponent(jButton12).addGap(0, 0, Short.MAX_VALUE))));

		commandConfigTextArea.addTab("User Config", jPanel3);

		historyTextArea.setBackground(new java.awt.Color(0, 0, 0));
		historyTextArea.setColumns(20);
		historyTextArea.setForeground(new java.awt.Color(33, 255, 9));
		historyTextArea.setRows(5);
		jScrollPane3.setViewportView(historyTextArea);

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel4Layout.createSequentialGroup()
						.addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1012, Short.MAX_VALUE)
						.addContainerGap()));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE));

		commandConfigTextArea.addTab("Command History", jPanel4);

		relativeDirectoryTextArea.setBackground(new java.awt.Color(0, 0, 0));
		relativeDirectoryTextArea.setColumns(20);
		relativeDirectoryTextArea.setForeground(new java.awt.Color(255, 255, 255));
		relativeDirectoryTextArea.setRows(5);
		jScrollPane4.setViewportView(relativeDirectoryTextArea);

		relativeDirectorySaveButton.setText("Save");
		relativeDirectorySaveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				relativeDirectorySaveButtonActionPerformed(evt);
			}
		});

		relativeDirectoryFetchButton.setText("Fetch");
		relativeDirectoryFetchButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				relativeDirectoryFetchButtonActionPerformed(evt);
			}
		});

		relativeDirectoryCreateButton.setText("Create");
		relativeDirectoryCreateButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				relativeDirectoryCreateButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel5Layout.createSequentialGroup().addGroup(
						jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1012, Short.MAX_VALUE)
								.addGroup(
										jPanel5Layout.createSequentialGroup().addComponent(relativeDirectorySaveButton)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(relativeDirectoryFetchButton)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(relativeDirectoryCreateButton)
												.addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel5Layout.createSequentialGroup()
						.addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
								jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(relativeDirectorySaveButton)
										.addComponent(relativeDirectoryFetchButton)
										.addComponent(relativeDirectoryCreateButton))));

		commandConfigTextArea.addTab("Relative Directory", jPanel5);

		workSpaceHistoryTextArea.setBackground(new java.awt.Color(0, 0, 0));
		workSpaceHistoryTextArea.setColumns(20);
		workSpaceHistoryTextArea.setRows(5);
		jScrollPane8.setViewportView(workSpaceHistoryTextArea);

		workSpaceHistorySaveButton.setText("Save");
		workSpaceHistorySaveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				workSpaceHistorySaveButtonActionPerformed(evt);
			}
		});

		workSpaceHistoryFetchButton.setText("Fetch");
		workSpaceHistoryFetchButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				workSpaceHistoryFetchButtonActionPerformed(evt);
			}
		});

		workSpaceHistoryCreateButton.setText("Create");
		workSpaceHistoryCreateButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				workSpaceHistoryCreateButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
		jPanel7.setLayout(jPanel7Layout);
		jPanel7Layout.setHorizontalGroup(
				jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane8)
						.addGroup(jPanel7Layout.createSequentialGroup().addComponent(workSpaceHistorySaveButton)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(workSpaceHistoryFetchButton)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(workSpaceHistoryCreateButton).addGap(0, 769, Short.MAX_VALUE)));
		jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel7Layout.createSequentialGroup()
						.addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
								jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(workSpaceHistoryFetchButton)
										.addComponent(workSpaceHistorySaveButton)
										.addComponent(workSpaceHistoryCreateButton)).addGap(21, 21, 21)));

		commandConfigTextArea.addTab("WorkSpace History", jPanel7);

		inputComboBox.setEditable(true);
		inputComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"", "pwd"}));

		JTextField jTextField = (JTextField) inputComboBox.getEditor().getEditorComponent();

		jTextField.addKeyListener(
				new InputBoxKeyListener(inputComboBox, hostComboBox, commandConfigProvider, channelProvider, historyTextArea, outputStream,userConfigProvider.getUserConfig()));

		hostComboBox.setEditable(true);
		hostComboBox.setModel(new javax.swing.DefaultComboBoxModel(hosts.toArray()));

		JTextField comboBoxTextField = (JTextField) hostComboBox.getEditor().getEditorComponent();
		comboBoxTextField.addKeyListener(
				new HostInputBoxKeyListener(hostComboBox, sessionCreater, channelProvider, userConfigProvider,jListModel, commandConfigProvider));

		hostComboBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				hostComboBoxItemStateChanged(evt);
			}
		});
		selectCommandList.setBackground(new java.awt.Color(0, 0, 0));
		selectCommandList.setForeground(new java.awt.Color(255, 255, 255));
		selectCommandList.setModel(jListModel);
		selectCommandList.addMouseListener(new SelectCommandListMouseListener(hostComboBox, this.commandConfigProvider, this.channelProvider, historyTextArea,inputComboBox, outputStream, userConfigProvider.getUserConfig()));

		selectCommandList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
				selectCommandListValueChanged(evt);
			}
		});

		jScrollPane1.setViewportView(selectCommandList);

		executeButton.setText("Execute");
		executeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				executeButtonActionPerformed(evt);
			}
		});

		interruptMultiHostButton.setText("Interrupt MultiHost");
		interruptMultiHostButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				interruptExecutor(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap()
						.addComponent(hostComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 196,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(inputComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(executeButton)
						.addContainerGap()).addGroup(layout.createSequentialGroup().addGroup(
						layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jScrollPane1)
								.addComponent(interruptMultiHostButton, javax.swing.GroupLayout.DEFAULT_SIZE, 184,
										Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(commandConfigTextArea)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addGroup(
						layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(inputComboBox, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(hostComboBox, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(executeButton)).addGap(3, 3, 3).addGroup(
						layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
								layout.createSequentialGroup().addComponent(commandConfigTextArea).addContainerGap())
								.addGroup(layout.createSequentialGroup().addComponent(jScrollPane1)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(interruptMultiHostButton).addGap(15, 15, 15)))));

		pack();
	}// </editor-fold>

	private void hostComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {
		try {
			String host = connectingHostProvider.fetchHost(hostComboBox.getSelectedItem().toString().trim());
			Map<String, List<String>> commandConfigMap = commandConfigProvider.getCommandConfigMap().get(host);
			if(Objects.isNull(commandConfigMap)){
				return;
			}
			jListModel.clear();
			List<String> list = new ArrayList<>(commandConfigMap.keySet());
			for (int i=0;i<list.size(); i++) {
				jListModel.add(i, list.get(i));
			}

			if(host.equalsIgnoreCase("localhost")){
				return;
			}

			HostConnector hostConnector = new HostConnector(sessionCreater, channelProvider, userConfigProvider, host);
			hostConnector.connectHost();
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Exception Occured" + e.getMessage(), 1);
		}
	}

	private void selectCommandListValueChanged(javax.swing.event.ListSelectionEvent evt) {
		int index = selectCommandList.getSelectedIndex();
		if(index == -1){
			return;
		}
		String host = connectingHostProvider.fetchHost(hostComboBox.getSelectedItem().toString().trim());
		String commandName = selectCommandList.getModel().getElementAt(index);
		Map<String,List<String>> hostCommandsMap = commandConfigProvider.getHostCommands(host);
		List<String> commands = hostCommandsMap.get(commandName);
		resultSpaceTextArea.setText("");
		for (String command : commands) {
			resultSpaceTextArea.setText(resultSpaceTextArea.getText() + "\n" + command);
		}
	}

	private void interruptExecutor(java.awt.event.ActionEvent evt) {
		CmdExecutor cmdExecutor = CommandExecutorProvider.commandExecutor;
		if(Objects.isNull(cmdExecutor)){
			JOptionPane.showMessageDialog(null,"No executor present");
		}
		CommandExecutorProvider.commandExecutor.interrupt();
	}

	private void hostCommandSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			FileUtils.writeContentIntoFile(userConfigProvider.getUserConfig().getCommandConfigFilePath(),
					hostCommandTextArea.getText());
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, e, "Exception Occured" + e.getMessage(), 1);
		}
	}

	private void hostCommandFetchButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			hostCommandTextArea.setText(FileUtils.getFileContent(userConfigProvider.getUserConfig().getCommandConfigFilePath()));
		}
		catch (Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Exception Occured" + e.getMessage(), 1);
		}
	}

	private void hostCommandCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void userConfigSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			FileUtils.writeContentIntoFile(userConfigProvider.getUserConfig().getUserConfigFilePath(),
					userConfigTextArea.getText());
		}
		catch (Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Exception Occured" + e.getMessage(), 1);
		}
	}

	private void userConfigFetchButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String userConfigJson = "";
		try {
			userConfigJson = FileUtils.getFileContent(userConfigProvider.getUserConfig().getUserConfigFilePath());
			userConfigTextArea.setText(userConfigJson);
		}
		catch (Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Exception Occured" + e.getMessage(), 1);
		}
		UserConfig userConfig = JsonUtils.deser(userConfigJson, UserConfig.class);
		userConfigProvider.setUserConfig(userConfig);
	}

	private void userConfigCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void relativeDirectorySaveButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void relativeDirectoryFetchButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void relativeDirectoryCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void executeButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String host= hostComboBox.getSelectedItem().toString();
		List<String> hosts = new ArrayList<>();
		try {
			Host hostInstance = commandConfigProvider.getHosts().get(host);
			if (Objects.nonNull(hostInstance) && StringUtils.isNotEmpty(hostInstance.getHostsProviderInfo().getHostsFilePath())) {
				String hostString = FileUtils.getFileContent(hostInstance.getHostsProviderInfo().getHostsFilePath());
				hosts = Arrays.asList(hostString.split("\n"));
			}
			if(CollectionUtils.isEmpty(hosts)){
				throw new ShellException("no host machines found for given host");
			}

			List<String> commands = new ArrayList<>(commandConfigProvider.getHostCommands(host).get(selectCommandList.getSelectedValue()));
			for (int i = 0; i < commands.size(); i++) {
				String command = commands.get(0);
				command = command.replace("##", inputComboBox.getSelectedItem().toString());
				commands.set(i, command);
			}
			CommandExecutorProvider.commandExecutor = new MultiHostCommandExecutor(commands, hosts, outputStream, userConfigProvider.getUserConfig(),
					inputComboBox.getSelectedItem().toString());
			CommandExecutorProvider.commandExecutor.execute();
		}
		catch (Exception e){
			log.error("Exception occured: {}",e);
		}
	}

	private void workSpaceHistorySaveButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void workSpaceHistoryFetchButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void workSpaceHistoryCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private javax.swing.JTabbedPane commandConfigTextArea;
	private javax.swing.JButton executeButton;
	private javax.swing.JButton interruptMultiHostButton;
	private javax.swing.JTextArea historyTextArea;
	private javax.swing.JComboBox<String> hostComboBox;
	private javax.swing.JButton hostCommandCreateButton;
	private javax.swing.JButton hostCommandFetchButton;
	private javax.swing.JButton hostCommandSaveButton;
	private javax.swing.JTextArea hostCommandTextArea;
	private javax.swing.JComboBox<String> inputComboBox;
	private javax.swing.JButton jButton11;
	private javax.swing.JButton jButton12;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JScrollPane jScrollPane5;
	private javax.swing.JScrollPane jScrollPane6;
	private javax.swing.JScrollPane jScrollPane8;
	private javax.swing.JButton relativeDirectoryCreateButton;
	private javax.swing.JButton relativeDirectoryFetchButton;
	private javax.swing.JButton relativeDirectorySaveButton;
	private javax.swing.JTextArea relativeDirectoryTextArea;
	private javax.swing.JTextArea resultSpaceTextArea;
	private javax.swing.JList<String> selectCommandList;
	private javax.swing.JButton userConfigCreateButton;
	private javax.swing.JButton userConfigFetchButton;
	private javax.swing.JButton userConfigSaveButton;
	private javax.swing.JTextArea userConfigTextArea;
	private javax.swing.JButton workSpaceHistoryCreateButton;
	private javax.swing.JButton workSpaceHistoryFetchButton;
	private javax.swing.JButton workSpaceHistorySaveButton;
	private javax.swing.JTextArea workSpaceHistoryTextArea;
}

