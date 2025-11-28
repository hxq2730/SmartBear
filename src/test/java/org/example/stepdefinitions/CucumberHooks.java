package org.example.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.example.helpers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class CucumberHooks {

    @Before
    public void beforeScenario() {
        // Code khởi tạo Driver (Giống hệt BaseTest)
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Đẩy vào DriverManager để dùng chung
        DriverManager.setDriver(driver);
    }

    @After
    public void afterScenario() {
        // Đóng trình duyệt
        DriverManager.quit();
    }
}