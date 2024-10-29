package tests;

import data_provider.DPAddContact;
import dto.ContactDtoLombok;
import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.AddPage;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.TestNGListener;

import java.lang.reflect.Method;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.*;

@Listeners(TestNGListener.class)

public class AddContactsTests extends ApplicationManager {

    UserDto user = new UserDto("qa_mail@mail.com", "Qwerty123!");

    AddPage addPage;

    @BeforeMethod(alwaysRun = true)
    public void login() {
        logger.info("start method--> login "+ "user: "+"qa_mail@mail.com");
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(user).clickBtnLoginPositive();
        addPage = clickButtonsOnHeader(HeaderMenuItem.ADD);
    }

    @Test(groups = "smoke")
    public void addNewContactPositiveTest(Method method) {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        logger.info("start--> "+method.getName()+ " with data: "+contact.toString());
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isLastPhoneEquals(contact.getPhone()));

    }

    @Test
    public void addNewContactNegativeTest_wrongEmailWOAt() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email("qa_testgmail.com")
                .address(generateString(20))
                .description(generateString(10))
                .build();
        addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative();
        String alertMessage = addPage.alertText();
        Assert.assertTrue(alertMessage.contains("Email not valid"));
        addPage.closeAlert(); //после Assert никаких действий не выполняют
    }

    @Test
    public void addNewContactNegativeTest_fieldPhoneEmpty() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(0))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative();
        String alertMessage = addPage.alertText();
        Assert.assertTrue(alertMessage.contains("Phone not valid"));
        addPage.closeAlert(); //после Assert никаких действий не выполняют
    }

    @Test
    public void addNewContactNegativeTest_fieldPhoneIsNotValid() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("@@aaa12345678")
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative();
        String alertMessage = addPage.alertText();
        Assert.assertTrue(alertMessage.contains("Phone not valid"));
        addPage.closeAlert(); //после Assert никаких действий не выполняют
    }

    @Test
    public void addNewContactNegativeTest_nameIsEmpty() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name("")
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .urlContainsAdd());
    }

    @Test
    public void addNewContactNegativeTest_wrongEmail() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(4))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateString(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
       Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isAlertPresent(5));
    }

    @Test(dataProvider = "addNewContactDP", dataProviderClass = DPAddContact.class)
    public void addNewContactNegativeTest_wrongDP(ContactDtoLombok contact) {          //Data Provider - DP
        System.out.println("-->" +contact);
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isAlertPresent(5));
    }

    @Test(dataProvider = "addNewContactDPFile", dataProviderClass = DPAddContact.class)
    public void addNewContactNegativeTest_wrongDPFile(ContactDtoLombok contact) {          //Data Provider - DP
        System.out.println("-->" +contact);
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isAlertPresent(5));
    }
}