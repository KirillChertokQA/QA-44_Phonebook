package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import utils.WDListener;


public class ApplicationManager {
    private WebDriver driver;

    public Logger logger = LoggerFactory.getLogger(ApplicationManager.class);

    public WebDriver getDriver(){
        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters()
    public void setUp(){
       // logger.info("Start testing ==============================");
        driver = new ChromeDriver();
        //new realization WDListener in Selenium
        WebDriverListener webDriverListener = new WDListener();
        driver = new EventFiringDecorator<>(webDriverListener).decorate(driver);

        driver.manage().window().maximize();
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        logger.info("Stop testing =================================");
      if(driver != null){
           driver.quit();
        }
    }
}
