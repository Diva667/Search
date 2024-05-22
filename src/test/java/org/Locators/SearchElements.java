package org.Locators;

import org.helper.Base_Class;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SearchElements extends Base_Class {
	
	public SearchElements() {
		PageFactory.initElements(driver, this);
	}
	
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//a[@class= 'client-login']")
	private WebElement clientLogin;
	
	public WebElement getClientLogin() {
		return clientLogin;
	}
	
	public WebElement getUsername() {
		return username;
	}
	
	public WebElement getPassword() {
		return password;
	}
	
	public WebElement getSignInButton() {
		return signInButton;
	}
	public WebElement getContinue() {
		return Continue;
	}
	
	public WebElement getSearch() {
		return search;
	}
	
	public WebElement getDealSearch() {
		return dealSearch;
	}
	
	public WebElement getRegionFilter() {
		return RegionFilter;
	}
	
	public WebElement getStatusFilter() {
		return StatusFilter;
	}
	
	public WebElement getIssuerFilter() {
		return IssuerFilter;
	}
	
	public WebElement getIndustryFilter() {
		return IndustryFilter;
	}
	
	public WebElement getCurrencyFilter() {
		return CurrencyFilter;
	}
	
	public WebElement getKeywordFilter() {
		return KeywordFilter;
	}
	
	public WebElement getFindDealsButton() {
		return FindDealsButton;
	}
	
	public WebElement getRegion() {
		return Region;
	}
	
	public WebElement getBackToSearchButton() {
		return BackToSearchButton;
	}

	public WebElement getResetFilterButton() {
		return ResetFilterButton;
	}


	public WebElement getSearchBar() {
		return SearchBar;
	}

	public WebElement getStartDateBox() {
		return StartDateBox;
	}

	public WebElement getEndDateBox() {
		return EndDateBox;
	}
	
	public WebElement getFirstResult() {
		return FirstResult;
	}

	public WebElement getStatusField() {
		return StatusField;
	}

	public WebElement getCurrencyField() {
		return CurrencyField;
	}

	public WebElement getRegionField() {
		return RegionField;
	}

	public WebElement getIssuerField() {
		return IssuerField;
	}

	public WebElement getIndustry() {
		return Industry;
	}

	public WebElement getIndustryClass() {
		return IndustryClass;
	}

	public WebElement getIndustrySubClass() {
		return IndustrySubClass;
	}

	@CacheLookup
	@FindBy(xpath = "//input[@type='text']" )
	private WebElement username;
	
	@CacheLookup
	@FindBy(xpath = "//input[@type='password']")
	private WebElement password;
	
	@CacheLookup
	@FindBy(xpath = "//button[text()='Sign In']")
	private WebElement signInButton;
	
	@CacheLookup
	@FindBy(xpath = "//span[text()='Continue']")
	private WebElement Continue;
	
	@CacheLookup
	@FindBy(xpath = "//span[text()='Search']")
	private WebElement search;
	
	@CacheLookup
	@FindBy(xpath = "//span[text()='Deal Search']")
	private WebElement dealSearch;
	
	@FindBy(xpath = "(//input[@type='search'])[1]")
	private WebElement RegionFilter;
	
	@CacheLookup
	@FindBy(xpath = "(//input[@type='search'])[2]")
	private WebElement StatusFilter;
	
	@CacheLookup
	@FindBy(xpath = "(//input[@type='search'])[3]")
	private WebElement IssuerFilter;
	
	@CacheLookup
	@FindBy(xpath = "(//input[@type='search'])[4]")
	private WebElement IndustryFilter;
	
	@CacheLookup
	@FindBy(xpath = "(//input[@type='search'])[5]")
	private WebElement CurrencyFilter;
	
	@CacheLookup
	@FindBy(xpath = "(//input[@type='text'])[4]")
	private WebElement KeywordFilter;
	
	@CacheLookup
	@FindBy(xpath = "//button[text()='Find Deals']")
	private WebElement FindDealsButton;

	@CacheLookup
	@FindBy(xpath = "(//label[@class='control-label'])[2]")
	private WebElement Region;
	
	@CacheLookup
	@FindBy(xpath = "//button[text()='Back To Search']")
	private WebElement BackToSearchButton;
	
	@CacheLookup
	@FindBy(xpath = "//button[text()='Reset Filters']")
	private WebElement ResetFilterButton;
	
	@CacheLookup
	@FindBy(xpath = "//input[@id='search']")
	private WebElement SearchBar;
	
	@CacheLookup
	@FindBy(xpath = "(//input[@type='text'])[2]")
	private WebElement StartDateBox;
	
	@CacheLookup
	@FindBy(xpath = "(//input[@type='text'])[3]")
	private WebElement EndDateBox;
	
	@CacheLookup
	@FindBy(xpath = "(//a[@class='ember-view'])[10]")
	private WebElement FirstResult;

	@CacheLookup
	@FindBy(xpath = "//*[contains(text(),'Status: ')]//following::span[1]")
	private WebElement StatusField;
	
	@CacheLookup
	@FindBy(xpath = "//*[contains(text(),'Currency: ')]//following::span[1]")
	private WebElement CurrencyField;
	
	@CacheLookup
	@FindBy(xpath = "//*[contains(text(),'Regions')]//following::span[1]")
	private WebElement RegionField;
	
	@CacheLookup
	@FindBy(xpath = "//*[contains(text(),'Issuer: ')]//following::span[1]")
	private WebElement IssuerField;
	
	@CacheLookup
	@FindBy(xpath = "//*[contains(text(),'Industry: ')]//following::span[1]")
	private WebElement Industry;
	
	@CacheLookup
	@FindBy(xpath = "//*[contains(text(),'Industry Class: ')]//following::span[1]")
	private WebElement IndustryClass;
	
	@CacheLookup
	@FindBy(xpath = "//*[contains(text(),'Industry Subclass: ')]//following::span[1]")
	private WebElement IndustrySubClass;

}
