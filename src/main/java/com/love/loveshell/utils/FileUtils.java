package com.love.loveshell.utils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

	public static String getFileContent(String filePath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}

	public static void main(String[] args) {
		String fileSeparator = System.getProperty("file.separator");
		System.out.println(fileSeparator);
		String filePath = "/home/prem.b/maverick_x".replace("/", fileSeparator);
		writeContentIntoFile(filePath, "");
	}
	public static void writeContentIntoFile(String filePath, String content) {
		try {
			if(!fileExists(filePath)){
				File file = new File(filePath);
				Files.createFile(file.toPath());
			}
			Files.write(Paths.get(filePath), content.getBytes());
		}
		catch (Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Exception Occured" + e.getMessage(), 1);
		}
	}

	public static boolean fileExists(String filePath){
		File f = new File(filePath);
		return f.exists() && !f.isDirectory();
	}

	private FileUtils(){}
}
