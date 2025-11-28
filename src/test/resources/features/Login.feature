Feature: Login Functionality

  Scenario: Successful login with valid credentials
    Given I open the browser and navigate to Login page
    When I enter username "tomsmith" and password "SuperSecretPassword!"
    And I click the Login button
    Then I should see the Secure Area page