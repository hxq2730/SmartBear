package org.example.tests;

import org.example.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LearnAssertion extends BaseTest {
    @Test
    public void testHardAssert() {
        System.out.println(">>>Bat dau Hard Assert<<<");
        String actualTitle = "Google.com";
        String expectedTitle = "Google";
        //1. Hard Assertion: Neu Sai => Dung ngay lap tuc
        Assert.assertEquals(actualTitle, expectedTitle, "Tieu de khong khop!");
        System.out.println("Dòng này có được chạy không?");
    }

    @Test
    public void testSoftAssert() {
        System.out.println(">>>Bat dau Soft Assert<<<");
        String actualTitle = "Google.com";
        String expectedTitle = "Google";

        SoftAssert soft = new SoftAssert();
        //check 1: sai, nhung van chay tiep
        System.out.println("Check 1: Kiểm tra tiêu đề");
        soft.assertEquals(actualTitle, expectedTitle, "Tieu de trang web khong khop");
        //check 2: dung, kiem tra url
        System.out.println("Check 2: Kiểm tra URL");
        soft.assertTrue(true, "Loi 2: URL sai");
        //check 3: sai
        System.out.println("Check 3: Kiểm tra Icon");
        soft.assertEquals(1, 2, "Lỗi 3: Sai Icon");
        soft.assertAll();
    }
}
