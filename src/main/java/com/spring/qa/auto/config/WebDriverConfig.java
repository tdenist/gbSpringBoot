package com.spring.qa.auto.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class WebDriverConfig {

    @Value("${web.driver.wait.seconds}")
    private Integer secondsWait;

    @Bean
    @Profile("window")
    public WebDriver chromeWebDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeDriver chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
        chromeDriver.manage().timeouts().implicitlyWait(Duration.of(secondsWait, ChronoUnit.SECONDS));
        return chromeDriver;
    }

    @Bean
    @Profile("no_window")
    public WebDriver chromeWebDriverWithoutWindow() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        ChromeDriver chromeDriver = new ChromeDriver(chromeOptions);
        chromeDriver.manage().timeouts().implicitlyWait(Duration.of(secondsWait, ChronoUnit.SECONDS));
        return chromeDriver;
    }
}
