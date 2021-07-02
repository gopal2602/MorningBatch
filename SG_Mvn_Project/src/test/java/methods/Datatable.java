package methods;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.relevantcodes.extentreports.ExtentTest;
import driver.DriverScript;

public class Datatable extends DriverScript{
	
	/***********************************************
	 * Method Name		: getTestData()
	 *
	 ************************************************/
	public Map<String, String> getTestData(String sheetName, String logicalName, ExtentTest test)
	{
		FileInputStream fin = null;
		Map<String, String> objData = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row1 = null;
		Row row2 = null;
		Cell cell1 = null;
		Cell cell2 = null;
		String sKey = null;
		String sValue = null;
		int rowNum = 0;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		try {
			objData = new HashMap<String, String>();
			fin = new FileInputStream(System.getProperty("user.dir") + "\\TestData\\"+strModuleName+".xlsx");
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh == null) {
				reports.writeResult(null, "Fail", "The sheet '"+sheetName+"' doesnot exist.", test);
				return null;
			}
			
			//Verify the logical is present. If present you need to find its rowNum
			for(int r=0; r<sh.getPhysicalNumberOfRows(); r++)
			{
				row1 = sh.getRow(r);
				cell1 = row1.getCell(0);
				if(cell1.getStringCellValue().equalsIgnoreCase(logicalName)) {
					rowNum = r;
					break;
				}
			}
			
			
			if(rowNum > 0) {
				row1 = sh.getRow(0);
				row2 = sh.getRow(rowNum);
				
				for(int c=0; c<row1.getPhysicalNumberOfCells(); c++)
				{
					cell1 = row1.getCell(c);
					cell2 = row2.getCell(c);
					sKey = cell1.getStringCellValue();
					
					//format the cell value
					if(cell2==null || cell2.getCellType()==CellType.BLANK) {
						sValue = "";
					}
					else if(cell2.getCellType()==CellType.BOOLEAN) {
						sValue = String.valueOf(cell2.getBooleanCellValue());
					}
					else if(cell2.getCellType()==CellType.STRING) {
						sValue = cell2.getStringCellValue();
					}
					else if(cell2.getCellType()==CellType.NUMERIC) {
						if(DateUtil.isCellDateFormatted(cell2)) {
							double dt = cell2.getNumericCellValue();
							Calendar cal = Calendar.getInstance();
							cal.setTime(DateUtil.getJavaDate(dt));
							
							//If day is <10, then prefix with zero
							if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
								sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
							}else {
								sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
							}
							
							
							//If month is <10, then prefix with zero
							if((cal.get(Calendar.MONTH)+1) < 10) {
								sMonth = "0" + (cal.get(Calendar.MONTH)+1);
							}else {
								sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
							}
							
							sYear = String.valueOf(cal.get(Calendar.YEAR));
							
							sValue = sDay + "/" + sMonth + "/" + sYear;
						}else {
							sValue = String.valueOf(cell2.getNumericCellValue());
						}
					}
					objData.put(sKey, sValue);
				}
			}else {
				reports.writeResult(null, "Fail", "The given logical Name '"+logicalName+"' doesnot exist", test);
				return null;
			}
			
			return objData;
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exception in getTestData() method. "+ e, test);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell1 = null;
				cell2 = null;
				row1 = null;
				row2 = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				reports.writeResult(null, "Exception", "Exception in getTestData() method. "+ e, test);
				return null;
			}
		}
	}
	
	
	
	
	
	/**************************
	 * Method Name		: getRowCount
	 * 
	 ******************************/
	public int getRowCount(String filePath, String sheetName, ExtentTest test)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh == null) {
				reports.writeResult(null, "Fail", "Failed to find the sheet '"+sheetName+"'", test);
				return -1;
			}
			
			return sh.getPhysicalNumberOfRows() - 1;
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exceptoin in getRowCount() method. "+ e, test);
			return -1;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				reports.writeResult(null, "Exception", "Exceptoin in getRowCount() method. "+ e, test);
				return -1;
			}
		}
	}
	
	
	
	/**************************
	 * Method Name		: getCellData
	 * 
	 ******************************/
	public String getCellData(String filePath, String sheetName, String colName, int rowNum)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		int colNum = 0;
		String strData = null;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			if(sh == null) {
				reports.writeResult(null, "Fail", "Failed to find the sheet '"+sheetName+"'", test);
				return null;
			}
			
			//Find the column number based on the given column name
			row = sh.getRow(0);
			for(int c=0; c<row.getPhysicalNumberOfCells(); c++)
			{
				cell = row.getCell(c);
				if(cell.getStringCellValue().equalsIgnoreCase(colName)) {
					colNum = c;
					break;
				}
			}
			
			
			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);
			
			//format the cell data
			if(cell==null || cell.getCellType()==CellType.BLANK) {
				strData = "";
			}
			else if(cell.getCellType()==CellType.BOOLEAN) {
				strData = String.valueOf(cell.getBooleanCellValue());
			}
			else if(cell.getCellType()==CellType.STRING) {
				strData = cell.getStringCellValue();
			}
			else if(cell.getCellType()==CellType.NUMERIC) {
				if(DateUtil.isCellDateFormatted(cell)) {
					double dt = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(dt));
					
					//If day is <10, then prefix with zero
					if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
						sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
					}else {
						sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
					}
					
					
					//If month is <10, then prefix with zero
					if((cal.get(Calendar.MONTH)+1) < 10) {
						sMonth = "0" + (cal.get(Calendar.MONTH)+1);
					}else {
						sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
					}
					
					sYear = String.valueOf(cal.get(Calendar.YEAR));
					
					strData = sDay + "/" + sMonth + "/" + sYear;
				}else {
					strData = String.valueOf(cell.getNumericCellValue());
				}
			}
		
			return strData;
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exceptoin in getCellData() method. "+ e, test);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
				strData = null;
				sDay = null;
				sMonth = null;
				sYear = null;
			}catch(Exception e)
			{
				reports.writeResult(null, "Exception", "Exceptoin in getCellData() method. "+ e, test);
				return null;
			}
		}
	}
	
	
	
	
	/**************************
	 * Method Name		: setCellData
	 * 
	 ******************************/
	public void setCellData(String filePath, String sheetName, String colName, int rowNum, String strData)
	{
		FileInputStream fin = null;
		FileOutputStream fout = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		int colNum = 0;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			if(sh == null) {
				reports.writeResult(null, "Fail", "Failed to find the sheet '"+sheetName+"'", test);
				return;
			}
			
			//Find the column number based on the given column name
			row = sh.getRow(0);
			for(int c=0; c<row.getPhysicalNumberOfCells(); c++)
			{
				cell = row.getCell(c);
				if(cell.getStringCellValue().equalsIgnoreCase(colName)) {
					colNum = c;
					break;
				}
			}
			
			
			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);
			
			if(row.getCell(colNum) == null) {
				cell = row.createCell(colNum);
			}
			
			cell.setCellValue(strData);
			
			fout = new FileOutputStream(filePath);
			wb.write(fout);
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exceptoin in setCellData() method. "+ e, test);
			return;
		}
		finally
		{
			try {
				fout.flush();
				fout.close();
				fout = null;
				fin.close();
				fin = null;
				cell = null;
				sh = null;
				row = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				reports.writeResult(null, "Exception", "Exceptoin in setCellData() method. "+ e, test);
				return;
			}
		}
	}
}
