package org.example.base;

import io.qameta.allure.testng.AllureTestNg;
import org.example.helpers.DriverManager;
import org.example.listeners.TestListener;
import org.example.utils.LogUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Listeners({AllureTestNg.class, TestListener.class})
public class BaseTest {

    protected WebDriverWait wait;

    @Parameters({"browser"})
    @BeforeMethod
    public void createDriver(@Optional("chrome") String browserName) {
        //System.out.println(">>> Start: Initializing Browser: " + browserName);
        LogUtils.info(">>> Start: Initializing Browser: " + browserName);
        // 1. Create temp variable (Local variable) to receive Driver
        WebDriver tmpDriver;

        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<String, Object>();
            //Turn of Save password buddle
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);

            //Turn off Password Leak Detection
            prefs.put("profile.password_manager_leak_detection", false);

            options.setExperimentalOption("prefs", prefs);

            //Turn off "Chrome is being controlled by automated test software"
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

            //options.addArguments("--incognito");
            
            options.addArguments("--headless=new"); // Chạy không giao diện
            options.addArguments("--window-size=1920,1080"); // Giả lập màn hình to

            tmpDriver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("firefox")) {
            tmpDriver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            tmpDriver = new EdgeDriver();
        } else {
            throw new RuntimeException("Trinh duyet khong ho tro " + browserName);
        }

        // 2. Giao Driver tạm đó cho DriverManager quản lý
        DriverManager.setDriver(tmpDriver);

        // 3. Các thiết lập (Lúc này gọi thông qua DriverManager)
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        DriverManager.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        // 4. XỬ LÝ WAIT
        // Khởi tạo wait bằng driver lấy từ ThreadLocal ra
        wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
    }

    @AfterMethod
    public void closeDriver() {
        // Gọi hàm quit từ Manager để đóng và dọn dẹp
        LogUtils.info("<<< End: Closing Browser");
        DriverManager.quit();
    }
}