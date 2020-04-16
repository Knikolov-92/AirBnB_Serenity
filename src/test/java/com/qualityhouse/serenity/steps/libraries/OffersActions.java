package com.qualityhouse.serenity.steps.libraries;

import com.qualityhouse.serenity.page_objects.OffersPage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

import java.util.List;
import java.util.NoSuchElementException;

import static com.qualityhouse.serenity.page_objects.OffersPage.*;

public class OffersActions {

    private OffersPage offersPage;
    @Steps
    private BaseActions rumi;

    public String getsCurrentPageNumberWithOffers() {

        String currentPageNumberString = offersPage.currentNumberOfPageWithOffers.getText().trim();
        System.out.println("Current page with offers: " +currentPageNumberString);
        return  currentPageNumberString;
    }

    public String getsLastPageNumberWithOffers() {

        List<WebElementFacade> pageLinkButtons = offersPage.findAll(OFFERS_SEARCH_RESULT_LAST_PAGE_LOCATOR);
        String lastPageNumberString = pageLinkButtons.get(pageLinkButtons.size() - 1).getText().trim();
        System.out.println("Last page with offers: " +lastPageNumberString);
        return  lastPageNumberString;
    }

    public void clicksOnNextOffersResultPage() {

        List<WebElementFacade> resultPaginationButtons =
                offersPage.findAll(OFFERS_SEARCH_RESULT_BUTTON_LIST_LOCATOR);
        int lastIndexOfList = resultPaginationButtons.size() - 1;

        try {
            rumi.clicksOn(resultPaginationButtons.get(lastIndexOfList));
        } catch (NoSuchElementException e) {
            System.out.println("Button 'Next-page' not found, no more offers to display");
        }
    }

    @Step
    public void picksCurrency(String targetCurrency) {

        String loopElement = "";
        rumi.movesPointerToElement(offersPage.currencyPickButton);
        rumi.clicksOn(offersPage.currencyPickButton);
        List<WebElementFacade> currencyPickList = offersPage.findAll(CURRENCY_PICK_LIST_LOCATOR);

        for(int i = 0; i < currencyPickList.size(); i++) {

            loopElement = currencyPickList.get(i).getText().toUpperCase().substring(0, 3);
            System.out.println("current currency is: " +loopElement);
            if(loopElement.equals(targetCurrency) ) {
                rumi.clicksOn(currencyPickList.get(i) );
                break;
            }
        }
    }
}
