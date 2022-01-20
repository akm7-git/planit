package com.planit.src.pageobjects;

import com.planit.src.infra.BaseCommon;
import com.planit.src.infra.UIAutomationException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 
 * @author alokmishra
 * 
 *         This class will be used to navigate between any pages in the site.
 *
 */
public class JupiterToysPageNavigator extends BaseCommon {

	private WebDriver driver;

	private final String shopLInk = "JupiterToysMenuBar_shop_id";
	private final String cartLink = "JupiterToysMenuBar_cart_id";
	private final String contactLink = "JupiterToysMenuBar_contact_id";

	public JupiterToysPageNavigator(WebDriver driver) {
		this.driver = driver;
	}

	public JupiterToysShopPage navigateToShopPage() throws UIAutomationException {
		driver.findElement(By.id(getLocator(shopLInk))).click();
		return new JupiterToysShopPage(driver);
	}

	public JupiterToysCartPage navigateToCartPage() throws UIAutomationException {
		driver.findElement(By.id(getLocator(cartLink))).click();
		return new JupiterToysCartPage(driver);
	}

	public JupiterToysContactPage navigateToContactPage() throws UIAutomationException {
		driver.findElement(By.id(getLocator(contactLink))).click();
		return new JupiterToysContactPage(driver);
	}

}
