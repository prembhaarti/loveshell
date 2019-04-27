package com.love.loveshell.ui;

import com.love.loveshell.models.BackgroundWorkerRequest;
import com.love.loveshell.models.BackgroundWorkerResponse;

import javax.swing.*;
import java.util.function.Function;

public class SwingBackGroundWorker extends SwingWorker<BackgroundWorkerResponse, Void> {

	Function<BackgroundWorkerRequest,BackgroundWorkerResponse> executionLogic;
	BackgroundWorkerRequest backgroundWorkerRequest;

	public SwingBackGroundWorker(BackgroundWorkerRequest backgroundWorkerRequest, Function<BackgroundWorkerRequest,BackgroundWorkerResponse> executionLogic) {
		this.backgroundWorkerRequest = backgroundWorkerRequest;
		this.executionLogic = executionLogic;
	}

	@Override
	protected BackgroundWorkerResponse doInBackground() throws Exception {
		return executionLogic.apply(backgroundWorkerRequest);
	}
}
