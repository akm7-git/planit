package com.test.src.pageobjects;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.src.infra.BaseCommon;
import com.test.src.infra.UIAutomationException;

public class JupiterToysCartPage extends BaseCommon {

	private WebDriver driver;
	private WebDriverWait webDriverWait;
	private final String itemsInCart = "jupiterToysCartPage_items_tr_xpath";
	private final String checkoutButton = "jupiterToysCartPage_checkout_href_link";
	private final String startShoppingLink = "jupiterToysCartPage_shoppingLink_href_xpath";
	private Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	public JupiterToysCartPage(WebDriver driver) throws UIAutomationException {
		this.driver = driver;
		webDriverWait = getWebDriverWait(driver);
		waitForThePageToBeLoaded();
	}

	private void waitForThePageToBeLoaded() throws UIAutomationException {
		int itemsInCart= isCartPageEmpty();
		if (itemsInCart ==0) {
			log.info("Shopping cart is empty !!!");
			webDriverWait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath(getLocator(startShoppingLink)))));

		} else {
			log.info("Shopping cart is not empty and has " + itemsInCart + " no of items");
			webDriverWait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.linkText(getLocator(checkoutButton)))));
		}
		log.info("Shopping cart page is loaded successfully.");
	}

	public int isCartPageEmpty() throws UIAutomationException {
		List<WebElement> items = driver.findElements(By.xpath(getLocator(itemsInCart)));
		if (items.size() > 0)
			return items.size();
		return 0;
	}

}
