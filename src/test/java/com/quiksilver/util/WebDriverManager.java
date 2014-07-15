package com.quiksilver.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.Reporter;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;


public class WebDriverManager implements SauceOnDemandSessionIdProvider {
	private static WebDriver driver ;
	private static Logger log;
	private static DesiredCapabilities capability;
	static ProfilesIni allProfiles = new ProfilesIni();
	static FirefoxProfile webdriver;
	static FirefoxProfile defaultProfile;
	
	static ReadingProperties rp = new ReadingProperties();
	static String chromePath =rp.readConfigProperties("chromePath");
	
	//default no args is Firefox 
	public static WebDriver startDriver (){

	    webdriver = allProfiles.getProfile("WebDriver");
		defaultProfile=allProfiles.getProfile("default");
		
		webdriver.setPreference("network.http.phishy-userpass-length", 255);
		defaultProfile.setPreference("network.http.phishy-userpass-length", 255);

		webdriver.setEnableNativeEvents(true);
		defaultProfile.setEnableNativeEvents(true);
		
		driver=new FirefoxDriver(webdriver);
		//   defaultWindowSize(driver);
		return driver;
	}

	public static WebDriver startDriver(String browser) throws MalformedURLException
	{
		
		if(browser.equalsIgnoreCase("firefox")) 
		{
			DesiredCapabilities caps = DesiredCapabilities.firefox();
			caps.setCapability("platform", "Windows 7");
		    caps.setCapability("version", "30");
		    driver = new RemoteWebDriver(
						  new URL("http://veronicapeterfluid:f7c04d36-8386-4bf8-b75a-5ff52f50f61a@ondemand.saucelabs.com:80/wd/hub"),   //http://FluidQA:535f3514-ec3d-4632-af78-a613825a77a9@ondemand.saucelabs.com:80/wd/hub
						  caps);
			
		   //535f3514-ec3d-4632-af78-a613825a77a9

		    
			//*************Use jenkins plugin browser version
			/*	   DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
desiredCapabilities.setBrowserName(System.getenv("SELENIUM_BROWSER"));
desiredCapabilities.setVersion(System.getenv("SELENIUM_VERSION"));
desiredCapabilities.setCapability(CapabilityType.PLATFORM, System.getenv("SELENIUM_PLATFORM"));
driver = new RemoteWebDriver(
     new URL("http://veronicapeterfluid:f7c04d36-8386-4bf8-b75a-5ff52f50f61a@ondemand.saucelabs.com:80/wd/hub"),
     desiredCapabilities);
			 defaultWindowSize(driver);*/
		    
	    }
		
		
	    if(browser.equalsIgnoreCase("iexplore")) {
	        capability = DesiredCapabilities.internetExplorer();
	        capability.setBrowserName("iexplore");
	        capability.setPlatform(org.openqa.selenium.Platform.WINDOWS);
	    }
	    
if(browser.equalsIgnoreCase("chrome")) {
           /*  System.setProperty("webdriver.chrome.driver", "/Users/vpeter/Desktop/Selenium/AllWebDriverJARs/chromedriver.exe");

              driver=new ChromeDriver();


            defaultWindowSize(driver);*/
	DesiredCapabilities caps = DesiredCapabilities.chrome();
	caps.setCapability("platform", "OS X 10.6");
	caps.setCapability("version", "27");
    driver = new RemoteWebDriver(
				  new URL("http://veronicapeterfluid:f7c04d36-8386-4bf8-b75a-5ff52f50f61a@ondemand.saucelabs.com:80/wd/hub"),  //
				  caps);

	    }
	    if(browser.equalsIgnoreCase("safari")) {
	    	
	    	/*SafariOptions options = new SafariOptions();

	        capability = DesiredCapabilities.safari();
	        capability.setBrowserName("safari");
	        capability.setPlatform(org.openqa.selenium.Platform.ANY);

	    	//~/Library/Safari/Extensions
			//download safariextz>>import to lib folder; 
			//String locationSafariextz=System.getProperty("user.dir")+"/lib/SafariDriver2.32.0.safariextz";
			//System.setProperty("webdriver.safari.driver", locationSafariextz);	


	        if(isSupportedPlatform()==true);

			{

			    driver = new SafariDriver(capability);
			}*/
	    	
	    	DesiredCapabilities caps = DesiredCapabilities.safari();
	    	caps.setCapability("platform", "OS X 10.6");
	    	caps.setCapability("version", "5");
	    	 driver = new RemoteWebDriver(
					  new URL("http://veronicapeterfluid:f7c04d36-8386-4bf8-b75a-5ff52f50f61a@ondemand.saucelabs.com:80/wd/hub"),   //  http://FluidQA:535f3514-ec3d-4632-af78-a613825a77a9@ondemand.saucelabs.com:80/wd/hub
					  caps);
	    	
	    }

		//defaultWindowSize(driver);
		return driver;
	}
	
	private static boolean isSupportedPlatform() {
	    Platform current = Platform.getCurrent();
	    return Platform.MAC.is(current) || Platform.WINDOWS.is(current);
	  }
	
	
	public static void stopDriver()
	{
		driver.quit();
	}

	public static WebDriver getDriverInstance()
	{
		//in progress
		return driver;
	}

	public static  void defaultWindowSize(WebDriver driver)

	{
		//diver.manage().window().maximize(); //this would work only for Firefox and IE
		driver.manage().window().setPosition(new Point(0,0));
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		//Dimension dim = new Dimension(1024, 768);

		Dimension dim = new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
		driver.manage().window().setSize(dim);
		Reporter.log("Currently testing URL: " +driver.getCurrentUrl());
	}


	public static void getBrowser(WebDriver driver)
	{
		if (driver instanceof FirefoxDriver) {
			System.out.println("Firefox DRIVER");
			Reporter.log("Using Firefox browser version 26");
		} 

		else if (driver instanceof ChromeDriver) {
			System.out.println("Chrome DRIVER");
			Reporter.log("Using Chrome version 32 browser");
		}

		else if (driver instanceof SafariDriver) {
			System.out.println("Safari DRIVER");
			Reporter.log("Using Safari 6 browser");
		}
	}

	/*to use it        Logger log=WebDriverManager.LoggerGetInstance(); */
	public static Logger LoggerGetInstance() {
		log = Logger.getLogger(WebDriverManager.class);
		return log;
	}

	@Override
	public String getSessionId() {
		String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s", ((RemoteWebDriver) driver).getSessionId().toString(), "QUIKSILVER-DC SHOES Brand Automation Tests");
        System.out.println(message);
		return message;
	}
}


