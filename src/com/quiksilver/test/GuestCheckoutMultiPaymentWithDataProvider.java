package com.quiksilver.test;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.quiksilver.util.BaseSuite;
import com.quiksilver.util.CommonMethods;
import com.quiksilver.util.ObjectMap;
import com.quiksilver.util.ReadingProperties;
import com.quiksilver.util.Return2D;
import com.quiksilver.util.TakeScreenshot;
import com.quiksilver.util.WebDriverManager;


//TC #8 Guest Visa Checkout  with data provider using data from xlsx spreadsheet
public class GuestCheckoutMultiPaymentWithDataProvider extends BaseSuite {


	 String excelPath= rp.readConfigProperties("excel.path");
     String path = System.getProperty("user.dir")+excelPath;
     String submittedOrdersPath=rp.readConfigProperties("submittedorders.path");
     
	
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
		ts.takeScreenshot(driver);
		
		//2/24 using new CommonMethod, '1' means it will click on 2nd product on subcat page
		cm.subcatPageClickProduct(driver, 1);	
				
		//on PDP add to cart
		Reporter.log("On PDP page title is "+ driver.getTitle());
		ts.takeScreenshot(driver);
		cm.pdpPageSelectAddToCart(driver);

		cm.fromMiniCartToCart(driver);
		
		//on Cart page click on Secure checkout
		ts.takeScreenshot(driver);
		cm.fromCartToSignIn(driver);
		
		//click on unregistered checkout btn
		By locator_unregisteredcheckoutbtn=map.getLocator("interstitial_unregisteredcheckoutbtn");
		cm.checkoutSignInClickElement(driver, locator_unregisteredcheckoutbtn);
	}
	
	@Test(dataProvider="inscriptionData")
	public void fillOutStep1InscriptionForm(Hashtable<String, String> data) throws Exception
	{
       // cm.step1Salutation(driver, "Mrs");

		WebElement fname=driver.findElement(map.getLocator("inscription_fnameid"));
        fname.clear();
        fname.sendKeys(data.get("FNAME"));

        WebElement lname=driver.findElement(map.getLocator("inscription_lnameid"));
        lname.clear();
        lname.sendKeys(data.get("LNAME"));
        //        System.out.println("=============="+data.get("LNAME"));
//GENDER
        
        
      
		new Select(driver.findElement(map.getLocator("inscription_genderid"))).selectByVisibleText("Female");
		
	
      WebElement phone =driver.findElement(map.getLocator("inscription_phoneid"));
        phone.clear();
        phone.sendKeys(data.get("PHONE"));

        WebElement email = driver.findElement(map.getLocator("inscription_emailid"));
        email.clear();
        email.sendKeys(data.get("EMAIL"));
        
        WebElement confirmEmail =driver.findElement(map.getLocator("inscription_confirmemailid"));
        confirmEmail.clear();
        confirmEmail.sendKeys(data.get("EMAIL"));

        WebElement address1 =driver.findElement(map.getLocator("inscription_address1id"));
        address1.clear();
        address1.sendKeys(data.get("ADDRESS1"));
       
       /* WebElement address2 =driver.findElement(map.getLocator("inscription_address2id"));
        address2.clear();
        address2.sendKeys(data.get("ADDRESS2"));
*/
       
        WebElement addInfo =driver.findElement(map.getLocator("inscription_additionalid"));
        addInfo.clear();
        addInfo.sendKeys(data.get("ADDITIONAL INFO"));

        WebElement loyaltyCard =driver.findElement(map.getLocator("inscription_loyaltycardid"));
        loyaltyCard.clear();
        loyaltyCard.sendKeys(data.get("LOYALTY"));


        WebElement zip =driver.findElement(map.getLocator("inscription_zipid"));
        zip.clear();
        
        //zip.sendKeys(data.get("ZIP"));
        //slowType needed because of ajax and to trigger autopopulate of city

        By locator_zip=map.getLocator("inscription_zipid");
        cm.slowType(driver, locator_zip, data.get("ZIP"));
        
        Boolean isCityInError =cm.isCityInError(driver) ;
        System.out.println("isCityInError ="+isCityInError);
        
        Boolean isUSsite=driver.getCurrentUrl().contains("en_US");
		if(isUSsite==true)
		{
			//use US address
		}
        
		//this is optional if the num of test is large just remove taking screenshot below
        ts.takeScreenshot(driver);
        
        cm.fromInscriptionToStep2Payment(driver);
        Reporter.log("After click on 'continue' button on Inscription page got to "+ driver.getTitle());
        //might use to getText() on 
        
        //on Step2 billing select visa       
        String cardType=data.get("CARD TYPE");        
        cm.selectPaymentOnStep2(driver, cardType);
        
        //on Verification  Click on "Terms and Condition of Sale" checkbox and Place order button
        cm.verificationClickPlaceOrder(driver);
        
        //on Confirmation page 
        cm.submitConfirmation(driver);	        
	}
	
	   @DataProvider(name="inscriptionData")
       public Object[][] countryProvider() throws Exception
       {
			 
               Return2D return2d = new Return2D();
               return2d.open(path, "TestData");
               Object[][] data=null;

               data = return2d.getData();
               return data;
       }
	

}