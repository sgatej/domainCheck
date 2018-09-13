Feature: Test available domain in eurodns.com
@ignore
Scenario: Test available domains using a list 
	Given I search for a set of domains located in a list
	And I click on "bulk search"