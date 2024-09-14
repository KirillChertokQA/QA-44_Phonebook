package tests;

import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class LoginTests extends ApplicationManager {

    @Test
    public void loginPositiveTest() {
        boolean result = new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_mail@mail.com", "Qwerty123!")
                .clickBtnLoginPositive()
                .isElementContactPresent();
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
        public void loginNegativeTest_wrongEmail(){
            Assert.assertTrue(new HomePage(getDriver())
                    .clickBtnLoginHeader()
                    .typeLoginForm("qa_mailmail.ru", "Qwerty123!")
                    .clickBtnLoginNegative().closeAllert()
                    .isTextInElementPresent_errorMassage());
        }
    }


