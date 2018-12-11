package com.lh.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class UtilityForDataProvider {
	
	public  Object [][] getTestData(String workBookName, String sheetName) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(System.getProperty("user.dir") +"/src/test/resources/"+workBookName)));
        HSSFSheet worksheet = workbook.getSheet(sheetName);


    int rowcount = worksheet.getPhysicalNumberOfRows();

    Object [][] data = new Object [rowcount][2];

    for (int i=0; i<rowcount;i++){

    	HSSFRow row = worksheet.getRow(i);
    	HSSFCell username = row.getCell(0);
        data[i][0] = username.getStringCellValue();

        HSSFCell password = row.getCell(1);
        data[i][1] = password.getStringCellValue();
    }
    workbook.close();
    workbook = null;
    worksheet = null;
    System.out.println(data);
    return data;



}
}
