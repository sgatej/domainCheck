Feature: Test available domain

  #Background: Given I start a new browser session for "newurl"

  #Scenario Outline: Test multiple users as available domains
  #  Given I fill in the field using <domainName>
   # And I click on "Cauta"
    #And I see the  response message "toni.ro nu este momentan disponibil!"
    #Then I close the browser

    #Examples: 
     # | domainName |
      #| toni       |
      #| sashadj    |

  Scenario: Test available domains using a list

   Given I search for a set of domains located in a list