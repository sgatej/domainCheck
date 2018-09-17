package ro.gazduire.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = { "ro.gazduire.stepdefinition" }, tags = { "~@ignore",
		"~@skip" }, plugin = { "html:target/cucumber-html-report", "json:target/cucumber.json", "progress",
				"usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml" })
public class CucumberRunnerTest {

}
