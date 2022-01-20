package com.planit.src.pageobjects;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.planit.src.infra.BaseCommon;
import com.planit.src.infra.UIAutomationException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 * @author alokmishra
 * 
 *         This is a Page object model of Jupiter Toys Shop page
 *
 */
public class JupiterToysShopPage extends BaseCommon {

	private WebDriver driver;
	private WebDriverWait webDriverWait;

	private final String product1BuyButton = "jupiterToysShopPage_product1Buy_button_xpath";
	private final String productBuyButton = "jupiterToysShopPage_ItemBuy_button_xpath";

	private Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

	public JupiterToysShopPage(WebDriver driver) throws UIAutomationException {
		this.driver = driver;
		webDriverWait = getWebDriverWait(driver);
		waitForThePageToBeLoaded();
		log.info("Page is loaded succesfully.");
	}

	private void waitForThePageToBeLoaded() throws UIAutomationException {
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(getLocator(product1BuyButton))));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(getLocator(product1BuyButton))));
	}

	public void shopMultipleItems(HashMap<String, Integer> items) throws UIAutomationException {
		for (String item : items.keySet()) {
			shopItems(item, items.get(item));
		}
	}

	public void shopItems(String item, int quantity) throws UIAutomationException {
		String locator = getLocator(productBuyButton);
		locator = String.format(locator, item);
		List<WebElement> productToBeBought = driver.findElements(By.xpath(locator));
		if (productToBeBought.size() == 0) {
			log.severe("Item : " + item + " is not listed in the shopping page");
			throw new UIAutomationException("Item : " + item + " is not listed in the shopping page");
		}
		int ctr = 0;
		while (ctr != quantity) {
			driver.findElement(By.xpath(locator)).click();
			ctr++;
			webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
		}
	}

}
