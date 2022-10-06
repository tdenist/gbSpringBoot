package com.spring.qa.auto.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JavaMobileProfessionPage {

    private final WebDriver driver;

    @FindBy(xpath = "//div[@class='java-hero-container']/div/p")
    private List<WebElement> formOffer;

    public JavaMobileProfessionPage(@Lazy WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getFormOffer() {
        return formOffer.get(0);
    }
}
