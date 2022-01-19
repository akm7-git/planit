package com.test.src.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.test.src.infra.BaseCommon;
import com.test.src.infra.UIAutomationException;

public class JupiterToysPageNavigator extends BaseCommon {
	private WebDriver driver;
	private final String shopLInk = "JupiterToysMenuBar_shop_link";
	private final String cart = "JupiterToysMenuBar_cart_xpath";


	public JupiterToysPageNavigator(WebDriver driver) {
		this.driver = driver;
	}

	public JupiterToysShopPage navigateToShopPage() throws UIAutomationException {
		driver.findElement(By.linkText(getLocator(shopLInk))).click();
		return new JupiterToysShopPage(driver);
	}
	
	public JupiterToysCartPage navigateToCartPage() throws UIAutomationException
	{
		driver.findElement(By.xpath(getLocator(cart))).click();
		return new JupiterToysCartPage(driver);
	}

}
