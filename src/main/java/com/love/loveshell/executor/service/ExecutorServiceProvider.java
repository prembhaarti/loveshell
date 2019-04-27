package com.love.loveshell.executor.service;

import lombok.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
public class ExecutorServiceProvider {
	public static ExecutorService executorService = Executors.newFixedThreadPool(100);
}
