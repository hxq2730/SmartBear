package org.example.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class BaseTest_No_ThreadLocal {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @Parameters({"browser"}) // 1. Khai báo nhận tham số từ XML có tên là "browser"
    @BeforeMethod
    //@BeforeClass
    public void createDriver(@Optional("chrome") String browserName) {
        System.out.println(">>> Start: Đang khởi tạo Browser: " + browserName);
        // 2. Logic chọn trình duyệt dựa trên tham số nhận được
        if (browserName.equalsIgnoreCase("Chrome")) {
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else {
            throw new RuntimeException("Trinh duyet khong ho tro " + browserName);
        }

        //ChromeOptions options = new ChromeOptions();
        // Chạy ẩn danh
        //options.addArguments("--incognito");
        // --- KHỞI TẠO DRIVER VỚI OPTIONS ---
        // Truyền options vào trong hàm khởi tạo

        //driver = new ChromeDriver(options);
        //driver = new ChromeDriver();

        driver.manage().window().maximize();

        // 1. Setup Implicit Wait (Chờ ngầm định 10s)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // 2. Setup Page Load (Chờ trang tải tối đa 30s)
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        // 3. Setup Explicit Wait (Chờ tường minh 10s)
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    //@AfterClass
    public void closeDriver() {
        System.out.println("<<< End: Đang đóng Browser...");
        if (driver != null) {
            driver.quit();
        }
    }

    //Ham ho tro sleep, dung khi bug,demo
    public void sleep(double second) {
        try {
            Thread.sleep((long) second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}