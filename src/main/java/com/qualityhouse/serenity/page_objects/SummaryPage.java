package com.qualityhouse.serenity.page_objects;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

public class SummaryPage extends PageObject {

    public final static By SUMMARY_TAXES_LOCATOR =
            //By.cssSelector("ul._1hvzytt li ._ra05uc");
            By.cssSelector("#book_it_form ._ni9axhe span._1jlnvra2 ._j1kt73");
    public final static By SUMMARY_DISCOUNTS_LOCATOR =
            //By.cssSelector("ul._1hvzytt li span._l1ngr4");
            By.cssSelector("#book_it_form ._ni9axhe span._1jlnvra2 span._krjbj");

    @FindBy(xpath = "//*[@id=\"book_it_form\"]/div[1]/div[1]/div/div/div/div/div/div/div[1]/div/div")
    //@FindBy(xpath = "//*[@id=\"site-content\"]/div/div[4]/div/div/div[3]/div/div/div[1]/div/div/div/div[1]/div[2]/div/div/div/div[1]/div/div/div[2]/div[1]/div[2]")
    public WebElementFacade checkInDate;

    @FindBy(xpath = "//*[@id=\"book_it_form\"]/div[1]/div[1]/div/div/div/div/div/div/div[3]/div")
    //@FindBy(xpath = "//*[@id=\"site-content\"]/div/div[4]/div/div/div[3]/div/div/div[1]/div/div/div/div[1]/div[2]/div/div/div/div[1]/div/div/div[2]/div[2]/div[2]")
    public WebElementFacade checkOutDate;

    @FindBy(css = "div .guest-label span[class^='guest-label__text guest-label__text-guests']")
    //@FindBy(css = "div ._1ir6ymk span")
    public WebElementFacade numberOfGuests;

    @FindBy(css = "div .guest-label span[class^='guest-label__text guest-label__text-infants']")
    public WebElementFacade numberOfBabies;

    @FindBy(css = "div ._ni9axhe ._121z06r2 ._j1kt73, ._1d3ext9m")
    //@FindBy(css = "._j44qhm ._1d3ext9m")
    public WebElementFacade totalPrice;

    //@FindBy(css = "button._1o4htsfg[type='submit']")
    //public WebElementFacade reserveButton;

}
