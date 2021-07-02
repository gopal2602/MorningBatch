package methods;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import driver.DriverScript;
import locators.ObjectLocators;

public class UserModuleMethods extends DriverScript implements ObjectLocators{
	/******************************************
	 * Method Name		: createUser()
	 * 
	 *******************************************/
	public String createUser(WebDriver oDriver, Map<String, String> data, ExtentTest test)
	{
		String strStatus = null;
		String userName = null;
		try {
			strStatus+= appInd.clickObject(oDriver, objUserMenu, test);
			strStatus+= appInd.clickObject(oDriver, objAddUserBtn, test);
			Thread.sleep(2000);
			
			if(appInd.verifyElementExist(oDriver, objAddUserWindow, test)) {
				reports.writeResult(oDriver, "Pass", "The Add User page has opened successful", test);
				strStatus+= appInd.setObject(oDriver, objFirstNameEdit, data.get("FN"), test);
				strStatus+= appInd.setObject(oDriver, objLastNameEdit, data.get("LN"), test);
				strStatus+= appInd.setObject(oDriver, objEmailEdit, data.get("Email"), test);
				strStatus+= appInd.setObject(oDriver, objUser_UNEdit, data.get("User_UN"), test);
				strStatus+= appInd.setObject(oDriver, objUser_PwdEdit, data.get("User_PWD"), test);
				strStatus+= appInd.setObject(oDriver, objUser_RetypePWdEdit, data.get("User_RetypePWD"), test);
				strStatus+= appInd.clickObject(oDriver, objCreateUserBtn, test);
				Thread.sleep(2000);
				
				userName = data.get("LN")+", "+data.get("FN");
				
				strStatus+= appInd.verifyElementExist(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+userName+"'"+"]"), test);
				
				if(strStatus.contains("false")){
					reports.writeResult(oDriver, "Fail", "Failed to create the user in the actiTime", test);
					return null;
					
				}else {
					reports.writeResult(oDriver, "screenshot", "User created Successful", test);
					reports.writeResult(oDriver, "Pass", "The user is created successful", test);
					return userName;
				}
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to open the Add User page.", test);
				return null;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in createUser() method. "+ e, test);
			return null;
		}
	}
	
	
	
	
	/******************************************
	 * Method Name		: deleteUser()
	 * 
	 *******************************************/
	public boolean deleteUser(WebDriver oDriver, String userName, ExtentTest test)
	{
		String strStatus = null;
		try {
			strStatus+= appInd.clickObject(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+userName+"'"+"]"), test);
			Thread.sleep(2000);
			
			strStatus+= appInd.clickObject(oDriver, objDeleteUserBtn, test);
			Thread.sleep(2000);
			
			oDriver.switchTo().alert().accept();
			
			Thread.sleep(2000);
			
			strStatus+= appInd.verifyElementNotExist(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+userName+"'"+"]"), test);
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "Failed to delete the user from the actiTime", test);
				return false;
			}else {
				reports.writeResult(oDriver, "screenshot", "user deleted Successful", test);
				reports.writeResult(oDriver, "Pass", "The user deleted from the actiTime successful", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in deleteUser() method. "+ e, test);
			return false;
		}
	}
}
