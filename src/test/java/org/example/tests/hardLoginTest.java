package org.example.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class hardLoginTest {
    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
                {"user1", "pass1"}, {"admin", "admin123"}, {"user3", "pass3"}};
    }

    private boolean fakeLogin(String u, String p) {
        return "admin".equals(u) && "admin123".equals(p);
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password) {
        boolean expectedSuccess = true;
        System.out.println("Testing with: " + username + " - Pass: " + password);
        boolean success = fakeLogin(username, password);
        Assert.assertEquals(success, expectedSuccess, "Login result mismatch for " + username);
    }
}