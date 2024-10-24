package tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import manager.ApplicationManager;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.TakeScreenShot;
import utils.TestNGListener;

import static utils.TakeScreenShot.takeScreenShot;

@Listeners(TestNGListener.class)

public class LoginTests extends ApplicationManager {

    @Description("positive log in method")
    @Owner("Kirill Qa-44")
    @Test
    public void loginPositiveTest() {

        Allure.step("fill LogIn form");
        boolean result = new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_mail@mail.com", "Qwerty123!")
                .clickBtnLoginPositive()
                .isElementContactPresent();
        takeScreenShot((TakesScreenshot) getDriver());
        Assert.assertTrue(result);
    }

    @Test
    public void loginNegativeTest_wrongPassword() {
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_mail@mail.com", "Qwerty123!----")
                .clickBtnLoginNegative().closeAllert()
                .isTextInElementPresent_errorMassage());

    }

    @Test
    public void loginNegativeTest_wrongEmailUnregUser() {
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_mail567@mail.ru", "Qwerty123!")
                .clickBtnLoginNegative().closeAllert()
                .isTextInElementPresent_errorMassage());
    }

    @Test
    public void loginNegativeTest_wrongEmailWOAt() {       //WO=without
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_mail567mail.ru", "Qwerty123!")
                .clickBtnLoginNegative().closeAllert()
                .isTextInElementPresent_errorMassage());
    }
}

