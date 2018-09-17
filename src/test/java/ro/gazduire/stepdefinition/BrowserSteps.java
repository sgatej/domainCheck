package ro.gazduire.stepdefinition;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import cucumber.api.java.en.Given;
import ro.gazduire.utils.ReadFile;
import ro.gazdurire.pageobjects.PageObjects;
import ro.gazdurire.pageobjects.WebHelper;

public class BrowserSteps {

	private static final Logger LOG = LogManager.getLogger(BrowserSteps.class);
	private WebDriver driver;
	public static Set<String> availableDomainsFound = new HashSet<>();

	WebHelper webHelper;
	ReadFile readFile;

	public BrowserSteps() {

		System.setProperty("webdriver.gecko.driver", "src/geckodriver");
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setCapability("marionette", true);
		driver = new FirefoxDriver(firefoxOptions);
		LOG.info("A new browser session is firing up");
		driver.get(PageObjects.url);
		LOG.info("The webpage is opened");
		webHelper = new WebHelper(driver);
		readFile = new ReadFile();
	}

	@Given("^I search for a set of domains located in a list$")
	public void searchUsingList() throws Throwable {
		webHelper.waitForThread();
		WebElement input = webHelper.findInputField("search");
		List<String> domains = readFile.readLines();
		for (String domain : domains) {
			input = webHelper.locateInputField(input);
			input.clear();
			LOG.info("Searching for " + domain + ".ro");
			input.sendKeys(domain);
			webHelper.buttonIsClicked();
			webHelper.messageIsShown(domain);
		}
		EmailSteps.sendEmail();
		driver.close();
	}

}
