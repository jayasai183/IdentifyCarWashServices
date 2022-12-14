package com.justdial.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.justdial.homepage.Freelisting;
import com.justdial.homepage.Homepage;



public class FreelistingTestCases extends Homepage {

	public ExtentReports report;
	public Freelisting obj;
    
	//Browser calling and Report Generation
	@BeforeClass
	public void browser() {
		loadproperties();
		setup(prop.getProperty("browser"), prop.getProperty("baseUrl"));
		ExtentHtmlReporter Reporter1 = new ExtentHtmlReporter(".\\HTMLReports\\"
				                                                      + "FreeListingPageExtentReport.html");
		report = new ExtentReports();
		report.attachReporter(Reporter1);
		obj = new Freelisting();
	}

	//Method for Launching FreeListing Page
	@Test(priority = 1)
	public void launch(){
		ExtentTest test1 = report.createTest("Free Listing Page Launching",
				                                                      "Testing title of Free Listing Page");
		driver.findElement(By.xpath("//*[@id='city']")).clear();
		driver.findElement(By.xpath("//*[@id='city']")).sendKeys(prop.getProperty("location"));
		Thread(3);
		visibilitywaitwithclick("//ul[@id='cuto']/li", 100);
		visibilitywaitwithclick("//li[@class='button-top']", 100);
		test1.info("Free Listing is opening");
		Thread(3);
		Assert.assertEquals(driver.getTitle(), "Free Listing - Just Dial - List In Your Business For Free");
		test1.pass("Test case is Passed");
	}
	
	//This Method Enter all fields as Null in FreeListing Page
    @Test(priority=2)
    public void allNullValues() {
    	obj.elements();
    	ExtentTest test2 = report.createTest("All NullValues","Testing Freelisting Page with all Null values");
    	obj.cityname.clear();
    	obj.submit.click();
    	test2.info("All fields are null");
    	Thread(2);
    	String alert = driver.findElement(By.xpath("//span[@class='almsg']")).getText();
		Assert.assertEquals("City is blank", alert);
		test2.pass("Test case Is Passed");
		driver.navigate().refresh();
    }

    //This Method Keeps Company field as Null 
	@Test(priority = 3, dataProvider = "testData")
	public void companyNullValue(String company, String fname, String lname, String mobile, String land){
		obj.elements();
		ExtentTest test3 = report.createTest("Company NullValue",
				                          "Testing Company Null Value Validation Of The Freelisting Page");
		obj.firstname.sendKeys(fname);
		obj.lastname.sendKeys(lname);
		obj.mobilenumber.sendKeys(mobile);
		obj.landline.sendKeys(land);
		obj.submit.click();
		test3.info("The Form values Are Entered With Company Value As Null");
		String alert = driver.findElement(By.xpath("//span[@class='almsg']")).getText();
		test3.info("The Error Message Is Shown In The Window");
		Assert.assertEquals("Company name is blank", alert);
		test3.pass("Test case Is Passed");
		driver.navigate().refresh();
	}

	//This Method keeps Mobile field as Null
	@Test(priority = 4, dataProvider = "testData")
	public void mobileNullValue(String company, String fname, String lname, String mobile, String land){
		obj.elements();
		ExtentTest test4 = report.createTest("MobileNumber Nullvalue",
				                       "Testing MobileNumber Null Value Validation Of The FreelistingPage");
		obj.companyname.sendKeys(company);
		obj.firstname.sendKeys(fname);
		obj.lastname.sendKeys(lname);
		test4.info("The Form values Are Entered With MobileNumber As Null");
		obj.submit.click();
		Thread(3);
		test4.info("The Alert Is Displayed");
		String text = driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();
		Assert.assertEquals("Please enter mobile number or landline number", text);
		test4.pass("Test case Is Passed");
		driver.navigate().refresh();

	}

	//This method keeps City field as Null
	@Test(priority = 5, dataProvider = "testData")
	public void cityNullvalue(String company, String fname, String lname, String mobile, String land){
		obj.elements();
		ExtentTest test5 = report.createTest("City Nullvalue",
				                               "Testing City Null Value Validation Of The FreelistingPage");
		obj.companyname.sendKeys(company);
		obj.cityname.clear();
		obj.firstname.sendKeys(fname);
		obj.lastname.sendKeys(lname);
		test5.info("The Form values Are Entered With City As Null");
		obj.submit.click();
		Thread(3);
		test5.info("The Error Message Is Shown In The Window");
		String alert = driver.findElement(By.xpath("//span[@class='almsg']")).getText();
		Assert.assertEquals("City is blank", alert);
		test5.pass("Test case Is Passed");
		driver.navigate().refresh();
	}
	
	//This method Enter all fields with valid data
	@Test(priority = 6, dataProvider = "testData")
	public void validDataTest(String company, String fname, String lname, String mobile, String land) {
		obj.elements();
		ExtentTest test6 = report.createTest("Valid Data",
				                                       "Validation Of The FreelistingPage with valid data");
		obj.companyname.sendKeys(company);
		obj.firstname.sendKeys(fname);
		obj.lastname.sendKeys(lname);
		obj.mobilenumber.sendKeys(mobile);
		obj.landline.sendKeys(land);
		obj.submit.click();	
		test6.info("The Form values Are Entered With all valid details");
		String actual=driver.getTitle();
		String expected="Free Listing - Just Dial - List In Your Business For Free";
		Assert.assertEquals(actual, expected);
		test6.pass("Successfully registered and case is passed");
		
	}
	
	//Data Provider to all the used methods
	@DataProvider
	public Object[][] testData() throws Exception {
		return data("Testdata.xlsx");
	}

	//Method for closing Browser
	@AfterClass
	public void terminate() {
		driver.close();
		report.flush();
	}
}
