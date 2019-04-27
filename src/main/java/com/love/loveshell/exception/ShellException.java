package com.love.loveshell.exception;

public class ShellException extends Exception{

	public ShellException(String message) {
		super(message);
	}

	public ShellException(String message, Throwable cause) {
		super(message, cause);
	}
}
