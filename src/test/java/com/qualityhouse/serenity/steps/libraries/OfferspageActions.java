package com.qualityhouse.serenity.steps.libraries;

import com.qualityhouse.serenity.page_objects.OffersPage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Steps;

import java.util.List;
import java.util.NoSuchElementException;

import static com.qualityhouse.serenity.page_objects.OffersPage.OFFERS_SEARCH_RESULT_BUTTON_LIST_LOCATOR;
import static com.qualityhouse.serenity.page_objects.OffersPage.OFFERS_SEARCH_RESULT_LAST_PAGE_LOCATOR;

public class OfferspageActions {

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

    public void clicksOnNextOffersResultPage() throws InterruptedException {

        List<WebElementFacade> resultPaginationButtons =
                offersPage.findAll(OFFERS_SEARCH_RESULT_BUTTON_LIST_LOCATOR);
        int lastIndexOfList = resultPaginationButtons.size() - 1;

        try {
            rumi.clicksOn(resultPaginationButtons.get(lastIndexOfList));
        } catch (NoSuchElementException e) {
            System.out.println("Button 'Next-page' not found, no more offers to display");
        }
    }
}
