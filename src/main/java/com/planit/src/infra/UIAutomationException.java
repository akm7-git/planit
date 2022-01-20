package com.planit.src.infra;

public class UIAutomationException extends Exception {

	/**
	 * 
	 *         This is a customised Exception class to be used in this framework to
	 *         make things fail fast with exact Error.Which can save us time
	 *         debugging the misleading Exceptions.
	 *
	 */
	private static final long serialVersionUID = 1L;

	public UIAutomationException() {
		// TODO Auto-generated constructor stub
	}

	public UIAutomationException(String msg) {
		super(msg);
	}

}
