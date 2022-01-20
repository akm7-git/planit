package com.planit.src.pageobjects;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.JsonObject;
import com.planit.src.infra.BaseCommon;
import com.planit.src.infra.UIAutomationException;

/**
 * 
 * @author alokmishra
 * 
 *         This is a Page object model of Jupiter Toys Cart page
 *
 */
public class JupiterToysCartPage extends BaseCommon {

	private WebDriver driver;
	private WebDriverWait webDriverWait;
	private final String NoOfitemsInCart = "jupiterToysCartPage_cartItems_span_xpath";
	private final String checkoutButton = "jupiterToysCartPage_checkout_href_xpath";
	private final String startShoppingLink = "jupiterToysCartPage_shoppingLink_href_xpath";
	private final String itemInCart = "jupiterToysCartPage_item_td_xpath";
	private final String quantityOfAnItem = "jupiterToysCartPage_quantity_inputValue_xpath";
	private final String priceOfAnItem = "jupiterToysCartPage_price_td_xpath";
	private final String subTotal = "jupiterToysCartPage_total_td_xpath";
	private Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

	public JupiterToysCartPage(WebDriver driver) throws UIAutomationException {
		this.driver = driver;
		webDriverWait = getWebDriverWait(driver);
		waitForThePageToBeLoaded();
		log.info("Page is loaded succesfully.");
	}

	private void waitForThePageToBeLoaded() throws UIAutomationException {
		int itemsInCart = isCartPageEmpty();
		if (itemsInCart == 0) {
			log.info("Shopping cart is empty !!!");
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(getLocator(startShoppingLink))));
			webDriverWait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath(getLocator(startShoppingLink)))));

		} else {
			log.info("Shopping cart is not empty and has " + itemsInCart + " no of items");
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(getLocator(checkoutButton))));
			webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(getLocator(checkoutButton))));
		}
		log.info("Shopping cart page is loaded successfully.");
	}

	public int isCartPageEmpty() throws UIAutomationException {
		String noOfItems = driver.findElement(By.xpath(getLocator(NoOfitemsInCart))).getText();
		int itemCount;
		if (StringUtils.isNotEmpty(noOfItems)) {
			itemCount = Integer.parseInt(noOfItems);
			return itemCount;
		}
		return 0;
	}

	public boolean isItemPresentOnCartPage(String item) throws UIAutomationException {
		String locator = getLocator(itemInCart);
		locator = String.format(locator, item);
		List<WebElement> items = driver.findElements(By.xpath(locator));
		return items.size() > 0;
	}

	public double fetchUnitPriceForAnItem(String item) throws UIAutomationException {
		if (!isItemPresentOnCartPage(item)) {
			throw new UIAutomationException("item :" + item + " is not found in the shopping cart");
		}
		String locator = getLocator(priceOfAnItem);
		locator = String.format(locator, item);
		String price = driver.findElement(By.xpath(locator)).getText();
		return Double.parseDouble(price.replace(uiAutomationContext.getCurrency(), "").trim());
	}

	public int fetchUnitListedForAnItem(String item) throws UIAutomationException {
		if (!isItemPresentOnCartPage(item)) {
			throw new UIAutomationException("item :" + item + " is not found in the shopping cart");
		}
		String locator = getLocator(quantityOfAnItem);
		locator = String.format(locator, item);
		String qty = driver.findElement(By.xpath(locator)).getAttribute(attributeValue);
		return Integer.parseInt(qty.trim());
	}

	public double fetchSubTotalPriceForAnItem(String item) throws UIAutomationException {
		if (!isItemPresentOnCartPage(item)) {
			throw new UIAutomationException("item :" + item + " is not found in the shopping cart");
		}
		String locator = getLocator(subTotal);
		locator = String.format(locator, item);
		String total = driver.findElement(By.xpath(locator)).getText();
		return Double.parseDouble(total.replace(uiAutomationContext.getCurrency(), "").trim());
	}

	public JsonObject fetchDetails(String item) throws UIAutomationException {

		if (!isItemPresentOnCartPage(item)) {
			throw new UIAutomationException("item :" + item + " is not found in the shopping cart");
		}
		JsonObject itemDetails = new JsonObject();
		itemDetails.addProperty("unitPrice", fetchUnitPriceForAnItem(item));
		itemDetails.addProperty("subTotal", fetchSubTotalPriceForAnItem(item));
		itemDetails.addProperty("quantity", fetchUnitListedForAnItem(item));
		log.info("itemDetails : " + itemDetails);
		return itemDetails;
	}

}
