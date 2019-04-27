package com.love.loveshell.models;

import com.jcraft.jsch.Channel;
import lombok.Data;


@Data
public class BackgroundWorkerResponse {
	Channel channel;
}
