#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@Login
Feature: Test the Login functionality of the application

  Background: 
    Given I launch the Wordpress application or Wordpress Url

  Scenario: Valid Login
    When I Enter valid login credentials
    Then I should land on homescreen

  Scenario: Invalid Login
    When I Enter invalid login credentials
    Then I should not land on homescreen

  Scenario: Login to the Site and search for Photography
    When I Enter valid login credentials
    Then I should land on homescreen
    And I Search for "Photography"
    Then I Select the First item in the search list

  Scenario: Login to the Site and search for Food
    When I Enter valid login credentials
    Then I should land on homescreen
    And I Search for "Food"
    Then I Select the First item in the search list

  Scenario: Login to the Site and search for Ted
    When I Enter valid login credentials
    Then I should land on homescreen
    And I Search for "Ted"
    Then I Select the First item in the search list

  Scenario: Login to the Site and search for Watch
    When I Enter valid login credentials
    Then I should land on homescreen
    And I Search for "Watch"
    Then I Select the First item in the search list
