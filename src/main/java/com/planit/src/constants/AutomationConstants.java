package com.planit.src.constants;

/**
 * 
 * @author alokmishra
 * 
 *         This class is used to accommodate all the constants used in this
 *         framework.
 *
 */
public class AutomationConstants {

	public static final long DEFAULT_TASK_POLLING_INTERVAL = 5000;
	public static final long MAX_TASK_POLLING_INTERVAL = 5000 * 40;
	public static final long DEFAULT_WEB_DRIVER_WAIT_INTERVAL = 30000;
	public static final boolean HEADLESS_CONTEXT = false;
	public static final String DEFAULT_BROWSER_TYPE = "chrome";
	public static final String RUNTIME_PROP_FILE_LOCATION = "/configs/runtime.props";
	public static final String LOCATOR_PROP_FILE_LOCATION = "/configs/locator.props";
	public static final String DEFAULT_CURRENCY_SYMBOL = "$";

	private AutomationConstants() {

	}

}
