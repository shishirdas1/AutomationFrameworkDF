package com.lh.pages.authenticated;

import static com.lh.helper.DriverFactory.driver;
import static com.lh.utils.SeleniumUtil.hoverAndClick;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import com.lh.utils.ExcelFileUtility;
import com.lh.utils.SeleniumUtil;

/**
 * <h2>This is the Base class, it contains all the common methods which can be
 * used by all pages</h2>
 * <p>
 * 
 * @author shishir.das
 * @version 1.0
 */

public class MainPage {

	private static Logger logger = LogManager.getLogger(com.lh.pages.authenticated.MainPage.class);
	protected static ExcelFileUtility testDataObj;

	private By theProgramsMenu = By.cssSelector("a[title='The Programs']");
	private By yourToolsMenu = By.cssSelector("a[title='Your Tools']");
	private By resourcesMenu = By.cssSelector("a[title='Resources']");

	private By homeIcon = By.xpath("//*[@alt='Home icon']");
	private By programOverviewSubMenu = By.xpath(".//a[@href='/V2/index.aspx']");

	private String theProgramSubMenu = "//ul[@class='navbar-global-nav']/li/span[contains(text(),'The Program')]/../ul//li/a[contains(text(),'%s')]";
	private By overviewTab = By.xpath(".//*[@id='ontrack-subnav']/li[1]/a");
	private By communitySubMenu = By.xpath("//a[contains(@href,'/V2/memberservices/community/CommunityHome.aspx')]");
	protected By breadcrumbFirst = By
			.id("ctl00_TopNavigationBreadcrumb_breadcrumbItemRepeater_ctl00_breadcrumbItemUrl");
	protected By breadcrumbSecond = By
			.id("ctl00_TopNavigationBreadcrumb_breadcrumbItemRepeater_ctl02_breadcrumbItemUrl");

	protected String toolsPath = "//ul[@class='navbar-global-nav']/li/span[contains(text(),'Tools')]/../ul//li/a[contains(text(),'%s')]";
	protected By imageText = By.xpath("//span[@id='ctl00_TopNavigationTextLabel']/..");
	protected String actionPath = "//*[@id='ctl00_ActionCenter_ActionCenterBlockRepeater_ctl0%s_blockTitleLable']";
	By eventsSubMenu = By.xpath("//a[contains(@href,'/V2/MemberServices/MSEvents/Scheduleevents.aspx')]");
	By wellnessBankHomePageWidget = By.xpath(".//*[@id='ctl00_wbHomeWidget_hrefWellnessBankURL']/div[1]/img");

	boolean flag;
	String url;

	public MainPage() {
		logger.info("Initialized the Main Page Object");
	}

	public boolean verifyElementFoundByText(String text) {
		assertTrue(SeleniumUtil.findElementByText(text));
		return true;
	}

	public void clickOnHomeIcon() {
		if (!driver.getCurrentUrl().contains("index")) {
			hoverAndClick(homeIcon);
			logger.info("Navigating back to the Portal Home Page.");
		}
	}

	/**
	 * Clicks and Opens the Program Overview page
	 */
	public void clickProgramOverview() {

		try {
			SeleniumUtil.hover(theProgramsMenu, driver);

			SeleniumUtil.element(programOverviewSubMenu, driver).click();

			logger.info("Clicked on the Program Overview sub menu. ");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void clickOverviewTab() {

		logger.info("Clicking on the Overview Tab Locator identified by- " + overviewTab);
		driver.findElement(overviewTab).click();

	}

	/**
	 * Clicks and opens the Community page
	 */
	public void clickCommunity() {
		clickOnHomeIcon();
		try {
			SeleniumUtil.hover(resourcesMenu, driver);

			SeleniumUtil.element(communitySubMenu, driver).click();

			logger.info("Clicked on the Community sub menu.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verifies that the breadcrumb has the expected text.
	 * 
	 * @param breadcrumbSecondChild
	 *            The visible text expected in the second breadcrumb.
	 * @return true/false depending on the match between the text appearing and
	 *         expected in the second breadcrumb.
	 */
	public boolean verifyBreadcrumbFirstChild(String breadcrumbSecondChild) {

		String breadcrumbSecondChildExpectedTxt = breadcrumbSecondChild.trim();

		boolean isExpectedtextVisible = false;

		String breadcrumbVisibleText = driver.findElement(breadcrumbSecond).getText().trim();

		logger.info("The expected text visible for the breadcrumb is - " + breadcrumbSecondChildExpectedTxt);
		logger.info("The actual text visible for the breadcrumb is - " + breadcrumbVisibleText);

		if (!breadcrumbSecondChildExpectedTxt.isEmpty()) {

			if (breadcrumbSecondChildExpectedTxt.equals(breadcrumbVisibleText)) {

				isExpectedtextVisible = true;

				logger.info("The text visible for the breadcrumb is as expected.");

			} else {

				logger.info("The expected text for the breadcrumb does not match the actual text.");
			}

		} else {

			logger.info("Empty string is passed as the expected text for the breadcrumb.");
		}

		return isExpectedtextVisible;

	}

	public boolean verifyTheProgramSubMenuItem(String breadcrumb) {

		boolean isPresent = false;

		clickProgramOverview();

		if (verifyBreadcrumbFirstChild(breadcrumb)) {

			isPresent = true;

			logger.info("The bread crumb is found as expected.");

		}

		return isPresent;

	}

	public boolean verifyCommunintyMenuItem(String breadcrumb) {

		boolean isPresent = false;

		clickCommunity();

		if (verifyBreadcrumbFirstChild(breadcrumb)) {

			isPresent = true;

			logger.info("The bread crumb is found as expected.");

		}

		return isPresent;
	}

	/**
	 * This method mouse hovers and clicks on tools-menu sub links
	 * 
	 * @param sublinkText
	 * 
	 */
	public void clickOnToolsSubLinks(String sublinkText) {
		try {
			SeleniumUtil.hover(yourToolsMenu, driver);
			driver.findElement(SeleniumUtil.dynamicXpath(toolsPath, sublinkText)).click();
			logger.info("Click on the tools sub link item");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method verifies the text present on the banner with the actual text
	 * 
	 * @param text
	 * @return
	 */
	public boolean verifyToolSubText(String text) {

		try {

			String imgText = SeleniumUtil.getTextfromWebElement(imageText, driver);
			logger.info("The image text is " + imgText);
			if (imgText.contains(text)) {
				flag = true;
				logger.info("Successfully verified the tools sub menu" + imgText);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * Click the various sub menu of The Program menu item
	 */
	public void clickTheProgramSubMenu(String subMenu) {

		try {
			SeleniumUtil.hover(theProgramsMenu, driver);
			SeleniumUtil.element(SeleniumUtil.dynamicXpath(theProgramSubMenu, subMenu), driver).click();
			logger.info("Clicked on the" + subMenu + " sub menu. ");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
