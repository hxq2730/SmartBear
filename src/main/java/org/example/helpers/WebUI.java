package org.example.helpers;

import org.example.utils.LogUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class WebUI {

    // Helper: Lấy Driver từ ThreadLocal
    private static WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    /// Actions
    // Khởi tạo Actions
    private static Actions getActions() {
        return new Actions(getDriver());
    }

    //1. Hover
    public static void hoverOnElement(By by) {
        WebElement element = getDriver().findElement(by);
        getActions().moveToElement(element).perform();
    }

    //2. Right Click
    public static void rightClick(By by) {
        WebElement element = getDriver().findElement(by);
        getActions().contextClick(element).perform();
    }

    //3. Double Click
    public static void doubleClick(By by) {
        WebElement element = getDriver().findElement(by);
        getActions().doubleClick(element).perform();
    }

    //4. Drag and Drop
    public static void dragAndDrop(By source, By target) {
        WebElement sourceElement = getDriver().findElement(source);
        WebElement targetElement = getDriver().findElement(target);
        getActions().dragAndDrop(sourceElement, targetElement).perform();
    }

    //5. Slider
    public static void dragAndDropByOffset(By by, int x, int y) {
        WebElement element = getDriver().findElement(by);
        getActions().dragAndDropBy(element, x, y).perform();
    }

    public static void setSliderValue(By by, String value) {
        WebElement slider = getDriver().findElement(by);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        // Đoạn Script chuyên dụng cho React input range
        // Nó gọi trực tiếp vào setter gốc của trình duyệt để bypass cơ chế chặn của React
        js.executeScript(
                "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, " +
                        "'value').set;" +
                        "nativeInputValueSetter.call(arguments[0], arguments[1]);" +
                        "var event = new Event('input', { bubbles: true});" +
                        "arguments[0].dispatchEvent(event);",
                slider, value
        );
    }

    //6. SendKeys advanced (Press Enter/Tab)
    public static void pressKey(Keys key) {
        getActions().sendKeys(key).perform();
    }

    //7. Copy and past (hold Ctrl key)
    public static void copyAndPast(By source, By target) {
        WebElement sourceElement = getDriver().findElement(source);
        WebElement targetElement = getDriver().findElement(target);

        // Ctrl + A -> Ctrl + C (Trên Element nguồn)
        getActions().click(sourceElement)
                    .keyDown(Keys.CONTROL)
                    .sendKeys("a")
                    .keyUp(Keys.CONTROL)
                    .keyDown(Keys.CONTROL)
                    .sendKeys("c")
                    .keyUp(Keys.CONTROL)
                    .perform();

        // Ctrl + V (Trên Element đích)
        getActions().click(targetElement).keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();

    }

    /// JavascriptExecutor
    // CLICK CƯỠNG BỨC (JS Click)
    // Dùng khi bị lỗi ElementClickInterceptedException (Bị che khuất)
    public static void jsClick(By by) {
        WebElement element = getDriver().findElement(by);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", element);
    }

    // CUỘN TỚI PHẦN TỬ (Scroll to Element)
    // Dùng khi phần tử nằm tít dưới đáy trang, Selenium không tự cuộn tới được
    public static void scrollToElement(By by) {
        WebElement element = getDriver().findElement(by);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    //
    public static void scrollToElementOnTop(By by) {
        WebElement element = getDriver().findElement(by);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        // block: "center" sẽ đưa phần tử vào giữa màn hình
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
    }

    /// Helper
    public static void removeAds() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        try {
            // Xóa cái banner quảng cáo dưới đáy (theo hình bạn gửi)
            js.executeScript("const ads = document.getElementsByClassName('ad-plus-atf'); for(let i=0; i<ads.length; " +
                    "i++){ ads[i].style.display='none'; }");

            // Xóa cái khung Fixedban bên phải (nếu có)
            js.executeScript("const fixedban = document.getElementById('fixedban'); if(fixedban) fixedban.style" +
                    ".display='none';");

            // Xóa luôn thẻ footer cho rộng chỗ
            js.executeScript("const footer = document.querySelector('footer'); if(footer) footer.style" +
                    ".display='none';");

            //System.out.println("Đã xóa quảng cáo thành công!");
        } catch (Exception e) {
            //System.out.println("Không tìm thấy quảng cáo để xóa (Không sao cả).");

        }
    }

    // HIGHLIGHT PHẦN TỬ (Tô màu viền)
    // Dùng để Debug: Giúp bạn biết code đang thao tác vào phần tử nào trên màn hình
    public static void highlightElement(By by) {
        WebElement element = getDriver().findElement(by);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        // Vẽ viền đỏ 3px và tô nền vàng
        js.executeScript("arguments[0].setAttribute('style', 'border: 3px solid red; background: " + "yellow;');",
                element);
    }

    public static void refreshPage() {
        getDriver().navigate().refresh();
    }

    public static void backToPage() {
        getDriver().navigate().back();
    }

    public static void forwardToPage() {
        getDriver().navigate().forward();
    }

    public static void openURL(String url) {
        getDriver().get(url);
    }

    public static String getPageTitle() {
        return getDriver().getTitle();
    }

    public static boolean verifyElementVisible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getCurrentURL() {
        waitForPageLoaded();
        return getDriver().getCurrentUrl();
    }

    public static boolean verifyElementChecked(By by) {
        waitForElementVisible(by);
        return getDriver().findElement(by).isSelected();
    }

    public static void waitForElementVisible(By by) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }


    public static void waitForElementClickable(By by) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public static void waitForElementInvisible(By by) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        try {
            wait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                }
            });
        } catch (Throwable error) {
            //System.out.println("Timeout waiting for Page Load Request to complete.");
            LogUtils.info("Timeout waiting for Page Load Request to complete.");
        }
    }

    public static void clickElement(By by) {
        waitForElementClickable(by); // Luôn wait trước khi click
        getDriver().findElement(by).click();
    }

    public static void setText(By by, String text) {
        waitForElementVisible(by);
        getDriver().findElement(by).sendKeys(text);
    }

    /// Select
    // 1. Chọn theo Text hiển thị (Khuyên dùng)
    public static void selectOptionByText(By by, String text) {
        waitForElementVisible(by); // Chờ select hiện ra
        Select select = new Select(getDriver().findElement(by));
        select.selectByVisibleText(text);
        //System.out.println("Selected option by text: " + text);
        LogUtils.info("Selected option by text: " + text);
    }

    // 2. Chọn theo Value (HTML attribute)
    public static void selectOptionByValue(By by, String value) {
        waitForElementVisible(by);
        Select select = new Select(getDriver().findElement(by));
        select.selectByValue(value);
        //System.out.println("Selected option by value: " + value);
        LogUtils.info("Selected option by value: " + value);
    }

    // 3. Chọn theo Index (Thứ tự từ 0)
    public static void selectOptionByIndex(By by, int index) {
        waitForElementVisible(by);
        Select select = new Select(getDriver().findElement(by));
        select.selectByIndex(index);
        //System.out.println("Selected option by index: " + index);
        LogUtils.info("Selected option by index: " + index);
    }

    /// Handle Alert
    //Accept Aler
    public static void acceptAlert() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());

        //Switch to alert and press OK
        getDriver().switchTo().alert().accept();
    }

    //Dismiss Alert
    public static void dismissAlert() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
        //Switch to alert and press Cancel
        getDriver().switchTo().alert().dismiss();
    }

    public static String getTextAlert() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
        return getDriver().switchTo().alert().getText();
    }

    public static String getElementText(By by) {
        waitForElementVisible(by);
        return getDriver().findElement(by).getText();
    }

    /// Handle New Tab / Window
    //Switch to new Tab/ Window
    public static void switchToWindowOrTab(int index) {
        // Lấy tất cả ID của các Tab đang mở
        Set<String> windowHandle = getDriver().getWindowHandles();
        // Chuyển Set thành List để lấy theo index
        List<String> windowList = new ArrayList<>(windowHandle);
        // Switch đến tab mong muốn (Tab đầu tiên là 0, Tab mới mở là 1)
        getDriver().switchTo().window(windowList.get(index));
    }

    //Switch to main Tab
    public static void switchToMainWindow() {
        //Get current Tab ID
        String mainWindow = getDriver().getWindowHandle();
        getDriver().switchTo().window(mainWindow);
    }

    /// Handle IFrame
    // Chui vào Iframe (bằng XPath/CSS)
    public static void switchToFrame(By by) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
    }

    // Chui ra khỏi Iframe (về trang chính)
    public static void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
    }

    /// Handle upload file
    public static void uploadFile(By by, String filePath) {
        // Không được click, chỉ sendKeys đường dẫn tuyệt đối
        getDriver().findElement(by).sendKeys(filePath);
    }
}