package stepDefinition;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import pageObjects.PageObjects;
import pageObjects.WebHelper;

public class BrowserSteps {

	private static final Logger LOG = LogManager.getLogger(BrowserSteps.class);
	private WebDriver driver;

	WebHelper webHelper;

	public BrowserSteps() {

		System.setProperty("webdriver.chrome.driver", "src/chromedriver");

//		ChromeOptions firefoxOptions = new ChromeOptions();
//		firefoxOptions.setCapability("marionette", false);
		driver = new ChromeDriver();
		LOG.info("A new browser session is firing up");
		driver.get(PageObjects.url);
		LOG.info("The webpage is opened");
		webHelper = new WebHelper(driver);
	}

	@Given("^I start a new browser session for \"([^\"]*)\"$")
	public void i_start_a_new_browser_session_for(String url) throws Throwable {
		LOG.info("Start new browser session");
		driver.manage().window().maximize();
	}

	@Given("^I fill in the field$")
	public void i_fill_in_the_field(DataTable table) throws Throwable {
		webHelper.waitForThread();
		table.raw().forEach(inputRow -> {
			WebElement input = webHelper.findInputField(inputRow.get(0));
			input.clear();
			input.sendKeys(" ");
			input.sendKeys(Keys.BACK_SPACE);
			//
			input.sendKeys(inputRow.get(1));
			input.sendKeys(Keys.TAB);
			LOG.info("The fields are being filled in");
		});
	}

	@Given("^I click on \"([^\"]*)\"$")
	public void i_click_on(String buttonName) throws Throwable {
		webHelper.findButtonOrHref(buttonName).click();
		LOG.info("Found field to be clicked. Proceed on clicking it");
	}

	@Given("^I see the  response message \"([^\"]*)\"$")
	public void i_see_the_response_message(String message) throws Throwable {
		try {
			WebElement modal = webHelper.findModal();
			Assert.assertEquals(
					modal.findElements(By.xpath(String.format("//*[contains(text(), '%s')]", message))).size(), 1);
		} catch (AssertionError | TimeoutException | NoSuchElementException e) {
			EmailSteps.sendEmail();
		}
		LOG.info("The response message is being shown");
	}

	@Given("^I fill in the field using ([^\\\"]*)$")
	public void fillInField(String domainName) throws Throwable {
		try {
			webHelper.waitForThread();
			WebElement newDomainName = webHelper.findInputField(domainName);
			newDomainName.clear();
			newDomainName.sendKeys(domainName);
			newDomainName.sendKeys(Keys.TAB);
			LOG.info("The field is being populated with " + domainName);
		} catch (Exception e) {
			System.out.println("Warning: " + domainName + "domain name could not be entered");
		}
	}

	@Given("^I select values for the following dropdown field$")
	public void selectDropdown(DataTable table) throws Throwable {
		table.raw().forEach(inputRow -> {
			WebElement input = webHelper.findDropdown(inputRow.get(0));
			Select select = new Select(input);
			select.selectByVisibleText(inputRow.get(1));
			LOG.info("The value from the dropdown list is selected");
		});
	}

	@Then("^I close the browser$")
	public void closeBrowser() throws Throwable {
		driver.close();
	}

}
