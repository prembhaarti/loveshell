package com.love.loveshell.driver;

import com.love.loveshell.ui.LoveShell;
import lombok.Data;

@Data
public class Driver {

	public static void main(String[] args) {
		String basePath = args[0];
		LoveShell loveShell = new LoveShell(basePath);
		loveShell.display();
	}
}
