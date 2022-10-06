package com.spring.qa.auto.page;

import com.spring.qa.auto.util.Pause;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class SearchResultPage {

    private final WebDriver driver;

    @Value("${element.explicit.wait}")
    private Long explicitWait;

    @FindBy(xpath = "//ul[@class='search-page-tabs']/li")
    private List<WebElement> searchPageHeader;

    @FindBy(xpath = "//ul[@class='search-page-tabs']//a[contains(text(),'Профессии')]")
    private WebElement profession;

    @FindBy(xpath = "//div[@class='container']/div[@class='row']/div[@class='profession-item-wrapper search_row col-md-6 col-xs-12 col-lg-4']//div[@class='profession-title']")
    private List<WebElement> professionTitles;

    public SearchResultPage(@Lazy WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void getProfession(String name) {

        WebElement waitedProfession = new WebDriverWait(driver, Duration.ofSeconds(explicitWait))
                .until(ExpectedConditions.elementToBeClickable(profession));

        //element not click after waiting
        Pause.pause(1);
        waitedProfession.click();

        List<WebElement> waitProfessionTitles = new WebDriverWait(driver, Duration.ofSeconds(explicitWait))
                .until(ExpectedConditions.visibilityOfAllElements(professionTitles));

        //element not click after waiting
        Pause.pause(1);
        waitProfessionTitles.stream()
                .filter(w -> w.getText().contains(name))
                .findFirst()
                .orElseThrow()
                .click();
    }

}
