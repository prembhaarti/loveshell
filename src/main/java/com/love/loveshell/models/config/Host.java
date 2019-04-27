package com.love.loveshell.models.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Host {
	private String hostName;
	private HostsProviderInfo hostsProviderInfo;
	private Map<String,List<String>> commands;
}
