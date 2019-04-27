package com.love.loveshell.provider;

public class ConnectingHostProvider {

	public String fetchHost(String hostString){
		if (!hostString.contains(":")) {
			return hostString;
		}
		String[] hostSplit = hostString.split(":");
		return hostSplit[0];
	}
}
