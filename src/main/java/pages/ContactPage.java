package pages;

import dto.ContactDtoLombok;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactPage extends BasePage {

    public ContactPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//a[text()='CONTACTS']")
    WebElement btnContact;

    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']//div[@class='contact-item_card__2SOIM'][last()]/h3")
    WebElement lastPhoneInList;

    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']//div[@class='contact-item_card__2SOIM']")
            WebElement firstContactOnList;

    @FindBy(xpath = "//button[text()='Remove']")
    WebElement btnRemoveContact;

    @FindBy(xpath = "//button[text()='Edit']")
    WebElement btnEditContact;

    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']//div[@class='contact-item_card__2SOIM']/h3")
    WebElement firstPhoneInList;

    public boolean isElementContactPresent() {
        return btnContact.isDisplayed();
    }

    public boolean isLastPhoneEquals(String phone) {
        return lastPhoneInList.getText().equals(phone);
    }

    public boolean urlContainsAdd() {
        return urlContains("add", 3);
    }

    public boolean isAlertPresent(int time) {
        try {
            Alert alert = new WebDriverWait(driver, Duration.ofSeconds(time))
                    .until(ExpectedConditions.alertIsPresent());
            System.out.println(alert.getText());
            alert.accept();
            return true;
        } catch (TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void clickFirstElementOfContactsList() {
        firstContactOnList.click();
    }

    public void clickBtnRemoveContact(){
        btnRemoveContact.click();
    }

    public int getContactNumber() {
       // pause(2);
        clickWait(By.xpath("//div[@class='contact-item_card__2SOIM']"),2);
        return driver.findElements(By.xpath("//div[@class='contact-item_card__2SOIM']")).size();
    }

    public void clickBtnEditContact(){
        btnEditContact.click();
    }

    @FindBy(xpath = "//input[@placeholder='Name']")
    WebElement inputName;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement inputLastName;

    @FindBy(xpath = "//input[@placeholder='Phone']")
    WebElement inputPhone;

    @FindBy(xpath = "//input[@placeholder='email']")
    WebElement inputEmail;

    @FindBy(xpath = "//input[@placeholder='Address']")
    WebElement inputAddress;

    @FindBy(xpath = "//input[@placeholder='description']")
    WebElement inputDescription;

    @FindBy(xpath = "//button[text()='Save']")
    WebElement btnSaveEditedContact;

    //=====================

    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']/h2")
    WebElement contactCardNameLastName;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']")
    WebElement contactCardPhone;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']/br")
    WebElement contactCardEmail;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']/br[last()]")
    WebElement contactCardAddress;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']/h3")
    WebElement contactCardDescription;


    public ContactPage fillContactFormEdit(ContactDtoLombok contact) {
        inputName.clear();
        inputName.sendKeys(contact.getName());
        inputLastName.clear();
        inputLastName.sendKeys(contact.getLastName());
        inputPhone.clear();
        inputPhone.sendKeys(contact.getPhone());
        inputEmail.clear();
        inputEmail.sendKeys(contact.getEmail());
        inputAddress.clear();
        inputAddress.sendKeys(contact.getAddress());
        inputDescription.clear();
       inputDescription.sendKeys(contact.getDescription());
        return this;
    }

    public ContactPage clickBtnEditContactPositive() {
        btnSaveEditedContact.click();
        return new ContactPage(driver);
    }

    public boolean isFirstPhoneEquals(String phone) {
        pause(3);
        return firstPhoneInList.getText().equals(phone);
    }



    public ContactDtoLombok getContactFromDetailedCard() {
        pause(3);
//        System.out.println(contactCardNameLastName.getText());
//        System.out.println(contactCardPhone.getText());
//        System.out.println(contactCardEmail.getText());
//        System.out.println(contactCardAddress.getText());
//        System.out.println(contactCardDescription.getText());

        String name = contactCardNameLastName.getText().split(" ")[0];
        String lastName = contactCardNameLastName.getText().split(" ")[1];
        String phone = contactCardPhone.getText().split("\n")[1];
        String email = contactCardPhone.getText().split("\n")[2];
        String address = contactCardPhone.getText().split("\n")[3];
        String description = contactCardDescription.getText().split(": ")[1];

        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(name)
                .lastName(lastName)
                .phone(phone)
                .address(address)
                .email(email)
                .description(description)
                .build();
        System.out.println(contact);
        return contact;
    }
}