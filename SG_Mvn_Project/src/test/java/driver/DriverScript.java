package driver;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import methods.AppDependentMethods;
import methods.AppIndependentMethods;
import methods.Datatable;
import methods.TaskModuleMethods;
import methods.UserModuleMethods;
import reports.ReportUtils;

public class DriverScript {
	public static AppIndependentMethods appInd = null;
	public static AppDependentMethods appDep = null;
	public static Datatable datatable = null;
	public static UserModuleMethods userMethods = null;
	public static TaskModuleMethods taskMethods = null;
	public static ReportUtils reports = null;
	public static ExtentReports extent = null;
	public static ExtentTest test = null;
	public static String screnshotLocation = null;
	public static String strModuleName = null;
	public static String controller = null;
	
	@BeforeSuite
	public void loasClasses()
	{
		try {
			appInd = new AppIndependentMethods();
			appDep = new AppDependentMethods();
			datatable = new Datatable();
			userMethods = new UserModuleMethods();
			taskMethods = new TaskModuleMethods();
			reports = new ReportUtils();
			extent = reports.startExtentReport("TestReports", appInd.readPropFile("config", "BuildName"));
			controller = System.getProperty("user.dir") + "\\Controller\\ExecutionController.xlsx";
					
		}catch(Exception e)
		{
			System.out.println("Exception in loasClasses() method. " + e);
		}
	}
	
	
	@Test
	public void executeTests()
	{
		int mRows = 0;
		int tcRows = 0;
		String executeTest = null;
		Class cls = null;
		Object obj = null;
		Method objTest = null;
		int ModulesCount = 0;
		int testScriptCount = 0;
		try {
			mRows = datatable.getRowCount(controller, "Modules", test);
			for(int m=1; m<=mRows; m++)
			{
				executeTest = datatable.getCellData(controller, "Modules", "ExecuteModule", m);
				if(executeTest.equalsIgnoreCase("Yes"))
				{
					ModulesCount++;
					strModuleName = datatable.getCellData(controller, "Modules", "ModuleName", m);
					tcRows = datatable.getRowCount(controller, strModuleName, test);
					
					for(int tc=1; tc<=tcRows; tc++)
					{
						executeTest = datatable.getCellData(controller, strModuleName, "ExecuteTest", tc);
						if(executeTest.equalsIgnoreCase("Yes"))
						{
							testScriptCount++;
							String scriptName = datatable.getCellData(controller, strModuleName, "ScriptName", tc);
							String className = datatable.getCellData(controller, strModuleName, "ClassName", tc);
							cls = Class.forName(className);
							obj = cls.newInstance();
							objTest = obj.getClass().getMethod(scriptName);
							String status = String.valueOf(objTest.invoke(obj));
							
							if(status.equalsIgnoreCase("True")) {
								datatable.setCellData(controller, strModuleName, "Result", tc, "Pass");
							}else {
								datatable.setCellData(controller, strModuleName, "Result", tc, "Fail");
							}
						}
					}
				}
			}
			
			if(ModulesCount == 0) {
				System.out.println("No module is selected for execution. Please select atleast one module for execution under ExecutionController.xlsx");
			}else if(testScriptCount == 0) {
				System.out.println("No TestScript is selected for execution. Please select atleast one TestScript for execution under ExecutionController.xlsx");
			}
		}catch(Exception e)
		{
			System.out.println("Exception in executeTests() method. " + e);
		}
		finally {
			executeTest = null;
			cls = null;
			obj = null;
			objTest = null;
		}
	}
}
