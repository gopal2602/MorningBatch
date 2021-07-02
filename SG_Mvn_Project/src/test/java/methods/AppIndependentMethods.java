package methods;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import com.relevantcodes.extentreports.ExtentTest;
import driver.DriverScript;

public class AppIndependentMethods extends DriverScript{
	/********************************************************
	 * Method Name		: readPropFile()
	 * Purpose			:
	 * 
	 * 
	 ********************************************************/
	public String readPropFile(String fileName, String strKey)
	{
		FileInputStream fin = null;
		Properties prop = null;
		try {
			fin = new FileInputStream(System.getProperty("user.dir")+"\\Configuration\\" + fileName + ".properties");
			prop = new Properties();
			
			prop.load(fin);
			
			return prop.getProperty(strKey);
		}catch(Exception e)
		{
			System.out.println("Exception in readPropFile() method. " + e);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
			}catch(Exception e)
			{
				System.out.println("Exception in readPropFile() method. " + e);
				return null;
			}
		}
	}
	
	
	
	
	/********************************************************
	 * Method Name		: getDateTime()
	 * Purpose			:
	 * 
	 * 
	 ********************************************************/
	public String getDateTime(String dtFormat)
	{
		Date dt = null;
		SimpleDateFormat sdf = null;
		try {
			dt = new Date();
			sdf = new SimpleDateFormat(dtFormat);
			return sdf.format(dt);
		}catch(Exception e)
		{
			System.out.println("Exception in getDateTime() method. " + e);
			return null;
		}
		finally {
			dt = null;
			sdf = null;
		}
	}
	
	
	
	/***********************************************
	 * Method Name		: clickObject()
	 *
	 ************************************************/
	public boolean clickObject(WebDriver oDriver, By objBy, ExtentTest test)
	{
		List<WebElement> objEle = null;
		try {
			objEle = oDriver.findElements(objBy);
			if(objEle.size() > 0) {
				reports.writeResult(oDriver, "Pass", "The element '"+String.valueOf(objBy)+"' was clicked successful", test);
				objEle.get(0).click();
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to find the element '"+String.valueOf(objBy)+"'", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in clickObject() method. "+ e, test);
			return false;
		}
		finally {
			objEle = null;
		}
	}
	
	
	
	/***********************************************
	 * Method Name		: clickObject()
	 *
	 ************************************************/
	public boolean clickObject(WebDriver oDriver, String strObjectName, ExtentTest test)
	{
		List<WebElement> objEle = null;
		try {
			objEle = oDriver.findElements(By.xpath(strObjectName));
			if(objEle.size() > 0) {
				reports.writeResult(oDriver, "Pass", "The element '"+strObjectName+"' was clicked successful", test);
				objEle.get(0).click();
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to find the element '"+strObjectName+"'", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in clickObject() method. "+ e, test);
			return false;
		}
		finally {
			objEle = null;
		}
	}
	
	
	
	/***********************************************
	 * Method Name		: setObject()
	 *
	 ************************************************/
	public boolean setObject(WebDriver oDriver, By objBy, String strValue, ExtentTest test)
	{
		List<WebElement> objEle = null;
		try {
			objEle = oDriver.findElements(objBy);
			if(objEle.size() > 0) {
				reports.writeResult(oDriver,"Pass", "The data '"+strValue+"' was set in the element '"+String.valueOf(objBy)+"' successful", test);
				objEle.get(0).sendKeys(strValue);
				return true;
			}else {
				reports.writeResult(oDriver,"Fail", "Failed to find the element '"+String.valueOf(objBy)+"'", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver,"Exception", "Exception in setObject() method. "+ e, test);
			return false;
		}
		finally {
			objEle = null;
		}
	}
	
	
	
	/***********************************************
	 * Method Name		: setObject()
	 *
	 ************************************************/
	public boolean setObject(WebDriver oDriver, String strObjectName, String strValue, ExtentTest test)
	{
		List<WebElement> objEle = null;
		try {
			objEle = oDriver.findElements(By.xpath(strObjectName));
			if(objEle.size() > 0) {
				reports.writeResult(oDriver,"Pass", "The data '"+strValue+"' was set in the element '"+strObjectName+"' successful", test);
				objEle.get(0).sendKeys(strValue);
				return true;
			}else {
				reports.writeResult(oDriver,"Fail", "Failed to find the element '"+strObjectName+"'", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver,"Exception", "Exception in setObject() method. "+ e, test);
			return false;
		}
		finally {
			objEle = null;
		}
	}
	
	
	
	/***********************************************
	 * Method Name		: clearAndSetObject()
	 *
	 ************************************************/
	public boolean clearAndSetObject(WebDriver oDriver, By objBy, String strValue, ExtentTest test)
	{
		List<WebElement> objEle = null;
		try {
			objEle = oDriver.findElements(objBy);
			if(objEle.size() > 0) {
				reports.writeResult(oDriver,"Pass", "Cleared & data '"+strValue+"' was set in the element '"+String.valueOf(objBy)+"' successful", test);
				objEle.get(0).clear();
				objEle.get(0).sendKeys(strValue);
				return true;
			}else {
				reports.writeResult(oDriver,"Fail", "Failed to find the element '"+String.valueOf(objBy)+"'", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver,"Exception", "Exception in clearAndSetObject() method. "+ e, test);
			return false;
		}
		finally {
			objEle = null;
		}
	}
	
	
	
	/***********************************************
	 * Method Name		: clearAndSetObject()
	 *
	 ************************************************/
	public boolean clearAndSetObject(WebDriver oDriver, String strObjectName, String strValue, ExtentTest test)
	{
		List<WebElement> objEle = null;
		try {
			objEle = oDriver.findElements(By.xpath(strObjectName));
			if(objEle.size() > 0) {
				reports.writeResult(oDriver,"Pass", "Cleared & data '"+strValue+"' was set in the element '"+strObjectName+"' successful", test);
				objEle.get(0).clear();
				objEle.get(0).sendKeys(strValue);
				return true;
			}else {
				reports.writeResult(oDriver,"Fail", "Failed to find the element '"+strObjectName+"'", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver,"Exception", "Exception in clearAndSetObject() method. "+ e, test);
			return false;
		}
		finally {
			objEle = null;
		}
	}
	
	
	
	/***********************************************
	 * Method Name		: compareValues()
	 *
	 ************************************************/
	public boolean compareValues(WebDriver oDriver, String actual, String expected, ExtentTest test)
	{
		try {
			if(actual.equalsIgnoreCase(expected)) {
				reports.writeResult(oDriver,"Pass", "The actual '"+actual+"' & the expected '"+expected+"' are matching", test);
				return true;
			}else {
				reports.writeResult(oDriver,"Fail", "Mis-match in both the actual '"+actual+"' & the expected '"+expected+"' values", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver,"Exception", "Exception in compareValues() method. "+ e, test);
			return false;
		}
	}
	
	
	
	/***********************************************
	 * Method Name		: verifyText()
	 *
	 ************************************************/
	public boolean verifyText(WebDriver oDriver, By objBy, String strObjectType, String expectedText, ExtentTest test)
	{
		List<WebElement> objEle = null;
		Select oSel = null;
		String actual = null;
		try {
			objEle = oDriver.findElements(objBy);
			
			if(objEle.size() > 0) {
				switch(strObjectType.toLowerCase()) {
					case "text":
						actual = objEle.get(0).getText();
						break;
					case "value":
						actual = objEle.get(0).getAttribute("value");
						break;
					case "dropdown":
						oSel = new Select(objEle.get(0));
						actual = oSel.getFirstSelectedOption().getText();
						break;
					default:
						reports.writeResult(oDriver,"Fail", "Invalid name for the objectType '"+strObjectType+"'", test);
						return false;
				}
				
				if(compareValues(oDriver, actual, expectedText, test)) {
					return true;
				}else {
					return false;
				}
			}else {
				reports.writeResult(oDriver,"Fail", "Failed to find the element '"+String.valueOf(objBy)+"'", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver,"Exception", "Exception in verifyText() method. "+ e, test);
			return false;
		}
		finally {
			oSel = null;
			objEle = null;
		}
	}
	
	
	
	
	/***********************************************
	 * Method Name		: verifyText()
	 *
	 ************************************************/
	public boolean verifyText(WebDriver oDriver, String strObjectName, String strObjectType, String expectedText, ExtentTest test)
	{
		List<WebElement> objEle = null;
		Select oSel = null;
		String actual = null;
		try {
			objEle = oDriver.findElements(By.xpath(strObjectName));
			
			if(objEle.size() > 0) {
				switch(strObjectType.toLowerCase()) {
					case "text":
						actual = objEle.get(0).getText();
						break;
					case "value":
						actual = objEle.get(0).getAttribute("value");
						break;
					case "dropdown":
						oSel = new Select(objEle.get(0));
						actual = oSel.getFirstSelectedOption().getText();
						break;
					default:
						reports.writeResult(oDriver,"Fail", "Invalid name for the objectType '"+strObjectType+"'", test);
						return false;
				}
				
				if(compareValues(oDriver, actual, expectedText, test)) {
					return true;
				}else {
					return false;
				}
			}else {
				reports.writeResult(oDriver,"Fail", "Failed to find the element '"+strObjectName+"'", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver,"Exception", "Exception in verifyText() method. "+ e, test);
			return false;
		}
		finally {
			oSel = null;
			objEle = null;
		}
	}
	
	
	
	/***********************************************
	 * Method Name		: verifyElementExist()
	 *
	 ************************************************/
	public boolean verifyElementExist(WebDriver oDriver, By objBy, ExtentTest test)
	{
		List<WebElement> objEle = null;
		try {
			objEle = oDriver.findElements(objBy);
			
			if(objEle.size() > 0) {
				reports.writeResult(oDriver,"Pass", "The element '"+String.valueOf(objBy)+"' exist in the DOM", test);
				return true;
			}else {
				reports.writeResult(oDriver,"Fail", "Failed to find the element '"+String.valueOf(objBy)+"' in the DOM", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver,"Exception", "Exception in verifyElementExist() method. "+ e, test);
			return false;
		}
		finally {
			objEle = null;
		}
	}
	
	
	
	/***********************************************
	 * Method Name		: verifyElementExist()
	 *
	 ************************************************/
	public boolean verifyElementExist(WebDriver oDriver, String strObjectName, ExtentTest test)
	{
		List<WebElement> objEle = null;
		try {
			objEle = oDriver.findElements(By.xpath(strObjectName));
			
			if(objEle.size() > 0) {
				reports.writeResult(oDriver,"Pass", "The element '"+strObjectName+"' exist in the DOM", test);
				return true;
			}else {
				reports.writeResult(oDriver,"Fail", "Failed to find the element '"+strObjectName+"' in the DOM", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver,"Exception", "Exception in verifyElementExist() method. "+ e, test);
			return false;
		}
		finally {
			objEle = null;
		}
	}
	
	
	
	/***********************************************
	 * Method Name		: verifyElementNotExist()
	 *
	 ************************************************/
	public boolean verifyElementNotExist(WebDriver oDriver, By objBy, ExtentTest test)
	{
		List<WebElement> objEle = null;
		try {
			objEle = oDriver.findElements(objBy);
			
			if(objEle.size() > 0) {
				reports.writeResult(oDriver,"Fail", "The element '"+String.valueOf(objBy)+"' exist in the DOM", test);
				return false;
			}else {
				reports.writeResult(oDriver,"Pass", "The element '"+String.valueOf(objBy)+"' was removed form the DOM", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver,"Exception", "Exception in verifyElementNotExist() method. "+ e, test);
			return false;
		}
		finally {
			objEle = null;
		}
	}
	
	
	
	/***********************************************
	 * Method Name		: verifyElementNotExist()
	 *
	 ************************************************/
	public boolean verifyElementNotExist(WebDriver oDriver, String strObjectName, ExtentTest test)
	{
		List<WebElement> objEle = null;
		try {
			objEle = oDriver.findElements(By.xpath(strObjectName));
			
			if(objEle.size() > 0) {
				reports.writeResult(oDriver,"Fail", "The element '"+strObjectName+"' exist in the DOM", test);
				return false;
			}else {
				reports.writeResult(oDriver,"Pass", "The element '"+strObjectName+"' was removed form the DOM", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver,"Exception", "Exception in verifyElementNotExist() method. "+ e, test);
			return false;
		}
		finally {
			objEle = null;
		}
	}
	
	
	
	/***********************************************
	 * Method Name		: verifyOptionalElementExist()
	 *
	 ************************************************/
	public boolean verifyOptionalElementExist(WebDriver oDriver, By objBy, ExtentTest test)
	{
		List<WebElement> objEle = null;
		try {
			objEle = oDriver.findElements(objBy);
			
			if(objEle.size() > 0) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver,"Exception", "Exception in verifyOptionalElementExist() method. "+ e, test);
			return false;
		}
		finally {
			objEle = null;
		}
	}
	
	
	
	/***********************************************
	 * Method Name		: verifyOptionalElementExist()
	 *
	 ************************************************/
	public boolean verifyOptionalElementExist(WebDriver oDriver, String strObjectName, ExtentTest test)
	{
		List<WebElement> objEle = null;
		try {
			objEle = oDriver.findElements(By.xpath(strObjectName));
			
			if(objEle.size() > 0) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver,"Exception", "Exception in verifyOptionalElementExist() method. "+ e, test);
			return false;
		}
		finally {
			objEle = null;
		}
	}
	
	
	/***********************************************
	 * Method Name		: launchBrowser()
	 *
	 ************************************************/
	public WebDriver launchBrowser(String browserType, ExtentTest test)
	{
		WebDriver oDriver = null;
		try {
			switch(browserType.toLowerCase()) {
				case "chrome":
					System.setProperty("webdriver.chrome.driver", ".\\Library\\drivers\\chromedriver.exe");
					oDriver = new ChromeDriver();
					break;
				case "firefox":
					System.setProperty("webdriver.gecko.driver", ".\\Library\\drivers\\geckodriver.exe");
					oDriver = new FirefoxDriver();
					break;
				case "ie":
					System.setProperty("webdriver.ie.driver", ".\\Library\\drivers\\ieDriverServer.exe");
					oDriver = new InternetExplorerDriver();
					break;
				default:
					reports.writeResult(oDriver,"Fail", "Invalid browser type '"+browserType+"'", test);	
			}
			
			if(oDriver!=null) {
				reports.writeResult(oDriver,"Pass", "The '"+browserType+"' has launched successful", test);
				oDriver.manage().window().maximize();
				return oDriver;
			}else {
				reports.writeResult(oDriver,"Fail", "Failed to launch the '"+browserType+"'", test);
				return null;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver,"Exception", "Exception in launchBrowser() method. "+ e, test);
			return null;
		}
	}
	
	
	/******************************************
	 * Method Name		: closeBrowser()
	 * 
	 *******************************************/
	public boolean closeBrowser(WebDriver oDriver, ExtentTest test)
	{
		try {
			oDriver.close();
			oDriver = null;
			return true;
		}catch(Exception e)
		{
			reports.writeResult(oDriver,"Exception", "Exception in closeBrowser() method. "+ e, test);
			return false;
		}
	}
}
