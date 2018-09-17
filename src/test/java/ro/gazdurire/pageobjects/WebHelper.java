package ro.gazdurire.pageobjects;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ro.gazduire.stepdefinition.BrowserSteps;

public class WebHelper {
	private WebDriver driver;
	private static final Logger LOG = LogManager.getLogger(WebHelper.class);
	
	@FindBy(id = "gzd7_r18_c39")
	WebElement submitButton;

	@FindBy(id = "buttonRegisterCheck")
	WebElement searchButton;
	
	@FindBy(id = "gzd7_r13_c18")
	WebElement newInputField;

	@FindAll({ @FindBy(id = "gzd7_r18_c39")})
	public List<WebElement> inField;

	@FindAll({ @FindBy(id = "textAlreadyRegistered") })
	public List<WebElement> messageDisplayed;

	public WebHelper(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		LOG.info("Driver is being initiated");
	}

	public void waitForThread() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			LOG.info("Thread not available");
		}
		LOG.info("Waiting for Thread");
	}

	public WebElement findInputField(String label) {
		LOG.info("Found input field");
		return newInputField;
	}


	public List<WebElement> newMessage() {
		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(PageObjects.NEW_MESSAGE)));
		return messageDisplayed;
	}

	public WebElement newSearchButton() {
		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(PageObjects.NEW_BUTTON)));
		return searchButton;
	}

	public void clickButton() throws Throwable {
		submitButton.click();
		LOG.info("Found button to be clicked. Proceed on clicking it");
	}

	public void messageIsShown(String domain) {
		List<WebElement> messageAvailable = messageDisplayed;
		if (messageAvailable.isEmpty()) {
			BrowserSteps.availableDomainsFound.add(domain + ".ro");
		} else {
			newMessage();
		}
		LOG.info("The response message is being shown");
	}

	public void buttonIsClicked() throws Throwable {
		List<WebElement> searchButton = inField;
		if (searchButton.isEmpty()) {
			newSearchButton().click();
		} else {
			clickButton();
		}
		Thread.sleep(2000);
	}

	public WebElement locateInputField(WebElement input) {
		List<WebElement> inputs = inField;
		if (inputs.isEmpty()) {
			input = driver.findElement(By.id("registerName"));
		}
		return input;
	}

}
