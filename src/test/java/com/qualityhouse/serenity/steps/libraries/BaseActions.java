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
    public void clicksOn(WebElementFacade buttonOrLink) {

        buttonOrLink.waitUntilClickable().click();
    }

    @Step
    public void clicksOn(final By locator) {

        currentPage.find(locator)
                .waitUntilClickable()
                .click();
    }

    @Step
    public void entersTextInField(WebElementFacade inputField, String text) {

        inputField.waitUntilEnabled().type(text);
    }

    @Step
    public void movesPointerToElement(WebElementFacade element) {

        Actions actions = new Actions(driver);
        element.waitUntilPresent();
        actions.moveToElement(element,0, 3).perform();
        element.waitUntilVisible();
    }

    public void scrollsDownThePage() {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
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
