package com.lh.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

/**
 * <h1>This class is used to read a PROPERTY file</h1><p>
 * 
 * @author shishir.das
 * @version 1.0
 */
public class PropertyFileReader {
	
	/** Logger to log the Driver Factory log messages */
	private static Logger logger = LogManager.getLogger(com.lh.utils.PropertyFileReader.class);
	
	public String returnPropVal(final String propertyFileName, final String key) {

		// get a new properties object:
		final Properties properties = new Properties();
		String value = null;
		{
			try {
				
				//properties.load(new FileInputStream(new File("../config/"+ propertyFileName + ".properties")));
				properties.load(new FileInputStream(new File(System.getProperty("user.dir")+ "/src/test/resources/automation.properties")));
				

				// get PROPERTY value based on key:
				value = properties.getProperty(key);

			} catch (final FileNotFoundException e) {
				logger.error("The file was not found at "+"/src/test/resouces"+ propertyFileName + ".properties", e);

			} catch (final IOException e) {
				logger.error("IOException was found in returnPropVal method", e);
			}
		}
		return value;
	}

	/**
	 * 
	 * @param propertyFileName
	 * @return A PROPERTY File containing automation key value pair
	 * 
	 */
	public Properties returnProperties(final String propertyFileName) {

		// get a new properties object:
		Properties properties = new Properties();
		{
			try {
				//properties.load(new FileInputStream(new File("../config/"+ propertyFileName + ".properties")));
				properties.load(new FileInputStream(new File(System.getProperty("user.dir")+ "/src/test/resources/automation.properties")));
				
			} catch (final FileNotFoundException e) {
				//logger.error("The file was not found at " + "../config/"+ propertyFileName + ".properties", e);
				logger.error("The file was not found at " + "/src/test/resources"+ propertyFileName + ".properties", e);
				
			} catch (final IOException e) {
				logger.error("IOException was found in returnProperties method", e);
			}
		}
		return properties;
	}	

}
	
	


		


