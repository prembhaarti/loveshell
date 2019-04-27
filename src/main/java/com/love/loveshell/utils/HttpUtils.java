package com.love.loveshell.utils;

import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.asynchttpclient.Dsl.asyncHttpClient;
import static org.asynchttpclient.Dsl.config;

public class HttpUtils {

	public String post(String url, Map<String,String> headerMap, String payload, Integer timeOut) {

		HttpHeaders headers = new DefaultHttpHeaders();
		if(MapUtils.isNotEmpty(headerMap)) {
			for (String header : headerMap.keySet()) {
				headers.add(header, headerMap.get(header));
			}
		}

		String responseData = StringUtils.EMPTY;
		try {
			AsyncHttpClient c =
					asyncHttpClient(config().setKeepAlive(true).setConnectTimeout(timeOut).setMaxConnections(1));
			String body = "hello there";
			Response response = c
					.preparePost(url)
					.setBody(body)
					.setHeaders(headers)
					.execute().get(timeOut, TimeUnit.SECONDS);
			responseData = response.getResponseBody();
		}
		catch (Exception e) {

		}
		return responseData;
	}

	public String get(String url, String headerJson, Integer timeOut) {

		HttpHeaders headers = new DefaultHttpHeaders().add(HttpHeaders.Names.CONTENT_TYPE, HttpHeaders.Values.APPLICATION_JSON);
		String responseData = StringUtils.EMPTY;
		try {
			AsyncHttpClient c =
					asyncHttpClient(config().setKeepAlive(true).setConnectTimeout(timeOut).setMaxConnections(1));
			Response response = c
					.prepareGet(url)
					.setHeaders(headers)
					.execute().get(timeOut, TimeUnit.SECONDS);
			responseData = response.getResponseBody();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return responseData;
	}
}
