package com.quiksilver.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.quiksilver.util.BaseSuite;
import com.quiksilver.util.WebDriverManager;

//TC#16 Search for item Guest Checkout
public class SearchForItemGuestCheckoutTest extends BaseSuite {
	public WebDriverManager wm=new WebDriverManager();
	@BeforeMethod
	public void searchForItemGetToCheckout() throws Exception
	{
		cm.openHomePage(driver);
		Boolean isLoggedIn= cm.isLoggedIn(driver);

		if (isLoggedIn==true)
		{
			//logout to start test with desired logged out condition
			cm.logout(driver);
		}

		cm.searchByItemName(driver);
Thread.sleep(5000L);

Boolean isUKsite=driver.getCurrentUrl().contains("uk");
if(isUKsite==true)
{
	cm.subcatPageClickProduct(driver, 2);
	//on PDP add to cart
			Reporter.log("On PDP page title is "+ driver.getTitle());
			////ts.takeScreenshot(driver);
			cm.pdpPageSelectAddToCartNumSizes(driver);

			cm.fromMiniCartToCart(driver);

			//on Cart page click on Secure checkout
			////ts.takeScreenshot(driver);
			cm.fromCartToSignIn(driver);

			//click on unregistered checkout btn
			By locator_unregisteredcheckoutbtn=map.getLocator("interstitial_unregisteredcheckoutbtn");
			cm.checkoutSignInClickElement(driver, locator_unregisteredcheckoutbtn);

			return;
}

String item2 = "BOARDSHORTS";
//DC US no search button need to click on Enter key
driver.findElement(map.getLocator("searchField")).clear();
driver.findElement(map.getLocator("searchField")).sendKeys(item2);
wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("searchKeywordLink")));
driver.findElement(map.getLocator("searchKeywordLink")).click();

wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("searchPageTitle")));

	cm.subcatPageClickProduct(driver, 1);
		
		//on PDP add to cart
		Reporter.log("On PDP page title is "+ driver.getTitle());
		////ts.takeScreenshot(driver);
		cm.pdpPageSelectAddToCartNumSizes(driver);

		cm.fromMiniCartToCart(driver);

		//on Cart page click on Secure checkout
		////ts.takeScreenshot(driver);
		cm.fromCartToSignIn(driver);

		//click on unregistered checkout btn
		By locator_unregisteredcheckoutbtn=map.getLocator("interstitial_unregisteredcheckoutbtn");
		cm.checkoutSignInClickElement(driver, locator_unregisteredcheckoutbtn);
	
	}

	@Test
	public void searchForItemGuestCheckout() throws Exception
	{
		Boolean isUKsite=driver.getCurrentUrl().contains("uk");
		if(isUKsite==true)
		{
		   cm.step1Fields(driver);	                	      

	        cm.fromInscriptionToStep2Payment(driver);
	        Reporter.log("After click on 'continue' button on Inscription page got to "+ driver.getTitle());
	        //might use to getText() on 

	        //on Step2 billing select visa
	        cm.selectPaymentOnStep2(driver,  "visa");

	        //on Verification  Click on "Terms and Condition of Sale" checkbox and Place order button
	        cm.verificationClickPlaceOrder(driver);	        

	        //on Confirmation page 
	        cm.submitConfirmation(driver);	
	        return;
		}

		//US site functionality
		
		 cm.step1FieldsUS(driver);
		 cm.fromInscriptionToStep2Payment(driver);
	        Reporter.log("After click on 'continue' button on Inscription page got to "+ driver.getTitle());

	        cm.selectPaymentOnStep2US(driver,"visa");

	        //on Verification  Click on "Terms and Condition of Sale" checkbox and Place order button
	       cm.verificationClickPlaceOrder(driver);

	        //on Confirmation page 
	        cm.submitConfirmation(driver);
cm.sauceReport();

	}

}
