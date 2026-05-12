package com.krce.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.krce.base.BaseTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class TestListener implements ITestListener, ISuiteListener {

    private static final ExtentReports extent = ExtentReportManager.getInstance();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    private int passed  = 0;
    private int failed  = 0;
    private int skipped = 0;

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest t = extent.createTest(result.getMethod().getMethodName());
        test.set(t);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test Passed");
        passed++;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());
        failed++;
        try {
            BaseTest base = (BaseTest) result.getInstance();
            String path = ScreenshotUtils.takeScreenshot(base.driver, result.getMethod().getMethodName());
            if (path != null) {
                test.get().addScreenCaptureFromPath(path);
            }
        } catch (Exception e) {
            System.out.println("Could not attach screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test Skipped");
        skipped++;
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public void onStart(ISuite suite) {
        passed  = 0;
        failed  = 0;
        skipped = 0;
    }

    @Override
    public void onFinish(ISuite suite) {
        int total = passed + failed + skipped;
        System.out.printf("Suite finished — Total: %d | Passed: %d | Failed: %d | Skipped: %d%n",
                total, passed, failed, skipped);

        if (!"true".equals(System.getenv("CI"))) {
            System.out.println("Local run — email skipped.");
            return;
        }

        try {
            EmailSender.send(passed, failed, skipped, total, suite.getName());
        } catch (Exception e) {
            System.err.println("Email send failed: " + e.getMessage());
        }
    }
}