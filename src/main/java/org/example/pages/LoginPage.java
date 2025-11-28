package org.example.pages;

import org.example.helpers.WebUI;
import org.openqa.selenium.By;

public class LoginPage {
    //Declare Locators
    //private By headerPage = By.xpath("//h2[text()='Login Page']");
    private static final By INPUT_USERNAME = By.id("username");
    private static final By INPUT_PASSWORD = By.id("password");
    private static final By BTN_LOGIN = By.cssSelector("button[type='submit']");
    private static final By ALERT_MESSAGE = By.id("flash");

    //Declare Action functions  - User WebUI
    public void enterUsername(String username) {
        WebUI.setText(INPUT_USERNAME, username);
    }

    public void enterPassword(String password) {
        WebUI.setText(INPUT_PASSWORD, password);
    }

    public void clickLoginButton() {
        WebUI.clickElement(BTN_LOGIN);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    //Verify functions (Return data in order to Test Class Assert)
    public String getAlertText() {
        return WebUI.getElementText(ALERT_MESSAGE);
    }

    public boolean isLoginSuccess() {
        return WebUI.getCurrentURL().contains("secure");
    }
}
