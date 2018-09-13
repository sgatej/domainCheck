package com.eurodns.stepdefinition;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.eurodns.pageobjects.PageObjects;

public class EmailSteps {
	private static final Logger LOG = LogManager.getLogger(EmailSteps.class);

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
			LOG.info("The email has been successfully sent!");
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
