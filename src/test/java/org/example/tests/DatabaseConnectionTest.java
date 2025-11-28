package org.example.tests;

import org.example.helpers.DatabaseHelpers;
import org.example.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DatabaseConnectionTest {

    @Test
    public void testGetPasswordFromDB() {
        System.out.println("--- Check connection to Database XAMPP ---");

        String username = "tomsmith";

        // Gọi hàm lấy pass từ DB
        String actualPass = DatabaseHelpers.getPasswordForUser(username);

        // Kiểm tra xem có lấy được đúng pass không
        Assert.assertEquals(actualPass, "SuperSecretPassword!", "Wrong password or can't connection!");

        LogUtils.info("✅ Connect successfully! Password: " + actualPass);
    }

    @Test
    public void testVerifyUserCreatedInDB() {
        // 1. Giả sử bạn vừa chạy Selenium đăng ký user "newuser01" trên web xong
        String newUser = "tomsmith"; // Dùng tạm user có sẵn để test

        // 2. Verify trong DB
        boolean isExist = DatabaseHelpers.isUserExist(newUser);

        // 3. Assert
        Assert.assertTrue(isExist, "Lỗi: User registered but not found in database!");
    }
}