package org.example.tests;

import io.qameta.allure.testng.AllureTestNg;
import org.example.base.BaseTest;
import org.example.helpers.DriverManager;
import org.example.helpers.ExcelHelpers;
import org.example.helpers.WebUI;
import org.example.listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;


@Epic("Regression Test")
@Feature("Login Functionality")
public class DataDrivenLoginTest extends BaseTest {

    // Chuẩn bị kho dữ liệu
    //@DataProvider(name = "LoginData")
//    @Description("Kiểm tra đăng nhập với nhiều loại tài khoản khác nhau")
//    @Severity(SeverityLevel.CRITICAL)
    //    public Object[][] dataLogin() {
//        return new Object[][]{
//                // Username, Password, Expected Result
//                {"tomsmith", "SuperSecretPassword!", "pass"},  // Case 1: Đúng -> Mong đợi Pass
//                {"wronguser", "wrongpass", "fail"},            // Case 2: Sai -> Mong đợi Fail
//                {"admin", "admin123", "fail"}                  // Case 3: Sai -> Mong đợi Fail
//        };
//    }
    @DataProvider(name = "LoginDataExcel")
    @Description("Kiểm tra đăng nhập với nhiều loại tài khoản khác nhau")
    @Severity(SeverityLevel.CRITICAL)


    public Object[][] dataLoginFromExcel() {
        ExcelHelpers excel = new ExcelHelpers();
        try {
            // Đường dẫn đến file Excel
            String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/LoginData.xlsx";

            // Gọi hàm đọc file: Bắt đầu từ dòng 1 (bỏ dòng 0 là tiêu đề), cột 0
            return excel.getDataHashTable(filePath, "Login", 1, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //@Test(dataProvider = "LoginData")
    @Test(dataProvider = "LoginDataExcel")
    public void testLogin(String username, String password, String expectedResult) {
        System.out.println("--- Chạy test với User: " + username + " | Mong đợi: " + expectedResult + " ---");

        // 1. Mở trang login
        DriverManager.getDriver().get("https://the-internet.herokuapp.com/login");

        // 2. Điền dữ liệu
        //WebUI.highlightElement(By.id("username"));
        DriverManager.getDriver().findElement(By.id("username")).sendKeys(username);
        DriverManager.getDriver().findElement(By.id("password")).sendKeys(password);
        DriverManager.getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        /// 3. Kiểm tra kết quả
        String currentUrl = DriverManager.getDriver().getCurrentUrl();

        if (expectedResult.equals("pass")) {
            Assert.assertTrue(currentUrl.contains("secure"),
                    "Lỗi: Mong đợi Pass nhưng URL không chứa 'secure'.");
        } else {
            // --- LOGIC FAIL CASE ---
            System.out.println("   -> Đang chờ thông báo lỗi hiện ra...");

            try {
                // Dùng trực tiếp biến 'wait' (được kế thừa từ BaseTest)
                WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));

                System.out.println("   -> Tìm thấy thông báo lỗi: " + errorMsg.getText());
                Assert.assertTrue(errorMsg.isDisplayed(), "Thông báo lỗi có trong DOM nhưng không hiển thị!");

            } catch (Exception e) {
                // Nếu vào đây tức là chờ 10s rồi mà không thấy đâu
                System.out.println("   -> LỖI: Không tìm thấy element #flash");
                Assert.fail("Lỗi: Case này mong đợi FAIL nhưng không thấy thông báo lỗi hiện ra (Timeout).");
            }
        }
    }
}