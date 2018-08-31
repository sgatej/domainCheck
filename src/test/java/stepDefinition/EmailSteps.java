package stepDefinition;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import pageObjects.PageObjects;

public class EmailSteps {

	public static void sendEmail() {
		try {
			Email email = new SimpleEmail();

			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator(PageObjects.MY_EMAIL, PageObjects.MY_PASSWORD));
			email.setSSLOnConnect(true);
			email.setFrom(PageObjects.MY_EMAIL);
			email.setSubject("Domain available");
			email.setMsg("The domain you are searching for is now available");
			email.addTo(PageObjects.FRIEND_EMAIL);
			email.send();
			System.out.println("Email Sent!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
