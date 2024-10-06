package tests;

import dto.ContactDtoLombok;
import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.HeaderMenuItem;

import java.lang.reflect.Method;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;

public class EditContactTests extends ApplicationManager {

    UserDto user = new UserDto("qa_mail@mail.com", "Qwerty123!");

    ContactPage contactPage;

    @BeforeMethod
    public void login() {
        logger.info("start method--> login "+ "user: "+"qa_mail@mail.com");
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        contactPage =  loginPage.typeLoginForm(user).clickBtnLoginPositive();

    }

    @Test
    public void EditContactPositiveTest(Method method){
        contactPage.clickFirstElementOfContactsList();
        contactPage.clickBtnEditContact();

        ContactDtoLombok editContact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        logger.info("start--> "+method.getName()+ " with data: "+editContact.toString());
        Assert.assertTrue(contactPage.fillContactFormEdit(editContact)
                .clickBtnEditContactPositive()
                .isFirstPhoneEquals(editContact.getPhone()));
    }
}
