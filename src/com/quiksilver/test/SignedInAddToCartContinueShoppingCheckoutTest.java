package com.quiksilver.test;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.seleniumhq.jetty7.util.log.Log;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.quiksilver.util.BaseSuite;

//TC # 9 Browse from top level-subcategory-category navigation-express shop add to cart-Continue Shoping 
//Add other item to cart-View cart and checkout

/**
 * @author igonzalez
 *Smoke tests group
 */
@Listeners({ com.quiksilver.util.TestListenerFailPass.class })

public class SignedInAddToCartContinueShoppingCheckoutTest extends BaseSuite {
	public  String testEmail = rp.readConfigProperties("yahoo");
	  public String testPassword = rp.readConfigProperties("password_yahoo");  
	  

	@BeforeMethod
	public void addToCartContinueShoppingPDP() throws Exception
	{
		cm.openHomePage(driver);
		Boolean isLoggedIn= cm.isLoggedIn(driver);

		if (isLoggedIn==true)
		{
			//logout to start test with desired logged out condition
			cm.logout(driver);
		}
		
		//login using email and password read from config.properties
		System.out.println("Using email "+testEmail+" password "+testPassword);
		cm.login(driver, testEmail, testPassword);
		
		By locator_tshirtLink=map.getLocator("men_tshirtcss");		
		cm.homePageMainNavMen(driver, locator_tshirtLink);
		
		//on subcat page click on product - pass driver and locator for the product you want to click on
		Reporter.log("On Subcat page title is "+ driver.getTitle());
		ts.takeScreenshot(driver);
		
		By locator_subcatProduct = map.getLocator("subcat_product");
		cm.subcatPageHoverOnProductClickExpressLink(driver,locator_subcatProduct);

		cm.fromMiniCartToCart(driver);
		
		//click on continue shopping
		cm.fromCartToContinueShopping(driver);
		
		//main nav jeans
		//driver.findElement(By.cssSelector("a.omni_header_flyout_link.men_clothing_jeans-denim")).click();
		By locator_jeansLink=map.getLocator("men_jeanscss");
		cm.homePageMainNavMen(driver, locator_jeansLink);
		
        //click on jeans product; locator is the same for a diff subcat 
		//cm.subcatPageClickProduct(driver, locator_subcatProduct);
		
		//2/21 click on 8th thumbnail 
		cm.subcatPageClickProduct(driver, 7);
		
		//on PDP select Size
		
		//on PDP select Length
		
		//on PDP click on ADD TO CART btn
		cm.pdpPageSelectAddToCart(driver);
		//click on Checkout btn in Mini-cart
		cm.fromMiniCartToCart(driver);
		
		//on Cart page click on Secure checkout
		ts.takeScreenshot(driver);
		cm.fromCartToSignIn(driver);
	}
	
	@Test
	public void testSignedInAddToCartContinueShopping() throws Exception
	{
		//because signed in Inscription page is skipped

	        //on Step2 billing select master card
	        cm.selectPaymentOnStep2(driver, "master");
	        
	        //on Verification  Click on "Terms and Condition of Sale" checkbox and Place order button
	        cm.verificationClickPlaceOrder(driver);
	        
	      //on Confirmation page 
	        cm.submitConfirmation(driver);	
	        
	        //recommended products
	        
	}

}
