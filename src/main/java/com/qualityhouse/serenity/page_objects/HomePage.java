package com.qualityhouse.serenity.page_objects;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

@DefaultUrl("https://bg.airbnb.com/")
public class HomePage extends PageObject {

    public final static By CALENDAR_DAY_PICK_LOCATOR =
            By.xpath("//*[@aria-label='Календар']/div[2]/div/div[2]/div/table/tbody/tr/td[@class]");
    public final static By CALENDAR_MONTH_YEAR_TEXT_LOCATOR =
            By.xpath("//*[@aria-label='Календар']/div[2]/div/div[2]/div/div/strong");
    public final static By GUESTS_TYPE_PICK_LOCATOR =
            By.cssSelector("._mke2gl1 div._1p3joamp");
    public final static By GUESTS_ADD_BUTTON_LOCATOR =
            By.cssSelector("._1a72ixey [type='button']._1iz654np");

    @FindBy(name = "query")
    public WebElementFacade whereInputField;

    @FindBy(name = "checkin")
    public WebElementFacade checkInDate;

    @FindBy(name = "checkout")
    public WebElementFacade checkOutDate;

    @FindBy(id = "lp-guestpicker")
    public WebElementFacade guestsPickButton;

    @FindBy(css = "[type='button']._b0ybw8s")
    public WebElementFacade guestsSaveButton;

    @FindBy(css = "._1r868w ._1vs0x720")
    public WebElementFacade bookingSearchButton;

}
