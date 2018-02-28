package org.itstep.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.itstep.model.Account;
import org.itstep.model.Good;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ImitatorAmazon {
	
	public static final String AMAZON_URL = "https://www.amazon.com";

	public WebDriver getWebDriver() {
		
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + System.getProperty("file.separator") + "chromedriver.exe");
		
		ChromeOptions options = new ChromeOptions();
		WebDriver chromeDriver = new ChromeDriver(options);
		
		chromeDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		chromeDriver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		
		return chromeDriver;
	}
	
	public WebDriver registerAccount(Account account) {
		
		WebDriver driver = getWebDriver();
		driver.manage().window().maximize();
		driver.get(AMAZON_URL);
		Timer.getTimeInSeconds(10);
		WebElement registerBlock = driver.findElement(By.id("nav-link-accountList"));
		String registerElement = registerBlock.getAttribute("href");
			if(!registerBlock.getAttribute("href").contains(AMAZON_URL)) {
				registerElement = AMAZON_URL + registerElement;
			}
		
		driver.get(registerElement);
		Timer.getTimeInSeconds(4);
		
		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
		Timer.getTimeInSeconds(4);
		
		WebElement registerButton = driver.findElement(By.id("createAccountSubmit"));
		String regElement = registerButton.getAttribute("href");
			if(!registerButton.getAttribute("href").contains(AMAZON_URL)) {
				regElement = AMAZON_URL + regElement;
			}
		driver.get(regElement);
		Timer.getTimeInSeconds(6);
		
		currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
		Timer.getTimeInSeconds(4);
		
		WebElement nameElement = driver.findElement(By.id("ap_customer_name"));
		WebElement emailElement = driver.findElement(By.id("ap_email"));
		WebElement passwordElement = driver.findElement(By.id("ap_password"));
		WebElement rePasswordElement = driver.findElement(By.id("ap_password_check"));
		WebElement createAccountElement = driver.findElement(By.id("continue"));
		nameElement.sendKeys(account.getName());
		emailElement.sendKeys(account.getEmailUser());
		passwordElement.sendKeys(account.getPassword());
		rePasswordElement.sendKeys(account.getPassword());
		createAccountElement.submit();
		Timer.getTimeInSeconds(20);
		
		currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
		Timer.getTimeInSeconds(3);
		
		return driver;
	}
	
	public Good searchGood(String asin) {
		Good good = new Good();
		WebDriver driver = getWebDriver();
		driver.manage().window().maximize();
		driver.get(AMAZON_URL);
		Timer.getTimeInSeconds(10);
		
		WebElement searchBlock = driver.findElement(By.id("twotabsearchtextbox"));
		searchBlock.sendKeys(asin);
		List<WebElement> submitElements = driver.findElements(By.tagName("input"));
		for (WebElement submitElement : submitElements) {
			if(submitElement.getAttribute("type").equals("submit")) {
				submitElement.submit();
				break;
			}
		}
		
		Timer.getTimeInSeconds(6);
		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
		Timer.getTimeInSeconds(3);
		
		WebElement blockElement = driver.findElement(By.id("s-results-list-atf"));
		List<WebElement> liElements = blockElement.findElements(By.tagName("li"));
		for (WebElement liElement : liElements) {
			good.setAsinGood(asin);
			
			List<WebElement> aElements = liElement.findElements(By.tagName("a"));
			for (WebElement aElement : aElements) {
				if(aElement.getAttribute("class").contains("s-access-detail-page")) {
					String hrefElement = aElement.getAttribute("href");
					if(!hrefElement.contains(AMAZON_URL)) {
						hrefElement = AMAZON_URL + hrefElement;
					}
					good.setUrl(hrefElement);
					WebElement h2Element = aElement.findElement(By.tagName("h2"));
					String nameElement = h2Element.getText();
					good.setName(nameElement);
				}
			}
			List<WebElement> spanElements = liElement.findElements(By.tagName("span"));
			for (WebElement spanElement : spanElements) {
				if(spanElement.getAttribute("class").equals("sx-price-whole")) {
				String priceElement = spanElement.getText();
				good.setPrice(priceElement);
				}
			}
		}
		Timer.getTimeInSeconds(5);
		driver.quit();
		
	return good;	
	}

	
	public WebDriver getAddToCart(WebDriver driver, String asinItem) {
		
			if(asinItem == null) {
				return null;
			}
			
			WebElement searchElement = driver.findElement(By.id("twotabsearchtextbox"));
			searchElement.clear();
			searchElement.sendKeys(asinItem);
			List<WebElement> searchButton = driver.findElements(By.tagName("input"));
			for (WebElement searchButtonElement : searchButton) {
				if(searchButtonElement.getAttribute("type").equals("submit")) {
					searchButtonElement.submit();
					break;
				}
			}
			
			Timer.getTimeInSeconds(5);
			String currentUrl = driver.getCurrentUrl();
			driver.get(currentUrl);
			Timer.getTimeInSeconds(15);
			
			WebElement ulElement = driver.findElement(By.id("s-results-list-atf"));
			List<WebElement> linkAElement = ulElement.findElements(By.tagName("a"));
			for (WebElement linkElement : linkAElement) {
				if(linkElement.getAttribute("class").contains("s-access-detail-page")) {
					String hrefElement = linkElement.getAttribute("href");
					if(!linkElement.getAttribute("href").contains(AMAZON_URL)) {
						hrefElement = AMAZON_URL + hrefElement;
					}
					driver.get(hrefElement);
					break;
				}
			}
			
			Timer.getTimeInSeconds(5);
			currentUrl = driver.getCurrentUrl();
			driver.get(currentUrl);
			Timer.getTimeInSeconds(15);
			
			WebElement addToCartBtn = driver.findElement(By.id("add-to-cart-button"));
			addToCartBtn.submit();
			Timer.getTimeInSeconds(10);
		
		return driver;
	}
	
	public WebDriver getAddToWishList(WebDriver driver, String asinItem) {
		
		if(asinItem == null) {
			return null;
		}
		
		WebElement searchElement = driver.findElement(By.id("twotabsearchtextbox"));
		searchElement.clear();
		searchElement.sendKeys(asinItem);
		List<WebElement> searchButton = driver.findElements(By.tagName("input"));
		for (WebElement searchButtonElement : searchButton) {
			if(searchButtonElement.getAttribute("type").equals("submit")) {
				searchButtonElement.submit();
				break;
			}
		}
		
		Timer.getTimeInSeconds(5);
		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
		Timer.getTimeInSeconds(15);
		
		WebElement ulElement = driver.findElement(By.id("s-results-list-atf"));
		List<WebElement> linkAElement = ulElement.findElements(By.tagName("a"));
		for (WebElement linkElement : linkAElement) {
			if(linkElement.getAttribute("class").contains("s-access-detail-page")) {
				String hrefElement = linkElement.getAttribute("href");
				if(!linkElement.getAttribute("href").contains(AMAZON_URL)) {
					hrefElement = AMAZON_URL + hrefElement;
				}
				driver.get(hrefElement);
				break;
			}
		}
		
		Timer.getTimeInSeconds(5);
		currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
		Timer.getTimeInSeconds(15);
		
		WebElement addToWishListBtn = driver.findElement(By.id("add-to-wishlist-button-submit"));
		addToWishListBtn.click();
		Timer.getTimeInSeconds(10);
	
	return driver;
	}
	
}
