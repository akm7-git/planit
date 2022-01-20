package com.planit.src.pageobjects;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

import com.planit.src.infra.BaseCommon;
import com.planit.src.infra.UIAutomationException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 * @author alokmishra
 * 
 *         This is a Page object model of Jupiter Toys Home page
 *
 */
public class JupiterToysHomePage extends BaseCommon {

	private final String jupiterToysHeading = "jupiterToysHomePage_H1_xpath";
	private WebDriver driver;
	private WebDriverWait webDriverWait;

	private Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

	public JupiterToysHomePage(WebDriver driver) throws UIAutomationException {
		this.driver = driver;
		webDriverWait = getWebDriverWait(this.driver);
		waitForThePageToBeLoaded();
		log.info("Page is loaded succesfully.");
	}

	private void waitForThePageToBeLoaded() throws UIAutomationException {
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(getLocator(jupiterToysHeading))));

	}

}
