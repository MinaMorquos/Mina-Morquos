package testingGoogleSearch;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class TestSearching {
	@BeforeClass
	public void setup() {
		TestBase.initialize();
		TestBase.actionInstance();
	}
	
	
  @Test(priority=1 )
  public void verifyGoogleTitle() {
	  String title = TestBase.driver.getTitle();
	  //Checking the Search Engine Title 
	  Assert.assertEquals(title, Constants.title);
	  
  }
  
  
  @Test(priority=2 )
  public void verifyGoogleEmptySearch (){
	  TestBase.driver.findElement(By.name("q")).clear();
	  // If search box is empty then when hitting Enter it should stay in same page
	  TestBase.actions.sendKeys(Keys.ENTER);
	  TestBase.actions.perform();	
	  //Checking if it stayed in same paged
	  Assert.assertEquals(TestBase.driver.
			  findElement(By.className(Constants.googleSearchBox)).
			  isDisplayed(),true);

  
	 }
  
  @Test(priority=3,
		  dataProvider="testData")
  public void percentageSignTest(String firstSearch,String secondSearch,String thirdSearch) {
      WebElement p=TestBase.driver.findElement(By.name("q"));
      //enter text with sendKeys() then apply submit()
      p.sendKeys(firstSearch);
      // Explicit wait condition for search results
      WebDriverWait w = new WebDriverWait(TestBase.driver, 5);
      w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul")));
      p.submit();
	  WebElement element = TestBase.driver.findElement(By.partialLinkText(Constants.percentageLink));
	  TestBase.actions.moveToElement(element);
	  TestBase.actions.perform();
	  //Checking whether % signs will redirect to 404 ERROR
   	  Assert.assertEquals(TestBase.driver.
   			  //Updated  removed xpath because it's bad practice because it's dynamic
   			  findElement(By.partialLinkText(Constants.percentageLink)).
   			  isDisplayed(),true);
  }
  
  
  @Test(priority=4,
		  dataProvider="testData")
  public void afterSearchLogoTest(String firstSearch,String secondSearch,String thirdSearch) {
     TestBase.driver.findElement(By.name("q")).clear();
      WebElement p=TestBase.driver.findElement(By.name("q"));
      //enter text with sendKeys() then apply submit()
      p.sendKeys(secondSearch);
      // Explicit wait condition for search results
      WebDriverWait w = new WebDriverWait(TestBase.driver, 5);
      w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul")));
      p.submit();
      // Checking search result
	  Assert.assertEquals(TestBase.driver.
   			  //Updated  removed xpath because it's bad practice because it's dynamic
			  findElement(By.partialLinkText(Constants.googleLink)).
			  isDisplayed(),true);

  }
  
  
  
  @Test(priority=5,
		  dataProvider="testData")
  public void verifyGoogleAutoSuggestions (String firstSearch,String secondSearch,String thirdSearch) throws InterruptedException {
	  TestBase.driver.findElement(By.name("q")).clear();
	  TestBase.driver.findElement(By.name("q")).sendKeys(thirdSearch);
	 
	  //Thread.sleep(2000); --> another bad practice 
	  //implicit wait is more efficient 
	  TestBase.driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);

	  // Making a list of Auto suggestion to store results
	  List<WebElement> listOfElements=TestBase.driver.findElements(By.xpath(Constants.googleSearchBoxAutoSug));
	  for (WebElement webElement : listOfElements) {
	   System.out.println(webElement.getText());
	   // we want a certain from Auto suggestions
	   if(webElement.getText().contains("instabug")) {
	    webElement.click();
	    break;
	   }
	  }
	  
	  Assert.assertEquals(TestBase.driver.
   			  //Updated  removed xpath because it's bad practice because it's dynamic
			  findElement(By.partialLinkText(Constants.instabugLink)).
			  isDisplayed(),true);

	 }
  
  
  

		
  @AfterClass
  public void tearDown() {
	 TestBase.quit();
  }
  
  
  
  @DataProvider
  public String[][] testData() throws InvalidFormatException, IOException{
  ReadExcelData obj = new ReadExcelData();
	return obj.read_sheet();
	  
  }
}
