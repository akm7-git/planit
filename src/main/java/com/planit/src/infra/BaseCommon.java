package com.planit.src.infra;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 * @author alokmishra
 * 
 *         This is the top most class and would be inherited by all the Page
 *         object class in order to use the generic capabilities of ui
 *         Automation
 *
 */
public class BaseCommon {

	private Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	protected UIAutomationContext uiAutomationContext;
	protected final String attributeValue = "value";

	{
		try {
			uiAutomationContext = UIAutomationContext.getInstance();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void executeJavaScript(WebDriver driver, String... scripts) {
		driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
		for (String script : scripts) {
			getJavaScriptExecutor(driver).executeScript(script);
		}
	}

	protected String executeJavaScriptAndReturnString(WebDriver driver, String... scripts) {
		driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
		String result = "";
		for (String script : scripts) {
			result = (String) getJavaScriptExecutor(driver).executeScript(script);
		}
		return result;
	}

	protected JavascriptExecutor getJavaScriptExecutor(WebDriver driver) {
		return (JavascriptExecutor) driver;
	}

	protected Actions getAction(WebDriver driver) {
		return new Actions(driver);
	}

	protected WebDriverWait getWebDriverWait(WebDriver driver) {
		return new WebDriverWait(driver, Duration.ofMillis(uiAutomationContext.getDefaultWebdriverWait()));
	}

	protected WebDriverWait getWebDriverWait(WebDriver driver, long timeOutInMs) {
		return new WebDriverWait(driver, Duration.ofMillis(timeOutInMs));
	}

	protected void getURLAndMaximizeWindow(WebDriver driver, String url) {
		driver.get(url);
		driver.manage().window().maximize();
	}

	protected void maximizeBrowserWithJavascript(WebDriver driver) {
		log.info("Maximizing the browser!!");
		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(20));
		executeJavaScript(driver, "window.resizeTo(1024, 768);");
	}

	public ArrayList<String> getConsoleError(WebDriver driver) {
		ArrayList<String> consoleErrors = new ArrayList<>();
		LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
		for (LogEntry entry : logEntries) {
			String error = new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage() + "\n";
			log.info(error);
			consoleErrors.add(error);
		}
		return consoleErrors;
	}

	protected String getLocator(String key) throws UIAutomationException {
		if (StringUtils.isEmpty(key)) {
			log.log(Level.SEVERE, "Key can not be null or empty");
			throw new UIAutomationException("Key can not be null or empty");
		}
		Properties locatorProps = uiAutomationContext.getLocatorProperties();
		if (locatorProps.containsKey(key)) {
			return locatorProps.getProperty(key);
		} else {
			log.log(Level.SEVERE, "Key :" + key + " could not be found in the locator.properties file");
			throw new UIAutomationException("Key :" + key + " could not be found in the locator.properties file");
		}
	}

	protected void reloadPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	protected void scrolltoViewElement(WebDriver driver, WebElement element) throws Exception {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		/*
		 * Sleep for a second to make sure visibility of element is retained after
		 * Scroll, As scroll to particular element is achieved through java script.
		 */
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			log.severe("Exception while executing Thread sleep ");
			throw new UIAutomationException("Exception while executing thread sleep after scrolling to webelement.");
		}

	}

}
