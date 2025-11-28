package org.example.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.helpers.DriverManager;
import org.example.pages.LoginPage;
import org.testng.Assert;

public class LoginSteps {

    LoginPage loginPage;

    @Given("I open the browser and navigate to Login page")
    public void openBrowserAndNavigate() {
        DriverManager.getDriver().get("https://the-internet.herokuapp.com/login");
        loginPage = new LoginPage();
    }

    @When("I enter username {string} and password {string}")
    public void enterCredentials(String username, String password) {
        // {string} là chỗ nó sẽ điền "tomsmith" và "SuperSecretPassword!" vào
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @And("I click the Login button")
    public void clickLogin() {
        loginPage.clickLoginButton();
    }

    @Then("I should see the Secure Area page")
    public void verifyLoginSuccess() {
        Assert.assertTrue(loginPage.isLoginSuccess(), "Login failed! Secure Area not found.");
    }
}