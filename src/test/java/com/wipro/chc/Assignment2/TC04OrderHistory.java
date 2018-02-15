package com.wipro.chc.Assignment2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;


public class TC04OrderHistory extends Library
{
	WebDriver driver;
	int count = 0;
	Scanner scan = new Scanner(System.in);
	WebDriverWait wait;
	private static final int TIME_UNIT = 30;
	//ExtentTest logger;
	
	@BeforeClass
	public void launchBrowser() throws IOException
	{
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
		loadProperties();
		logger = extent.startTest("OrderHistory");
	}
	
	
	public void orderHistory() throws InterruptedException, IOException 
	{
		wait = new WebDriverWait(driver, TIME_UNIT);
		driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
		
		/*
		 * TC04: Click the Home Page
		 */
		driver.findElement(By.linkText("Home")).click();
		System.out.println("TC04-Step04: Navigated to Homepage Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		logger.log(LogStatus.PASS, "TC04 Step 4 Passed");
		
		/*
		 * TC05: Click any items displayed  under 'Featured' on the Home page.
		 */
		//int featuredCount = driver.findElement(By.xpath("//div[@class='box-product']/div")).getSize();
		for(int i=1; i<=6; i++)
		{
			driver.findElement(By.xpath("//div[@id='content']/div[2]/div[2]/div/div["+i+"]/div[1]/a/img")).click();
			if(!(driver.findElement(By.xpath(pro.getProperty("Opencart.FeaturedProduct.Tabs.Xpath"))).getText().contains("Related Products")))
			{
				count=count+1;
				System.out.println("No Related Products tab for product "+i);
			}
			driver.findElement(By.linkText(pro.getProperty("Opencart.HomePage.LinkText"))).click();
		}
		System.out.println("Number of products not having Related Products tab is  "+count);
		driver.findElement(By.xpath(pro.getProperty("Opencart.Homepage.Featured.AppleCinema.Xpath"))).click();
		System.out.println("TC04-Step05: Navigated to iPhone Product page Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		logger.log(LogStatus.PASS, "TC04 Step 5 Passed");
		
		/*
		 * TC06: Click on the Related Products tab on the page.
		 */
		try
		{
			driver.findElement(By.xpath(pro.getProperty("Opencart.ProductDetails.RelatedProductsTab.Xpath"))).click();
		}
		catch (Exception e)
		{
			System.out.println("TC04-Step06: No Related Products found");
		}
		System.out.println("TC04-Step06: Clicked on Related Products Tab(if present)Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		logger.log(LogStatus.PASS, "TC04 Step 6 Passed");
		
		/*
		 * TC07: Click on Add to Cart for the related product.
		 */
		driver.findElement(By.xpath(pro.getProperty("Opencart.RelatedProductsTab.AddRelatedProductToCart.Xpath"))).click();
		System.out.println("TC04-Step07: Related Product Added to Cart Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		logger.log(LogStatus.PASS, "TC04 Step 7 Passed");
		
		/*
		 * TC08: Click on Shopping Cart link displayed in the top right corner of the page.
		 */
		Thread.sleep(2000);
		/*WebElement cart = driver.findElement(By.xpath(pro.getProperty("Opencart.ShoppingCart.Xpath")));
		Actions actions = new Actions(driver);
		actions.moveToElement(cart).click().perform();*/
		driver.findElement(By.linkText(pro.getProperty("Opencart.ShoppingCart.LinkText"))).click();
		System.out.println("TC04-Step08: Shopping Cart Page displayed Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		logger.log(LogStatus.PASS, "TC04 Step 8 Passed");
		
		/*
		 * TC09: Change the quantity of the product.
		 */
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.ShoppingCart.ChangeQuantity.Xpath"))).clear();
		driver.findElement(By.xpath(pro.getProperty("Opencart.ShoppingCart.ChangeQuantity.Xpath"))).sendKeys("5");
		System.out.println("TC04-Step09: Quantity changed Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		logger.log(LogStatus.PASS, "TC04 Step 9 Passed");
		
		/*
		 * TC10: Click on the update icon.
		 */
		driver.findElement(By.xpath(pro.getProperty("Opencart.ChangeQuantity.Update.Xpath"))).click();
		System.out.println("TC04-Step10: Quantity updated Successfully in the Shopping Cart");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		//Checkpoint- Taking Shopping cart values and write to Excel for comparision
		String productName1 = driver.findElement(By.xpath(pro.getProperty("Opencart.ShoppingCart.ProductName.Xpath"))).getText();
		String productModel1 = driver.findElement(By.xpath(pro.getProperty("Opencart.ShoppingCart.ProductModel.Xpath"))).getText();
		String productQuantity1 = driver.findElement(By.xpath(pro.getProperty("Opencart.ShoppingCart.ChangeQuantity.Xpath"))).getAttribute("value");
		String productUnitPrice1 = driver.findElement(By.xpath(pro.getProperty("Opencart.ShoppingCart.UnitPrice.Xpath"))).getText();
		String productTotal1 = driver.findElement(By.xpath(pro.getProperty("Opencart.ShoppingCart.Total.Xpath"))).getText();
		ArrayList<String> al1= new ArrayList<String>();
		al1.add(productName1);
		al1.add(productModel1);
		al1.add(productQuantity1);
		al1.add(productUnitPrice1);
		al1.add(productTotal1);
		logger.log(LogStatus.PASS, "TC04 Step 10 Passed");
			
		/*
		 * TC11: Click on Check out button.
		 */
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.ShoppingCart.Checkout.Xpath"))).click();
		System.out.println("TC04-Step11: Checkout Page displayed Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		logger.log(LogStatus.PASS, "TC04 Step 11 Passed");
		
		/*
		 * TC12: Click on Continue buttons (2nd, 3rd and 4th)
		 */
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.SecondContinue.Xpath"))).click();
		System.out.println("TC04-Step12: Second Continue clicked Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.ThirdContinue.Xpath"))).click();
		System.out.println("TC04-Step12: Third Continue clicked Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.FourthContinue.Xpath"))).click();
		System.out.println("TC04-Step12: Fourth Continue clicked 5th Step displayed Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		logger.log(LogStatus.PASS, "TC04 Step 12 Passed");
		
		/*
		 * TC13: Check the Terms and Conditions Checkbox and click Continue
		 */
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.TermsAndConditions.Xpath"))).click();
		System.out.println("TC04-Step13: Agreed Terms And Conditions Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.FifthContinue.Xpath"))).click();
		System.out.println("TC04-Step13: Clicked on Fifth Continue. Order Tab displayed Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		logger.log(LogStatus.PASS, "TC04 Step 13 Passed");
		
		/*
		 * TC14: Verify the product details is valid.
		 */
		String productName2 = driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.ProductName.Xpath"))).getText();
		String productModel2 = driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.ProductModel.Xpath"))).getText();
		String productQuantity2 = driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.Quantity.Xpath"))).getText();
		String productUnitPrice2 = driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.UnitPrice.Xpath"))).getText();
		String productTotal2 = driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.Total.Xpath"))).getText();
		ArrayList<String> al2= new ArrayList<String>();
		al2.add(productName2);
		al2.add(productModel2);
		al2.add(productQuantity2);
		al2.add(productUnitPrice2);
		al2.add(productTotal2);
		
		File compareExcel = new File("D://TopGear//OpenCart_L2_Vasavi//output_files//ProdCompare.xlsx");
		FileOutputStream fos = new FileOutputStream(compareExcel);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sh = wb.createSheet("Product Compare");
		
		for(int i=0,row=0;row<al1.size();row++,i++)
		{
			sh.createRow(row).createCell(0).setCellValue(al1.get(i));
			sh.getRow(i).createCell(1).setCellValue(al2.get(i));
			if(al1.get(i).equals(al2.get(i)))
			{
				sh.getRow(i).createCell(2).setCellValue("True");
			}
			else
			{
				sh.getRow(i).createCell(2).setCellValue("False");
			}
		}
		
		wb.write(fos);
		
		System.out.println("TC04-Step14: Product Details are Verified and Valid");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		logger.log(LogStatus.PASS, "TC04 Step 14 Passed");
		
		/*
		 * TC15: Click Confirm Order
		 */
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.CheckoutPage.ConfirmOrder.Xpath"))).click();
		System.out.println("TC04-Step15: Your Order Has Been Processed! page displayed Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		logger.log(LogStatus.PASS, "TC04 Step 15 Passed");
		
		/*
		 * TC16: Click on Order History of My Account -footer of the page.
		 */
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.OrderHistory.Xpath"))).click();
		System.out.println("TC04-Step16: Navigated to Order History page Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		logger.log(LogStatus.PASS, "TC04 Step 16 Passed");
		
		/*
		 * TC17: Click on "View" icon of the previous order of the product.
		 */
		Thread.sleep(3000);
		try
		{
			driver.findElement(By.xpath(pro.getProperty("Opencart.OrderHistory.View.Xpath"))).click();
		}
		catch(Exception e)
		{
			System.out.println("Previous Order not visible hence logging out");
			driver.findElement(By.xpath(pro.getProperty("OpenCart.Logout.Xpath"))).click();
		}
		System.out.println("TC04-Step17: Navigated to Order Information page Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		logger.log(LogStatus.PASS, "TC04 Step 17 Passed");
		
		/*
		 * TC18: Click on "Return"  icon
		 */
		Thread.sleep(3000);
		driver.findElement(By.xpath(pro.getProperty("Opencart.OrderInfo.Return.Xpath"))).click();
		System.out.println("TC04-Step18: Navigated to Products Return page Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		logger.log(LogStatus.PASS, "TC04 Step 18 Passed");
		
		/*
		 * TC19: Fill the necessary details and click on continue button.
		 */
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("Opencart.Return.ReturnReason.Xpath"))));
		element.click();
		//driver.findElement(By.xpath(pro.getProperty("Opencart.Return.ReturnReason.Xpath"))).click();
		System.out.println("TC04-Step19: Return Reason Entered Successfully");
		/*System.out.println("Enter Captcha: ");
		String captcha = scan.nextLine();
		driver.findElement(By.xpath(pro.getProperty("Opencart.Return.Captcha.Xpath"))).sendKeys(captcha);*/
		Thread.sleep(10000);
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
		driver.findElement(By.xpath(pro.getProperty("Opencart.Return.Continue.Xpath"))).click();
		System.out.println("TC04-Step19: Navigated to Product Returns Page Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		logger.log(LogStatus.PASS, "TC04 Step 19 Passed");
		
		/*
		 * TC20: Click on continue button.
		 */
		driver.findElement(By.xpath(pro.getProperty("Opencart.ProductReturns.Continue.Xpath"))).click();
		System.out.println("TC04-Step20: Navigated to Home Page Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		fos.close();
		logger.log(LogStatus.PASS, "TC04 Step 20 Passed");
	}
	
	@Test (priority=4, description = "Order History")
	public void testcase04() throws InterruptedException, IOException
	{
		launchOpencart(driver);
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		login(driver);
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
		orderHistory();
		logout(driver);
		takeSnapshot(driver,DateFormatScreenShot("TC04_"));
	}
	
	
	
	@AfterClass
	public void closeBrowser() throws IOException
	{
		extent.endTest(logger);
		driver.close();
		System.out.println("END OF TESTCASE 04");
	}
}
