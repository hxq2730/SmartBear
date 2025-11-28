package org.example.helpers;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureHelpers {

    public static void captureScreenshot(String screenshotName) {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver == null) {
                System.out.println("Driver is null, cannot take screenshot.");
                return;
            }

            // 1. Tạo tên ảnh
            String dateName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = screenshotName + "_" + dateName + ".png";

            // 2. Kiểm tra và tạo thư mục screenshots nếu chưa tồn tại
            File screenshotDir = new File("./screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs(); // Tạo thư mục
            }

            // 3. Thực hiện chụp ảnh
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("./screenshots/" + fileName);
            FileHandler.copy(source, destination);

            System.out.println(" -> Đã chụp ảnh màn hình: " + destination.getAbsolutePath());

        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }

    // Đính kèm ảnh vào Allure Report
    public static void attachScreenshotToAllure(String screenshotName) {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver != null) {
                // 1. Chụp ảnh dưới dạng Byte Array (thay vì File)
                byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                // 2. Gửi cho Allure
                Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshotBytes));
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đính kèm ảnh vào Allure: " + e.getMessage());
        }
    }
}