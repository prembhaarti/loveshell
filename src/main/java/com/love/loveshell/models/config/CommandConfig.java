package com.love.loveshell.models.config;

import lombok.Data;

import java.util.List;

@Data
public class CommandConfig {
	private List<Host> hosts;
}

