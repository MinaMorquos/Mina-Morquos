package SingletoneTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class TestBase {
	//Singleton
	public static WebDriver driver= null;
    public static Actions actions = null;

    
   
    //Singleton
	public static WebDriver initialize() {
		if (driver==null)
		{
			if(Constants.browserName.equalsIgnoreCase("chrome")) {
				  System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe"); 
				  driver= new ChromeDriver();
			}
			else if (Constants.browserName.equalsIgnoreCase("FireFox"))
			{
				  System.setProperty("webdriver.gecko.driver", ".\\driver\\geckodriver.exe"); 
				  driver= new FirefoxDriver();
			}
		}
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(Constants.url);
		return driver;
	}
	
	//Another one for Singleton
	 public static Actions actionInstance() {
	    	if(actions==null) {
	    		   actions = new Actions(TestBase.driver);
	    	}
	    	return actions;
	    }
	
	public static void quit () {
		System.out.println("Quitting the browser");
		driver.quit();
		driver = null;
	}
	
	public static void close () {
		System.out.println("Closing the browser");
		driver.close();
		driver = null;
	}

}
