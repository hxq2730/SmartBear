package org.example.tests;

import org.example.base.BaseTest;
import org.example.helpers.DriverManager;
import org.example.helpers.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoHandleTest extends BaseTest {
    @Test(priority = 1)
    public void testAlert() throws InterruptedException {
        System.out.println("TEST 1: Handle Alert");
        DriverManager.getDriver().get("https://the-internet.herokuapp.com/javascript_alerts");
        //Click alert
        DriverManager.getDriver().findElement(By.cssSelector("button[onclick='jsConfirm()']")).click();
        //Get Text
        String alertText = WebUI.getTextAlert();
        System.out.println("Alert: " + alertText);
        Assert.assertEquals(alertText, "I am a JS Confirm");
        //Press Cancel (Dismiss)
        WebUI.dismissAlert();
        //Verify result
        String result = DriverManager.getDriver().findElement(By.id("result")).getText();
        Assert.assertEquals(result, "You clicked: Cancel");
        Thread.sleep(3000);
    }

    @Test(priority = 2)
    public void testNewTab() throws InterruptedException {
        System.out.println("--- Test 2: Handle New Tab ---");
        DriverManager.getDriver().get("https://demoqa.com/browser-windows");

        //Click "New Tab" button
        DriverManager.getDriver().findElement(By.id("tabButton")).click();

        //Switch Driver to new Tab
        WebUI.switchToWindowOrTab(1);

        //Check text at New Tab
        String textNewTab = DriverManager.getDriver().findElement(By.id("sampleHeading")).getText();
        System.out.println("Text at New tab: " + textNewTab);
        Assert.assertEquals(textNewTab, "This is a sample page");

        //Close new tab, back to old tab
        DriverManager.getDriver().close();
        WebUI.switchToWindowOrTab(0);

        Thread.sleep(3000);
    }

    @Test(priority = 3)
    public void testIframe() throws InterruptedException {
        System.out.println("--- Test 3: Handle Iframe ---");
        DriverManager.getDriver().get("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_input_test");

        By iframe = By.id("iframeResult");

        // 1. Go inside the iframe
        WebUI.switchToFrame(iframe);

        // 2. Working with the "First name" box
        By inputFirstName = By.id("fname");
        DriverManager.getDriver().findElement(inputFirstName).clear();
        DriverManager.getDriver().findElement(inputFirstName).sendKeys("Selenium Java");

        // Verify
        String textValue = DriverManager.getDriver().findElement(inputFirstName).getAttribute("value");
        Assert.assertEquals(textValue, "Selenium Java");

        // 3. Get out of IFrame
        WebUI.switchToDefaultContent();

        // 4. Check if you're out by looking for the "Run" button on the parent page
        boolean isRunBtnDisplayed = DriverManager.getDriver().findElement(By.id("runbtn")).isDisplayed();
        Assert.assertTrue(isRunBtnDisplayed, "Unable to exit iframe successfully!");

        System.out.println("Test Iframe W3Schools successful!");

        Thread.sleep(4000);
    }

    @Test(priority = 4)
    public void testUploadFile() throws InterruptedException {
        System.out.println("--- Test 4: Upload File ---");
        DriverManager.getDriver().get("https://the-internet.herokuapp.com/upload");

        // Chuẩn bị file (Lấy đường dẫn tuyệt đối của 1 file có sẵn trong project)
        // Ví dụ lấy luôn file pom.xml để upload
        String filePath = System.getProperty("user.dir") + "\\pom.xml";

        // Upload bằng sendKeys vào thẻ input type='file'
        WebUI.uploadFile(By.id("file-upload"), filePath);

        // Bấm nút Upload
        DriverManager.getDriver().findElement(By.id("file-submit")).click();

        //verify
        String uploadedFile = DriverManager.getDriver().findElement(By.id("uploaded-files")).getText();
        Assert.assertEquals(uploadedFile, "pom.xml");

        Thread.sleep(3000);
    }
}
