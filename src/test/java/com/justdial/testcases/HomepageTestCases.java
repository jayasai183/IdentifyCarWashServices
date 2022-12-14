package com.justdial.testcases;

import java.util.ArrayList;
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
import com.justdial.homepage.Homepage;

public class HomepageTestCases extends Homepage{
        public ExtentReports report;
        
        //Browser Calling and Report Generation
        @BeforeClass
        public void Browser() {
        	loadproperties();
        	setup(prop.getProperty("browser"), prop.getProperty("baseUrl"));
    		ExtentHtmlReporter Reporter1=new ExtentHtmlReporter(".\\HTMLReports\\HomepageExtentReport.html");
    		report = new ExtentReports();
    		report.attachReporter(Reporter1);
        }
        
        //Method to check whether WebSite is Opening or not
        @Test(priority=1)
        public void websiteTest() {
        	ExtentTest test=report.createTest("Website validation", "Test for checking website Functioning");
        	String actual=driver.getTitle();
        	String expected="Justdial - Local Search, Social, News, Videos, Shopping";
        	Assert.assertEquals(actual, expected);
        	test.pass("Website is opened");
        }
        
        //Method to check The Presence of Elements in justdial.com
        @Test(priority=2)
        public void elementsTest() {
        	ExtentTest test=report.createTest("Validating Elements in homepage", 
        			           "Test for checking City,Search and Search box elements");
        	Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"city\"]")).isDisplayed());
        	test.info("City box is Present");
        	Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"srchbx\"]")).isDisplayed());
        	test.info("Search box is Present");
        	Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"srIconwpr\"]")).isDisplayed());
        	test.info("Search button is Present");
        	test.pass("All required elements are present and case is passed");
        }
        
        //This Method is used to Check Login Pop-up
        @Test(priority=3)
        public void loginPageTest() {
        	ExtentTest test=report.createTest("Login page validation", "Test for checking login popup");
        	driver.findElement(By.id("h_login")).click();
        	test.info("login is clicked");
        	Assert.assertTrue(driver.findElement(By.xpath("//div[@class='modal-header']")).isDisplayed());
        	driver.navigate().refresh();
        	test.pass("login popup is displayed and case is passed");
        }
        
        //This Method is used To check Location
        @Test(priority=4)
        public void locationTest() {
        	ExtentTest test=report.createTest("Validating Location", 
        		                   	"Test for checking whether location can be changeable or not");
        	driver.findElement(By.xpath("//*[@id=\"city\"]")).clear();
    		test.info("City box is clicked");
    		driver.findElement(By.xpath("//*[@id=\"city\"]")).sendKeys(prop.getProperty("location"));
    		Thread(2);
    		driver.findElement(By.xpath("//ul[@id='cuto']/li")).click();
    		test.info(prop.getProperty("location")+" Location is entered");
    		String expected=prop.getProperty("location");
    		driver.navigate().refresh();
    		String actual=driver.findElement(By.xpath("//*[@id='city']")).getAttribute("placeholder");
    		Assert.assertEquals(actual,expected);
    		test.pass("Location is as expected and case is passed");
        }
        
        //This method is used to Enter all search details
        @Test(priority=5,dependsOnMethods="locationTest")
        public void searchingTest() {
        	ExtentTest test=report.createTest("Entering Search details",
        			                      "Test for entering details related to search");
        	driver.findElement(By.xpath("//*[@id=\"srchbx\"]")).sendKeys(prop.getProperty("service"));
        	test.info(prop.getProperty("service")+" is entered in searchbox");
        	driver.findElement(By.xpath("//*[@id='srIconwpr']")).click();
        	test.info("search button is clicked");
        	Assert.assertTrue(true);
        	test.pass("All entered details are taken and case is passed");
        }
        
        //This method is used to check Result page
        @Test(priority=6,dependsOnMethods="searchingTest")
        public void resultPageTest() {
    		ExtentTest test= report.createTest("Result Page Validation",
    				             "Test To Validate The Title of SearchResultPage");
    		String actual = driver.findElement(By.tagName("h1")).getText();
    		String expected = prop.getProperty("service") + " in " + prop.getProperty("location");
    		Assert.assertEquals(actual, expected);
    		test.pass("Result page is as expected and case is passed");
    	}
        
        //This Method is used to check Best deals Pop-up
        @Test(priority=7,dependsOnMethods="resultPageTest")
        public void bestdealsTest() {
        	ExtentTest test=report.createTest("validating BestDeals Popup", 
        			                               "Test to check Bestdeals in ResultPage");
        	driver.findElement(By.xpath("//ul[@class='brdUL']//li[7]")).click();
        	test.info("Bestdeals button is clicked");
        	Thread(2);
    	    String text=driver.findElement(By.xpath("//*[@id='best_deal_div']//section/"
    	     		                                            + "span[@class='jbt']")).getText();
    		Assert.assertTrue(text.contains(prop.getProperty("service")));
    		test.info("Best Deals Popup Is Displayed");
    		Thread(1);
    		driver.findElement(By.xpath("//*[@id=\"best_deal_div\"]/section/span")).click();
    		test.pass("Best Deals Filter Test Case Is Passed");

        }
        
        //This Method is used to check Votes and Rating
        @Test(priority=8,dependsOnMethods="bestdealsTest")
        public void ratingCheck() {
        	ExtentTest test=report.createTest("Validating Stars and Votes", 
        			                              "Test for checking the presence of stars and votes");
        	Boolean rating=driver.findElement(By.xpath("//ul[contains(@class,'padding')]//child::li//"
        			     + "p[contains(@class,'new')]//span[contains(@class,'green')]")).isDisplayed();
            Boolean votes=driver.findElement(By.xpath("//ul[contains(@class,'padding')]//child::li//"
            		      + "p[contains(@class,'new')]//span[contains(@class,'vote')]")).isDisplayed();
            Assert.assertTrue(rating);
            test.info("Ratings are present");
            Assert.assertTrue(votes);
            test.info("Votes are present");
            test.pass("Stars,Votes are validated and Case is passed");
        }
        
        //This method is used to print data 
        @Test(priority=9,dependsOnMethods="ratingCheck")
        public void printData() {
        	ExtentTest test=report.createTest("Validating Print data",
        			                                            "Test to display top 5 Car Wash Services");
        	driver.findElement(By.xpath("//*[@id=\"distdrop_rat\"]")).click();
        	ArrayList<String> phone=new ArrayList<String>();
    	    List<WebElement> shop = driver.findElements(By.xpath("//ul[contains(@class,'padding')]//"
    	  		                                                                        + "child::li//h2"));
    	    List<WebElement> contact=driver.findElements(By.xpath("//*[contains(@class,'store-details')]"));
    		List<WebElement> star =driver.findElements(By.xpath("//ul[contains(@class,'padding')]//child::"
    				                    + "li//p[contains(@class,'new')]//span[contains(@class,'green')]"));
    		List<WebElement> votes =driver.findElements(By.xpath("//ul[contains(@class,'padding')]//child::"
    				                    + "li//p[contains(@class,'new')]//span[contains(@class,'vote')]"));
    		
    		int totalpassed=0;
    		for (int i = 0; i < star.size(); i++) {
    			List<WebElement> num=contact.get(i).findElements(By.xpath("//*[@id="
                                            +"'"+"bcard"+i+"'"+"]/div[1]/section/div[1]/p[2]/span/a/span"));
                ArrayList<String> numlist = new ArrayList<String>();

                for(int j=0;j<num.size();j++) {
                String s=num.get(j).getAttribute("class").split("-")[1];
                String digits=digit(s);
                numlist.add(digits);
                }

                phone.add(String.join("",numlist));
    
    			float ratings = Float.parseFloat(star.get(i).getText());
    			String numeric[] = votes.get(i).getText().split(" ");
    			int vote = Integer.parseInt(numeric[0]);
    			if(totalpassed<5) {
    			if (ratings > 4 && vote > 20) {
    				System.out.println(i + 1 + ". " + shop.get(i).getText() + "|| " + ratings + "|| "
    						+ votes.get(i).getText());
    				System.out.println("   Contact Number : "+phone.get(i));
    				totalpassed++;
    			} 
    		}
        }
    		System.out.println("--------------------------------------------------------------");
    		test.pass("Top 5 Car Wash Services are displayed");
    		
        }
        
       //This Method is used to check result page from Menu-bar
       @Test(priority=10)
       public void dataFromMenubar() {
       	ExtentTest test=report.createTest("Validating data from menu bar", 
       			                                      "Test to check result Page from menubar");
       	driver.findElement(By.xpath("//*[contains(@class,'jdtext')]")).click();
       	driver.findElement(By.xpath("//*[@id=\"city\"]")).clear();
   		test.info("City box is clicked");
   		driver.findElement(By.xpath("//*[@id=\"city\"]")).sendKeys(prop.getProperty("location"));
   		Thread(2);
   		test.info("Location is selected");
   		driver.findElement(By.xpath("//ul[@id='cuto']/li")).click();
   		driver.findElement(By.xpath("//*[@id=\"hotkeys_text_8\"]")).click();
   		test.info("Automobile is selected in menubar");
   		Thread(2);
   		driver.findElement(By.xpath("//*[@id=\"mnintrnlbnr\"]/ul/li[9]/a")).click();
   		test.info("Car Services is selected");
   		String title="Car Repair & Services in "+prop.getProperty("location");
   		Assert.assertTrue(driver.getTitle().contains(title));
   		test.pass("Result Page is as expected and case is passed");
       }
       
       //Method for closing Browser
        @AfterClass
    	public void terminate() {
    		driver.close();
    		report.flush();
    	}

       
}
