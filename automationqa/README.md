# README #

This README documents the steps necessary to setup the automation framework and get it running to test any client portal.

### What is this repository for? ###

* The repository contains the QA Automation based on __Selenium 2.0 framework__ and can be configured to test the the website on _IE/Chrome/Firefox_
* __Version__- 2.0

### How do I get set up the automation framework to run the test on my machine? ###

 ___Setup instructions:___

 1. Setup Java 7/8 JDK -
      1. Download and install JDK 1.7 from [here](http://www.oracle.com/technetwork/java/javase/downloads/index.html). Ensure the Path Variable is set to include jdk/bin path.
 2.  Download the Apache Maven jars or Install Maven plugin for eclipse. 
        1.Open Eclipse IDE
        2.Click Help -> Install New Software...
        3.Click Add button at top right corner
        4.At pop up: fill up Name as "M2Eclipse" and Location as "http://download.eclipse.org/technology/m2e/releases"  
 3.  Download this Automation Project from BitBucket to a local location.
 4.  Open command prompt and navigate to the local folder containing the Automation Project.
 5.  Add an environment variable for Maven in PATH.In the cmd prompt type- *mvn* and hit enter.
 6.  Add sqljdbc4-2.0.jar in build path in Eclipse for executing dao queries. 
 

###Configuration###
 * JDK 7
 + Selenium binaries- 2.53.0 
 + Log4J - 1.2.17
 * Apache Maven - 3.3.9
 + TestNG Binaries- 6.8.5
 * jxl binaries

###Dependencies###
The framework requires the Java Environment (JDK 7/8) and Maven setup on the local machine.
 
###How to run tests###
  Using the POM.xml (run as Maven Test). 

### Contribution guidelines (TBD) ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###


* Abhy.Nadar@livehealthier.com