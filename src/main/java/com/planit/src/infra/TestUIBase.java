package com.planit.src.infra;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.planit.src.constants.BrowserType;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.planit.src.pageobjects.JupiterToysPageNavigator;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author alokmishra
 *
 *         This class inherits all the generic capabilities of ui automation
 *         from the base class and tops up extra methods which can be used by
 *         the ui automation tests. All the ui Automation tests would inherit
 *         this class in order to reuse the generic capabilities.
 */
public class TestUIBase extends BaseCommon {

	private WebDriver driver;
	private WebDriverWait webDriverWait;
	private JavascriptExecutor jse;
	private Actions action;
	protected JupiterToysPageNavigator jupiterToysPageNavigator;

	protected WebDriver getDriverInstance() throws IOException {
		if (null != driver) {
			return driver;
		}
		if (null == uiAutomationContext) {
			uiAutomationContext = UIAutomationContext.getInstance();
		}
		BrowserType browser = uiAutomationContext.getBrowserType();
		boolean isHeadless = uiAutomationContext.isHeadlessContextSet();

		switch (browser) {
		case CHROME:
			driver = getChromeInstance(isHeadless);
			break;

		case FIREFOX:
			driver = getFirefoxInstance(isHeadless);
			break;

		case SAFARI:
			driver = getSafariInstance();
			break;

		case IE:
			driver = getIEInstance();
			break;

		default:
			driver = getChromeInstance(isHeadless);
			break;
		}
		setDriverDependencies(driver);
		return driver;
	}

	private ChromeDriver getChromeInstance(boolean isHeadless) {
		WebDriverManager.chromedriver().setup();
		ChromeOptions chromeOptions = getDesiredChromeOptions(isHeadless);
		ChromeDriver driver = new ChromeDriver(chromeOptions);
		return driver;
	}

	private ChromeOptions getDesiredChromeOptions(boolean isHeadless) {
		ChromeOptions chrome_options = new ChromeOptions();

		// Disable extensions and hide infobars
		chrome_options.addArguments("--disable-extensions");
		chrome_options.addArguments("disable-infobars");
		chrome_options.addArguments("--start-maximized");

		// headless
		if (isHeadless) {
			chrome_options.addArguments("--headless");
			chrome_options.addArguments("--window-size=1920,1080");
			chrome_options.addArguments("--disable-gpu");
		}
		// Accept certificate
		chrome_options.setAcceptInsecureCerts(true);

		Map<String, Object> prefs = new HashMap<String, Object>();

		// Hide save credentials prompt
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("download.prompt_for_download", false);
		prefs.put("safebrowsing.enabled", true);

		chrome_options.setExperimentalOption("prefs", prefs);
		return chrome_options;
	}

	private FirefoxDriver getFirefoxInstance(boolean isHeadless) {
		WebDriverManager.firefoxdriver().setup();
		FirefoxOptions firefoxOptions = getDesiredFirefoxOptions(isHeadless);
		FirefoxDriver driver = new FirefoxDriver(firefoxOptions);
		return driver;
	}

	private FirefoxOptions getDesiredFirefoxOptions(boolean isHeadless) {

		FirefoxOptions ff_options = new FirefoxOptions();

		// Disable extensions and hide info bars
		ff_options.addArguments("--disable-extensions");
		ff_options.addArguments("disable-infobars");
		ff_options.addArguments("--start-maximized");

		// headless
		if (isHeadless) {
			ff_options.addArguments("--headless");
			ff_options.addArguments("--window-size=1920,1080");
			ff_options.addArguments("--disable-gpu");
		}

		ff_options.setAcceptInsecureCerts(true);
		ff_options.addPreference("dom.webnotifications.enabled", false);

		return ff_options;
	}

	private SafariDriver getSafariInstance() {
		WebDriverManager.safaridriver().setup();
		SafariDriver driver = new SafariDriver();
		return driver;
	}

	private InternetExplorerDriver getIEInstance() {
		WebDriverManager.iedriver().setup();
		InternetExplorerDriver driver = new InternetExplorerDriver();
		return driver;
	}

	private void setDriverDependencies(WebDriver driver) {
		webDriverWait = getWebDriverWait(driver, uiAutomationContext.getMaxTaskPollingInterval());
		jse = getJavaScriptExecutor(driver);
		action = getAction(driver);
		jupiterToysPageNavigator = new JupiterToysPageNavigator(driver);
	}

	public WebDriverWait getWebDriverWait() {
		return webDriverWait;
	}

	public JavascriptExecutor getJse() {
		return jse;
	}

	public Actions getAction() {
		return action;
	}

}
