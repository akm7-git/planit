package com.planit.src.infra;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;
import java.util.logging.Logger;

import com.planit.src.constants.BrowserType;
import org.apache.commons.lang3.StringUtils;

import com.planit.src.constants.AutomationConstants;

/**
 * 
 * @author alokmishra
 * 
 *         This is a singleton class and is used to initialise all the runtime
 *         properties as well as the locator properties file required by the
 *         page object models as well as the tests.
 *
 */
public class UIAutomationContext {
	private Properties runtimeProperties;
	private Properties locatorProperties;
	private InputStream runtimePropsInput = null;
	private InputStream locatorPropsInput = null;

	private final String runtimePropertiesLocation = AutomationConstants.RUNTIME_PROP_FILE_LOCATION;
	private final String locatorPropertiesFileLocation = AutomationConstants.LOCATOR_PROP_FILE_LOCATION;

	private BrowserType browser;
	private boolean isHeadlessContextSet;

	private long TASK_POLLING_INTERVAL;
	private long MAX_TASK_POLLING_INTERVAL;
	private long WEB_DRIVER_WAIT_INTERVAL;

	private String currency;

	private static UIAutomationContext uiAutomationContext;
	private Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

	private UIAutomationContext()  {
		initializeUIAutomationContext();
	}

	private void initializeUIAutomationContext() {
		try {
			runtimePropsInput = getClass().getResourceAsStream(runtimePropertiesLocation);
			runtimeProperties = new Properties();
			runtimeProperties.load(runtimePropsInput);

			locatorPropsInput = getClass().getResourceAsStream(locatorPropertiesFileLocation);
			locatorProperties = new Properties();
			locatorProperties.load(locatorPropsInput);

			String taskPollingInterval = runtimeProperties.getProperty("polling_interval");
			if (StringUtils.isNotEmpty(taskPollingInterval)) {
				TASK_POLLING_INTERVAL = Long.parseLong(taskPollingInterval);
			} else {
				log.warning(
						"Task polling interval could not be retrieved from the runtime properties, hence setting the default value");
				TASK_POLLING_INTERVAL = AutomationConstants.DEFAULT_TASK_POLLING_INTERVAL;
			}
			log.info("Setting the Timeout to : " + TASK_POLLING_INTERVAL + " ms");

			String maxTimeout = runtimeProperties.getProperty("max_time_out");
			if (StringUtils.isNotEmpty(maxTimeout)) {
				MAX_TASK_POLLING_INTERVAL = Long.parseLong(maxTimeout);
			} else {
				log.warning(
						"max polling timeout could not be retrieved from the runtime properties, hence setting the default value");
				MAX_TASK_POLLING_INTERVAL = AutomationConstants.MAX_TASK_POLLING_INTERVAL;
			}
			log.info("Setting the max. timeout interval to : " + MAX_TASK_POLLING_INTERVAL + " ms");

			String webdriverWait = runtimeProperties.getProperty("webDriverWait_interval");
			if (StringUtils.isNotEmpty(webdriverWait)) {
				WEB_DRIVER_WAIT_INTERVAL = Long.parseLong(webdriverWait);
			} else {
				log.warning(
						"Web driver wait interval could not be retrieved from the runtime properties, hence setting the default value");
				WEB_DRIVER_WAIT_INTERVAL = AutomationConstants.DEFAULT_WEB_DRIVER_WAIT_INTERVAL;
			}
			log.info("Setting the Timeout to : " + WEB_DRIVER_WAIT_INTERVAL + " ms");

			String browserType = runtimeProperties.getProperty("browser").toLowerCase();
			if (StringUtils.isEmpty(browserType)) {
				browserType = AutomationConstants.DEFAULT_BROWSER_TYPE;
			}
			browser = getBrowserType(browserType);

			String headlessOption = runtimeProperties.getProperty("headlessContext");
			if (StringUtils.isEmpty(headlessOption)) {
				log.info(
						"headless context could not be retrieved from the runtime properties, hence setting it to the deault value");
				isHeadlessContextSet = AutomationConstants.HEADLESS_CONTEXT;
			} else {
				isHeadlessContextSet = "true".equalsIgnoreCase(headlessOption.trim());
			}
			log.info("head less context is set to : " + isHeadlessContextSet);
			currency = runtimeProperties.getProperty("currencySymbol");
			if (StringUtils.isEmpty(currency)) {
				currency = AutomationConstants.DEFAULT_CURRENCY_SYMBOL;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeAllInputConnections() {
		try {
			if (null != locatorPropsInput)
				locatorPropsInput.close();
			if (null != runtimePropsInput)
				runtimePropsInput.close();

		} catch (IOException e) {
			log.warning("Error while closing the input streams : " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	public static UIAutomationContext getInstance() throws IOException {
		if (null != uiAutomationContext)
			return uiAutomationContext;
		uiAutomationContext = new UIAutomationContext();
		return uiAutomationContext;
	}

	public long getTaskPollingInterval() {
		return TASK_POLLING_INTERVAL;
	}

	public long getDefaultWebdriverWait() {
		return MAX_TASK_POLLING_INTERVAL;
	}

	public long getMaxTaskPollingInterval() {
		return MAX_TASK_POLLING_INTERVAL;
	}

	public BrowserType getBrowserType() {
		return browser;
	}

	public Properties getRuntimeProperties() {
		return runtimeProperties;
	}

	public Properties getLocatorProperties() {
		return locatorProperties;
	}

	public boolean isHeadlessContextSet() {
		return isHeadlessContextSet;
	}

	public String getCurrency() {
		return currency;
	}

	private BrowserType getBrowserType(String browser) {
		switch (browser.replace(" ", "").toLowerCase()) {
		case "chrome":
			return BrowserType.CHROME;

		case "ff":
			return BrowserType.FIREFOX;
		case "firefox":
			return BrowserType.FIREFOX;
		case "safari":
			return BrowserType.SAFARI;
		case "ie":
			return BrowserType.IE;
		case "internetexplorer":
			return BrowserType.IE;
		default:
			throw new IllegalArgumentException("Unexpected value: " + browser);
		}

	}

}
