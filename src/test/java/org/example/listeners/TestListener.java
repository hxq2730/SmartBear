package org.example.listeners;

import org.example.helpers.CaptureHelpers;
import org.example.utils.LogUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        //System.out.println("=== Bắt đầu Auto Test: " + result.getName() + " ===");
        LogUtils.info("=== Start Automation Test: " + result.getName() + " ===");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        //System.out.println("✅ Test Pass: " + result.getName());
        LogUtils.info("✅ Test Pass: " + result.getName());
    }

    // --- QUAN TRỌNG NHẤT: KHI TEST FAIL ---
    @Override
    public void onTestFailure(ITestResult result) {
        //System.out.println("❌ Test Fail: " + result.getName());
        LogUtils.error("❌ Test Fail: " + result.getName());

        // 1. Chụp ảnh lưu file
        CaptureHelpers.captureScreenshot(result.getName());

        // 2. Đính kèm ảnh vào Allure Report
        CaptureHelpers.attachScreenshotToAllure(result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        //System.out.println("⚠️ Test Skipped: " + result.getName());
        LogUtils.warn("⚠️ Test Skipped: " + result.getName());
    }

    // Các hàm khác của Interface (để trống cũng được)
    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }
}
