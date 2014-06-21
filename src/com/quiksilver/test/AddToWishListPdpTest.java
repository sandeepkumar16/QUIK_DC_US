package com.quiksilver.test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.quiksilver.util.BaseSuite;


//TC # 6 Add to WishList from PDP
public class AddToWishListPdpTest extends BaseSuite {

	@Test
	public void saveForLaterPDP() throws Exception
	{
		cm.openHomePage(driver);
		Boolean isLoggedIn= cm.isLoggedIn(driver);

		if (isLoggedIn==true)
		{
			//logout to start test with desired logged out condition
			cm.logout(driver);
		}
		cm.login(driver, testEmailWithSavedCards, pswdForTestEmailWithSavedCards);
		System.out.println("Using email "+testEmailWithSavedCards+" password "+pswdForTestEmailWithSavedCards);
		
		//pass locator for subcat you want to the method (here it is t-shirts)
		By locator_tshirtLink=map.getLocator("men_tshirtcss");
		cm.homePageMainNavMen(driver, locator_tshirtLink);
		
		Reporter.log("On Subcat page title is "+ driver.getTitle());
		
		//2/24 using new CommonMethod, '1' means it will click on 2nd product on subcat page
		cm.subcatPageClickProduct(driver, 1);	
		
		//on PDP click on save for later and assert 'saved' msg displayed on the screen
		Reporter.log("On PDP page title is "+ driver.getTitle());
		cm.pdpPageSaveForLater(driver);		
		
		ts.takeScreenshot(driver);
				

	}
	
}
