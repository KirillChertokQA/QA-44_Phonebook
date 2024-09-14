package tests;

import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class RegistrationTests extends ApplicationManager {

    @Test
    public void registrationPositiveTest(){
        Assert.assertTrue(new HomePage(getDriver()).clickBtnLoginHeader()
                .typeLoginForm("my1_qa_email44@mail.com", "Password123!")
                .clickBtnRegistrationPositive()
                .isElementContactPresent());

    }
@Test
    public void registrationNegativeTest_wrongEmail(){
        Assert.assertTrue(new HomePage(getDriver()).clickBtnLoginHeader()
                .typeLoginForm("qa_email44mail.com", "Password123!")
                .clickBtnRegistrationNegative().closeAllert()
                .isTextInElementPresent_errorMessageReg());

    }
}
