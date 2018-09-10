package pageObjects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebHelper {
	private WebDriver driver;
	private static final Logger LOG = LogManager.getLogger(WebHelper.class);

	public WebHelper(WebDriver driver) {
		this.driver = driver;
		LOG.info("Driver is being initiated");
	}

	public void waitForThread() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
		LOG.info("Waiting for Thread");
	}

	public WebElement findInputField(String label) {
		LOG.info("Found input field");
		return driver.findElement(By.id(PageObjects.INPUT_FIELD));
	}

	public WebElement findButtonOrHref(String buttonName) {
		WebDriverWait wait = new WebDriverWait(driver, 3);
		String buttonHrefXpathSelector = String.format(
				"//button[contains(., '%s')] | //a[contains(., '%s')] | //a[contains(@title, '%s')]", buttonName,
				buttonName, buttonName);
		By xpath = By.xpath(buttonHrefXpathSelector);
		wait.until(ExpectedConditions.elementToBeClickable(xpath));
		LOG.info("Button found. Proceed on clicking it");
		return driver.findElement(xpath);
	}

	public WebElement findDropdown(String label) {
		LOG.info("DropDown found");
		return driver.findElement(By.id(PageObjects.DROPDOWN_ID));
	}

	public WebElement findModal() {
		WebDriverWait wait = new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(PageObjects.MODAL_MESSAGE)));
		LOG.info("The Response message available");
		return driver.findElement(By.id(PageObjects.MODAL_MESSAGE));
	}
}
