package tests;

import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.TestNGListener;

import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;

@Listeners(TestNGListener.class)

public class RegistrationTests extends ApplicationManager {

    @Test
    public void registrationPositiveTest() {
        Assert.assertTrue(new HomePage(getDriver()).clickBtnLoginHeader()
                .typeLoginForm("my1_qa_email44@mail.com", "Password123!")
                .clickBtnRegistrationPositive()
                .isElementContactPresent());

    }

//    @Test
//    public void registrationNegativeTest_wrongEmail() {
//        Assert.assertTrue(new HomePage(getDriver()).clickBtnLoginHeader()
//                .typeLoginForm("qa_email44mail.com", "Password123!")
//                .clickBtnRegistrationNegative().closeAllert()
//                .isTextInElementPresent_errorMessageReg());
//
//  }

    @Test
    public void registrationNegativeTest_wrongEmail() {
        String email = generateString(10);
        UserDto user = new UserDto(email, "Qwerty123!");

        new HomePage(getDriver()).clickBtnLoginHeader()
                .typeLoginForm(user)
                .clickBtnRegistrationNegative()
                .closeAllert()
                .isTextInElementPresent_errorMassage();

    }
}
