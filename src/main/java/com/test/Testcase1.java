package com.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.planit.src.infra.TestUIBase;
import com.planit.src.infra.UIAutomationException;
import com.planit.src.pageobjects.JupiterToysContactPage;
import com.planit.src.pageobjects.JupiterToysHomePage;

/**
 * 
 * @author alokmishra
 *
 */
public class Testcase1 extends TestUIBase {
	private WebDriver driver;
	private JupiterToysContactPage jupiterToysContactPage;
	private String url = "http://jupiter.cloud.planittesting.com";

	@Test

	public void loadHomePageAndNavigateToContactPage() throws UIAutomationException, IOException {
		driver = getDriverInstance();
		getURLAndMaximizeWindow(driver, url);
		new JupiterToysHomePage(driver);
		jupiterToysContactPage = jupiterToysPageNavigator.navigateToContactPage();

	}

	@Test(dependsOnMethods = { "loadHomePageAndNavigateToContactPage" }, dataProvider = "feedbackData")
	public void FillUpFeedback(JsonElement item) throws UIAutomationException, InterruptedException {
		JsonObject data = item.getAsJsonObject();
		boolean result = jupiterToysContactPage.submitFeedbackAndVerifySuccessMessage(data);
		Assert.assertEquals(result, true, "The success message verification has failed");
		jupiterToysContactPage.clickOnBackButton();
	}

	@DataProvider(name = "feedbackData")
	public Iterator<JsonElement> formDataq() throws JsonIOException, JsonSyntaxException, FileNotFoundException {

		JsonArray content = (JsonArray) JsonParser.parseReader(new FileReader(
				new File(System.getProperty("user.dir") + "/src/main/resources/dataprovider/feedbackData.json")));

		Iterator<JsonElement> itr = content.iterator();
		return itr;
	}

	@AfterClass
	public void teardown() {
		driver.quit();
	}
}
