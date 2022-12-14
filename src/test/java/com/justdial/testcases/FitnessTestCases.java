package com.justdial.testcases;
import com.justdial.homepage.Homepage;


import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class FitnessTestCases extends Homepage {
	public ExtentReports report;
    
	//Method for Browser calling and Report generation
	@BeforeClass
	public void browser() {
		loadproperties();
		setup(prop.getProperty("browser"), prop.getProperty("baseUrl"));
		ExtentHtmlReporter Reporter1 = new ExtentHtmlReporter(".\\HTMLReports\\FitnessPageExtentReport.html");
		report = new ExtentReports();
		report.attachReporter(Reporter1);
	}
    
	//This Method is used to click Fitness Option in Menu bar
	@Test(priority = 1)
	public void fitness() {
		ExtentTest test1 = report.createTest("Fitness Page Validation","Test for Validating FitnessPage");
		driver.findElement(By.xpath("//*[@id='city']")).clear();
		driver.findElement(By.xpath("//*[@id='city']")).sendKeys(prop.getProperty("location"));
		test1.info("The City Is Selected In The HomePage");
		visibilitywaitwithclick("//ul[@id='cuto']/li", 100);
		test1.info("The Fitness Menu Is Clicked");
		visibilitywaitwithclick("//ul[@class='hotkeys-list ']//li/a[contains(@title,'Fitness')]", 100);
		Assert.assertEquals(driver.getTitle(), "Fitness in " + prop.getProperty("location") + 
				                                                                 ", India - Justdial");
		test1.pass("The Fitness Page Is Validated");
	}
    
	//This Method is used to print Sub-Menu Options in Fitness Page
	@Test(priority=2)
	public void fitnessMenu() {
		ExtentTest test2 = report.createTest("Fitness Menu","Test To Check The Sub-menu options in Fitness Page");
		List<WebElement> menu = driver.findElements(By.xpath("//*[@id=\"mnintrnlbnr\"]/ul"));
		System.out.println("The Displayed Sub-Menu Items in Fitness are: ");
		for (WebElement menus : menu) {
			System.out.println(menus.getText());
		}
		System.out.println("----------------------------------------");
		test2.pass("Sub-menu options In The Fitness Page Are Printed");
		
	}
	
	//This Method is used to click Gym Option
	@Test(priority = 3)
	public void gym() {
		ExtentTest test3 = report.createTest("Gym Option", "Test To Check The GYM option In The Fitness Page");
		visibilitywaitwithclick("//ul[contains(@class,'listview')]//li/a/span[@title='Gym']", 100);
		test3.info("GYM Option is clicked");
		Assert.assertTrue(driver.getCurrentUrl().contains("Gym"));
		test3.pass("The Gym Option Is Selected");
	}

	//This Method is used to Print all Sub-Menu options in Gym Page
	@Test(priority = 4)
	public void gymMenu() {
		ExtentTest test4 = report.createTest("Gym Menu Item's", "Test To Print Sub-menu's In The Gym Page");
		List<WebElement> menu = driver.findElements(By.className("mm-listview"));
		System.out.println("The Displayed Sub-Menu Items in Gym are: ");
		for (WebElement menus : menu) {
			System.out.println(menus.getText());
		}
		test4.pass("Sub-menu options In The Gym Page Are Printed");
	}
    
	//Method to close Browser
	@AfterClass
	public void terminate() {
		driver.close();
		report.flush();
	}

}

