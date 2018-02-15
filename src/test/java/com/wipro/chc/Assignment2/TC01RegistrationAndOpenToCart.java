package com.wipro.chc.Assignment2;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import jxl.Sheet;
import jxl.Workbook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import jxl.Cell;
import jxl.read.biff.BiffException;
public class TC01RegistrationAndOpenToCart extends Library
{
	WebDriver driver;
	public static String getFirstName;
	public static String getLastName;
	public static String getEmail;
	public static String getTelephone;
	public static String getFax;
	public static String uniqueEmail;
	public static String getPassword;
	
	private static final int TIME_UNIT = 30;
	Scanner scan = new Scanner(System.in);
	FileWriter fw;
	BufferedWriter bw;
	//ExtentTest logger;
	
	/**
	 * 
	 * @throws InterruptedException - checked exception
	 * @throws IOException - checked exception
	 */
	@BeforeClass
	public void launchBrowser() throws InterruptedException, IOException
	{
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
		loadProperties();
		logger = extent.startTest("Registration to Opencart");
	}
	
	public void registrationAndOpenToCart(String FirstName, String LastName, String EmailID, String Telephone, String Fax,
			String Company, String CompID, String Add1, String Add2, String City, String PostCode,
			String Pwd, String PwdConfirm) throws IOException, InterruptedException 
	{
		driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
		
		/*
		 * TC02: Click on "Create an account" link.
		 */
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.CreateAnAccount.Xpath"))).click();
		System.out.println("TC01-Step02: Registration Page Opened Successfully");
		Assert.assertEquals(pro.getProperty("CreatAnAccount.URL"), driver.getCurrentUrl());
		takeSnapshot(driver,DateFormatScreenShot("TC01_"));
		logger.log(LogStatus.PASS, "TC01 Step 2 Passed");
		
		/*
		 * TC03: Fill in the Details of the page -Name(s), Email, Phone, Company and Address, City, Post Code.
		 * Select values from the list for Country and State/Region.
		 */
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.FirstName.Xpath"))).sendKeys(FirstName);
		getFirstName = driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.FirstName.Xpath"))).getAttribute("value");
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.LastName.Xpath"))).sendKeys(LastName);
		getLastName = driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.LastName.Xpath"))).getAttribute("value");
		uniqueEmail = EmailID+DateFormatEmailID()+"@gmail.com";
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.Email.Xpath"))).sendKeys(uniqueEmail);
		getEmail = driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.Email.Xpath"))).getAttribute("value");
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.Telephone.Xpath"))).sendKeys(Telephone);
		getTelephone = driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.Telephone.Xpath"))).getAttribute("value");
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.Fax.Xpath"))).sendKeys(Fax);
		getFax = driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.Fax.Xpath"))).getAttribute("value");
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.Company.Xpath"))).sendKeys(Company);
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.CompID.Xpath"))).sendKeys(CompID);
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.Add1.Xpath"))).sendKeys(Add1);
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.Add2.Xpath"))).sendKeys(Add2);
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.City.Xpath"))).sendKeys(City);
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.PostCode.Xpath"))).sendKeys(PostCode);
		Select country = new Select(driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.Country.Xpath"))));
		country.selectByVisibleText("India");
		Select state = new Select(driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.State.Xpath"))));
		state.selectByVisibleText("Andhra Pradesh");
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.Pwd.Xpath"))).sendKeys(Pwd);
		getPassword = driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.Pwd.Xpath"))).getAttribute("value");
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.PwdConfirm.Xpath"))).sendKeys(PwdConfirm);
		System.out.println("TC01-Step03: Registration Details Entered Successfully");	
		takeSnapshot(driver,DateFormatScreenShot("TC01_"));
		logger.log(LogStatus.PASS, "TC01 Step 3 Passed");
		
		/*
		 * TC04: Check the "Privacy Policy" and click on "Continue"
		 */
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.PrivacyPolicy.Xpath"))).click();
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.Continue1.Xpath"))).click();
		//Checkpoint2
		String actualText = driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.SuccessMessage1.Xpath"))).getText();
		Assert.assertEquals(actualText, "Your Account Has Been Created!");
		System.out.println("TC01-Step04: Your Account Has Been Created Successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC01_"));
		//Checkpoint1
		driver.findElement(By.linkText(pro.getProperty("OpenCart.Register.EditAccount.LinkText"))).click();
		String firstNameSaved = driver.findElement(By.name(pro.getProperty("OpenCart.Register.EditAccount.FirstName.Name"))).getAttribute("value");
		String lastNameSaved = driver.findElement(By.name(pro.getProperty("OpenCart.Register.EditAccount.LasttName.Name"))).getAttribute("value");
		String emailSaved = driver.findElement(By.name(pro.getProperty("OpenCart.Register.EditAccount.Email.Name"))).getAttribute("value");
		Assert.assertEquals(firstNameSaved, FirstName);
		Assert.assertEquals(lastNameSaved, LastName);
		Assert.assertEquals(emailSaved, uniqueEmail);
		logger.log(LogStatus.PASS, "TC01 Step 4 Passed");
		
		/*
		 * TC05: Click on "Contact" link
		 */
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Register.ContactUs.Xpath"))).click();
		//Checkpoint
		String name = driver.findElement(By.xpath(pro.getProperty("OpenCart.ContactUs.Name.Xpath"))).getAttribute("value");
		Assert.assertEquals(name, getFirstName);
		//Checkpoint
		String email = driver.findElement(By.xpath(pro.getProperty("OpenCart.ContactUs.Email.Xpath"))).getAttribute("value");
		Assert.assertEquals(email, getEmail);
		System.out.println("TC01-Step05: Contact Us Page Loaded and Data Verified Successfully ");
		takeSnapshot(driver,DateFormatScreenShot("TC01_"));
		logger.log(LogStatus.PASS, "TC01 Step 5 Passed");
		
		/*
		 * TC06: Type the Enquiry - 100 characters
		 */
		driver.findElement(By.xpath(pro.getProperty("OpenCart.ContactUs.Enquiry.Xpath"))).sendKeys("This is to Change of Address/Phone number");
		System.out.println("TC01-Step06: Enquiry filled in Contact Us page Successfully ");
		takeSnapshot(driver,DateFormatScreenShot("TC01_"));
		logger.log(LogStatus.PASS, "TC01 Step 6 Passed");
		
		/*
		 * TC07: Type the "Enter Code" in the textbox
		 */
		/*System.out.println("Enter Captcha: ");
		String captcha = scan.nextLine();
		driver.findElement(By.xpath(pro.getProperty("OpenCart.ContactUs.Captcha.Xpath"))).sendKeys(captcha);
		driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);*/
		Thread.sleep(10000);
		System.out.println("TC01-Step07: Entered Captcha Message in Contact Us page Succesfully");
		takeSnapshot(driver,DateFormatScreenShot("TC01_"));
		logger.log(LogStatus.PASS, "TC01 Step 7 Passed");
		
		/*
		 * TC08: Click on "Continue" and Click on "Continue" 
		 */
		driver.findElement(By.xpath(pro.getProperty("OpenCart.ContactUs.Continue1.Xpath"))).click();
		//Checkpoint
		String actualMsg = driver.findElement(By.xpath(pro.getProperty("OpenCart.ContactUs.SuccessMessage1.Xpath"))).getText();
		Assert.assertEquals(actualMsg, pro.getProperty("OpenCart.ContactUs.ExpectedMessage"));
		driver.findElement(By.xpath(pro.getProperty("OpenCart.ContactUs.Continue2.Xpath"))).click();
		System.out.println("TC01-Step08: Submitted Enquiry and Success Message displayed");
		takeSnapshot(driver,DateFormatScreenShot("TC01_"));
		logger.log(LogStatus.PASS, "TC01 Step 8 Passed");
		
		/*
		 * TC09: Click on "Samsung Galaxy Tab" on home page which is shown as the main advertisement - 
		 * this might change - but the user should click on this main image only.
		 */
		driver.findElement(By.xpath(pro.getProperty("OpenCart.HomePage.SamsungImage.Xpath"))).click();
		System.out.println("TC01-Step09: Navigated to Samsung Galaxy Tab page successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC01_"));
		logger.log(LogStatus.PASS, "TC01 Step 9 Passed");
		
		/*
		 * TC10: Click on "Review" tab below
		 */
		driver.findElement(By.xpath(pro.getProperty("OpenCart.SamsungTab.ReviewTab.Xpath"))).click();
		System.out.println("TC01-Step10: Navigated to Samsung Galaxy Review Tab successfully");
		takeSnapshot(driver,DateFormatScreenShot("TC01_"));
		logger.log(LogStatus.PASS, "TC01 Step 10 Passed");
	}
	
	
	public void reviewProducts(String name, String review, String rating) throws InterruptedException, IOException
	{
		driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
		
		/*
		 * TC11 and TC12
		 * Fill in your details -Name and Review. Select the rating as given in Excel. 
		 * Enter the code in the box - Click Continue
		 */
		
		int rate = Integer.parseInt(rating);
		driver.findElement(By.xpath(pro.getProperty("OpenCart.ReviewTab.Name.Xpath"))).clear();
		driver.findElement(By.xpath(pro.getProperty("OpenCart.ReviewTab.Name.Xpath"))).sendKeys(name);
		driver.findElement(By.xpath(pro.getProperty("OpenCart.ReviewTab.Review.Xpath"))).clear();
		driver.findElement(By.xpath(pro.getProperty("OpenCart.ReviewTab.Review.Xpath"))).sendKeys(review);
		int reviewLength = review.length();
		if(rate == 1)
		{
			driver.findElement(By.xpath(pro.getProperty("OpenCart.ReviewTab.Rating1.Xpath"))).click();
			
		}
		else if(rate == 2)
		{
			driver.findElement(By.xpath(pro.getProperty("OpenCart.ReviewTab.Rating2.Xpath"))).click();
		}
		else if(rate == 3)
		{
			driver.findElement(By.xpath(pro.getProperty("OpenCart.ReviewTab.Rating3.Xpath"))).click();
		}
		else if(rate == 4)
		{
			driver.findElement(By.xpath(pro.getProperty("OpenCart.ReviewTab.Rating4.Xpath"))).click();
		}
		else if(rate == 5)
		{
			driver.findElement(By.xpath(pro.getProperty("OpenCart.ReviewTab.Rating5.Xpath"))).click();
		}
		//Captcha
		/*System.out.println("Enter Captcha");
		String captcha1 = scan.nextLine();
		driver.findElement(By.xpath(pro.getProperty("OpenCart.ReviewTab.Captcha.Xpath"))).clear();
		driver.findElement(By.xpath(pro.getProperty("OpenCart.ReviewTab.Captcha.Xpath"))).sendKeys(captcha1);*/
		Thread.sleep(10000);
		driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath(pro.getProperty("OpenCart.ReviewTab.Continue.Xpath"))).click();
		if(reviewLength < 25)
		{
			String actualWarning = driver.findElement(By.xpath(pro.getProperty("OpenCart.ReviewTab.WarningMsg.Xpath"))).getText();
			Assert.assertEquals(actualWarning, pro.getProperty("OpenCart.ReviewTab.ExpectedWarningMsg"));
			System.out.println("TC01-Step11: Warning Message Displayed successfully");
		}
		else
		{
			String successMessage = driver.findElement(By.xpath(pro.getProperty("Opencart.ReviewTab.SuccessMsg.Xpath"))).getText();
			Assert.assertEquals(successMessage, pro.getProperty("OpenCart.ReviewTab.ExpectedSuccessMsg"));
			System.out.println("TC01-Step12: Success Message Displayed Successfully");
		}
		takeSnapshot(driver,DateFormatScreenShot("TC01_"));
		logger.log(LogStatus.PASS, "TC01 Step 11 & 12 Passed");
	}
		public void addToWishlist() throws IOException, InterruptedException
		{
			driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
			
			/*
			 * TC13: Click on "Add to wish list"
			 */
			driver.findElement(By.xpath(pro.getProperty("Opencart.AddToWishList.Xpath"))).click();
			System.out.println("TC01-Step13: Product Added to WishList Successfully");
			takeSnapshot(driver,DateFormatScreenShot("TC01_"));
			logger.log(LogStatus.PASS, "TC01 Step 13 Passed");
			
			/*
			 * TC14: Close the success ribbon message on the page.
			 */
			//WebDriverWait wait = new WebDriverWait(driver,10);
			//WebElement Ribbon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("Opencart.ReviewTab.CloseRibbon.Xpath"))));
			//Ribbon.click();
			//driver.findElement(By.xpath(pro.getProperty("Opencart.ReviewTab.CloseRibbon.Xpath"))).click();
			WebElement ribbon = driver.findElement(By.xpath(pro.getProperty("Opencart.ReviewTab.CloseRibbon.Xpath")));
			Actions actions = new Actions(driver);
			actions.moveToElement(ribbon).click().perform();
			System.out.println("TC01-Step14: Add to WishList Ribbon Closed Successfully");
			takeSnapshot(driver,DateFormatScreenShot("TC01_"));
			logger.log(LogStatus.PASS, "TC01 Step 14 Passed");
			
			/*
			 * TC15: Click on "Wishlist" link.
			 */
			driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
			Thread.sleep(5000);
			WebElement wishlist = driver.findElement(By.xpath(pro.getProperty("Opencart.ReviewTab.WishList.Xpath")));
			Actions action = new Actions(driver);
			action.moveToElement(wishlist).click().perform();
			Thread.sleep(5000);
			System.out.println("TC01-Step15: Navigated to Wishlist page Successfully");
			takeSnapshot(driver,DateFormatScreenShot("TC01_"));
			//Checkpoint
			String wishlistLinkCount = driver.findElement(By.id(pro.getProperty("Opencart.WishListLink.id"))).getText().substring(11, 12);
			List<WebElement> list = driver.findElements(By.xpath(pro.getProperty("Opencart.WishList.ItemsCount.Xpath")));
			String tableCount = Integer.toString(list.size());
			Assert.assertEquals(wishlistLinkCount, tableCount, "WishList Link Count and Number of products in WishList are not matching");
			logger.log(LogStatus.PASS, "TC01 Step 15 Passed");
			
			/*
			 * TC16: Click on "Pound Sterling".
			 */
			driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
			driver.findElement(By.xpath(pro.getProperty("Opencart.WishList.PoundSterling.Xpath"))).click();
			System.out.println("TC01-Step16: Price displayed in Pounds Successfully");
			takeSnapshot(driver,DateFormatScreenShot("TC01_"));
			logger.log(LogStatus.PASS, "TC01 Step 16 Passed");
			
			/*
			 * TC17: Retrieve the value and write into any flat file.
			 */
			driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
			String poundValue = driver.findElement(By.xpath(pro.getProperty("Opencart.WishList.RetrievePrice.Xpath"))).getText();
			fw = new FileWriter("D://TopGear//OpenCart_L2_Vasavi//output_files//tc01_output.txt");
			bw = new BufferedWriter(fw);
			bw.newLine();
			bw.append(poundValue);
			System.out.println("TC01-Step17: Pound Strerling Value retrieved Successfully");
			takeSnapshot(driver,DateFormatScreenShot("TC01_"));
			logger.log(LogStatus.PASS, "TC01 Step 17 Passed");
			
			/*
			 * TC18: Click on "Euro".
			 */
			driver.findElement(By.xpath(pro.getProperty("Opencart.WishList.Euro.Xpath"))).click();
			System.out.println("TC01-Step18: Price displayed in Euros Successfully");
			takeSnapshot(driver,DateFormatScreenShot("TC01_"));
			logger.log(LogStatus.PASS, "TC01 Step 18 Passed");
			
			/*
			 * TC19: Retrieve the value and write into any flat file.
			 */
			driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
			String EuroValue = driver.findElement(By.xpath(pro.getProperty("Opencart.WishList.RetrievePrice.Xpath"))).getText();
			bw.newLine();
			bw.append(EuroValue);
			System.out.println("TC01-Step19: Euro Value retrieved Successfully");
			takeSnapshot(driver,DateFormatScreenShot("TC01_"));
			logger.log(LogStatus.PASS, "TC01 Step 19 Passed");
			
			/*
			 * TC20: Click on "US Dollar".
			 */
			driver.findElement(By.xpath(pro.getProperty("Opencart.WishList.Dollar.Xpath"))).click();
			System.out.println("TC01-Step20: Price displayed in Dollars Successfully");
			takeSnapshot(driver,DateFormatScreenShot("TC01_"));
			logger.log(LogStatus.PASS, "TC01 Step 20 Passed");
			
			/*
			 * TC21: Retrieve the value and write into any flat file.
			 */
			//driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
			String DollarValue = driver.findElement(By.xpath(pro.getProperty("Opencart.WishList.RetrievePrice.Xpath"))).getText();
			bw.newLine();
			bw.append(DollarValue);
			System.out.println("TC01-Step21: Dollar Value retrieved Successfully");
			takeSnapshot(driver,DateFormatScreenShot("TC01_"));
			logger.log(LogStatus.PASS, "TC01 Step 21 Passed");
			
			/*
			 * TC22: Click on "Add To Cart" icon
			 */
			driver.findElement(By.xpath(pro.getProperty("OpenCart.WishList.AddToCart.Xpath"))).click();
			System.out.println("TC01-Step22: Product added to Cart Successfully");
			takeSnapshot(driver,DateFormatScreenShot("TC01_"));
			logger.log(LogStatus.PASS, "TC01 Step 22 Passed");
			
			/*
			 * TC23: Close the success message on the page.
			 */
			driver.findElement(By.xpath(pro.getProperty("Opencart.ReviewTab.CloseRibbon.Xpath"))).click();
			System.out.println("TC01-Step23: Add to Cart Success Ribbon Closed Successfully");
			takeSnapshot(driver,DateFormatScreenShot("TC01_"));
			logger.log(LogStatus.PASS, "TC01 Step 23 Passed");
			
			/*
			 * TC24: Click "Remove" icon on the product in My Wishlist page
			 */
			driver.findElement(By.xpath(pro.getProperty("OpenCart.WishList.RemoveFromWishList.Xpath"))).click();
			System.out.println("TC01-Step24: Product Removed form WishList Successfully");
			takeSnapshot(driver,DateFormatScreenShot("TC01_"));
			logger.log(LogStatus.PASS, "TC01 Step 24 Passed");
			
			/*
			 * TC25: click on continue.
			 */
			driver.findElement(By.xpath(pro.getProperty("OpenCart.RemoveFromWishList.Continue.Xpath"))).click();
			System.out.println("TC01-Step25: Navigated to My Account page Successfully");
			takeSnapshot(driver,DateFormatScreenShot("TC01_"));
			logger.log(LogStatus.PASS, "TC01 Step 25 Passed");
		}
		
	
	
	/** DATA PROVIDER
	 * This is a data provider to read registration details from Excel and store in data provider
	 * @return - this returns the data stored in data provider 
	 * @throws BiffException - checked exception
	 * @throws IOException - checked exception
	 */
	
	@DataProvider (name="RegistrationDetails")
	public Object[][] readRegistrationDetails() throws BiffException, IOException
	{
		File reg = new File("D://TopGear//OpenCart_L2_Vasavi//input_files//TestData.xls");
		Workbook wb = Workbook.getWorkbook(reg);
		Sheet s1 = wb.getSheet("RegistrationDetails");
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
	
	/** DATA PROVIDER
	 * This is the Data Provider for storing Samsung Product reviews
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
	@DataProvider (name="ReviewProductDetails")
	public Object[][] readExcel() throws BiffException, IOException 
	{
		File rev = new File("D://TopGear//OpenCart_L2_Vasavi//input_files//TestData.xls");
		Workbook wb = Workbook.getWorkbook(rev);
		Sheet s1 = wb.getSheet("ReviewProduct");
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
	
	@Test (dataProvider="RegistrationDetails", priority=-1, description = "Registration to Opencart")
	public void testcase01_part01(String FirstName, String LastName, String EmailID, String Telephone, String Fax,
			String Company, String CompID, String Add1, String Add2, String City, String PostCode,
			String Pwd, String PwdConfirm) throws IOException, InterruptedException
	{
		DeleteFiles();
		launchOpencart(driver);
		takeSnapshot(driver,DateFormatScreenShot("TC01_"));
		registrationAndOpenToCart(FirstName, LastName, EmailID, Telephone, Fax,
				Company, CompID, Add1, Add2, City, PostCode,
				Pwd, PwdConfirm);
	}
	
	@Test (dataProvider="ReviewProductDetails", priority=0, description = "Adding Review to the Product")
	public void testcase01_part02(String name, String review, String rating) throws InterruptedException, IOException
	{
		reviewProducts(name, review, rating);
	}
	
	@Test (priority=1)
	public void testcase01_part03() throws IOException, InterruptedException
	{
		addToWishlist();
		logout(driver);
		takeSnapshot(driver,DateFormatScreenShot("TC01_"));
	}
	
	
	/**
	 * This will close the browser
	 * @throws IOException 
	 */
	@AfterClass
	public void closeBrowser() throws IOException
	{
		bw.close();
		fw.close();
		extent.endTest(logger);
		driver.close();
		System.out.println("END OF TESTCASE 01");
		
	}
}
