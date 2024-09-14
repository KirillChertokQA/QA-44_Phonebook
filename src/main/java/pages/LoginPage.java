package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage{

    public LoginPage(WebDriver driver){
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//input[@name='email']")
    WebElement inputEmail;

    @FindBy(xpath = "//input[@name='password']")
    WebElement inputPassword;

    @FindBy(xpath = "//button[@name='login']")
    WebElement btnLoginSubmit; //kirill@gmail.com   Ab123456!

    @FindBy(xpath = "//button[@name ='registration']")
    WebElement btnRegistration;

    @FindBy(xpath = "//div[@class='login_login__3EHKB']/div")
    WebElement errorMessageLogin;

    @FindBy(xpath = "//div[@class='login_login__3EHKB']/div")
    WebElement errorMessageRegistration;

    public LoginPage typeLoginForm(String email, String password){
        //inputEmail.clear();
        inputEmail.sendKeys(email);
        inputPassword.sendKeys(password);
        return this;
    }

    public ContactPage clickBtnLoginPositive(){
        btnLoginSubmit.click();
        return new ContactPage(driver);
    }

    public ContactPage clickBtnRegistrationPositive(){
        btnRegistration.click();
        return new ContactPage(driver);
    }

    public LoginPage clickBtnLoginNegative() {
        btnLoginSubmit.click();
        return this;
    }

    public LoginPage clickBtnRegistrationNegative(){
        btnRegistration.click();
        return this;
    }

    public LoginPage closeAllert() {
        pause(3);
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.alertIsPresent());
        System.out.println(alert.getText());
        alert.accept();
        return new LoginPage(driver);
    }

    public boolean isTextInElementPresent_errorMassage(){
        return isElementPresent(errorMessageLogin, "Login Failed with code 401");
    }

    public boolean isTextInElementPresent_errorMessageReg(){
        return isElementPresent(errorMessageRegistration, "Registration failed with code 400");
    }
}
