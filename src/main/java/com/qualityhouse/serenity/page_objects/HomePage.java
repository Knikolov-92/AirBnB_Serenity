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

    @FindBy(name = "query")
    public WebElementFacade whereInputField;

    @FindBy(css = "label._rin72m")
    public WebElementFacade checkInDate;

    @FindBy(name = "checkout")
    public WebElementFacade checkOutDate;

}
//*[@aria-label='Календар']/div[2]/div/div[2]/div/table/tbody/tr/td[@class]
//*[@aria-label='Календар']
//div[@class='_gucugi']/strong[text()='April 2020']
//*[@id="FMP-target"]/div/div[1]/div/div/div/div/div[1]/div[2]/div/form/div[2]/div/div/div/div[2]/div/div/div/div/div/div[2]/div[2]/div/div[2]/div/table/tbody/tr/td[@class]