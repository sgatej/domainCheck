Feature: Test more domains

Scenario: Test that Toni appears in the available domains
Given I start a new browser session for "url"
And I fill in the field 
|Input|toni|
And I click on "Cauta"
And I see the  response message "toni.ro nu este momentan disponibil!"
#And I select values for the following dropdown field
#|Value|net|
#And I click on "Cauta"
