package org.example.tests;

import org.example.base.BaseTest_No_ThreadLocal;
import org.testng.annotations.Test;

public class GroupExampleTest extends BaseTest_No_ThreadLocal {
    @Test(groups = {"smoke", "regression"})
    public void testLogin() {
        System.out.println("--- Test Login (Chạy trong cả Smoke & Regression) ---");
    }

    @Test(groups = {"regression"})
    public void testUserHistory() {
        System.out.println("--- Test Lịch sử người dùng (Chỉ Regression) ---");
    }

    // Bài này là chức năng Admin, gom vào nhóm "admin"
    @Test(groups = {"admin", "regression"})
    public void testAdminPage() {
        System.out.println("--- Test Trang Admin (Admin & Regression) ---");
    }

    // Bài này đang bị lỗi, gắn tag "broken" để né nó ra
    @Test(groups = {"broken"})
    public void testBuggyFeature() {
        System.out.println("--- Test chức năng lỗi (Không nên chạy) ---");
    }
}
