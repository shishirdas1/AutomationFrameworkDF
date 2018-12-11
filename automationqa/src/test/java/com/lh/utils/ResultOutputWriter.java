package com.lh.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

public class ResultOutputWriter {
	
	private static Logger logger = LogManager
			.getLogger(com.lh.utils.ResultOutputWriter.class);

	public void writeResultOutputToDisk(int noOfTestPassed, int noOfTestFailed, int noOfTestSkipped,
			String locationOfOutputFile, String nameOfOutputFile) {

		try {
			File file;

			if (nameOfOutputFile == null) {
				logger.info("The file path is null!!");
				//file = new File("D:/Workspace/LHWebdriver/testsuite-output/TestResultOutput.txt");
				file = new File(System.getProperty("user.dir")+"/src/test/resources/TestResultOutput.txt");
				
			} else {
				logger.info("The file path is - " + nameOfOutputFile);
				//file = new File("D:/Workspace/LHWebdriver/testsuite-output/" + nameOfOutputFile);
				file = new File(System.getProperty("user.dir")+"/src/test/resources/" + nameOfOutputFile);
			}

			if (!file.exists()) {
				file.createNewFile();
			}
			StringBuilder contentToWrite = new StringBuilder();

			contentToWrite.append("Number of Test Passed   :" + noOfTestPassed);
			contentToWrite.append("\n\n");
			contentToWrite.append("Number of Test Failed   :" + noOfTestFailed);
			contentToWrite.append("\n\n");
			contentToWrite.append("Number of Test Skipped  :" + noOfTestSkipped);

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter buffwriter = new BufferedWriter(fw);
			buffwriter.write(contentToWrite.toString());
			buffwriter.close();
			System.out.println("File Writing Complete .....");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
