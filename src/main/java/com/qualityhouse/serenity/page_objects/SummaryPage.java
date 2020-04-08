package com.qualityhouse.serenity.page_objects;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

public class SummaryPage extends PageObject {

    public final static By SUMMARY_TAXES_LOCATOR =
            By.cssSelector("._1jlnvra2 span._j1kt73");
    public final static By SUMMARY_DISCOUNTS_LOCATOR =
            By.cssSelector("._15osndh6 ._krjbj");

    @FindBy(xpath = "//*[@id=\"book_it_form\"]/div[1]/div[1]/div/div/div/div/div/div/div[1]/div/div")
    public WebElementFacade checkInDate;

    @FindBy(xpath = "//*[@id=\"book_it_form\"]/div[1]/div[1]/div/div/div/div/div/div/div[3]/div")
    public WebElementFacade checkOutDate;

    @FindBy(css = "div .guest-label span")
    public WebElementFacade numberOfGuests;

    @FindBy(css = "div ._ni9axhe ._121z06r2 ._j1kt73")
    public WebElementFacade totalPrice;

    @FindBy(css = "button._1o4htsfg[type='submit']")
    public WebElementFacade reserveButton;
}
