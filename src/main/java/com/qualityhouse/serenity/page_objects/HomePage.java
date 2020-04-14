package com.qualityhouse.serenity.page_objects;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

@DefaultUrl("https://bg.airbnb.com/")
public class HomePage extends PageObject {

    public final static By CALENDAR_DAY_CURRENT_MONTH_LOCATOR =
            By.xpath("//*[@aria-label='Календар']/div[2]/div/div[2]/div/table/tbody/tr/td[@class]");
    //public final static By CALENDAR_DAY_NEXT_MONTH_LOCATOR =
            //By.xpath("//*[@aria-label='Календар']/div[2]/div/div[3]/div/table/tbody/tr/td[@class]");
    public final static By CALENDAR_MONTH_YEAR_TEXT_LOCATOR =
            By.xpath("//*[@aria-label='Календар']/div[2]/div/div/div/div");
    //public final static By GUESTS_TYPE_PICK_LOCATOR =
            //By.cssSelector("._mke2gl1 div._1p3joamp");
    public final static By GUESTS_ADD_BUTTON_LOCATOR =
            By.cssSelector("div._3zlfom ._11yg8kv[type='button'][aria-label='увеличаване на стойността']");

    @FindBy(id = "bigsearch-query-attached-query")
    public WebElementFacade whereInputField;

    @FindBy(xpath = "//*[@class='_18yccw0']/div/div[3]/div/button")
    public WebElementFacade checkInOutDateButton;

    //@FindBy(name = "checkout")
    //public WebElementFacade checkOutDate;

    @FindBy(xpath = "//*[@class='_18yccw0']/div/div[5]/div/button")
    public WebElementFacade guestsPickButton;

    //@FindBy(css = "[type='button']._b0ybw8s")
    //public WebElementFacade guestsSaveButton;

    @FindBy(css = "div._h6px0p button")
    public WebElementFacade bookingSearchButton;

    @FindBy(css = "div._13tn83am button[aria-label='Напред']")
    public WebElementFacade bookingNextMonthButton;

}