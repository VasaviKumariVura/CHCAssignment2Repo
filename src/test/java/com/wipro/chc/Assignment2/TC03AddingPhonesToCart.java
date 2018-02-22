package com.wipro.chc.Assignment2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TC03AddingPhonesToCart extends Library
{
	WebDriver driver;
	private static final int TIME_UNIT = 30;
	FileWriter fw1;
	BufferedWriter bw1;
	WebDriverWait wait; 
	String imageCount;
	//ExtentTest logger;
	
	@BeforeClass
	public void launchBrowser() throws InterruptedException, IOException
	{
		logger = extent.startTest("Adding Phones to Cart");
		//System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		//driver = new ChromeDriver();
		
		DesiredCapabilities capability = new DesiredCapabilities();
	    capability.setBrowserName("chrome");
	    capability.setPlatform(Platform.WINDOWS);
	    driver = new RemoteWebDriver(new URL(Node), capability);
	    
		driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		loadProperties();
	}
	
	
	public void addingPhonesToCart(String FirstName, String LastName,
			String Company, String CompID, String Add1, String Add2, String City, String PostCode) throws InterruptedException, IOException
	{
		wait = new WebDriverWait(driver, TIME_UNIT);
		driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
		
		/*
		 * TC04: Click the Home Page
		 */
		driver.findElement(By.xpath(pro.getProperty("Opencart.Homepage"))).click();
		System.out.println("TC03-Step04: Navigated to Homepage Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 4 Passed");
		
		/*
		 * TC05: Click on "Samsung Galaxy Tab" on home page which is shown as the main advertisement - 
		 * this might change - but the user should click on this main image only.
		 */
		driver.findElement(By.xpath(pro.getProperty("OpenCart.HomePage.SamsungImage.Xpath"))).click();
		System.out.println("TC03-Step05: Navigated to Samsung Galaxy Tab page successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 5 Passed");
		
		/*
		 * TC06: Click on the picture of the Tab (main image)
		 */
		driver.findElement(By.xpath(pro.getProperty("Opencart.SamsungImage.MainImage.Xpath"))).click();
		Thread.sleep(3000);
		imageCount = driver.findElement(By.xpath(pro.getProperty("Opencart.MainImage.ImageCount.Xpath"))).getText().substring(11);
		System.out.println("imageCount:" +imageCount);
		fw1 = new FileWriter("D://Selenium Stuff//Selenium Workspace//Assignment2_Files//tc03_output.txt");
		bw1 = new BufferedWriter(fw1);
		bw1.append("Number of Images: "+imageCount);
		System.out.println("TC03-Step06: Count of Images written to File successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 6 Passed");
		
		/*
		 * TC07: Reach to the last image by navigating through Arrow buttons
		 */
		int imageLength = Integer.parseInt(imageCount);
		System.out.println("imageLength: " +imageLength);
		for(int i=0; i<imageLength; i++)
		{
			//driver.findElement(By.xpath(pro.getProperty("Opencart.MainImage.NextImage.Xpath"))).click();
			wait = new WebDriverWait(driver, TIME_UNIT);
			WebElement element1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("Opencart.MainImage.NextImage.Xpath"))));
			element1.click();
			takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		}
		System.out.println("TC03-Step07: Navigated through all Images successfully");
		logger.log(LogStatus.PASS, "TC03 Step 7 Passed");
		
		/*
		 * TC08: Close the Window
		 */
		//wait = new WebDriverWait(driver, TIME_UNIT);
		WebElement element2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("Opencart.ImageWindow.Close.Xpath"))));
		element2.click();
		System.out.println("TC03-Step08: Image Window Closed successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 8 Passed");
		
		/*
		 * TC09: Click on "Add to Cart".
		 */
		/*wait = new WebDriverWait(driver, TIME_UNIT);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("Opencart.SamsungGalaxy.AddToCart.Xpath"))));
		element.click();*/
		Thread.sleep(5000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.SamsungGalaxy.AddToCart.Xpath"))).click();
		//wait = new WebDriverWait(driver, TIME_UNIT);
		WebElement element3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("Opencart.SamsungGalaxy.AddToCart.SuccessMessage.Xpath"))));
		String SuccessMessage= element3.getText();
		//Validating Success Message
		Assert.assertTrue(SuccessMessage.contains("Success"), "Not Able to Add product to Cart");
		System.out.println("TC03-Step09: Added to Cart successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 9 Passed");
		
		/*
		 * TC10: Click on "Shopping Cart" displayed on ribbon message
		 */
		driver.findElement(By.linkText(pro.getProperty("OpenCart.AddToCart.SuccessMessage.ShoppingCart.linkText"))).click();
		Assert.assertTrue(driver.getCurrentUrl().contains("cart"), "Not able to navigate to Cart page");
		System.out.println("TC03-Step10: Shopping Cart page displayed successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 10 Passed");
		
		/*
		 * TC11: Click Estimate shipping and taxes.
		 */
		driver.findElement(By.xpath(pro.getProperty("Opencart.ShoppingCart.EstimateShipping.Xpath"))).click();
		System.out.println("TC03-Step11: Estimate shipping for the region,state and pincode displayed displayed successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 11 Passed");
		
		/*
		 * TC12: Click on Get Quotes  and check Flat Shipping Rate and Click on Apply Shipping 
		 */
		driver.findElement(By.xpath(pro.getProperty("Opencart.ShoppingCart.EstimateShipping.GetQuotes.Xpath"))).click();
		Thread.sleep(2000);
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		driver.findElement(By.id(pro.getProperty("Opencart.GetQuotes.ShippingMethod.id"))).click();
		Thread.sleep(2000);
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		driver.findElement(By.id(pro.getProperty("Opencart.GetQuotes.ApplyShipping.id"))).click();
		//wait = new WebDriverWait(driver, TIME_UNIT);
		WebElement element4 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("Opencart.ShippingEstimate.SuccessMessage.Xpath"))));
		String SuccessMessage1= element4.getText();
		//Validating Success Message
		Assert.assertTrue(SuccessMessage1.contains("Success"));
		String totalAmt = driver.findElement(By.xpath(pro.getProperty("Opencart.ShoppingCart.TotalAmount.Xpath"))).getText();
		bw1.newLine();
		bw1.append("Total Amount is: "+totalAmt);
		System.out.println("TC03-Step12: Total Amount written to File successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 12 Passed");
		
		/*
		 * TC13: Click on 'Use Coupon code' radio button
		 */
		driver.findElement(By.id(pro.getProperty("Opencart.ShoppingCart.UseCouponCode.id"))).click();
		System.out.println("TC03-Step13: Coupon Tab displayed successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 13 Passed");
		
		/*
		 * TC14: Enter the Random Coupon Number and click on Apply Coupon
		 */
		driver.findElement(By.name(pro.getProperty("Opencart.ShoppingCart.CouponCode.name"))).sendKeys("12345");
		driver.findElement(By.xpath(pro.getProperty("Opencart.ShoppingCart.CouponCode.ApplyCoupon.Xpath"))).click();
		//wait = new WebDriverWait(driver, TIME_UNIT);
		WebElement element5 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("Opencart.ShoppingCart.CouponCode.WarningMessage.Xpath"))));
		String warningMessage= element5.getText();
		bw1.newLine();
		bw1.append("Warning Message is: "+warningMessage);
		System.out.println("TC03-Step14: Warning Message written to File successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 14 Passed");
		
		/*
		 * TC15: Click on Check out button.
		 */
		driver.findElement(By.xpath(pro.getProperty("Opencart.ShoppingCart.CheckOut.Xpath"))).click();
		Assert.assertTrue(driver.getCurrentUrl().contains("checkout"));
		System.out.println("TC03-Step15: Navigated to Checkout page successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 15 Passed");
		
		/*
		 * TC16: Select Billing Details -> I want to use new address option
		 */
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckOut.BillingDetails.Xpath"))).click();
		//wait = new WebDriverWait(driver, TIME_UNIT);
		WebElement element6 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(pro.getProperty("Opencart.BillingDetails.NewAddress.id"))));
		element6.click();
		System.out.println("TC03-Step16: New Address Details displayed successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 16 Passed");
		
		/*
		 * TC17: Fill in the mandatory details and click continue
		 */
		driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.FirstName.name"))).sendKeys(FirstName);
		driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.LastName.name"))).sendKeys(LastName);
		driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.CompanyName.name"))).sendKeys(Company);
		driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.CompanyId.name"))).sendKeys(CompID);
		driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.Address1.name"))).sendKeys(Add1);
		driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.Address2.name"))).sendKeys(Add2);
		driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.City.name"))).sendKeys(City);
		driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.PostCode.name"))).sendKeys(PostCode);
		Select country = new Select(driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.Country.name"))));
		country.selectByVisibleText("India");
		Select state = new Select(driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.State.name"))));
		state.selectByVisibleText("Andhra Pradesh");
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.SecondContinue.Xpath"))).click();
		System.out.println("TC03-Step17: New Address Details Added successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 17 Passed");
		
		/*
		 * TC18: Select the new address for Delivery in the list box that was 
		 * specified just before and not the old address that was created during
		 *  login and click Continue
		 */
		Thread.sleep(3000);
		Select delAddress = new Select(driver.findElement(By.xpath(pro.getProperty("Opencart.DeliveryDetails.ListBox.Xpath"))));
		delAddress.selectByIndex(delAddress.getOptions().size()-1);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.ThirdContinue.Xpath"))).click();
		System.out.println("TC03-Step18: New Address Details selected in Delivery Details tab successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 18 Passed");
		
		/*
		 * TC19: Add Comments  and click on Continue.
		 */
		driver.findElement(By.name(pro.getProperty("Opencart.DeliveryMethod.Comments.name"))).
		sendKeys("I have added new address and selected the new address.");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.FourthContinue.Xpath"))).click();
		System.out.println("TC03-Step19: Comments added to Delivery Method tab successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 19 Passed");
		
		/*
		 * TC20: Check the Terms and conditions and click on the Terms and conditions link.
		 */
		Thread.sleep(2000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.TermsAndConditions.Xpath"))).click();
		driver.findElement(By.linkText(pro.getProperty("Opencart.CheckoutPage.TermsAndConditions.LinkText"))).click();
		Thread.sleep(2000);
		String terms = driver.findElement(By.id(pro.getProperty("Opencart.TermsAndConditions.Window.id"))).getText();
		int termsLength = terms.length();
		bw1.newLine();
		bw1.append("Number of Characters in Terms&Conditions Window: "+termsLength);
		System.out.println("TC03-Step20: Number of Characters in Terms&Conditions written to file successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 20 Passed");
		
		/*
		 * TC21: Close the Window and click Continue
		 */
		driver.findElement(By.id(pro.getProperty("Opencart.TermsAndConditions.Close.id"))).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.FifthContinue.Xpath"))).click();
		System.out.println("TC03-Step21: Closed Terms&Conditions and clicked continue button successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		//checkpoint
		WebElement element7 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("Opencart.CheckoutPage.ConfirmOrder.Price1.Xpath"))));
		String price1 = element7.getText();
		WebElement element8 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("Opencart.CheckoutPage.ConfirmOrder.Price2.Xpath"))));
		String price2 = element8.getText();
		WebElement element9 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("Opencart.CheckoutPage.ConfirmOrder.Price3.Xpath"))));
		String price3 = element9.getText();
		ArrayList<String> al= new ArrayList<String>();
		al.add(price1);
		al.add(price2);
		al.add(price3);
		
		File excel = new File("D://Selenium Stuff//Selenium Workspace//Assignment2_Files//PriceDetails.xlsx");
		FileOutputStream fos = new FileOutputStream(excel);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet s1 = wb.createSheet("Price Details");
		
		for(int i=0,row=0;row<al.size();row++,i++)
		{
			s1.createRow(row).createCell(1).setCellValue(al.get(i));
		}
		wb.write(fos);
		logger.log(LogStatus.PASS, "TC03 Step 21 Passed");
		
		/*
		 * TC22: Click on Modify for Billing details rollup (to select the initially created address)
		 */
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.BillingDetails.Modify.Xpath"))).click();
		System.out.println("TC03-Step22: Selected Initial Address in the Billing Details tab successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 22 Passed");
		
		/*
		 * TC23: Select the first address in the list box and click on continue until you reach confirm order
		 */
		Thread.sleep(2000);
		Select addressOld = new Select(driver.findElement(By.xpath(pro.getProperty("Opencart.BillingDetails.ListBox.Xpath"))));
		addressOld.selectByIndex(0);
		Thread.sleep(2000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.SecondContinue.Xpath"))).click();
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		Thread.sleep(2000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.ThirdContinue.Xpath"))).click();
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		Thread.sleep(2000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.FourthContinue.Xpath"))).click();
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		Thread.sleep(2000);
		boolean termsSelected = driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.TermsAndConditions.Xpath"))).isSelected();
		if(termsSelected == false)
		{
			driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.TermsAndConditions.Xpath"))).click();
		}
		Thread.sleep(2000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.FifthContinue.Xpath"))).click();
		System.out.println("TC03-Step23: Clicked on all Continue buttons in Checkout page successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 23 Passed");
		
		/*
		 * TC24: Click on Confirm order
		 */
		Thread.sleep(2000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.ConfirmOrder.Xpath"))).click();
		System.out.println("TC03-Step24: Your Order Has Been Processed! page displayed Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		logger.log(LogStatus.PASS, "TC03 Step 24 Passed");
	}
	
	
	
	
	
	
	/*@Test (dataProvider="BillingAddressDetails",priority=17)
	public void step17_fillBillingDetails(String FirstName, String LastName,
			String Company, String CompID, String Add1, String Add2, String City, String PostCode) 
	{
		driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.FirstName.name"))).sendKeys(FirstName);
		driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.LastName.name"))).sendKeys(LastName);
		driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.CompanyName.name"))).sendKeys(Company);
		driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.CompanyId.name"))).sendKeys(CompID);
		driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.Address1.name"))).sendKeys(Add1);
		driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.Address2.name"))).sendKeys(Add2);
		driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.City.name"))).sendKeys(City);
		driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.PostCode.name"))).sendKeys(PostCode);
		Select country = new Select(driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.Country.name"))));
		country.selectByVisibleText("India");
		Select state = new Select(driver.findElement(By.name(pro.getProperty("Opencart.BillingDetails.State.name"))));
		state.selectByVisibleText("Andhra Pradesh");
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.SecondContinue.Xpath"))).click();
		System.out.println("TC03-Step17: New Address Details Added successfully");
	}*/
	
	
	@Test (dataProvider="BillingAddressDetails", priority=3, description = "Adding Phones To Cart")
	public void testcase03(String FirstName, String LastName,
			String Company, String CompID, String Add1, String Add2, String City, String PostCode) throws InterruptedException, IOException
	{
		launchOpencart(driver);
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		login(driver);
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
		addingPhonesToCart(FirstName, LastName,
				Company, CompID, Add1, Add2, City, PostCode);
		logout(driver);
		takeSnapshot(driver,DateFormatScreenShot("TC03_"));
	}
	
	
	@DataProvider (name="BillingAddressDetails")
	public Object[][] readRegistrationDetails() throws BiffException, IOException
	{
		File reg = new File("D://Selenium Stuff//Selenium Workspace//Assignment2_Files//TestData.xls");
		Workbook wb = Workbook.getWorkbook(reg);
		Sheet s1 = wb.getSheet("BillingDetails");
		int rows = s1.getRows();
		int columns = s1.getColumns();
		String inputData[][] = new String[rows][columns];
		for(int row=0; row<rows; row++)
		{
			for(int col=0;col<columns;col++)
			{
				Cell c = s1.getCell(col,row);
				inputData[row][col] = c.getContents();
				//System.out.println(inputData[row][col]);
			}
		}
		return inputData;
	}
	
	
	@AfterClass
	public void closeBrowser() throws IOException, InterruptedException
	{
		bw1.close();
		fw1.close();
		//extent.endTest(logger);
		extent.flush();
		Thread.sleep(2000);
		driver.close();
		System.out.println("END OF TESTCASE 03");
	}
}
