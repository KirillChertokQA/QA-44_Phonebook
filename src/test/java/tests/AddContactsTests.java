package tests;

import dto.ContactDtoLombok;
import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.*;

public class AddContactsTests extends ApplicationManager {

    UserDto user = new UserDto("qa_mail@mail.com", "Qwerty123!");

    AddPage addPage;

    @BeforeMethod
    public void login() {
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(user).clickBtnLoginPositive();
        addPage = clickButtonsOnHeader(HeaderMenuItem.ADD);
    }

    @Test
    public void addNewContactPositiveTest() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
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
        addPage.closeAlert();
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
        addPage.closeAlert();
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
        addPage.closeAlert();
    }
}
