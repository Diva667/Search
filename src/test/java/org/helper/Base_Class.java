package org.helper;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Base_Class {
	
public  static WebDriver driver;
	
	public static  void openChromeBrowser() {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\diva6\\eclipse-workspace\\Informa_Search\\Chrome_Driver_124\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().deleteAllCookies();
	}
	
	
	public static void sleep(long l) throws InterruptedException {
	       
			Thread.sleep(l);
		}
	
	public static void buttonClick(WebElement element) {
		element.click();
		}
	
	public static void maximizeWindow() {
		driver.manage().window().maximize();
		}
	
	public static void NavigateBack() {
		driver.navigate().back();
		}
	
	public static void impWait(int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		}


}
