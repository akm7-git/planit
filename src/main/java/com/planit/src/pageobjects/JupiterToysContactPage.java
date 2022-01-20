package com.planit.src.pageobjects;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.JsonObject;
import com.planit.src.infra.BaseCommon;
import com.planit.src.infra.UIAutomationException;

/**
 * 
 * @author alokmishra
 * 
 *         This is a Page object model of Jupiter Toys Contact page
 *
 */
public class JupiterToysContactPage extends BaseCommon {
	private WebDriver driver;
	private WebDriverWait webDriverWait;

	private final String foreNameLocator = "jupiterToysContactPage_Forename_input_id";
	private final String surNameLocator = "jupiterToysContactPage_Surname_input_id";
	private final String emailLocator = "jupiterToysContactPage_Email_input_id";
	private final String telephoneLocator = "jupiterToysContactPage_Telephone_input_id";
	private final String msgLocator = "jupiterToysContactPage_Message_textarea_id";
	private final String submitButtonLocator = "jupiterToysContactPage_Submit_button_xpath";
	private final String backButton = "jupiterToysContactPage_Back_button_xpath";
	private final String successMessageText = "jupiterToysContactPage_SuccessMessage_text";
	private final String successMessageLocator = "jupiterToysContactPage_SuccessMessage_div_xpath";
	private final String feedbackSubmitPopup = "jupiterToysContactPage_SubmitFeedbackPopup_h1_xpath";

	private Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

	public JupiterToysContactPage(WebDriver driver) throws UIAutomationException {
		this.driver = driver;
		webDriverWait = getWebDriverWait(driver);
		waitForThePageToBeLoaded();
		log.info("Page is loaded succesfully.");
	}

	private void waitForThePageToBeLoaded() throws UIAutomationException {
		String locator = getLocator(foreNameLocator);
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id(locator)));
	}

	public void setForename(String forename) throws UIAutomationException {
		String locator = getLocator(foreNameLocator);
		driver.findElement(By.id(locator)).clear();
		driver.findElement(By.id(locator)).sendKeys(forename);
	}

	public void setSurname(String surname) throws UIAutomationException {
		String locator = getLocator(surNameLocator);
		driver.findElement(By.id(locator)).clear();
		driver.findElement(By.id(locator)).sendKeys(surname);
	}

	public void setTelephone(String telephone) throws UIAutomationException {
		String locator = getLocator(telephoneLocator);
		driver.findElement(By.id(locator)).clear();
		driver.findElement(By.id(locator)).sendKeys(telephone);
	}

	public void setEmail(String email) throws UIAutomationException {
		String locator = getLocator(emailLocator);
		driver.findElement(By.id(locator)).clear();
		driver.findElement(By.id(locator)).sendKeys(email);
	}

	public void setMessage(String msg) throws UIAutomationException {
		String locator = getLocator(msgLocator);
		driver.findElement(By.id(locator)).clear();
		driver.findElement(By.id(locator)).sendKeys(msg);
	}

	public void clickOnSubmit() throws UIAutomationException {
		String locator = getLocator(submitButtonLocator);
		driver.findElement(By.xpath(locator)).click();
		waitForTheFeedBackSubmit();
	}

	public void waitForTheFeedBackSubmit() throws UIAutomationException {

		String locator = getLocator(feedbackSubmitPopup);
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
		webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
		locator = getLocator(backButton);
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
	}

	public String retrieveFeedbackSubmitSuccessMessage() throws UIAutomationException {
		String locator = getLocator(successMessageLocator);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		return driver.findElement(By.xpath(locator)).getText();
	}

	public boolean retrieveFeedBackSuccessMessageAndVerify(String name) throws UIAutomationException {
		String msgFromSite = retrieveFeedbackSubmitSuccessMessage();
		String expectedMsg = getLocator(successMessageText);
		expectedMsg = String.format(expectedMsg, name);
		if (msgFromSite.equals(expectedMsg)) {
			log.info("Verified the feedback submit success message for : " + name);
			return true;
		}
		return false;
	}

	public JupiterToysContactPage clickOnBackButton() throws UIAutomationException {
		String locator = getLocator(backButton);
		// driver.wait(3000l);
		webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locator)));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
		getAction(driver).moveToElement(driver.findElement(By.xpath(locator))).click().perform();
		return new JupiterToysContactPage(driver);
	}

	public boolean submitFeedbackAndVerifySuccessMessage(JsonObject data) throws UIAutomationException {
		String foreName = data.get("forename").getAsString();
		setForename(foreName);
		setSurname(data.get("surname").getAsString());
		setEmail(data.get("email").getAsString());
		long no = data.get("telephone").getAsLong();
		if (no > 0) {
			setTelephone("" + no);
		}
		setMessage(data.get("msg").getAsString());
		clickOnSubmit();
		return retrieveFeedBackSuccessMessageAndVerify(foreName);

	}

}
