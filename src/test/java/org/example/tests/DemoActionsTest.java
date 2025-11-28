package org.example.tests;

import org.example.base.BaseTest;
import org.example.helpers.DriverManager;
import org.example.helpers.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoActionsTest extends BaseTest {
    //TEST 5: Auto-Suggest Dropdown
    @Test(priority = 5)
    public static void testAutoSuggest() throws InterruptedException {
        DriverManager.getDriver().get("https://demoqa.com/auto-complete");
        WebUI.removeAds();

        By inputMulti = By.id("autoCompleteMultipleInput");

        DriverManager.getDriver().findElement(inputMulti).sendKeys("R"); // Gõ chữ R
        Thread.sleep(1000); // Chờ gợi ý hiện ra (Red, Green, Purple...)

        // Giả lập hành động bàn phím Nhấn Mũi tên xuống -> Nhấn Enter
        Actions action = new Actions(DriverManager.getDriver());
        action.sendKeys(Keys.ARROW_DOWN).perform();
        action.sendKeys(Keys.ENTER).perform();

        System.out.println("Đã chọn màu từ gợi ý thành công!");
     
    }

    // --- TEST 1: HOVER MENU ---
    @Test(priority = 1)
    public void testHoverMenu() throws InterruptedException {

        DriverManager.getDriver().get("https://demoqa.com/menu");
        WebUI.removeAds();
        //Hover menu cha
        By mainItem2 = By.xpath("//a[contains(text(),'Main Item 2')]");
        //By mainItem2 = By.xpath("//a[text()='Main Item 2']");
        WebUI.scrollToElementOnTop(mainItem2);
        WebUI.hoverOnElement(mainItem2);

        //Hover menu con
        By subList = By.xpath("//a[text()='SUB SUB LIST »']");
        WebUI.waitForElementVisible(subList);
        WebUI.hoverOnElement(subList);

        //Click menu chau
        By subItem = By.xpath("//a[contains(text(),'Sub Sub Item 2')]");
        //WebUI.clickElement(subItem);
        WebUI.waitForElementVisible(subItem);
        DriverManager.getDriver().findElement(subItem).click();

        Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("#"), "Lỗi: Click menu không thành công");
        Thread.sleep(3000);

    }

    // --- TEST 2: DRAG & DROP ---
    @Test(priority = 2)
    public void testDragAndDrop() throws InterruptedException {

        DriverManager.getDriver().get("https://demoqa.com/droppable");
        WebUI.removeAds();

        //Hover droppable item
        By dragItem = By.cssSelector("div#draggable");
        By dropItem = By.cssSelector("#droppable");
        //By dropItem = By.xpath("//div[@id='simpleDropContainer']//div[@id='droppable']");
        WebUI.waitForElementVisible(dragItem);
        WebUI.waitForElementVisible(dropItem);
        WebUI.dragAndDrop(dragItem, dropItem);

        //Check result
        String actualText = DriverManager.getDriver().findElement(By.xpath("//div[@id='droppable']/p")).getText();
        Assert.assertEquals(actualText, "Dropped!", "Error: Drag and Drop Fail!");

        Thread.sleep(3000);
    }

    //TEST 3: DOUBLE CLICK
    @Test(priority = 3)
    public void testComplexClicks() throws InterruptedException {
        DriverManager.getDriver().get("https://demoqa.com/buttons");
        WebUI.removeAds();

        //test double click
        By doubleClickItem = By.id("doubleClickBtn");
        WebUI.scrollToElementOnTop(doubleClickItem);
        WebUI.waitForElementVisible(doubleClickItem);
        WebUI.doubleClick(doubleClickItem);

        String actualTestDoubleClick = DriverManager.getDriver().findElement(By.id("doubleClickMessage")).getText();
        Assert.assertEquals(actualTestDoubleClick, "You have done a double click", "Error: Double Click Fail");

        //test right click
        WebUI.rightClick(By.id("rightClickBtn"));

        String actualTestRightClick = DriverManager.getDriver().findElement(By.id("rightClickMessage")).getText();
        Assert.assertEquals(actualTestRightClick, "You have done a right click", "Error: Right Click Fail");

        Thread.sleep(3000);
    }

    //TEST 4: Slider (drag and drop by offset)
    @Test(priority = 4)
    public void testSlider() throws InterruptedException {
        DriverManager.getDriver().get("https://demoqa.com/slider");
        WebUI.removeAds();

        By slider = By.cssSelector("input[type='range']");
        WebUI.scrollToElementOnTop(slider);
        //Drag slider to the right with 50 offset
        String offset = "50";
        //WebUI.dragAndDropByOffset(slider, 50, 0);
        WebUI.setSliderValue(slider, offset);

        //Check value
        String value = DriverManager.getDriver().findElement(By.id("sliderValue")).getAttribute("value");
        System.out.println("Slider value after drag: " + value);

        if (offset.equals(value)) {
            System.out.println("Slider work exacly");
        }

        Thread.sleep(3000);
    }

}
