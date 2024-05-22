@SearchFunctionalities
Feature: Validating the Search functionality of story

@StoryIterator @Search
  Scenario: To search stories inside Search page
    Given navigate to Dashboard Page
    When Click on Search link option in the left side pane inside the IGM application
    Then Enter Story SearchKey and verify the search result
    
@DealIterate @Search
  Scenario: To search Deals inside Deal Search page with iterator
    Given navigate to Dashboard Page
    When Click on the Deal Search link option in the left side pane inside the IGM application
    Then Enter values for all Deal Search filters and Verify the search result    
    
@SaveSearchPopup @Deals
  Scenario: To save a search without a title inside Deal Search page
    Given navigate to Dashboard Page
    When Click on Deal Search link option in the left side pane inside the IGM application
    And Click on save button
    Then Search should not get saved and a warning popup message should appeared