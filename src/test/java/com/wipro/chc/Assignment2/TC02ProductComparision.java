package com.wipro.chc.Assignment2;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class TC02ProductComparision extends Library
{
	WebDriver driver;
	private static final int TIME_UNIT = 30;
	FileWriter fw1;
	BufferedWriter bw1;
	FileWriter fw2;
	BufferedWriter bw2;
	FileWriter fw3;
	BufferedWriter bw3;
	FileWriter fw4;
	BufferedWriter bw4;
	FileReader fr;
	BufferedReader br;
	WebDriverWait wait;
	//ExtentTest logger;
	
	@BeforeClass
	public void launchBrowser() throws InterruptedException, IOException
	{
		//System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		//driver = new ChromeDriver();
		
		/*DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
	    capability.setPlatform(Platform.WINDOWS);
	    driver = new RemoteWebDriver(new URL(Node), capability);*/
		
		DesiredCapabilities capability = new DesiredCapabilities();
	    capability.setBrowserName("chrome");
	    capability.setPlatform(Platform.WINDOWS);
	    driver = new RemoteWebDriver(new URL(Node), capability);
	        
		driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
		loadProperties();
		logger = extent.startTest("Product Comparision");
	}
	
	
	
	public void productComparision(String ProductName) throws AWTException, IOException, InterruptedException
	{
		wait = new WebDriverWait(driver, TIME_UNIT);
		driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
		
		/*
		 * TC04: Enter the key word in search text box on top right of the page.  
		 * Hit Enter button only and no icon click.
		 */
		Thread.sleep(2000);
		driver.findElement(By.name(pro.getProperty("OpenCart.Search.Name"))).clear();
		driver.findElement(By.name(pro.getProperty("OpenCart.Search.Name"))).sendKeys(ProductName);
		Thread.sleep(1000);
		driver.findElement(By.name(pro.getProperty("OpenCart.Search.Name"))).sendKeys(Keys.ENTER);
		/*Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);*/
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		//Count Number of Items
		String count = driver.findElement(By.className(pro.getProperty("OpenCart.Search.Results.ClassName"))).getText().substring(13,14);
		//System.out.println(count);
		fw1 = new FileWriter("D://Selenium Stuff//Selenium Workspace//Assignment2_Files//tc02_output.txt");
		bw1 = new BufferedWriter(fw1);
		bw1.newLine();
		bw1.append("Number of Search Items with phrase 'apple': "+count);
		System.out.println("TC02-Step04: Searched for the apple Product and added count to file Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 4 Passed");
		
		/*
		 * TC05: Select "Monitors" under Components in the drop down.  
		 * Check "Search in sub categories" and click Search.
		 */
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Components.Xpath"))).click();
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Components.Monitors.Xpath"))).click();
		String count1 = driver.findElement(By.className(pro.getProperty("OpenCart.Search.Results.ClassName"))).getText().substring(13,14);
		bw1.newLine();
		bw1.append("Number of Monitors: "+count1);
		System.out.println("TC02-Step05: Searched for the Monitors and added count to File Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 5 Passed");
		
		/*
		 * TC06: Click on "Phones and PDA's" tab.
		 */
		driver.findElement(By.xpath(pro.getProperty("OpenCart.PhonesAndPDAs.Xpath"))).click();
		String count2 = driver.findElement(By.className(pro.getProperty("OpenCart.Search.Results.ClassName"))).getText().substring(13,14);
		bw1.newLine();
		bw1.append("Number of Phones & PDA's: "+count2);
		System.out.println("TC02-Step06: Navigated to PhonesAndPDAs tab and added count to File Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 6 Passed");
		
		/*
		 * TC07: Sort from the "Price (High > Low)" for the page.
		 */
		Select sort = new Select(driver.findElement(By.xpath(pro.getProperty("OpenCart.PhonesAndPDAs.SortPrice.Xpath"))));
		sort.selectByVisibleText("Price (High > Low)");
		System.out.println("TC02-Step07: Sorted the Products from High to Low Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		//checkpoint
		String prod1 = driver.findElement(By.xpath(pro.getProperty("OpenCart.PhonesAndPDAs.HighToLow.P1.Xpath"))).getText().substring(9);
		String prod2 = driver.findElement(By.xpath(pro.getProperty("OpenCart.PhonesAndPDAs.HighToLow.P2.Xpath"))).getText().substring(9);
		String prod3 = driver.findElement(By.xpath(pro.getProperty("OpenCart.PhonesAndPDAs.HighToLow.P3.Xpath"))).getText().substring(9);
		float p1 = Float.parseFloat(prod1);
		float p2 = Float.parseFloat(prod2);
		float p3 = Float.parseFloat(prod3);
		Assert.assertTrue(((p1>p2)&&(p2>p3)), "Prices not Sorted successfully");
		logger.log(LogStatus.PASS, "TC02 Step 7 Passed");
		
		/*
		 * TC08: Click on "Add to Compare " for the first three phones" and click on Close button
		 */
		fw2 = new FileWriter("D://Selenium Stuff//Selenium Workspace//Assignment2_Files//tc02_output_productnames.txt");
		bw2 = new BufferedWriter(fw2);
		for(int i=1; i<4; i++)
		{
			Thread.sleep(2000);
			//driver.findElement(By.xpath(pro.getProperty("OpenCart.PhonesAndPDAs.AddToCompare.Xpath"))).click();
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='content']/div[4]/div["+i+"]/div[1]/div[3]/a")));
			element.click();
			Thread.sleep(3000);
			//driver.findElement(By.xpath("//div[@id='content']/div[4]/div["+i+"]/div[1]/div[3]/a")).click();
			WebElement element1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("OpenCart.PhonesAndPDAs.AddToCompare.SuccessMessage.Xpath"))));
			String SuccessMessage = null;
			SuccessMessage= element1.getText();
			Thread.sleep(3000);
			//Validate Success Message
			Assert.assertTrue(SuccessMessage.contains("Success"), "Not Able to Add product for Compare");
			System.out.println("Successfully added product "+i+" to Compare");
			takeSnapshot(driver,DateFormatScreenShot("TC02_"));
			Thread.sleep(3000);
			//Write Product Name to File
			String pname = driver.findElement(By.xpath(pro.getProperty("OpenCart.PhonesAndPDAs.AddToCompare.SuccessMessage.ProductName.Xpath"))).getText();
			bw2.append(pname);
			bw2.newLine();
		}
		bw2.close();
		fw2.close();
		//Close Success Message
		Thread.sleep(2000);
		driver.findElement(By.xpath(pro.getProperty("OpenCart.PhonesAndPDAs.AddToCompare.SuccessMessage.Close.Xpath"))).click();
		System.out.println("TC02-Step08: Added 3 products to Compare Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 8 Passed");
		
		/*
		 * TC09: Click on "Product Compare"
		 */
		Thread.sleep(2000);
		driver.findElement(By.id(pro.getProperty("OpenCart.ProductCompare.id"))).click();
		fr = new FileReader("D://Selenium Stuff//Selenium Workspace//Assignment2_Files//tc02_output_productnames.txt");
		br = new BufferedReader(fr);
		String content = "";
		int i=2;
		//String pname2 = driver.findElement(By.xpath("//div[@id='content']/table/tbody[1]/tr[1]/td["+i+"]/a")).getText();
		//System.out.println("Product name in Comparision Table: "+pname2);
		//System.out.println("Product name in Text file: "+br.readLine());
		
		while((content=br.readLine())!=null && i<5)
		{
			//System.out.println("test");
			String pname1 = driver.findElement(By.xpath("//div[@id='content']/table/tbody[1]/tr[1]/td["+i+"]/a")).getText();
			Assert.assertEquals(pname1, content);
			System.out.println("Product name in Comparision Table: "+pname1);
			System.out.println("Product name in Text file: "+content);
			i++;
		}
		System.out.println("TC02-Step09: Product Names in Comparision table and Flat file matched Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 9 Passed");
		
		/*
		 * TC10: Click on the first phone link on the page.
		 */
		//driver.findElement(By.xpath(pro.getProperty("OpenCart.ProductCompare.FirstPhoneLink.Xpath"))).click();
		driver.findElement(By.linkText("iPhone")).click();
		System.out.println("TC02-Step10: Product Details displaying Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 10 Passed");
		
		/*
		 * TC11: Check the fifth feature in the description section of the phone and write into flat file.
		 */
		fw3 = new FileWriter("D://Selenium Stuff//Selenium Workspace//Assignment2_Files//tc02_output_prod_desc.txt");
		bw3 = new BufferedWriter(fw3);
		try{
			String fifthLine = driver.findElement(By.xpath(pro.getProperty("OpenCart.ProductCompare.FirstPhoneLink.DescriptionFifthLine.Xpath"))).getText();
			bw3.append(fifthLine);
			System.out.println("TC02-Step11: Description Line5 added to Flat file Successfully");
		   }
		catch (Exception e)
		{
			bw3.append("NO DESCRIPTION");
			System.out.println("TC02-Step11: the Phrase NO DESCRIPTION added to flat file successfully");
		}
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 11 Passed");
		
		/*
		 * TC12: Click on "Add to Cart".
		 */
		driver.findElement(By.id(pro.getProperty("OpenCart.FirstPhoneLink.AddToCart.id"))).click();
		//wait = new WebDriverWait(driver, 10);
		WebElement element2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("OpenCart.FirstPhoneLink.AddToCart.SuccessMessage.Xpath"))));
		String SuccessMessage= element2.getText();
		Assert.assertTrue(SuccessMessage.contains("Success"), "Not Able to Add product for Cart");
		System.out.println("TC02-Step12: Product Added to Cart successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 12 Passed");
		
		/*
		 * TC13: Click on "Shopping Cart" displayed on ribbon message
		 */
		driver.findElement(By.linkText(pro.getProperty("OpenCart.AddToCart.SuccessMessage.ShoppingCart.linkText"))).click();
		System.out.println("TC02-Step13: Cart Displayed with the Product added to it successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 13 Passed");
		
		/*
		 * TC14: Click on Check out button.
		 */
		driver.findElement(By.xpath(pro.getProperty("OpenCart.ShoppingCart.Checkout.Xpath"))).click();
		System.out.println("TC02-Step14: Checkout Page displayed successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 14 Passed");
		
		/*
		 * TC15: Click on Continue buttons (2nd, 3rd and 4th)
		 */
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.SecondContinue.Xpath"))).click();
		System.out.println("TC02-Step15: Second Continue clicked Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.ThirdContinue.Xpath"))).click();
		System.out.println("TC02-Step15: Third Continue clicked Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.FourthContinue.Xpath"))).click();
		System.out.println("TC02-Step15: Fourth Continue clicked 5th Step displayed Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 15 Passed");
		
		/*
		 * TC16: Check the Terms and Conditions Checkbox and click Continue
		 */
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.TermsAndConditions.Xpath"))).click();
		System.out.println("TC02-Step16: Agreed Terms And Conditions Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.FifthContinue.Xpath"))).click();
		System.out.println("TC02-Step16: Clicked on Fifth Continue. Order Tab displayed Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 16 Passed");
		
		/*
		 * TC17: Click Confirm Order
		 */
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.ConfirmOrder.Xpath"))).click();
		System.out.println("TC02-Step17: Your Order Has Been Processed! page displayed Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 17 Passed");
		
		/*
		 * TC18: Click on browser Back button
		 */
		Thread.sleep(3000);
		driver.navigate().back();
		WebElement element3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("Opencart.EmptyShoppingCart.Xpath"))));
		String actual = element3.getText();
		Assert.assertEquals(actual, pro.getProperty("Opencart.EmptyShoppingCart.Message"));
		System.out.println("TC02-Step18: Cart is displayed with Empty Cart Message Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 18 Passed");
		
		/*
		 * TC19: Click on "Order history " from "My account "tab
		 */
		driver.findElement(By.xpath(pro.getProperty("Opencart.OrderHistory.Xpath"))).click();
		System.out.println("TC02-Step19: Navigated to Order History page Successfully");
		//checkpoint
		String[] orderNum = driver.findElement(By.xpath(pro.getProperty("Opencart.OrderHistory.OrderNumber.Xpath"))).getText().split(":");
		String[] cost = driver.findElement(By.xpath(pro.getProperty("Opencart.OrderHistory.OrderCost.Xpath"))).getText().split("Total:");
		fw4 = new FileWriter("D://Selenium Stuff//Selenium Workspace//Assignment2_Files//tc02_output_orderDetails.txt");
		bw4 = new BufferedWriter(fw4);
		bw4.append("Order Number is: "+orderNum[1]);
		bw4.newLine();
		bw4.append("Order Cost is: "+cost[1]);
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 19 Passed");
		
		/*
		 * TC20: Click on "Subscribe to news letters".
		 */
		driver.findElement(By.xpath(pro.getProperty("Opencart.EmptyShoppingCart.NewsLetter.Xpath"))).click();
		Assert.assertTrue(driver.getCurrentUrl().contains("newsletter"), "Not able to Navigate to NewsLetter Page");
		driver.findElement(By.xpath(pro.getProperty("Opencart.EmptyShoppingCart.NewsLetterSubscribe.Xpath"))).click();
		//wait = new WebDriverWait(driver, 10);
		//WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("Opencart.EmptyShoppingCart.NewsLetterSubscribe.Xpath"))));
		//element.click();
		System.out.println("TC02-Step20: Subscribed to NewsLetters Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 20 Passed");
		
		/*
		 * TC21: Click on Extras -> Specials in the footer
		 */
		driver.findElement(By.xpath(pro.getProperty("Opencart.Footer.Extras.Specials.Xpath"))).click();
		String count3 = driver.findElement(By.xpath(pro.getProperty("Opencart.Specials.NoOfItems.Xpath"))).getText().substring(13,15);
		System.out.println("TC02-Step21: "+count3+" Special Offers Displayed Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 21 Passed");
		
		/*
		 * TC22: Click on List (or) Grid whichever is enabled
		 */
		try
		{
			driver.findElement(By.xpath(pro.getProperty("Opencart.Specials.List"))).click();
			System.out.println("TC02-Step22: Clicked on List Successfully");
		}
		catch(Exception e)
		{
			driver.findElement(By.xpath(pro.getProperty("Opencart.Specials.Grid"))).click();
			System.out.println("TC02-Step22: Clicked on Grid Successfully");
		}
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		logger.log(LogStatus.PASS, "TC02 Step 22 Passed");
	}
	
	
	
	/*@Test (dataProvider = "SearchProduct",priority=4)
	public void step04_search(String ProductName) throws AWTException, IOException
	{
		driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
		driver.findElement(By.name(pro.getProperty("OpenCart.Search.Name"))).clear();
		driver.findElement(By.name(pro.getProperty("OpenCart.Search.Name"))).sendKeys(ProductName);
		//driver.findElement(By.name(pro.getProperty("OpenCart.Search.Name"))).sendKeys(Keys.ENTER);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		//Count Number of Items
		String count = driver.findElement(By.className(pro.getProperty("OpenCart.Search.Results.ClassName"))).getText().substring(13,14);
		//System.out.println(count);
		fw1 = new FileWriter("D://Selenium Stuff//Selenium Workspace//Assignment2_Files//tc02_output.txt");
		bw1 = new BufferedWriter(fw1);
		bw1.newLine();
		bw1.append("Number of Search Items with phrase 'apple': "+count);
		System.out.println("TC02-Step04: Searched for the apple Product and added count to file Successfully");
	}*/
	
	
	@DataProvider (name="SearchProduct")
	public Object[][] readRegistrationDetails() throws BiffException, IOException
	{
		File search = new File("D://Selenium Stuff//Selenium Workspace//Assignment2_Files//TestData.xls");
		Workbook wb = Workbook.getWorkbook(search);
		Sheet s1 = wb.getSheet("SearchProd");
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
	
	@Test (dataProvider="SearchProduct", priority=2, description = "Product Comparision")
	public void testcase02(String ProductName) throws AWTException, IOException, InterruptedException
	{
		launchOpencart(driver);
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		login(driver);
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
		productComparision(ProductName);
		logout(driver);
		takeSnapshot(driver,DateFormatScreenShot("TC02_"));
	}
	
	
	@AfterClass
	public void closeBrowser() throws IOException
	{
		bw1.close();
		fw1.close();
		bw3.close();
		fw3.close();
		bw4.close();
		fw4.close();
		br.close();
		fr.close();
		//extent.endTest(logger);
		extent.flush();
		driver.close();
		System.out.println("END OF TESTCASE 02");
	}
}
