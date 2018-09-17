package com.eurodns.stepdefinition;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;

import com.eurodns.pageobjects.PageObjects;
import com.eurodns.pageobjects.WebHelper;
import com.eurodns.utils.ReadFile;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class BrowserSteps {

	private static final Logger LOG = LogManager.getLogger(BrowserSteps.class);
	private WebDriver driver;
	static Set<String> availableDomainsFound = new HashSet<>();

	WebHelper webHelper;
	ReadFile readFile;

	public BrowserSteps() {

		System.setProperty("webdriver.gecko.driver", "src/geckodriver");
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setCapability("marionette", false);
		driver = new FirefoxDriver(firefoxOptions);
		LOG.info("A new browser session is firing up");
		driver.get(PageObjects.url);
		LOG.info("The webpage is opened");
		webHelper = new WebHelper(driver);
		readFile = new ReadFile();
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

	// @Given("^I search all the domains from my list$")
	// public void i_search_all_the_domains_from_my_list(String fieldName)
	// throws Throwable {
	// WebElement field = webHelper.findInputField(fieldName);
	// List<String> domains = new ReadFile().readLines();
	// for (String domain : domains) {
	// field.clear();
	// field.sendKeys(domain);
	//
	// }
	// }

	@Given("^I search for a set of domains located in a list$")
	public void searchUsingList() throws Throwable {
		webHelper.waitForThread();
		WebElement input = webHelper.findInputField("search");
		List<String> domains = readFile.readLines();
		WebElement newSearchButton;
		for (String domain : domains) {
			List<WebElement> inputs = driver.findElements(By.id("gzd7_r13_c18"));
			if (inputs.isEmpty()) {

				input = driver.findElement(By.id("registerName"));
			}
			input.clear();
			LOG.info("Searching for " + domain);
			input.sendKeys(domain);

			List<WebElement> searchButton = driver.findElements(By.id("gzd7_r18_c39"));

			if (searchButton.isEmpty()) {

				newSearchButton = driver.findElement(By.id("buttonRegisterCheck"));
				newSearchButton.click();

			} else {
				i_click_on("Cauta");
			}

			Thread.sleep(1000);
			List<WebElement> messageAvailable = driver.findElements(By.id("textAlreadyRegistered"));
			if (messageAvailable.isEmpty()) {
				// WebElement newMessageAvailable =
				// driver.findElement(By.className("domain-available"));
				availableDomainsFound.add(domain + ".ro");
			} else {
				driver.findElement(By.id("textAlreadyRegistered"));
			}
			Thread.sleep(1000);
			LOG.info("The response message is being shown");

		}
		EmailSteps.sendEmail();
		driver.close();
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