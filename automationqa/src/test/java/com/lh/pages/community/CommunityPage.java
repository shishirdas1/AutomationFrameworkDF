package com.lh.pages.community;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static com.lh.helper.DriverFactory.driver;
import com.lh.utils.SeleniumUtil;
public class CommunityPage{
	
	private By newGroupLink 					=  	By.id("newGroupLink");
	private By groupNameInput 					= 	By.xpath("//input[contains(@id, 'forumGroupNameTextbox')]");
	private By groupSubmitButton 				= 	By.xpath("//button[contains(@id, 'submitForumGroupButton')]");
	private By groupName						=	By.xpath("//tr[@class='headerRow']//div[@class='left']");
	private By forumName						= 	By.xpath("//td[@class='articlesubtitle']/div/a[normalize-space(text()='TestForum1')]");
	private By newForumLink						=	By.xpath("//a[contains(@id, 'newForumLink')]");
	private By forumNameInput					=	By.id("forumNameTextbox");
	private By forumNameDescriptionInput		=	By.id("forumDescriptionTextbox");
	private By forumSubmitButton				=	By.xpath("//button[contains(@id, 'submitForumButton')]");
	private By newGroupLinkNotPresent			=	By.xpath("//div[@id='addForumGroupSection' and @style='display: none;']");
	private By deleteGroupLink					=	By.xpath("//a[contains(@id, 'deleteGroupLink')]");
	private By cancelLink						=	By.xpath("//div[@class='newForumSection']//button[text()='Cancel']");
	private By forumTitleLink					=	By.xpath("//a[contains(@id, 'titleLink')]");
	private By newTopicButton					=	By.id("newTopicButton");
	private By newTopicSubmitButtonDisable		=	By.xpath("//input[@id='submitButton' and @disabled='disabled']");
	private By topicName						=	By.id("TopicNameTextBox");
	private By topicDescription					=	By.id("TopicDetailsTextBox");
	private By newTopicSubmitButtonEnable		=	By.xpath("//input[@id='submitButton']");
	private By okButton							=	By.xpath("//input[@id='OKButton']");
	private By sortArrow						=	By.xpath("//td[@class='topicTitleColumn']//span[@class='sortArrow arrow-right']");
	private By tableRows						= 	By.xpath("//table[@id='topicsTable']/tbody/tr");
	private By backToforumsLink					=	By.xpath("//div[@class='fullRowButtonGroup']/a[@class='button2 left']");
	private String topicNameText				= 	"(//td[@class='articlesubtitle']//a)[%s]" ;
	private By newTopicCancelButton				=	By.id("cancelButton");
	private By errorMessageGroup				= 	By.xpath("//span[@class='articletext error']");
	
	public static Logger logger = LogManager.getLogger(CommunityPage.class);
	ArrayList<String> topicNameList = new ArrayList<String>();
	
	public boolean GroupNameFieldIsRequired(String errorMessage){
		SeleniumUtil.element(newGroupLink, driver).click();
		SeleniumUtil.element(groupSubmitButton, driver).click();
		return SeleniumUtil.element(errorMessageGroup, driver).getText().contains(errorMessage);
	}
	
	public void createNewGroup( String groupName1) {
		SeleniumUtil.element(groupNameInput, driver).sendKeys(groupName1);
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", SeleniumUtil.element(groupSubmitButton, driver));
		
	}
	
	
	public boolean verifyGroupIsCreated(String expectedGroupName){
		SeleniumUtil.sleep(2);
		return expectedGroupName.equals(SeleniumUtil.element(groupName, driver).getText().trim());	
	}
	
	public boolean ForumNameDescriptionFieldIsRequired(String errorMessageForum ){
		SeleniumUtil.element(newForumLink, driver).click();
		SeleniumUtil.sleep(2);
		SeleniumUtil.element(forumSubmitButton, driver).click();
		return SeleniumUtil.checkTextPresentInPage(errorMessageForum, driver);
		
	}
	public void createNewForum(String forumName,String forumNameDescription){
		SeleniumUtil.waitForElementToBeVisible(newForumLink, driver);
		SeleniumUtil.element(forumNameInput, driver).sendKeys(forumName);
		SeleniumUtil.element(forumNameDescriptionInput, driver).sendKeys(forumNameDescription);
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", SeleniumUtil.element(forumSubmitButton, driver));
		logger.info("New Forum Created");	
	}
	
	public boolean verifyForumIsCreated (String expectedForumName){
		return expectedForumName.equals(SeleniumUtil.element(forumName, driver).getText().trim());
	}
	
	public boolean verifyGroupLinkIsNotPresent(){
		return (SeleniumUtil.element(newGroupLinkNotPresent, driver).isDisplayed());
	}
	public void deleteGroupandForum(){
		SeleniumUtil.element(backToforumsLink, driver).click();
		SeleniumUtil.element(deleteGroupLink, driver).click();
		driver.switchTo().alert().accept();
	}
	
	public void clickCancel(){
		SeleniumUtil.element(cancelLink, driver).click();
	}
	public boolean TopicNameAndTopicDescriptionisRequired(){
		SeleniumUtil.element(forumTitleLink, driver).click();
		SeleniumUtil.element(newTopicButton, driver).click();
		return SeleniumUtil.isElementPresent(newTopicSubmitButtonDisable, driver);
			
	}

	public boolean createTopic(ArrayList<String> topicName1,
			ArrayList<String> topicDescription1, String topicCreatedText) {

		for (int i = 0; i < topicName1.size(); i++) {
			SeleniumUtil.element(topicName, driver).sendKeys(topicName1.get(i));
			SeleniumUtil.element(topicDescription, driver).sendKeys(topicDescription1.get(i));
			SeleniumUtil.element(newTopicSubmitButtonEnable, driver).click();
			SeleniumUtil.element(okButton, driver).click();
			SeleniumUtil.element(newTopicButton, driver).click();
			
		}
		SeleniumUtil.element(newTopicCancelButton, driver).click();
		return true;
	}

	public boolean verifySortArrowisPresent(){
		return SeleniumUtil.isElementDisplayed(sortArrow, driver);
		
	}
	
	public void communityTopicSorting() {
		SeleniumUtil.element(sortArrow, driver).click();
		List<WebElement> tr_collection = driver.findElements(tableRows);
			for (int i = 0; i < tr_collection.size(); i++) {	
			topicNameList.add(i,SeleniumUtil.element(SeleniumUtil.dynamicXpath(topicNameText, String.valueOf(i+1)),driver).getText());
		}
	}
	
	public boolean VerifyCommunitytopicSortingCompare(ArrayList <String> ExpectedTopicNames){
		if (ExpectedTopicNames.size()!=topicNameList.size())
			return false;
		else
		for (int i=0;i<ExpectedTopicNames.size();i++){
			if (!ExpectedTopicNames.get(i).equals(topicNameList.get(i)))
					return false;
		}
		return true;
	}
}
