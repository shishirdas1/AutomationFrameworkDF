package com.lh.testConfig;

import com.lh.utils.PropertyFileReader;
import com.lh.utils.UtilityMethods;
import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import java.util.Properties;

/**
 * <h2>Test Properties are defined in this class!</h2>
 * This class reads the configuration properties
 * of the automation run.
 * <p>
 * <b>Note:</b> All the test properties should be defined in the 
 * PROPERTY files in the project
 * 
 * @author shishir.das
 * @version 1.0
 */

public class TestProperty {
	/** propObj is an instance of PropertyFileReader class */
	final static PropertyFileReader prop= new PropertyFileReader();
	/** Instance of the properties */
	final static Properties propObj = prop.returnProperties("automation");
	/** BASEURL contains the URL which is used for test Automation */
	public static String Browser_Jenkin;
	public static String TestRunID_Jenkin;
	public static String XlsData_Jenkin;
	public static String Test_Env_Jenkins;
	public static final String BASEURL = propObj.getProperty("BASEURL");
	public static final String PORTALURL = propObj.getProperty("PORTALURL");
	public static final String BASE_URL = propObj.getProperty("BASE_URL");


	public static final String BROWSER =  browser_Setter();

	/** ZIPFILE_REPORT_NAME contains the name of the zipped report */
	public static final String ZIPFILE_REPORT_NAME =  propObj.getProperty("ZIPFILE_REPORT_NAME");
	
	/** CONFIG_AUTOMATION contains relative path of the configuration file 	 */
	public static final String CONFIG_AUTOMATION = "/test/resources/automation.properties";
	
	/** WAITING_TIME contains the webDriverWait time */
	public static final int WAITING_TIME =  Integer.parseInt(propObj.getProperty("WAITING_TIME"));
	
	/** ELEMENT_WAIT_TIME is the time in milli seconds the driver waits for to proceed in HelperWebDriverEventListener */
	public static final int ELEMENT_WAIT_TIME =  Integer.parseInt(propObj.getProperty("ELEMENT_WAITTIME"));
	
	/** ELEMENT_WAIT_TIME is the time in seconds the driver waits for to proceed in HelperWebDriverEventListener */
	public static final int ELEMENT_POLL_TIME =  Integer.parseInt(propObj.getProperty("ELEMENT_POLLTIME"));

	/** THREAD_SLEEP contains the value in long for the Thread.sleep(THREAD_SLEEP) stmt */
	private static String THREAD_SLEEP_STRING = propObj.getProperty("THREAD_SLEEP_STRING").trim();
	

	public static long THREAD_SLEEP = Long.valueOf(THREAD_SLEEP_STRING).longValue();
	public static final String TESTDATA_WORKBOOK =  testdataWorkbook_Setter();
	public static final String TAKE_SCREENSHOTS = propObj.getProperty("TAKE_SCREENSHOTS").trim().toLowerCase();

	public static final String CLIENTURL = UtilityMethods.extractPartialClientURL(propObj.getProperty("BASEURL"));
	
	
	/** Logger for the HealthAssessment class */
	private static Logger logger 							= LogManager.getLogger(TestProperty.class);

	/**
	 * Overriding the default constructor
	 */
	protected TestProperty (){
		logger.debug("Creating instance of the TestProperty class");
	}

	
	/**
	 * Client Portal
	 */
	public static final String CLIENT_URL = propObj.getProperty("CLIENT_URL");
	public static final String CLIENT_USERNAME = propObj.getProperty("CLIENT_USERNAME");
	public static final String CLIENT_PASSWORD = propObj.getProperty("CLIENT_PASSWORD");


		/**
	 * File service
	 */
	public static final String XLS_FILESERVICE_DATA = propObj.getProperty("XLS_FILESERVICE_DATA");
	
	public static String browser_Setter(){
		Browser_Jenkin = System.getProperty("BrowserName");
		if(Browser_Jenkin == null)
			Browser_Jenkin = propObj.getProperty("BROWSER").toString();
		return Browser_Jenkin;
		
	}

	

	public static String testdataWorkbook_Setter(){
		XlsData_Jenkin = System.getProperty("XlsData");
		if(XlsData_Jenkin == null)
			XlsData_Jenkin = propObj.getProperty("XLS_DATA").trim();
		return XlsData_Jenkin;
		
	}

	
	/**
	 * Test Environment details
	 */
	public static final String TEST_ENV = testEnvSetter();


	public  static String testEnvSetter(){
		Test_Env_Jenkins = System.getProperty("TEST_ENV");
			if(Test_Env_Jenkins == null)
				Test_Env_Jenkins = propObj.getProperty("TEST_ENV");
			return Test_Env_Jenkins;
	}

	

	



}
