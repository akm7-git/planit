package com.test.src.infra;

import java.util.HashMap;
import java.util.Map;

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

import com.test.src.constants.BrowserType;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestUIBase extends BaseCommon{
	private WebDriver driver;
	private WebDriverWait webDriverWait;
	private JavascriptExecutor jse;
	private Actions action;


	protected WebDriver getDriverInstance() throws Exception {
		if(null !=driver) {
			return driver;
		}
		BrowserType browser = uiAutomationContext.getBrowserType();
		boolean isHeadless = uiAutomationContext.isHeadlessContextSet();


		switch (browser) {
		case CHROME:
			driver= getChromeInstance(isHeadless);
			break;
			
		case FIREFOX:
			driver=getFirefoxInstance(isHeadless);
			break;
			
		case SAFARI:
			driver=getSafariInstance();
			break;
			
		case IE:
			driver =getIEInstance();
			break;
			
		default:
			driver= getChromeInstance(isHeadless);
			break;
		}
		setDriverDependencies(driver);
		return driver;
	}
	
	private ChromeDriver getChromeInstance(boolean isHeadless)
	{
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

	private FirefoxDriver getFirefoxInstance(boolean isHeadless)
	{
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
        
        // headless
        if (isHeadless) {
        	ff_options.addArguments("--headless");
        	ff_options.addArguments("--disable-gpu");
        }

        ff_options.setAcceptInsecureCerts(true);
        ff_options.addPreference("dom.webnotifications.enabled", false);

        return ff_options;
    }
    
	private SafariDriver getSafariInstance()
	{
		WebDriverManager.safaridriver().setup();
		SafariDriver driver = new SafariDriver();
		return driver;
	}
	
	private InternetExplorerDriver getIEInstance()
	{
		WebDriverManager.iedriver().setup();
		InternetExplorerDriver driver = new InternetExplorerDriver();
		return driver;
	}
	
	private void setDriverDependencies(WebDriver driver)  
	{
		webDriverWait= getWebDriverWait(driver, uiAutomationContext.getMaxTaskPollingInterval());
		jse=getJavaScriptExecutor(driver);
		action=getAction(driver);
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
