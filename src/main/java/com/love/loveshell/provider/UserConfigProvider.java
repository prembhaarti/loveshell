package com.love.loveshell.provider;

import com.love.loveshell.models.config.UserConfig;
import com.love.loveshell.utils.FileUtils;
import com.love.loveshell.utils.JsonUtils;
import lombok.Data;

import javax.swing.*;
import java.io.IOException;

@Data
public class UserConfigProvider {

	private UserConfig userConfig;
	private String basePath;

	public UserConfigProvider(String basePath) {
		this.basePath = basePath;
	}

	public String loadUserConfig() {
		String userConfigJson = "";
		try {
			userConfigJson = FileUtils.getFileContent(basePath + "/" + "userConfigFile");
			UserConfig userConfig = JsonUtils.deser(userConfigJson, UserConfig.class);
			this.userConfig = userConfig;
		}
		catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Exception Occured while reading file : userConfigFile" + e.getMessage(), 1);
		}
		return userConfigJson;
	}

}
