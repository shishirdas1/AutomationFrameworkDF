package com.lh.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class contains the methods used to read and write to excel files
 * <p>
 * 
 * @author shishir.das
 * @version 1.0
 */
public class ExcelFileUtility {
	/**
	 * Logs messages to the appenders
	 */
	private static Logger logger = LogManager.getLogger(com.lh.utils.ExcelFileUtility.class);

	/**
	 * This method reads test data from the data file specified in the config
	 * properties
	 * 
	 * @param workBookName
	 * @param sheetName
	 * @return HashMap with the test values read from the test excel file
	 */
	
	
	public HashMap<String, String> readExcelSheet(final String workBookName, final String sheetName) {

		logger.info("Opening Excel file: " + workBookName + "  ||  " + "Worksheet:  "+sheetName);

		HashMap<String, String> dataMap = new HashMap<String, String>();

		try {
			
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\" + workBookName)));
			HSSFSheet sheet = workbook.getSheet(sheetName);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			String mapKey = new String();
			String mapValue = new String();
			int rowCounter = 0;

			logger.info("...reading the data from the excel data sheet");

			Iterator<Row> rows = sheet.rowIterator();

			while (rows.hasNext()) {

				HSSFRow row = (HSSFRow) rows.next();
				rowCounter = 0;
				String cellText = null;

				Iterator<Cell> cells = row.cellIterator();

				while (cells.hasNext()) {

					HSSFCell cell = (HSSFCell) cells.next();

					if (cell.getCellType() == Cell.CELL_TYPE_STRING ) {

						cellText = cell.getRichStringCellValue().getString();
						
					} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC ) {

						cellText =  String.valueOf( (int) cell.getNumericCellValue());

					} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

						cellText = String.valueOf(cell.getBooleanCellValue());

					} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA ) {
						cellText = evaluator.evaluate(cell).getStringValue();
										
					} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {

						cellText = null;
						continue;
					}

					else {
						logger.info("Not a valid type");
					}

					if (rowCounter == 0) {

						mapKey = cellText;
						rowCounter= rowCounter + 1;
						

					} else if (rowCounter == 1) {

						mapValue = cellText;
						rowCounter= rowCounter + 1;
					}

					dataMap.put(mapKey, mapValue);

				}
			}
			
			workbook.close();
			workbook = null;
			sheet = null;
			
			logger.info("***Closing the excel file and returning to the test...");

		} catch (IOException e) {
			
			logger.error("***ERROR*** Unable to read from the excel file -  " + workBookName);
			
			e.printStackTrace();
			
		} finally {
			
			logger.info("Inside Method: "+SeleniumUtil.getCurrentMethodName());
			
		}
		
		return dataMap;
		
	}
}