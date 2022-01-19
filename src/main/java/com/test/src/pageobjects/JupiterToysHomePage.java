package com.test.src.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.src.infra.BaseCommon;
import com.test.src.infra.UIAutomationException;

public class JupiterToysHomePage extends BaseCommon {
	private final String jupiterToysHeading = "jupiterToysHomePage_H1_xpath";
	private WebDriver driver;
	private WebDriverWait webDriverWait;

	public JupiterToysHomePage(WebDriver driver) throws UIAutomationException {
		this.driver = driver;
		webDriverWait = getWebDriverWait(driver);
		waitForThePageToBeLoaded();
	}

	private void waitForThePageToBeLoaded() throws UIAutomationException {
		webDriverWait
				.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(getLocator(jupiterToysHeading)))));

	}

}
