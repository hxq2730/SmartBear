package org.example.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DependencyGroupTest {

    // ---------------- NHÓM LOGIN (NHÓM CHA) ----------------

    @Test(groups = {"login"})
    public void testConnectServer() {
        System.out.println("1. Kết nối Server: Thành công");
        Assert.assertTrue(true);
    }

    @Test(groups = {"login"})
    public void testLoginUser() {
        System.out.println("2. Đăng nhập User: Đang chạy...");
        // CỐ TÌNH LÀM FAIL Ở ĐÂY
        Assert.fail("Lỗi: Server bị sập, không đăng nhập được!");
    }

    // ---------------- NHÓM SHOPPING (NHÓM CON) ----------------

    // Bài này phụ thuộc vào nhóm "login".
    // Vì testLoginUser ở trên bị FAIL, bài này sẽ bị SKIP (Bỏ qua)
    @Test(dependsOnGroups = {"login"})
    public void testOrderProduct() {
        System.out.println("3. Mua hàng (Dòng này sẽ KHÔNG BAO GIỜ được in ra)");
    }

    // Bài này cũng phụ thuộc nhóm "login" -> Cũng bị SKIP luôn
    @Test(dependsOnGroups = {"login"})
    public void testViewHistory() {
        System.out.println("4. Xem lịch sử (Dòng này cũng bị SKIP)");
    }

    // Bài này KHÔNG phụ thuộc ai cả -> Vẫn chạy bình thường
    @Test
    public void testOtherFeature() {
        System.out.println("5. Tính năng khác: Vẫn chạy bình thường");
    }
}