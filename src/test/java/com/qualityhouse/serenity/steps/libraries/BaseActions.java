package com.qualityhouse.serenity.steps.libraries;

import com.qualityhouse.serenity.page_objects.HomePage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;


public class BaseActions {

    private HomePage currentPage;
    private WebDriver driver = getDriver();

    @Step
    public void clicksOn(WebElementFacade buttonOrLink) throws InterruptedException {
        buttonOrLink.waitUntilClickable().click();
        Thread.sleep(1250);
    }

    @Step
    public void clicksOn(final By locator) throws InterruptedException {
        currentPage.find(locator)
                .waitUntilClickable()
                .click();
        Thread.sleep(1250);
    }

    @Step
    public void entersTextInField(WebElementFacade inputField, String text) {

        inputField.waitUntilVisible().type(text);
    }

    @Step
    public void movesPointerToElement(WebElementFacade element) throws InterruptedException {

        Actions actions = new Actions(driver);
        actions.moveToElement(element,0, 3).perform();
        Thread.sleep(1000);
    }

    public void scrollsDownThePage() throws InterruptedException {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        Thread.sleep(500);
    }

    public LocalDate getsCurrentDatePlus(int daysToAdd) {

        LocalDate currentDate = LocalDate.now();
        return currentDate.plusDays(daysToAdd);
    }

    public String getsDateFormatted(LocalDate dateToFormat, String formatPattern, @Nullable Locale locale) {

        if(locale != null) {
            return dateToFormat.format(DateTimeFormatter.ofPattern(formatPattern, locale) );
        }
        else {
            return dateToFormat.format(DateTimeFormatter.ofPattern(formatPattern) );
        }
    }

}
