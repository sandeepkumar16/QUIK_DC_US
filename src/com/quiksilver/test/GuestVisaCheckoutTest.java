package com.quiksilver.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.quiksilver.util.BaseSuite;

//TC#8 Guest Visa Checkout without DataProvider

public class GuestVisaCheckoutTest extends BaseSuite {

	@BeforeMethod
	public void getToStep1() throws Exception
	{
		cm.openHomePage(driver);
		Boolean isLoggedIn= cm.isLoggedIn(driver);

		if (isLoggedIn==true)
		{
			//logout to start test with desired logged out condition
			cm.logout(driver);
		}
		By locator_tshirtLink=map.getLocator("men_tshirtcss");
		cm.homePageMainNavMen(driver, locator_tshirtLink);

		//on subcat page click on product - pass driver and locator for the product you want to click on
		Reporter.log("On Subcat page title is "+ driver.getTitle());
		//ts.takeScreenshot(driver);

		By locator_subcatProduct = map.getLocator("subcat_product");
		cm.subcatPageClickProduct(driver, locator_subcatProduct);

		//on PDP add to cart
		Reporter.log("On PDP page title is "+ driver.getTitle());
		//ts.takeScreenshot(driver);
		cm.pdpPageSelectAddToCart(driver);

		cm.fromMiniCartToCart(driver);

		//on Cart page click on Secure checkout
		//ts.takeScreenshot(driver);
		cm.fromCartToSignIn(driver);

		//click on unregistered checkout btn
		By locator_unregisteredcheckoutbtn=map.getLocator("interstitial_unregisteredcheckoutbtn");
		cm.checkoutSignInClickElement(driver, locator_unregisteredcheckoutbtn);

	}

	@Test
	public void testGuestCheckoutVisa() throws Exception
	{
		    cm.step1Fields(driver);

			//this is optional if the num of test is large just remove taking screenshot below
	        //ts.takeScreenshot(driver);

	        cm.fromInscriptionToStep2Payment(driver);
	        Reporter.log("After click on 'continue' button on Inscription page got to "+ driver.getTitle());

	      
	        //on Step2 billing select visa
	        cm.selectPaymentOnStep2(driver,"visa");

	        //on Verification  Click on "Terms and Condition of Sale" checkbox and Place order button
	        cm.verificationClickPlaceOrder(driver);

	        //on Confirmation page 
	        cm.submitConfirmation(driver);	        

	}

}
