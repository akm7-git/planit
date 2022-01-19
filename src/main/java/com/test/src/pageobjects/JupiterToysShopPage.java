package com.test.src.pageobjects;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.src.infra.BaseCommon;
import com.test.src.infra.UIAutomationException;

public class JupiterToysShopPage extends BaseCommon {

	private WebDriver driver;
	private WebDriverWait webDriverWait;
	private final String productListClassName = "jupiterToysShopPage_Items_list_className";
	private final String product1BuyButton = "jupiterToysShopPage_product1Buy_button_xpath";
	private Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

	public JupiterToysShopPage(WebDriver driver) throws UIAutomationException {
		this.driver = driver;
		webDriverWait = getWebDriverWait(driver);
		waitForThePageToBeLoaded();
	}

	private void waitForThePageToBeLoaded() throws UIAutomationException {
		List<WebElement> products = driver.findElements(By.className(getLocator(productListClassName)));
		if (products.size() > 0) {
			webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(getLocator(product1BuyButton))));
		}
	}

	public void shopItems(HashMap<String, Integer> items) throws UIAutomationException, InterruptedException {
		Iterator<String> itemIterator = items.keySet().iterator();
		while (itemIterator.hasNext()) {
			String item = itemIterator.next();
			String locator = getLocator(product1BuyButton);
			locator = String.format(locator, item);
			List<WebElement> productToBeBought = driver.findElements(By.xpath(locator));
			if (productToBeBought.size() == 0) {
				log.severe("Item : " + item + " is not listed in the shopping page");
				throw new UIAutomationException("Item : " + item + " is not listed in the shopping page");
			}
			int noOfitems = items.get(item);
			int ctr = 0;
			while (ctr != noOfitems) {
				driver.findElement(By.xpath(locator)).click();
				ctr++;
				driver.wait(uiAutomationContext.getTaskPollingInterval());
			}
		}
	}

}
