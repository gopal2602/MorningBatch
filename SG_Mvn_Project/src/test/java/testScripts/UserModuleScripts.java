package testScripts;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import driver.DriverScript;

public class UserModuleScripts extends DriverScript{
	
	/**********************************************
	 * Script name 	: TS_Login_Logout
	 *
	 ***********************************************/
	public boolean TS_Login_Logout()
	{
		WebDriver oBrowser = null;
		String strStatus = null;
		Map<String, String> objData = null;
		try {
			test = extent.startTest("TS_Login_Logout");
			
			objData = datatable.getTestData("TestData", "Script1", test);
			
			oBrowser = appInd.launchBrowser(objData.get("BrowserName"), test);
			strStatus+= appDep.navigateURL(oBrowser, objData.get("URL"), test);
			strStatus+= appDep.loginToApp(oBrowser, objData.get("UN"), objData.get("PWD"), test);
			strStatus+= appDep.logoutFromApp(oBrowser, test);
			strStatus+= appInd.closeBrowser(oBrowser, test);
			
			if(strStatus.contains("false")) {
				reports.writeResult(oBrowser, "Fail", "The test script TS_Login_Logout() was failed", test);
				return false;
			}else {
				reports.writeResult(oBrowser, "Pass", "The test script TS_Login_Logout() was Passed", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in TS_Login_Logout() script. "+ e, test);
			return false;
		}
		finally {
			reports.endExtentReport(test);
		}
	}
	
	
	
	
	/**********************************************
	 * Script name 	: TS_Login_Create_DeleteUser
	 *
	 ***********************************************/
	public boolean TS_Login_Create_DeleteUser()
	{
		WebDriver oBrowser = null;
		String strStatus = null;
		Map<String, String> objData = null;
		try {
			test = extent.startTest("TS_Login_Create_DeleteUser");
			objData = datatable.getTestData("TestData", "Script2", test);
			oBrowser = appInd.launchBrowser(objData.get("BrowserName"), test);
			strStatus+= appDep.navigateURL(oBrowser, objData.get("URL"), test);
			strStatus+= appDep.loginToApp(oBrowser, objData.get("UN"), objData.get("PWD"), test);
			
			String userName = userMethods.createUser(oBrowser, objData, test);
			
			strStatus+= userMethods.deleteUser(oBrowser, userName, test);
			strStatus+= appDep.logoutFromApp(oBrowser, test);
			strStatus+= appInd.closeBrowser(oBrowser, test);
			
			if(strStatus.contains("false")) {
				reports.writeResult(oBrowser, "Fail", "The test script TS_Login_Create_DeleteUser() was failed", test);
				return false;
			}else {
				reports.writeResult(oBrowser, "Pass", "The test script TS_Login_Create_DeleteUser() was Passed", test);
				return true;
			}
			
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in TS_Login_Create_DeleteUser() script. "+ e, test);
			return false;
		}
		finally {
			reports.endExtentReport(test);
		}
	}
}
