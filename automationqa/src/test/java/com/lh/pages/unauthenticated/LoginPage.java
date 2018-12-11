package com.lh.pages.unauthenticated;

import com.lh.utils.SeleniumUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import static com.lh.helper.DriverFactory.driver;
import static com.lh.utils.SeleniumUtil.getCurrentMethodName;
import static com.lh.utils.SeleniumUtil.sendKeysToWebElement;

/**
 * <h2>All the methods required to work with Login page are defined here</h2>
 * <p>
 * 
 * @author shishir.das
 * @version 1.0
 */
public class LoginPage {

	/**
	 * Logger to log messages
	 */
	private static Logger logger = LogManager.getLogger(com.lh.pages.unauthenticated.LoginPage.class);

	protected By clientLogo = By.cssSelector("#ctl00_SiteGlobalNav_Logo");

	// The locators for the HTML elements should only be defined once
	private By usernameTxt = By.id("ctl00_ContentPlaceHolder1_UserName");
	private By passwordTxt = By.id("ctl00_ContentPlaceHolder1_Password");
	private By loginBtn = By.id("ctl00_ContentPlaceHolder1_lnkLogin");
	private By loginAdminBtn = By.xpath("//input[@value='Sign in']");
	protected By contactLiveHelathierLink = By.id("ctl00_ContactLink");

	/**
	 * Constructor
	 *
	 */
	public LoginPage() {
		logger.info("Initializing the LoginPage Object...");
		logger.info("Success - *Initialized* LoginPage.");
	}

	public LoginPage(String portalType) {
		if (portalType.equalsIgnoreCase("Portal")) {
			
		}
		logger.info("Initialising the LoginPage object");
	}

	private void setUsername(final String username) {
		sendKeysToWebElement(usernameTxt, username, driver);
		logger.info("Entered the Username: " + username + " ||  " + getCurrentMethodName());
	}

	private void setPassword(final String password) {
		sendKeysToWebElement(passwordTxt, password, driver);
		logger.info("Entered the Password: " + password + " ||  " + getCurrentMethodName());
	}

	private void submitLogin() {
		SeleniumUtil.element(loginBtn, driver).click();
		;
		logger.info("...logging into Client Portal. ||  " + getCurrentMethodName());
	}

	private void submitAdminLogin() {
		driver.findElement(loginAdminBtn).submit();
		logger.info("Logging into Admin Portal...");
	}

	public void loginAs(final String username, final String password) {
		logger.info("...logging into the application...");
		setUsername(username);
		setPassword(password);
		String URL = driver.getCurrentUrl();
		if (!URL.contains("admin"))
			submitLogin();
		else
			submitAdminLogin();

		logger.info("Logged into the application...");
	}

}
