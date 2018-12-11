package com.lh.test.base;

import com.lh.testConfig.TestProperty;
import com.lh.utils.ExcelFileUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.util.Map;

import static com.lh.helper.DriverFactory.*;

public class BaseTestClass {

	private static Logger logger             = LogManager.getLogger(BaseTestClass.class);

	@BeforeClass(alwaysRun = true)
	public void initSuite(ITestContext context){
		logger.info("\n\nStarting the Tests run at - ");
			getDriverInstance();
		openURL(TestProperty.BASEURL);
	}

	@AfterClass(alwaysRun = true)
	public void cleanupclass() {
		driver.quit();
	}
	@AfterSuite
	public void cleanupSuite() {
		logger.info("Writing the output to the Result output text file.");

	}


	protected Map<String, String> readexcelsheet(String sheetName) {
		ExcelFileUtility testDataObj = new ExcelFileUtility();
		return testDataObj.readExcelSheet(TestProperty.TESTDATA_WORKBOOK, sheetName);
	}
	
}