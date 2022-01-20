package com.planit.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.planit.src.infra.TestUIBase;
import com.planit.src.infra.UIAutomationException;
import com.planit.src.pageobjects.JupiterToysCartPage;
import com.planit.src.pageobjects.JupiterToysHomePage;
import com.planit.src.pageobjects.JupiterToysShopPage;
/**
 * 
 * @author alokmishra
 *
 */
public class Testcase3 extends TestUIBase {
	private WebDriver driver;
	private String url = "http://jupiter.cloud.planittesting.com";
	private JupiterToysShopPage jupiterToysShopPage;
	private JupiterToysCartPage jupiterToysCartPage;
	private HashMap<String, Integer> itemsToBeBought;

	@BeforeClass
	public void initializeDataSet() {
		itemsToBeBought = new HashMap<>();
		itemsToBeBought.put("Valentine Bear", 2);
		itemsToBeBought.put("Fluffy Bunny", 5);
		itemsToBeBought.put("Stuffed Frog", 2);
	}

	@Test

	public void loadHomePageAndNavigateToShoppingPage() throws IOException, UIAutomationException {
		driver = getDriverInstance();
		getURLAndMaximizeWindow(driver, url);
		new JupiterToysHomePage(driver);
		jupiterToysShopPage = jupiterToysPageNavigator.navigateToShopPage();

	}

	@Test(dependsOnMethods = { "loadHomePageAndNavigateToShoppingPage" })
	public void buyItemsAndNavigateToCart() throws UIAutomationException, InterruptedException {

		jupiterToysShopPage.shopMultipleItems(itemsToBeBought);
		jupiterToysCartPage = jupiterToysPageNavigator.navigateToCartPage();
	}

	@Test(dependsOnMethods = { "buyItemsAndNavigateToCart" }, dataProvider = "itemDetails")
	public void verifyTheSubTotalOfEachItem(String item, int qty) throws UIAutomationException, InterruptedException {

		JsonObject itemDetailsFromCart = jupiterToysCartPage.fetchDetails(item);
		Assert.assertTrue(jupiterToysCartPage.isItemPresentOnCartPage(item));

		int noOfUnit = itemDetailsFromCart.get("quantity").getAsInt();
		double unitPrice = itemDetailsFromCart.get("unitPrice").getAsDouble();
		double subTotalPrice = unitPrice * noOfUnit;

		Assert.assertEquals(itemsToBeBought.get(item), noOfUnit);
		Assert.assertEquals(itemDetailsFromCart.get("subTotal").getAsDouble(), subTotalPrice,
				"subTotal price is not correct for item: " + item + " with unit price : " + unitPrice
						+ " for quantity : " + noOfUnit);

	}

	@DataProvider(name = "itemDetails")
	public Object[][] itemDetails() {

		int size = itemsToBeBought.keySet().size();
		Iterator<String> itr = itemsToBeBought.keySet().iterator();
		Object[][] items = new Object[size][2];
		for (int i = 0; i < size; i++) {
			String item = itr.next();
			int qty = itemsToBeBought.get(item);
			items[i][0] = item;
			items[i][1] = qty;
		}
		return items;
	}

	@AfterClass
	public void teardown() {
		driver.quit();
	}

}
