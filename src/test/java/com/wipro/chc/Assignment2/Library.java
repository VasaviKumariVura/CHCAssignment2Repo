package com.wipro.chc.Assignment2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class Library 
{
	public static WebDriver driver;
	static Properties pro;
	static WebDriverWait wait;
	//private static final int TIME_UNIT = 30;
	static ExtentReports extent;
	static ExtentTest logger;
	public static String Node = "http://10.255.54.24:4444/wd/hub";
	
	/**
	 * This Method is to load the ObjectRepo properties file into class
	 * @throws IOException - Checked Exception
	 */
	public void loadProperties() throws IOException
	{
		File src = new File("D://Selenium Stuff//Selenium Workspace//OpenCartL2_28112017//ObjectRepo.properties");
		FileInputStream fis = new FileInputStream(src);
		pro = new Properties();
		pro.load(fis);
	}
	
	/**
	 * This Method will launch the Opencart Application
	 * @param driver
	 * @return 
	 * @throws MalformedURLException 
	 */
	public static WebDriver launchOpencart(WebDriver driver) throws MalformedURLException
	{
		
		/*DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setBrowserName("chrome");
        capability.setPlatform(Platform.WIN10);
        driver = new RemoteWebDriver(new URL(Node), capability);*/
 		
		driver.get(pro.getProperty("OpenCart.URL"));
		System.out.println("Step01: Opencart Application Launched Successfully");
		logger.log(LogStatus.PASS, "Step 1 : Opencart Application Launched..");
		Assert.assertEquals(driver.getTitle(), "Your Store");
		driver.manage().window().maximize();
		return driver;
	}
	
	/**
	 * This Method will login into the Opencart Application
	 * @param driver
	 * @return
	 */
	public static WebDriver login(WebDriver driver) 
	{
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Loginlink.Xpath"))).click();
		System.out.println("Step02: Login Page displayed Successfully");
		logger.log(LogStatus.PASS, "Step 2 : Entered Login Page of Application..");
		//driver.findElement(By.xpath(pro.getProperty("Opencart.UserName.Xpath"))).sendKeys(TC01RegistrationAndOpenToCart.uniqueEmail);
		//driver.findElement(By.xpath(pro.getProperty("Opencart.Password.Xpath"))).sendKeys(TC01RegistrationAndOpenToCart.getPassword);
		driver.findElement(By.xpath(pro.getProperty("Opencart.UserName.Xpath"))).sendKeys("opencartdemo1@gmail.com");
		driver.findElement(By.xpath(pro.getProperty("Opencart.Password.Xpath"))).sendKeys("demo1@123");
		driver.findElement(By.xpath(pro.getProperty("Opencart.LoginButton.Xpath"))).click();
		//checkpoint
		Assert.assertTrue(driver.findElement(By.xpath(pro.getProperty("Opencart.UsernameLink.Xpath"))).getText().contains("demo1"), "Login Failed");
		System.out.println("Step03: Logged into Opencart Successfully");
		logger.log(LogStatus.PASS, "Step 3 : Logged into Application..");
		return driver;
	}
	
	public static WebDriver logout(WebDriver driver) throws InterruptedException
	{
		//wait = new WebDriverWait(driver, TIME_UNIT);
		//WebElement logout = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("OpenCart.Logout.Xpath"))));
		//logout.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(pro.getProperty("OpenCart.Logout.Xpath"))).click();
		System.out.println("Logged Out Successfully");
		//Checkpoint-1
		String logoutMSG = driver.findElement(By.xpath(pro.getProperty("OpenCart.Logout.Message.Xpath"))).getText();
		Assert.assertEquals(logoutMSG, pro.getProperty("OpenCart.Logout.ExpectedMessage"));
		System.out.println("Checkpoint 1 Passed");
		//Checkpoint-2
		String loginLink = driver.findElement(By.xpath(pro.getProperty("OpenCart.Loginlink.Xpath"))).getText();
		Assert.assertEquals(loginLink, pro.getProperty("OpenCart.ExpectedLoginLink"));
		System.out.println("Checkpoint 2 Passed");
		return driver;
	}
	
	public void DeleteFiles() throws IOException 
	 {
	    System.out.println("Called deleteFiles");
	    File file = new File("D://TopGear//OpenCart_L2_Vasavi//output_files//Screenshots");
	    String[] myFiles;
	    if (file.isDirectory()) 
		{
	        myFiles = file.list();
	        for (int i = 0; i < myFiles.length; i++) 
			{
	            File myFile = new File(file, myFiles[i]);
	            myFile.delete();
	        }
	    }
	 }
	
	public static String DateFormatEmailID()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMHHmmss");
		Date Cdate = new Date();
		String CurrentDate = sdf.format(Cdate).toString();
		return CurrentDate;
	} 
	
	public static String DateFormatScreenShot(String tcName)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMHHmmssms");
		Date Cdate = new Date();
		String CurrentDate = sdf.format(Cdate).toString();
		String tc01ScreenshotPath = "D://Selenium Stuff//Selenium Workspace//Assignment2_Files//Screenshots//"+tcName+CurrentDate+".png";
		return tc01ScreenshotPath;
	} 
	
	public static void takeSnapshot(WebDriver driver, String filePath) throws IOException
	{
		try
		{
			TakesScreenshot scrShot =((TakesScreenshot)driver);
			File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
			File DestFile=new File(filePath);
			FileUtils.copyFile(SrcFile, DestFile);
		}
		catch (IOException e)
		{
			System.out.println("File Not fount to take the Screenshot");
		}
		catch (WebDriverException e)
		{
			System.out.println("Failed to take Screenshot");
		}
	}
	
	@BeforeSuite
	public void startReport()
	{
		extent = new ExtentReports(System.getProperty("user.dir") +"/test-output/ExtentReport.html", true);
		extent
        .addSystemInfo("Host Name", "Wipro - CHC")
        .addSystemInfo("Environment", "Automation Testing")
        .addSystemInfo("User Name", "Vasavi Kumari Vura");
		//extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
		
	}
	
	@AfterMethod
	public void getResult(ITestResult result){
		if(result.getStatus() == ITestResult.FAILURE){
			logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());
		}else if(result.getStatus() == ITestResult.SKIP){
			logger.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());
		}
	}
	
	@AfterSuite
	public void endReport()
	{
		//extent.endTest(logger);
		extent.flush();
		extent.close();
	}
}

