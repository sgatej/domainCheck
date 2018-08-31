package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebHelper {
	private WebDriver driver;

	public WebHelper(WebDriver driver) {
		this.driver = driver;
	}

	public void waitForThread() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}

	}

	public WebElement findInputField(String label) {

		return driver.findElement(By.id(PageObjects.INPUT_FIELD));
	}

	public WebElement findButtonOrHref(String buttonName) {
		WebDriverWait wait = new WebDriverWait(driver, 3);
		String buttonHrefXpathSelector = String.format(
				"//button[contains(., '%s')] | //a[contains(., '%s')] | //a[contains(@title, '%s')]", buttonName,
				buttonName, buttonName);
		By xpath = By.xpath(buttonHrefXpathSelector);
		wait.until(ExpectedConditions.elementToBeClickable(xpath));
		return driver.findElement(xpath);
	}

	public WebElement findDropdown(String label) {
		return driver.findElement(By.id(PageObjects.DROPDOWN_ID));
	}

	public WebElement findModal() {
		WebDriverWait wait = new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(PageObjects.MODAL_MESSAGE)));
		return driver.findElement(By.id(PageObjects.MODAL_MESSAGE));
	}
}
