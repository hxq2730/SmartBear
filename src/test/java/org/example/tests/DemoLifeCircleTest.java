package org.example.tests;

import org.example.base.BaseTest_No_ThreadLocal;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class DemoLifeCircleTest extends BaseTest_No_ThreadLocal {
    @Test(priority = 1)
    public void testGoogleSearch() {
        System.out.println("->  Dang chay Test 1: Google Search");

        driver.get("https://www.google.com");
        driver.findElement(By.name("q")).sendKeys("Selenium Java");

        sleep(2);
    }

    @Test(priority = 2)
    public void testAnhTester() {
        System.out.println("   -> Dang chạy Test 2: Anh Tester");

        driver.get("https://anhtester.com");

        sleep(2);
    }
}
