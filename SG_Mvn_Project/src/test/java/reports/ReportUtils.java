package reports;

import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import driver.DriverScript;

public class ReportUtils extends DriverScript{
	/********************************************************
	 * Method Name		: startExtentReport()
	 * Purpose			:
	 * 
	 * 
	 ********************************************************/
	public ExtentReports startExtentReport(String fileName, String buildName)
	{
		String resultPath = null;
		File objResFilePath = null;
		File objScreenshotPath = null;
		File objArchive = null;
		File objExistingReport = null;
		try {
			resultPath = System.getProperty("user.dir") + "\\Results\\" + buildName;
			
			objResFilePath = new File(resultPath);
			if(!objResFilePath.exists()) {
				objResFilePath.mkdirs();
			}
			
			screnshotLocation = resultPath + "\\screenshot";
			objScreenshotPath = new File(screnshotLocation);
			if(!objScreenshotPath.exists()) {
				objScreenshotPath.mkdirs();
			}
			
			objArchive = new File(resultPath + "\\Archive");
			if(!objArchive.exists()) {
				objArchive.mkdirs();
			}
			
			objExistingReport = new File(resultPath + "\\" + fileName + ".html");
			if(objExistingReport.exists()) {
				objExistingReport.renameTo(new File(objArchive+"\\"+ buildName+"_"+fileName+"_"+appInd.getDateTime("ddMMyyyyhhmmss")+".html"));
			}
			
			extent = new ExtentReports(resultPath + "\\" + fileName+".html", false);
			extent.addSystemInfo("Host Name", System.getProperty("os.name"));
			extent.addSystemInfo("Environment", appInd.readPropFile("config", "Environment"));
			extent.addSystemInfo("User Name", System.getProperty("user.name"));
			extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
			return extent;
		}catch(Exception e)
		{
			System.out.println("Exception in startExtentReport() method. " + e);
			return null;
		}
		finally {
			resultPath = null;
			objResFilePath = null;
			objScreenshotPath = null;
		}
	}
	
	
	
	/********************************************************
	 * Method Name		: endExtentReport()
	 * Purpose			:
	 * 
	 * 
	 ********************************************************/
	public void endExtentReport(ExtentTest test)
	{
		try {
			extent.endTest(test);
			extent.flush();
		}catch(Exception e)
		{
			System.out.println("Exception in endExtentReport() method. " + e);
		}
	}
	
	
	
	
	/********************************************************
	 * Method Name		: captureScreenshot()
	 * Purpose			:
	 * 
	 * 
	 ********************************************************/
	public String captureScreenshot(WebDriver oBrowser)
	{
		File objSource = null;
		String strDestination = null;
		File objDestination = null;
		try {
			strDestination = screnshotLocation +"\\" + "ScreenShot_" 
					+ appInd.getDateTime("ddMMYYYY_hhmmss")+".png";
			TakesScreenshot ts = (TakesScreenshot) oBrowser;
			objSource = ts.getScreenshotAs(OutputType.FILE);
			objDestination = new File(strDestination);
			FileHandler.copy(objSource, objDestination);
			return strDestination;
		}catch(Exception e)
		{
			System.out.println("Exception in captureScreenshot() method. " + e);
			return null;
		}
		finally
		{
			objSource = null;
			objDestination = null;
		}
	}
	
	
	
	/********************************************************
	 * Method Name		: writeResult()
	 * Purpose			:
	 * 
	 * 
	 ********************************************************/
	public void writeResult(WebDriver oBrowser, String status, String strDescription, ExtentTest test)
	{
		try {
			switch(status.toLowerCase())
			{
				case "pass":
					test.log(LogStatus.PASS, strDescription);
					break;
				case "fail":
					test.log(LogStatus.FAIL, strDescription + " : " 
							+ test.addScreenCapture(reports.captureScreenshot(oBrowser)));
					break;
				case "warning":
					test.log(LogStatus.WARNING, strDescription);
					break;
				case "info":
					test.log(LogStatus.INFO, strDescription);
					break;
				case "exception":
					test.log(LogStatus.FATAL, strDescription + " : " 
							+ test.addScreenCapture(reports.captureScreenshot(oBrowser)));
					break;
				case "screenshot":
					test.log(LogStatus.PASS, strDescription + " : " 
							+ test.addScreenCapture(reports.captureScreenshot(oBrowser)));
					break;
				default:
					System.out.println("Invalid status '"+status+"' for the result. please provide the appropriate status");
					
			}
		}catch(Exception e)
		{
			System.out.println("Exception in writeResult() method. " + e);
		}
	}
}
