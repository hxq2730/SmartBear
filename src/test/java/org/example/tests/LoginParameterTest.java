package org.example.tests;

import org.example.base.BaseTest_No_ThreadLocal;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginParameterTest extends BaseTest_No_ThreadLocal {
    @Test
    @Parameters({"url", "username", "password"})
    public void testLoginMultiEnv(String url, String username, String password) {
        System.out.println("Dang test tai web: " + url);
        System.out.println("Dang kiem tra login tai khoan: " + username);
        driver.get(url);
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        String currentUrl = driver.getCurrentUrl();
//        if (currentUrl.equals("secure")) {
//
//        }
        Assert.assertTrue(currentUrl.contains("secure") || driver.findElement(By.id("flash")).isDisplayed());
    }
}
