package com.test;

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
public class Testcase2 extends TestUIBase {
	private WebDriver driver;
	private String url = "http://jupiter.cloud.planittesting.com";
	private JupiterToysShopPage jupiterToysShopPage;
	private JupiterToysCartPage jupiterToysCartPage;
	private HashMap<String, Integer> itemsToBeBought;

	@BeforeClass
	public void setData() {
		itemsToBeBought = new HashMap<>();
		itemsToBeBought.put("Funny Cow", 2);
		itemsToBeBought.put("Fluffy Bunny", 1);
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
	public void verifyThePresenceOfEachItemAndQuantityInCart(String item, int qty)
			throws UIAutomationException, InterruptedException {

		JsonObject itemDetailsFromCart = jupiterToysCartPage.fetchDetails(item);
		Assert.assertEquals(jupiterToysCartPage.isItemPresentOnCartPage(item), true);
		Assert.assertEquals(itemsToBeBought.get(item), itemDetailsFromCart.get("quantity").getAsInt());
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
