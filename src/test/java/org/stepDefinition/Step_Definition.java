package org.stepDefinition;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.Locators.SearchElements;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.helper.Base_Class;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Step_Definition extends Base_Class{
	
	JavascriptExecutor JS = (JavascriptExecutor)driver;
	SearchElements s;
	
	private WebElement waitForElement(By locator, int timeoutInSeconds) throws InterruptedException {
        int attempts = 0;
        while (attempts < 3) {
            try {
                return driver.findElement(locator);
            } catch (Exception e) {
                Thread.sleep(timeoutInSeconds * 1000);
            }
            attempts++;
        }
        throw new InterruptedException("Element " + locator.toString() + " not found after " + timeoutInSeconds + " seconds.");
    }
	
	public static boolean checkTextInElement(WebElement element, String[] IndustryValues) {
	    if (element == null) {
	        return false; // Element not found
	    }
	    String elementText = element.getText();
	    for (String IndustryValue : IndustryValues) {
	        if (elementText.contains(IndustryValue)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	@Given("navigate to Dashboard Page")
	public void navigate_to_Dashboard_Page() throws InterruptedException, IOException {
		s = new SearchElements();
		
		s.getClientLogin().click();
		sleep(2000);
		
	    FileInputStream fis = new FileInputStream("C:\\Users\\diva6\\eclipse-workspace\\Informa_Search\\UserID.xlsx");
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(1);
        String id = row.getCell(0).getStringCellValue();
        String password = row.getCell(1).getStringCellValue();
	    
        s.getUsername().sendKeys(id);
        s.getPassword().sendKeys(password);
        s.getSignInButton().click();
		sleep(4000);
		s.getContinue().click();
		sleep(2000);
		wb.close();
	    
	}

	@When("Click on Search link option in the left side pane inside the IGM application")
	public void click_on_Search_link_option_in_the_left_side_pane_inside_the_IGM_application() throws InterruptedException {
		
		s.getSearch().click();
		sleep(10000);
	    
	}

	@Then("Enter Story SearchKey and verify the search result")
	public void enter_Story_SearchKey_and_verify_the_search_result() throws InterruptedException, IOException, AWTException {
		
		// Read the values from the Excel file
		FileInputStream fis = new FileInputStream("C:\\Users\\diva6\\eclipse-workspace\\Informa_Search\\Search_Cred.xlsx");
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sheet = wb.getSheetAt(0);

		// Iterate through each row in the sheet starting from the first indexed row (row 1)
		for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
			s = new SearchElements();
		    Row row = sheet.getRow(i);

		    // Iterate through each cell in the row
		    for (int j = 0; j < row.getLastCellNum(); j++) {
		        Cell cell = row.getCell(j);
		        if (cell != null && cell.getCellType() == CellType.STRING) {
		            // Read the keyword from the cell
		            String SearchKey = cell.getStringCellValue();
		            String[] keywords = SearchKey.split("\\s+");
		            // Locate the search bar
		            sleep(2000);
		            WebElement searchBar = s.getSearchBar();
		            searchBar.click();
		            searchBar.clear(); 
		            searchBar.sendKeys(SearchKey);
		            sleep(5000); //Short sleep to allow the page to load
		            // Locate results or no result message
		            List<WebElement> results = driver.findElements(By.className("content-block-headline"));
		            List<WebElement> noResults = driver.findElements(By.xpath("//h2[text()='Search for a story, analyst, or anything']"));

		            System.out.println("Validation part for " + SearchKey);
		            // Validate the results
		            if (!results.isEmpty()) {
		            	
                        boolean keywordFound = false;

                        // Iterate through search results
                        for (WebElement result : results) {
                            try {
                                result.click();
                                // Add static wait for the new page to load
                                sleep(5000);
                                String pageSource = driver.getPageSource();
                                // Check if the keyword or any part of it is present in the page source
                                for (String keyword : keywords) {
                                if (pageSource.contains(keyword) || pageSource.contains(SearchKey)) {
		                            keywordFound = true;
		                            Assert.assertTrue(keywordFound);
		                            System.out.println("Keyword '" + SearchKey + "' found in the clicked search result.");
		                            break;
		                        }
                                }
                                // Navigate back
                                NavigateBack();
                                //Static wait for the previous page to load and locate the search bar again
                                sleep(5000);
                                searchBar = driver.findElement(By.id("search"));
                            } catch (StaleElementReferenceException e) {
                                // Handle the stale element reference exception by re-acquiring the elements
                                // Re-acquire the results
                                results = driver.findElements(By.className("content-block-headline"));
                                continue;
                            }
                        }
                        // If no keyword is found, fail the test
                        if (!keywordFound) {
                            Assert.fail("None of the keywords were found in any of the results");
                        }
		            } else if (!noResults.isEmpty()) {
		                // No results found, verify the no result message
		                List<WebElement> elements = driver.findElements(By.cssSelector(".ember-view.list-group-item.search-filter"));

		                boolean allContainZero = true;
		                for (WebElement element : elements) {
		                    String text = element.getText();
		                    if (!text.contains("0")) {
		                        allContainZero = false;
		                        break;
		                    }
		                }
		                if (allContainZero) {
		                    System.out.println("No Search Result: Search count is '0' for the searched term");
		                } else {
		                    System.out.println("Search count is not '0' for the searched term");
		                }
		            }
		            sleep(4000);
		        }
		    }
		}
		wb.close();
	}

	@When("Click on the Deal Search link option in the left side pane inside the IGM application")
	public void click_on_the_Deal_Search_link_option_in_the_left_side_pane_inside_the_IGM_application() throws InterruptedException {
		s.getDealSearch().click();
		sleep(4000);
	}

	@Then("Enter values for all Deal Search filters and Verify the search result")
	public void enter_values_for_all_Deal_Search_filters_and_Verify_the_search_result() throws InterruptedException, AWTException, IOException {
		Robot r = new Robot();
		FileInputStream fis = new FileInputStream("C:\\Users\\diva6\\eclipse-workspace\\Informa_Search\\Search_Cred.xlsx");
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sheet = wb.getSheetAt(1);

		for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			s = new SearchElements();
		    Row row = sheet.getRow(rowIndex);
		    System.out.println("Deal Search of row: " +  rowIndex);
		    driver.findElement(By.xpath("//span[@role='button']")).click();
		    sleep(1000);
		    
		    //Date
		    Cell startDateCell = row.getCell(0);
		    Cell endDateCell = row.getCell(1);

		    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		    String startDate = dateFormat.format(startDateCell.getDateCellValue());
		    String endDate = dateFormat.format(endDateCell.getDateCellValue());

		    WebElement StartDateBox = s.getStartDateBox();
		    WebElement EndDateBox = s.getEndDateBox();

		    StartDateBox.click();
		    sleep(1000);

		    for (int a = 1; a <= 10; a++) {
		        r.keyPress(KeyEvent.VK_BACK_SPACE);
		        r.keyRelease(KeyEvent.VK_BACK_SPACE);
		    }
		    StartDateBox.sendKeys(startDate);
		    sleep(1000);
		    r.keyPress(KeyEvent.VK_ENTER);
		    r.keyRelease(KeyEvent.VK_ENTER);
		    sleep(1000);

		    EndDateBox.click();
		    sleep(1000);
		    for (int a = 1; a <= 10; a++) {
		        r.keyPress(KeyEvent.VK_BACK_SPACE);
		        r.keyRelease(KeyEvent.VK_BACK_SPACE);
		    }
		    EndDateBox.sendKeys(endDate);
		    sleep(1000);
		    r.keyPress(KeyEvent.VK_ENTER);
		    r.keyRelease(KeyEvent.VK_ENTER);
		    sleep(1000);

		    //Region
		    Cell Regioncell = row.getCell(2);
		    String RegioncellValue = (Regioncell != null) ? Regioncell.toString() : "No value";

		    // Locate the element and pass the value
		    if (!RegioncellValue.equals("No value")) {
		        s.getRegionFilter().click();
		        s.getRegionFilter().clear();
		        String[] Regionvalues = RegioncellValue.split(" ");
		        for (String Regionvalue: Regionvalues) {
		        	s.getRegionFilter().sendKeys(Regionvalue);
		            sleep(2000);
		            r.keyPress(KeyEvent.VK_ENTER);
		            r.keyRelease(KeyEvent.VK_ENTER);
		            sleep(1000);
		            s.getRegion().click();
		            sleep(1000);
		        }

		    } else {
		        System.out.println("Value for Region: No value");
		    }

		    //Issuer
		    Cell Issuercell = row.getCell(3);
		    String IssuercellValue = (Issuercell != null) ? Issuercell.toString() : "No value";

		    // Locate the element and pass the value
		    if (!IssuercellValue.equals("No value")) {
		        s.getIssuerFilter().click();
		        s.getIssuerFilter().clear();
		        s.getIssuerFilter().sendKeys(IssuercellValue);
		        sleep(3000);
		        r.keyPress(KeyEvent.VK_ENTER);
		        r.keyRelease(KeyEvent.VK_ENTER);
		        sleep(1000);
		        s.getRegion().click();
		        sleep(1000);
		    } else {
		        System.out.println("Value for Issuer: No value");
		    }

		  //Industry
		    Cell Industrycell = row.getCell(4);
		    String IndustrycellValue = (Industrycell != null) ? Industrycell.toString() : "No value";

		    // Locate the element and pass the value
		    if (!IndustrycellValue.equals("No value")) {
		        s.getIndustryFilter().click();
//		        WebElement IndustryFilt = s.getIndustryFilter();
//		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", IndustryFilt);
		        s.getIndustryFilter().clear();
		        String xpathLocator = String.format("//li[text()='%s']", IndustrycellValue);
		        WebElement IndustryOption = driver.findElement(By.xpath(xpathLocator));
		        IndustryOption.click();
		        sleep(2000);
		        s.getRegion().click();
		        sleep(1000);
		    } else {
		        System.out.println("Value for Industry: No value");
		    }

		    //Status 
		    Cell Statuscell = row.getCell(5);
		    String StatuscellValue = (Statuscell != null) ? Statuscell.toString() : "No value";

		    // Locate the element and pass the value
		    if (!StatuscellValue.equals("No value")) {
		    	s.getStatusFilter().click();
		        s.getStatusFilter().clear();
		        String[] Statusvalues = StatuscellValue.split(" ");
		        for (String Statusvalue: Statusvalues) {
		        	s.getStatusFilter().sendKeys(Statusvalue);
		            sleep(2000);
		            r.keyPress(KeyEvent.VK_ENTER);
		            r.keyRelease(KeyEvent.VK_ENTER);
		            sleep(1000);
		            s.getRegion().click();
		            sleep(1000);
		        }
		    } else {
		        System.out.println("Value for Status: No value");
		    }

		    //Currency
		    Cell Currencycell = row.getCell(6);
		    String CurrencycellValue = (Currencycell != null) ? Currencycell.toString() : "No value";

		    // Set value in the web element
		    if (!CurrencycellValue.equals("No value")) {
		        s.getCurrencyFilter().click();
		        s.getCurrencyFilter().clear();
		        String[] Currencyvalues = CurrencycellValue.split(" ");
		        for (String Currencyvalue: Currencyvalues) {
		        	s.getCurrencyFilter().sendKeys(Currencyvalue);
		            sleep(2000);
		            r.keyPress(KeyEvent.VK_ENTER);
		            r.keyRelease(KeyEvent.VK_ENTER);
		            sleep(1000);
		            s.getRegion().click();
		            sleep(1000);
		        }
		    } else {
		        System.out.println("Value for Currency: No value");
		    }

		    //AdditionalKeyword
		    Cell AdditionalKeywordcell = row.getCell(7);
		    String AdditionalKeywordcellValue = (AdditionalKeywordcell != null) ? AdditionalKeywordcell.toString() : "No value";

		    // Set value in the web element
		    if (!AdditionalKeywordcellValue.equals("No value")) {
		        s.getKeywordFilter().click();
		        s.getKeywordFilter().clear();
		        s.getKeywordFilter().sendKeys(AdditionalKeywordcellValue);
		        sleep(2000);
		        r.keyPress(KeyEvent.VK_ENTER);
		        r.keyRelease(KeyEvent.VK_ENTER);
		        sleep(1000);
		        s.getRegion().click();
		        sleep(1000);
		    } else {
		        System.out.println("Value for Additional Keyword: No value");
		    }
		    sleep(1000);
		    s.getFindDealsButton().click();
		    sleep(5000);
		    System.out.println("Validation of Deals search");

		    //Validation will get performs from here
		    try {
		        WebElement SearchPageResult = driver.findElement(By.xpath("//div[@class='search-results']"));

		        // Verify the date fields in the search results
		        if (SearchPageResult.isDisplayed()) {

		            //Date
		            Cell startDateCells = row.getCell(0);
		            Cell endDateCells = row.getCell(1);

		            SimpleDateFormat dateFormats = new SimpleDateFormat("MM-dd-yyyy");
		            String startDateString = dateFormats.format(startDateCells.getDateCellValue());
		            String endDateString = dateFormats.format(endDateCells.getDateCellValue());

		            LocalDate startDates = LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		            LocalDate endDates = LocalDate.parse(endDateString, DateTimeFormatter.ofPattern("MM-dd-yyyy"));

		            List < WebElement > dateElements = driver.findElements(By.xpath("//td[@class= 'deal-results-date']"));

		            for (int i = 0; i < dateElements.size(); i++) {

		                if (i % 2 == 0) {
		                    String dateString = dateElements.get(i).getText();
		                    try {
		                        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MMM-yyyy"));

		                        if ((date.isAfter(startDates) || date.isEqual(startDates)) && date.isBefore(endDates)) {
		                            System.out.println("Date at index " + i + " matches the searched date.");
		                        } else {
		                            System.out.println("Date at index " + i + " does not match the searched date.");
		                            Assert.fail();
		                        }
		                    } catch (DateTimeParseException e) {
		                        System.out.println("Error parsing date at index " + i + ": " + e.getMessage());
		                    }
		                }
		            }

		            // Click on the first search result
		            sleep(2000);
		            WebElement firstResult = s.getFirstResult();
		            firstResult.click();
		            sleep(5000);

		            //Status
		            Cell cellStatus = row.getCell(5);
		            String cellValueStatus = (cellStatus != null) ? cellStatus.toString() : "No value";

		            // Locate the element and verify
		            if (!cellValueStatus.equals("No value")) {
		                String StatusField = s.getStatusField().getText();
		                String[] StatusValues = cellValueStatus.split(" ");
		                boolean StatusMatch = false;
		                for (String StatusValue : StatusValues) {
		         		   StatusValue = StatusValue.trim();
		         		   if (StatusField.contains(StatusValue)){
		         		      // Match found
		                         StatusMatch = true;
		                         // Print the value that matches
		                         System.out.println("Status field matches with the: " + StatusValue);
		                         break; // Stop iteration since a match is found  
		         		   }
		                 }
		                if (!StatusMatch) {
		                    System.out.println("Status field does not matches with any of the search key");
		                    Assert.fail();
		                }
		            } else {
		                System.out.println("No value Passed for Status");
		            }


		            //Currency
		            Cell cellCurrency = row.getCell(6);
		            String cellValueCurrency = (cellCurrency != null) ? cellCurrency.toString() : "No value";

		            // Locate the element and verify
		            if (!cellValueCurrency.equals("No value")) {
		                String CurrencyField = s.getCurrencyField().getText();
		                String[] CurrencyValues = cellValueCurrency.split(" ");
		                boolean CurrencyMatch = false; 
                        for (String CurrencyValue: CurrencyValues) {
		                   CurrencyValue = CurrencyValue.trim();
		                   if (CurrencyField.contains(CurrencyValue)) {
		                   // Match found
		                   CurrencyMatch = true;
		                  // Print the value that matches
		                    System.out.println("Currency field matches with the: " + CurrencyValue);
		                    break;
		                   }
		                }
                        if (!CurrencyMatch) {
                            System.out.println("Currency field does not matches with any of the search key");
                            Assert.fail();
                        }
		            } else {
		                System.out.println("No value Passed for Currency");
		            }

		            //Region
		            Cell cellRegion = row.getCell(2);
		            String cellValueRegion = (cellRegion != null) ? cellRegion.toString() : "No value";

		            // Locate the element and verify
		            if (!cellValueRegion.equals("No value")) {
		                String RegionField = s.getRegionField().getText();
		                String[] RegionValues = cellValueRegion.split(" ");
		                boolean RegionMatch = false; 
		                for (String RegionValue: RegionValues) {
		                    RegionValue = RegionValue.trim();
		                   if (RegionField.contains(RegionValue)) {
		                   RegionMatch = true;
		                   System.out.println("Region field matches with the: " + RegionValue);
		                    break;
		                   }
		                }
		                if (!RegionMatch) {
		                    System.out.println("Region field does not matches with any of the search key");
		                    Assert.fail();
		                }
		            } else {
		                System.out.println("No value Passed for Region");
		            }

		            //Issuer
		            Cell cellIssuer = row.getCell(3);
		            String cellValueIssuer = (cellIssuer != null) ? cellIssuer.toString() : "No value";

		            // Locate the element and verify
		            if (!cellValueIssuer.equals("No value")) {
		                String IssuerField = s.getIssuerField().getText();
		                if (IssuerField.contains(cellValueIssuer)) {
		                    System.out.println("The loaded page contains the term " + cellValueIssuer);
		                } else {
		                    System.out.println("The loaded page does not contain the term" + cellValueIssuer);
		                    Assert.fail();
		                }
		                sleep(1000);
		            } else {
		                System.out.println("No value Passed for Issuer");
		            }


		          //Industry
		            Cell cellIndustry = row.getCell(4);
		            String cellValueIndustry = (cellIndustry != null) ? cellIndustry.toString() : "No value";
		            String[] IndustryValues = cellValueIndustry.split(" / ");

		            // Locate the element and verify
		            if (!cellValueIndustry.equals("No value")) {
		                WebElement Industry = null;
		                WebElement IndustryClass = null;
		                WebElement IndustrySubClass = null;

		                try {
		                    Industry = s.getIndustry();
		                } catch (NoSuchElementException e) {
		                    // Element not found
		                }

		                try {
		                    IndustryClass = s.getIndustryClass();
		                } catch (NoSuchElementException e) {
		                    // Element not found
		                }

		                try {
		                    IndustrySubClass = s.getIndustrySubClass();
		                } catch (NoSuchElementException e) {
		                    // Element not found
		                }

		                // Check if any element contains the text from the Excel sheet
		                boolean isMatchFound = false;

		                if (checkTextInElement(Industry, IndustryValues)) {
		                    System.out.println("Industry filter search keyword found in Industry");
		                    isMatchFound = true;
		                }

		                if (checkTextInElement(IndustryClass, IndustryValues)) {
		                    System.out.println("Industry filter search keyword found in IndustryClass");
		                    isMatchFound = true;
		                }

		                if (checkTextInElement(IndustrySubClass, IndustryValues)) {
		                    System.out.println("Industry filter search keyword found in IndustrySubClass");
		                    isMatchFound = true;
		                }

		                if (!isMatchFound) {
		                    System.out.println("No match found in any of the elements.");
		                    Assert.fail();
		                }

		            } else {
		                System.out.println("No value Passed for Industry");
		            }


		            //AdditionalKeyword
		            Cell cellAdditionalKeyword = row.getCell(7);
		            String cellValueAdditionalKeyword = (cellAdditionalKeyword != null) ? cellAdditionalKeyword.toString() : "No value";

		            // Set value in the web element
		            if (!cellValueAdditionalKeyword.equals("No value")) {
		                String pageSource = driver.getPageSource();

		                // Check if the word "Test" is present in the page source
		                if (pageSource.contains(cellValueAdditionalKeyword)) {
		                    System.out.println(cellValueAdditionalKeyword + "is present on the web page.");
		                } else {
		                    System.out.println(cellValueAdditionalKeyword + "is not present on the web page.");
		                    Assert.fail();
		                }
		                sleep(1000);
		            } else {
		                System.out.println("No value Passed for Additional Keyword");
		            }
		        }
		    } catch (NoSuchElementException e) {
		        // If the search results element is not found, check for the "no results found" message
		        try {
		            WebElement noResultsMessage = driver.findElement(By.xpath("//h2[text()='No results match your search']"));
		            if (noResultsMessage.isDisplayed()) {
		                System.out.println("No results found for the search combinations.");
		            } else {
		                System.out.println("Unexpected result: no search results and no 'no results found' message.");
		            }
		        } catch (NoSuchElementException ex) {
		            // Handle case where neither element is found
		            System.out.println("No such element found for either search results or no results message.");
		        }
		    }
		    // Navigate back twice to the search filter page
		    sleep(2000);
		    NavigateBack();
		    sleep(2000);
		    s.getBackToSearchButton().click();
		    sleep(2000);
		    s.getResetFilterButton().click();
		    sleep(3000);
		}
		// Close the workbook and the FileInputStream
		wb.close();
	}

	@When("Click on Deal Search link option in the left side pane inside the IGM application")
	public void click_on_Deal_Search_link_option_in_the_left_side_pane_inside_the_IGM_application() throws InterruptedException {
		
		driver.findElement(By.xpath("//span[text()='Deal Search']")).click();
		sleep(2000);
	    
	}

	@When("Click on save button")
	public void click_on_save_button() {
		
		WebElement saveButton = driver.findElement(By.xpath("//button[text()='Save']"));
        saveButton.click();
	    
	}

	@Then("Search should not get saved and a warning popup message should appeared")
	public void search_should_not_get_saved_and_a_warning_popup_message_should_appeared() throws InterruptedException {
		
		WebElement popup = waitForElement(By.className("message"), 10);

        String popupText = popup.getText();

        String expectedText = "Please add a title to your saved search";
        
//        Assert.assertEquals(popupText, expectedText, "Popup text verification failed!");
        
        if (popupText.equals(expectedText)) {
            System.out.println("Popup text verification successful!");
        } else {
            System.out.println("Popup text verification failed! Expected: " + expectedText + ", Actual: " + popupText);
        }
	    
	}

}
