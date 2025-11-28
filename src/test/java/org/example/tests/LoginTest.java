package org.example.tests;

import org.example.base.BaseTest;
import org.example.helpers.DatabaseHelpers;
import org.example.helpers.DriverManager;
import org.example.pages.LoginPage;
import org.example.pages.SecureAreaPage;
import org.example.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    //Test case 1: Login successfully
    @Test
    public void testLoginSuccess() {
        //1. Init Page Object
        LoginPage loginPage = new LoginPage();
        SecureAreaPage securePage = new SecureAreaPage();

        //2. Navigate to login page
        DriverManager.getDriver().get("https://the-internet.herokuapp.com/login");

        //3. Login
        loginPage.login("tomsmith", "SuperSecretPassword!");

        //4. Assert result
        //Check URL
        Assert.assertTrue(loginPage.isLoginSuccess(), "Wrong URL, Login fail");
        //Check page element
        Assert.assertEquals(securePage.getPageTitle(), "Secure Area", "Wrong page title");
    }

    //Test case 2: Login fail
    @Test
    public void testLoginFail() {
        //1. Init Page Object
        LoginPage loginPage = new LoginPage();

        //2. Navigate to login page
        DriverManager.getDriver().get("https://the-internet.herokuapp.com/login");

        //3. Login
        loginPage.login("wronguser", "wrongPassword!");

        //4. Verify Error
        Assert.assertTrue(loginPage.getAlertText().contains("Your username is invalid"), "Wrong error message is " +
                "display");

    }

    @Test
    public void testLoginWithDB() {
        //1. Init Page Object
        LoginPage loginPage = new LoginPage();
        SecureAreaPage securePage = new SecureAreaPage();

        String username = "tomsmith";
        String password = DatabaseHelpers.getPasswordForUser(username);

        Assert.assertNotNull(password, "Không tìm thấy user " + username + " trong DB!");

        DriverManager.getDriver().get("https://the-internet.herokuapp.com/login");
        loginPage.login(username, password);

        //Check URL
        Assert.assertTrue(loginPage.isLoginSuccess(), "Wrong URL, Login fail");
        //Check page element
        Assert.assertEquals(securePage.getPageTitle(), "Secure Area", "Wrong page title");
        LogUtils.info("✅ Login successfully with User: " + username + " - Pass: " + password);
    }
}
