package com.lh.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import com.lh.pages.authenticated.LogOutPage;
import com.lh.pages.authenticated.MainPage;
import com.lh.pages.community.CommunityPage;
import com.lh.pages.unauthenticated.LoginPage;
import com.lh.test.base.BaseTestClass;
import com.lh.utils.UtilityForDataProvider;
import com.lh.utils.UtilityMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * <h2>Define test for Community here!</h2>
 * <p>
 * 
 * @author shishir.das
 * @version 1.0
 */

public class CommunityTest extends BaseTestClass {

	private static final String VALID_USERID = "validUserName";
	private static final String VALID_PASSWORD = "validPassword";
	private static final String INVALID_USERID = "invalidUserName";
	private static final String INVALID_PASSWORD = "invalidPassword";
	private static final String GROUP_NAME = "groupName";
	private static final String FORUM_NAME = "forumName";
	private static final String FORUM_DESCRIPTION = "forumDescription";
	private static final String ERROR_MESSAGE_FORUM = "errorMessageForum";
	private static final String TOPIC_NAME = "topicName";
	private static final String TOPIC_DESCRIPTION = "topicDescription";
	private static final String TOPIC_CREATED_SUCCESSFULLY = "topicAdded";
	private static final String ERROR_MESSAGE_GROUP = "errorMessageGroup";
	public static Logger logger = LogManager.getLogger(CommunityTest.class);
	private CommunityPage communityPage;
	private Map<String, String> communityData;
	private Assertion communityAssert = new Assertion();
	LoginPage loginPage;
	LogOutPage logoutPage;
	MainPage mainPage;
	boolean linkNotPresent;
	ArrayList<String> topicNameList = new ArrayList<String>();
	ArrayList<String> topicDescriptionList = new ArrayList<String>();
	static ExtentTest test;
	static ExtentReports report;

	@BeforeClass
	public void initClass() {
		communityData = readexcelsheet("Community");
		loginPage = new LoginPage();
		logoutPage = new LogOutPage();
		mainPage = new MainPage();
		communityPage = new CommunityPage();
		report = new ExtentReports(System.getProperty("user.dir") + "\\ExtentReportResults.html");
		test = report.startTest("CommunityTest");

	}

	@Test(dataProvider = "CommunityDataProvider", priority = 1)
	public void multipleValidLogin(String userName, String password) throws InterruptedException {
		loginPage.loginAs(userName, password);
		logoutPage.clickLogoutLink("portal");
	}

	@Test(description = "New group name field is required", priority = 2)
	public void verifyGroupNameFieldIsRequired() {
		loginPage.loginAs(communityData.get(VALID_USERID), communityData.get(VALID_PASSWORD));
		mainPage.clickCommunity();
		if (communityPage.GroupNameFieldIsRequired(communityData.get(ERROR_MESSAGE_GROUP))) {
			test.log(LogStatus.PASS, "New group name field is required PASSED");
		} else
			test.log(LogStatus.FAIL, "New group name field is required FAILED");
	}

	@Test(description = "Leader/Admin - Create New Group", priority = 3)
	public void verifyCreateNewGroup() {

		communityPage.createNewGroup(communityData.get(GROUP_NAME));
		communityAssert.assertTrue(communityPage.verifyGroupIsCreated(communityData.get(GROUP_NAME)));
		logger.info("--Group is Created successfully--");
	}

	@Test(description = "Verify New Forum description field is required when creating a New Forum", priority = 4)
	public void verifyForumDescriptionFieldIsRequired() {
		communityAssert
				.assertTrue(communityPage.ForumNameDescriptionFieldIsRequired(communityData.get(ERROR_MESSAGE_FORUM)));

	}

	@Test(description = "Leader/Admin - Create New Forum", priority = 5)
	public void verifyCreateNewForum() {
		logger.info("Leader/Admin - Create Forum");
		communityPage.createNewForum(communityData.get(FORUM_NAME), communityData.get(FORUM_DESCRIPTION));
		communityAssert.assertTrue(communityPage.verifyForumIsCreated(communityData.get(FORUM_NAME)));
		logger.info("--Forum is created successfully--");

	}

	@Test(description = "Verify topic name field is required when creating a New Topic", priority = 6)
	public void verifyTopicNameAndTopicDescriptionisRequired() {
		communityAssert.assertTrue(communityPage.TopicNameAndTopicDescriptionisRequired());

	}

	@Test(description = "Create New Topic", priority = 7)
	public void verifyCreateTopic() {

		topicNameList = UtilityMethods.splitPipeSepratedString(communityData.get(TOPIC_NAME));
		topicDescriptionList = UtilityMethods.splitPipeSepratedString(communityData.get(TOPIC_DESCRIPTION));
		communityAssert.assertTrue(communityPage.createTopic(topicNameList, topicDescriptionList,
				communityData.get(TOPIC_CREATED_SUCCESSFULLY)));

	}

	@Test(description = "Verify Arrows appear in Forums Page", priority = 8)
	public void verifyArrowAppearsInForums() {
		communityAssert.assertTrue(communityPage.verifySortArrowisPresent());
	}

	@Test(description = "Verify user is able to sort Topics in both ascending and descending directions.", priority = 9)
	public void verifyTopicSorting() {
		communityPage.communityTopicSorting();
		communityPage.deleteGroupandForum();
		communityAssert.assertTrue(communityPage.VerifyCommunitytopicSortingCompare(topicNameList));
	}

	@Test(description = "Regular User - Should not be able to create New Group/Forum", priority = 10)
	public void verifyGroupLinkIsNotPresent() {
		logoutPage.clickLogoutLink("portal");
		loginPage.loginAs(communityData.get(INVALID_USERID), communityData.get(INVALID_PASSWORD));
		mainPage.clickCommunity();
		communityAssert.assertEquals(communityPage.verifyGroupLinkIsNotPresent(), false);
		logoutPage.clickLogoutLink("portal");
	}

	@DataProvider(name = "CommunityDataProvider")
	public Object[][] getLoginData() throws IOException {
		UtilityForDataProvider dataProviderUtility = new UtilityForDataProvider();
		Object data[][] = dataProviderUtility.getTestData("LH.xls", "CommunityLoginUser");
		return data;
	}

	@AfterClass
	public void classTearDown() {
		logger.info("Inside the classTearDown() method for Community class...");
		loginPage = null;
		logoutPage = null;
		mainPage = null;
		communityPage = null;
		report.endTest(test);
		report.flush();
		logger.info("Writing the output to the Result output text file.");
		logger.info("Exiting the classTearDown() method for Community class \n\n");

	}

}
