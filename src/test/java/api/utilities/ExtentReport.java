package api.utilities;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport implements ITestListener {
    
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extentReports;
    public ExtentTest extentTest;

    String repName;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Tets-Report-" + timeStamp + ".html";

        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/Reports/" + repName);
        sparkReporter.config().setDocumentTitle("Rest Assured Automation Project");
        sparkReporter.config().setReportName("Pet Store Users APIs");
        sparkReporter.config().setTheme(Theme.DARK);

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Applcation name", "Pet Store Users APIs");
        extentReports.setSystemInfo("User name", System.getProperty("user.name"));
        extentReports.setSystemInfo("Operation System", System.getProperty("os.name"));
        extentReports.setSystemInfo("Environment", "QA");
        extentReports.setSystemInfo("User", "Sanjiv");
    }

    public void onTestSuccess(ITestResult result) {
        extentTest = extentReports.createTest(result.getName());
        extentTest.assignCategory(result.getMethod().getGroups());
        extentTest.createNode(result.getName());
        //extentTest.log(Status.PASS, "Test Passed");
        extentTest.log(Status.PASS,MarkupHelper.createLabel("Test Passed:- "+result.getName(),ExtentColor.GREEN));
    }

    public void onTestFailure(ITestResult result) {
        extentTest = extentReports.createTest(result.getName());
        extentTest.createNode(result.getName());
        extentTest.assignCategory(result.getMethod().getGroups());
        //extentTest.log(Status.FAIL, "Test Failed");
        extentTest.log(Status.FAIL, result.getThrowable().getMessage());
        extentTest.log(Status.PASS,MarkupHelper.createLabel("Test Failed:- "+result.getName(),ExtentColor.RED));
    }

    public void onTestSkipped(ITestResult result) {
        extentTest = extentReports.createTest(result.getName());
        extentTest.createNode(result.getName());
        extentTest.assignCategory(result.getMethod().getGroups());
        //extentTest.log(Status.SKIP, "Test Skipped");
        extentTest.log(Status.SKIP, result.getThrowable().getMessage());
        extentTest.log(Status.PASS,MarkupHelper.createLabel("Test Skipped:- "+result.getName(),ExtentColor.PURPLE));
    }

    public void onFinish(ITestContext testContext) {
        extentReports.flush();
    }
}