package com.lh.utils;

import com.lh.testConfig.TestProperty;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import static com.lh.helper.DriverFactory.driver;
import static com.lh.testConfig.TestProperty.WAITING_TIME;

/**
 * @author shishi.das 
 * Version : 1.0
 */
public class SeleniumUtil {

	private static Logger logger = LogManager.getLogger(SeleniumUtil.class);

	public static String s;

	public static By dynamicXpath(String xpathValue, String... substitutionValue) {
		return By.xpath(String.format(xpathValue, substitutionValue));
	}

	/**
	 * This method is used for element is present on page or not
	 * 
	 * @param fieldLocator
	 * @param driver
	 * @return true if element is present, other wise it return Fail
	 */
	public static boolean isElementPresent(By fieldLocator, WebDriver driver) {
		waitForPageLoad(driver);
		waitForPageLoaded(driver);

		try {
			logger.debug("Element: " + fieldLocator);
			driver.findElement(fieldLocator);

		} catch (org.openqa.selenium.NoSuchElementException Ex) {
			logger.debug("Unable to locate Element: " + fieldLocator);
			return false;
		}
		return true;
	}

	/**
	 * This method is used for wait for element for ELEMENT_POLL_TIME defined in
	 * TestProperty file
	 * 
	 * @param elementLocator
	 * @param driver
	 */
	public static void waitForElementToBePresent(By elementLocator, WebDriver driver) {
		waitForPageLoad(driver);
		waitForPageLoaded(driver);

		WebElement myDynamicElement = (new WebDriverWait(driver, TestProperty.ELEMENT_WAIT_TIME))
				.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
		if (myDynamicElement.isDisplayed()) {
			logger.info("Element Found: " + elementLocator.toString());
		} else {
			logger.error("\nElement NOT found: " + elementLocator.toString());
		}
	}

	public static void sendKeysToWebElement(By locator, String text, WebDriver driver) {
		waitForPageLoad(driver);
		waitForPageLoaded(driver);

		try {
			element(locator, driver).clear();
			element(locator, driver).sendKeys(text);
			logger.debug("Input following text: " + text + " | Identifier: " + locator);
		} catch (Exception e) {
			logger.debug("**ERROR**Failed to send text into field.  Text: " + text + " | Identifier: " + locator);
			e.getStackTrace();
		}
	}

	public static boolean isElementDisplayed(By elementLocator, WebDriver driver) {
		waitForPageLoad(driver);
		waitForPageLoaded(driver);

		try {
			logger.debug("Element: " + elementLocator);
			driver.findElement(elementLocator).isDisplayed();

		} catch (org.openqa.selenium.NoSuchElementException Ex) {
			logger.debug("Unable to locate Element: " + elementLocator);
			logger.info("Unable to locate Element: " + elementLocator);
			// ErrorUtil.addVerificationFailure(new Throwable("Unable to locate
			// Element: " + elementLocator));
			return false;
		}
		return true;
	}

	public static WebElement element(By elementToken, WebDriver driver) {
		waitForPageLoad(driver);
		waitForPageLoaded(driver);

		return driver.findElement(elementToken);
	}

	public static void hover(By elementToken, WebDriver driver) {
		waitForPageLoad(driver);
		waitForPageLoaded(driver);

		WebElement element = driver.findElement(elementToken);
		Actions hoverClick = new Actions(driver);
		new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.presenceOfElementLocated(elementToken));
		hoverClick.moveToElement(driver.findElement(elementToken)).click().build().perform();
	}

	public static void hoverOverElement(By elementIdentifier) {
		waitForPageLoad(driver);
		waitForPageLoaded(driver);

		new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.presenceOfElementLocated(elementIdentifier));
		new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.elementToBeClickable(elementIdentifier));

		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(elementIdentifier)).perform();
		logger.info("...hovered over element.|Identifier: " + elementIdentifier + " | Text: "
				+ driver.findElement(elementIdentifier).getText());
	}

	public static void hoverAndClick(By elementIdentifier) {
		waitForPageLoad(driver);
		waitForPageLoaded(driver);

		new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.presenceOfElementLocated(elementIdentifier));
		new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.elementToBeClickable(elementIdentifier));

		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(elementIdentifier)).click().build().perform();

		logger.info("...clicked on element.|Identifier: " + elementIdentifier);
	}

	public static String getTextfromWebElement(By xpathLocator, WebDriver driver) {
		waitForPageLoad(driver);
		waitForPageLoaded(driver);

		String text = null;
		try {
			text = element(xpathLocator, driver).getText();
		} catch (Exception e) {
			logger.debug("Unable to get text from Webelement");
			e.getStackTrace();
		}
		return text;
	}

	/**
	 * This method is used for wait for element for ELEMENT_POLL_TIME defined in
	 * TestProperty file
	 *
	 * @param elementLocator
	 * @param driver
	 */
	public static void waitForElementToBeVisible(By elementLocator, WebDriver driver) {
		waitForPageLoad(driver);
		waitForPageLoaded(driver);

		try {
			WebElement myDynamicElement = (new WebDriverWait(driver, TestProperty.WAITING_TIME))
					.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
		} catch (Exception e) {
			logger.info("Exception occured.. " + e);
		}
	}

	public static void waitForPageLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, WAITING_TIME);
		wait.until(pageLoadCondition);
	}

	public static Boolean checkTextPresentInPage(String text, WebDriver driver) {
		waitForPageLoad(driver);
		waitForPageLoaded(driver);

		Boolean textState = false;
		if (driver.findElement(By.tagName("body")).getText().contains(text)) {
			textState = true;
		}
		return textState;
	}

	public static boolean findElementByText(String textToLookFor) {
		waitForPageLoad(driver);
		waitForPageLoaded(driver);

		By textContains = By
				.xpath("//*[contains(.,'" + textToLookFor + "')][not(.//*[contains(., '" + textToLookFor + "')])]");

		new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.presenceOfElementLocated(textContains));
		logger.info("Found unique element by looking for the following text: " + textToLookFor);
		return true;
	}

	public static void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
		sleep(1);

		logger.info("...switched frame back to 'Default Content'.");
	}

	public static void clickbyJS(String xpath, WebDriver driver) {
		waitForPageLoad(driver);
		waitForPageLoaded(driver);

		WebElement element = driver.findElement(By.xpath(xpath));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public static void waitForPageLoaded(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, WAITING_TIME);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}

	public static String getCurrentMethodName() {
		return Thread.currentThread().getStackTrace()[2].getClassName() + "."
				+ Thread.currentThread().getStackTrace()[2].getMethodName();
	}

}