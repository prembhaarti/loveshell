package com.love.loveshell.models.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class HostsProviderInfo {
	private String hostsFilePath;
	private List<String> hostsLoaderCommands;
}
