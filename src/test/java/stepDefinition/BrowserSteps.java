package stepDefinition;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;


import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import pageObjects.PageObjects;
import pageObjects.WebHelper;

public class BrowserSteps {
	//@Autowired
	//WebHelper webHelper;
	private WebDriver driver;
	
	WebHelper webHelper ;
	
	public BrowserSteps() {
		
		System.setProperty("webdriver.gecko.driver", "D:\\Workspaces\\Domains\\Domain\\geckodriver.exe");
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities = DesiredCapabilities.firefox();
		capabilities.setBrowserName("firefox");
		capabilities.setVersion("your firefox version");
		capabilities.setPlatform(Platform.WINDOWS);
		capabilities.setCapability("marionette", false);

		driver = new FirefoxDriver(capabilities);
		driver.get(PageObjects.url);
		webHelper = new WebHelper(driver);
	}

	@Given("^I start a new browser session for \"([^\"]*)\"$")
	public void i_start_a_new_browser_session_for(String url) throws Throwable {
		
		driver.manage().window().maximize();
	}

	@Given("^I fill in the field$")
	public void i_fill_in_the_field(DataTable table) throws Throwable {
		//WebHelper webHelper = new WebHelper(driver);

		webHelper.waitForThread();
		table.raw().forEach(inputRow -> {
			WebElement input = webHelper.findInputField(inputRow.get(0));
			input.clear();
			// angular doesn't clear the ng-model without this workaround:
			input.sendKeys(" ");
			input.sendKeys(Keys.BACK_SPACE);
			//
			input.sendKeys(inputRow.get(1));
			input.sendKeys(Keys.TAB);
		});
	}

	@Given("^I click on \"([^\"]*)\"$")
	public void i_click_on(String buttonName) throws Throwable {
	//	WebHelper webHelper = new WebHelper(driver);
		webHelper.findButtonOrHref(buttonName).click();
	}

	@Given("^I see the  response message \"([^\"]*)\"$")
	public void i_see_the_response_message(String message) throws Throwable {
		//WebHelper webHelper = new WebHelper(driver);

		try {
			WebElement modal = webHelper.findModal();
			Assert.assertEquals(
					modal.findElements(By.xpath(String.format("//*[contains(text(), '%s')]", message))).size(), 1);
		} catch (AssertionError | TimeoutException | NoSuchElementException e) {
			// modal.findElement(By.className("domain-available"));
			EmailSteps.sendEmail();
		}
		driver.close();
	}

	@Given("^I select values for the following dropdown field$")
	public void selectDropdown(DataTable table) throws Throwable {
	//	WebHelper webHelper = new WebHelper(driver);
		table.raw().forEach(inputRow -> {
			WebElement input = webHelper.findDropdown(inputRow.get(0));
			Select select = new Select(input);
			select.selectByVisibleText(inputRow.get(1));
		});
	}
}
