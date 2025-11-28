package org.example.helpers;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    // 1. Khởi tạo biến ThreadLocal, Nó giống như một cái tủ có nhiều ngăn, mỗi ngăn chứa 1 WebDriver riêng biệt
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // 2. Hàm setDriver: Cất Driver vào ngăn tủ của luồng hiện tại
    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    // 3. Hàm getDriver: Lấy Driver từ ngăn tủ của luồng hiện tại ra dùng
    public static WebDriver getDriver() {
        return driver.get();
    }

    // 4. Hàm unload: Xóa Driver khỏi ngăn tủ (Quan trọng để tránh tràn bộ nhớ)
    public static void quit() {
        if (driver.get() != null) {
            driver.get().quit(); // Tắt trình duyệt
            driver.remove();     // Xóa khỏi ThreadLocal
        }
    }
}