package com.lh.pages.authenticated;

import com.lh.utils.SeleniumUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import static com.lh.helper.DriverFactory.driver;
import static com.lh.utils.SeleniumUtil.hoverAndClick;

public class LogOutPage {
	private By settingsIconMenu				= By.xpath("//*[@alt='Settings icon']");
	private By logoutLnk 					= By.xpath(".//a[@href='/logout.aspx']");
	private By logoutLnkOntarget			= By.xpath(".//a[@href='/logout.aspx']");
	private By logoutLnkAdmin				= By.xpath("//a[@href='/Administration/AdminLogout.aspx']");
	private static Logger logger			= LogManager.getLogger(LogOutPage.class);
	
	/**
	 * Clicks on the LogOut link present on the top right corner of the website
	 */
	public void clickLogoutLink(String portalType) {
		if(portalType.equalsIgnoreCase("OnTarget")){
			SeleniumUtil.switchToDefaultContent(driver);
			SeleniumUtil.sleep(2);
			hoverAndClick(settingsIconMenu);
			hoverAndClick(logoutLnkOntarget);
		}
		else if(portalType.equalsIgnoreCase("Admin")){
			
			SeleniumUtil.switchToDefaultContent(driver);
			SeleniumUtil.sleep(2);
			SeleniumUtil.waitForElementToBeVisible(logoutLnkAdmin, driver);
			driver.findElement(logoutLnkAdmin).click();
		}
		
		else{
			SeleniumUtil.sleep(3);
			SeleniumUtil.switchToDefaultContent(driver);
			logger.info("Clicking on the  the logoutLinkLocator identified by- " + logoutLnk);
			hoverAndClick(settingsIconMenu);
			hoverAndClick(logoutLnk);
		}
	}

}
