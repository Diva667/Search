package org.stepDefinition;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.helper.Base_Class;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks extends Base_Class{
	
	@Before
	public void bef() throws InterruptedException, IOException {
		openChromeBrowser();
		
		FileInputStream fis = new FileInputStream("C:\\Users\\diva6\\eclipse-workspace\\Informa_Search\\Environment.xlsx");
		
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(1);
		Cell cell = row.getCell(0);
		String url = cell.getStringCellValue();
		
		driver.get(url);
		maximizeWindow();
		sleep(5000);
		wb.close();
		sleep(1000);
	}
	
	@After
	public  void aft() {
		driver.close();
	}
	

}
