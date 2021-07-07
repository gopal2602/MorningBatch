package methods;

import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import driver.DriverScript;
import locators.ObjectLocators;

public class AppDependentMethods extends DriverScript implements ObjectLocators{
	
	/******************************************
	 * Method Name		: navigateURL()
	 * 
	 *******************************************/
	public boolean navigateURL(WebDriver oDriver, String URL, ExtentTest test)
	{
		try {
			oDriver.navigate().to(URL);
			appInd.waitForElement(oDriver, objUserNameEdit, "Visible", "", 10);
			if(appInd.compareValues(oDriver, oDriver.getTitle(), "actiTIME - Login", test))
			{
				return true;
			}else {
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in navigateURL() method. "+ e, test);
			return false;
		}
	}
	
	
	
	/******************************************
	 * Method Name		: loginToApp()
	 * 
	 *******************************************/
	public boolean loginToApp(WebDriver oDriver, String userName, String passWord, ExtentTest test)
	{
		String strStatus = null;
		try {
			strStatus+= appInd.setObject(oDriver, objUserNameEdit, userName, test);
			strStatus+= appInd.setObject(oDriver, objPasswordEdit, passWord, test);
			strStatus+= appInd.clickObject(oDriver, objLoginBtn, test);
			
			appInd.waitForElement(oDriver, objHomePageTitle, "Text", "Enter Time-Track", 10);
			
			strStatus+= appInd.verifyText(oDriver, objHomePageTitle, "Text", "Enter Time-Track", test);
			
			if(appInd.verifyOptionalElementExist(oDriver, objShortcutWindow, test)) {
				strStatus+= appInd.clickObject(oDriver, objShotcutCloseBtn, test);
			}
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "The login to actiTime was failed", test);
				return false;
			}else {
				reports.writeResult(oDriver, "screenshot", "Login Successful", test);
				reports.writeResult(oDriver, "Pass", "The login to actiTime was successful", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in loginToApp() method. "+ e, test);
			return false;
		}
	}
	
	
	
	
	/******************************************
	 * Method Name		: logoutFromApp()
	 * 
	 *******************************************/
	public boolean logoutFromApp(WebDriver oDriver, ExtentTest test)
	{
		String strStatus = null;
		try {
			appInd.waitForElement(oDriver, objLogoutBtn, "Clickable", "", 20);
			strStatus+= appInd.jsClickObject(oDriver, objLogoutBtn, test);
			appInd.waitForElement(oDriver, objLoginLogoImg, "Clickable", "", 20);
			strStatus+= appInd.verifyText(oDriver, objLoginHeader, "Text", "Please identify yourself", test);
			
			if(strStatus.contains("false"))
			{
				reports.writeResult(oDriver, "Fail", "Failed to logout form the actiTime", test);
				return false;
			}else {
				reports.writeResult(oDriver, "screenshot", "Logout was Successful", test);
				reports.writeResult(oDriver,"Pass", "The logout from the actiTime was succesful", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver,"Exception", "Exception in logoutFromApp() method. "+ e, test);
			return false;
		}
	}
}
