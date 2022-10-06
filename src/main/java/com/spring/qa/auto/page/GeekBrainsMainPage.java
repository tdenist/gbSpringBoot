package com.spring.qa.auto.page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class GeekBrainsMainPage {

    private final WebDriver driver;
    private final SearchResultPage searchResultPage;

    @Value("${gb.url}")
    private String mainURL;

    @FindBy(xpath = "//div[@class='mn-header__icons-item']//div[@class='mn-btn-icon__icon']/img[@alt='magnifier-icon']")
    private WebElement search;

    @FindBy(xpath = "//div[@class='mn-search-panel__input-wrap']//input")
    private WebElement input;

    public GeekBrainsMainPage(@Lazy WebDriver driver, SearchResultPage searchResultPage) {
        this.searchResultPage = searchResultPage;
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public GeekBrainsMainPage getMainPage() {
        driver.get(mainURL);
        return this;
    }

    public SearchResultPage search(String info) {
        search.click();
        input.sendKeys(info);
        String originalWindow = driver.getWindowHandle();
        input.sendKeys(Keys.ENTER);

        //switch window
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        return searchResultPage;
    }
}
