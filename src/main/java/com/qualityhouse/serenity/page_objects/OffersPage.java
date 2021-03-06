package com.qualityhouse.serenity.page_objects;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

public class OffersPage extends PageObject {

    public final static By CURRENCY_PICK_LIST_LOCATOR =
            By.cssSelector("button._lwi6c1u div._a7a5sx");
    public final static By OFFERS_STAR_PRICE_LOCATOR =
            By.cssSelector("._4ntfzh ._13blcrbo, ._1llb8an");
    public final static By OFFERS_SEARCH_RESULT_BUTTON_LIST_LOCATOR =
            By.cssSelector("nav [data-id=SearchResultsPagination] li");
    public final static By OFFERS_SEARCH_RESULT_LAST_PAGE_LOCATOR =
            By.cssSelector("nav [data-id=SearchResultsPagination] li a[aria-label^=Страница]");
    public final static String OFFERSPAGE_HEADING_TITLE = "Престои в района на ";
    public final static By MORE_FILTERS_ZERO_TIMING = By.cssSelector("span._w37zq5[aria-label='Още филтри, 0 филтъра са приложени']");

    @FindBy(css = "h1._14i3z6h")
    public WebElementFacade offersHeadingInfoText;

    @FindBy(id = "menuItemButton-price_range")
    public WebElementFacade filterPriceButton;

    @FindBy(id = "menuItemButton-dynamicMoreFilters")
    public WebElementFacade filterMoreOptionsButton;

    @FindBy(id = "price_filter_min")
    public WebElementFacade inputFieldPriceMin;

    @FindBy(id = "price_filter_max")
    public WebElementFacade inputFieldPriceMax;

    @FindBy(id = "filter-panel-save-button")
    public WebElementFacade filterPriceSaveButton;

    @FindBy(css = "span._19c5bku a[href='/account-settings/currency']")
    public WebElementFacade currencyPickButton;

    @FindBy(css = "#filterItem-stepper-min_bathrooms-0 button[aria-label='увеличаване на стойността']")
    public WebElementFacade bathroomsAddButton;

    @FindBy(css = "#filterItem-stepper-min_bathrooms-0 div._1665lvv span")
    public WebElementFacade bathroomCurrentNumber;

    @FindBy(css = "label._14ulhf4[for='filterItem-checkbox-amenities-5']")
    public WebElementFacade airConditionerCheckbox;

    @FindBy(css = "label._14ulhf4[for='filterItem-checkbox-amenities-25']")
    public WebElementFacade hotTubeCheckbox;

    @FindBy(className = "_2i58o3a")
    public WebElementFacade filterMoreOptionsSaveButton;

    @FindBy(css = "nav [data-id=SearchResultsPagination] li ._115zncnj")
    public WebElementFacade currentNumberOfPageWithOffers;
}