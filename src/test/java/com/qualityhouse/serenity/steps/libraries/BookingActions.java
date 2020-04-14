package com.qualityhouse.serenity.steps.libraries;

import com.qualityhouse.serenity.page_objects.HomePage;
import com.qualityhouse.serenity.page_objects.OffersPage;
import com.qualityhouse.serenity.page_objects.SummaryPage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import java.time.LocalDate;
import java.util.*;

import static com.qualityhouse.serenity.page_objects.HomePage.*;
import static com.qualityhouse.serenity.page_objects.OffersPage.*;
import static com.qualityhouse.serenity.page_objects.SummaryPage.*;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingActions {

    private HomePage homePage;
    private OffersPage offersPage;
    private SummaryPage summaryPage;
    private WebDriver driver = getDriver();
    private SoftAssertions softly = new SoftAssertions();
    private String vacationStartDate;
    private String vacationEndDate;

    @Steps
    private BaseActions ilio;
    @Steps
    private HomepageActions niki;

    @Step
    public void picksCheckInCheckOutDates(String checkIn, String checkOut) throws InterruptedException {

        String patternDefault = "d/MMMM/yyyy";
        String patternSummary = "M/d/yyyy";
        Locale localeDefault = Locale.ENGLISH;
//get current date ----------------------------------------------------------------------------------------
        LocalDate currentDate = ilio.getsCurrentDatePlus(0);
        String currentDateFormatted = ilio.getsDateFormatted(currentDate, patternDefault, localeDefault);

//get start date ------------------------------------------------------------------------------------------
        LocalDate bookStart = ilio.getsCurrentDatePlus(Integer.parseInt(checkIn));
        String startDateFormatted = ilio.getsDateFormatted(bookStart, patternDefault, localeDefault);
        String[] startDateArray = startDateFormatted.split("/");
        String dayStart = startDateArray[0];
        String monthStart = startDateArray[1].toLowerCase();
        String yearStart = startDateArray[2];

//get end date --------------------------------------------------------------------------------------------
        LocalDate bookEnd = ilio.getsCurrentDatePlus(Integer.parseInt(checkOut));
        String endDateFormatted = ilio.getsDateFormatted(bookEnd, patternDefault, localeDefault);
        String[] endDateArray = endDateFormatted.split("/");
        String dayEnd = endDateArray[0];
        String monthEnd = endDateArray[1].toLowerCase();
        String yearEnd = endDateArray[2];

//print dates ---------------------------------------------------------------------------------------------
        System.out.println("current date: " +currentDateFormatted
                +", start: " +startDateFormatted+ ", end: " +endDateFormatted );

//set start/end date to check in summary page--------------------------------------------------------------
        this.vacationStartDate = ilio.getsDateFormatted(bookStart, patternSummary, null);
        this.vacationEndDate = ilio.getsDateFormatted(bookEnd, patternSummary, null);

//click on CheckInOut button ------------------------------------------------------------------------------
        ilio.clicksOn(homePage.checkInOutDateButton);

//select start date ---------------------------------------------------------------------------------------
        niki.selectsCheckInOutDate(dayStart, monthStart, yearStart);

//select end date -----------------------------------------------------------------------------------------
        niki.selectsCheckInOutDate(dayEnd, monthEnd, yearEnd);
    }

    @Step
    public void picksGuestsOptions(String adultsNumber, String kidsNumber, String babiesNumber)
            throws InterruptedException {

        ilio.clicksOn(homePage.guestsPickButton);
        List<WebElementFacade> guestsAddButtons = homePage.findAll(GUESTS_ADD_BUTTON_LOCATOR);

        for (int n = 1; n <= Integer.parseInt(adultsNumber); n++) {

            ilio.clicksOn(guestsAddButtons.get(0));
        }

        for (int n = 1; n <= Integer.parseInt(kidsNumber); n++) {

            ilio.clicksOn(guestsAddButtons.get(1));
        }

        for (int n = 1; n <= Integer.parseInt(babiesNumber); n++) {

            ilio.clicksOn(guestsAddButtons.get(2));
        }

        //ilio.clicksOn(homePage.guestsSaveButton);
    }

    @Step
    public void picksCurrency(String targetCurrency) throws InterruptedException {

        String loopElement = "";
        ilio.movesPointerToElement(offersPage.currencyPickButton);
        ilio.clicksOn(offersPage.currencyPickButton);
        List<WebElementFacade> currencyPickList = offersPage.findAll(CURRENCY_PICK_LIST_LOCATOR);

        for(int i = 0; i < currencyPickList.size(); i++) {

            loopElement = currencyPickList.get(i).getText().toUpperCase().substring(0, 3);
            System.out.println("current currency is: " +loopElement);
            if(loopElement.equals(targetCurrency) ) {
                ilio.clicksOn(currencyPickList.get(i) );
                break;
            }
        }
    }

    @Step
    public void entersPriceRange(String minPrice, String maxPrice) throws InterruptedException {

        ilio.clicksOn(offersPage.filterPriceButton);
        ilio.entersTextInField(offersPage.inputFieldPriceMin, minPrice);
        ilio.entersTextInField(offersPage.inputFieldPriceMax, maxPrice);
        ilio.clicksOn(offersPage.filterPriceSaveButton);
    }

    public void choosesMoreFilterOptions(String numBaths, String airConYesNo, String hotTubeYesNo)
            throws InterruptedException {

        ilio.clicksOn(offersPage.filterMoreOptionsButton);
        WebElementFacade airConBox = offersPage.airConditionerCheckbox;
        WebElementFacade hotTubeBox = offersPage.hotTubeCheckbox;

        for(int i = 1; i <= Integer.parseInt(numBaths); i++) {

            ilio.clicksOn(offersPage.bathroomsAddButton);
        }
        assertThat(offersPage.bathroomCurrentNumber.getText() ).isEqualTo(numBaths);

        ilio.movesPointerToElement(airConBox);
        if(airConYesNo.equalsIgnoreCase("yes") ) {
            ilio.clicksOn(airConBox);
        }

        ilio.movesPointerToElement(hotTubeBox);
        if(hotTubeYesNo.equalsIgnoreCase("yes") ) {
            ilio.clicksOn(hotTubeBox);
        }

        ilio.clicksOn(offersPage.filterMoreOptionsSaveButton);
    }

    @Step
    public void checksSummaryDates() throws InterruptedException {

        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(1));
        Thread.sleep(2000);
        ilio.movesPointerToElement(summaryPage.totalPrice);

        String actualStartDate = summaryPage.checkInDate.getText().trim();
        String actualEndDate = summaryPage.checkOutDate.getText().trim();

        System.out.println("Expected start_date:" +this.vacationStartDate);
        System.out.println("Actual start_date:" +actualStartDate);
        softly.assertThat(actualStartDate).isEqualTo(this.vacationStartDate);

        System.out.println("End_day:" +this.vacationEndDate);
        System.out.println("Actual end_date:" +actualEndDate);
        softly.assertThat(actualEndDate).isEqualTo(this.vacationEndDate);

        softly.assertAll();
    }

    @Step
    public void checksSummaryNumberOfGuests(int expectedNumberOfGuests, int expectedNumberOfBabies) {

        if(expectedNumberOfGuests > 0) {
            String actualNumberOfGuests = summaryPage.numberOfGuests.getText().substring(0, 1);
            System.out.println("Actual number of guests: " + actualNumberOfGuests);
            assertThat(actualNumberOfGuests).isEqualTo(Integer.toString(expectedNumberOfGuests));
        }

        if(expectedNumberOfBabies > 0) {
            String actualNumberOfBabies = summaryPage.numberOfBabies.getText().substring(0, 1);
            System.out.println("Actual number of babies: " + actualNumberOfBabies);
            assertThat(actualNumberOfBabies).isEqualTo(Integer.toString(expectedNumberOfBabies));
        }
    }

    @Step
    public void checksSummaryTotalPrice(String valueToCheck) {

        Currency euro = Currency.getInstance("EUR");
        String euroSymbol = euro.getSymbol();
        List<WebElementFacade> costsList = summaryPage.findAll(SUMMARY_TAXES_LOCATOR);
        List<WebElementFacade> discountsList = summaryPage.findAll(SUMMARY_DISCOUNTS_LOCATOR);
        String actualTaxText = "";
        String actualDiscountText = "0";
        int sumTotal = 0;

        for(WebElementFacade cost : costsList) {

            String[] costArr = cost.getText().trim().substring(1).split(" ");
                for(String value : costArr) {
                    actualTaxText += value;
                }
            sumTotal += Integer.parseInt(actualTaxText);
            System.out.println("+" +actualTaxText +"; current sum = " +sumTotal);
            actualTaxText = "";

        }

        if(discountsList.size() != 0) {
            for(WebElementFacade discount : discountsList) {
                actualDiscountText = discount.getText().trim().substring(7);
            }
             sumTotal -= Integer.parseInt(actualDiscountText);
            System.out.println("-" +actualDiscountText +", currernt sum =" +sumTotal);
        }

        String[] totalPriceArr = summaryPage.totalPrice.getText().trim().split(" ");
        String totalPriceText = "";
            for(String value : totalPriceArr) {
                totalPriceText += value.trim();
            }

        System.out.println("expected: " +euroSymbol +sumTotal +", actual: " +totalPriceText);

        softly.assertThat(Integer.parseInt(totalPriceText.substring(1)) ).isEqualTo(sumTotal);
        softly.assertThat(totalPriceText).isEqualTo(euroSymbol + (Integer.toString(sumTotal)));
        softly.assertThat(totalPriceText).isEqualTo(valueToCheck);
        softly.assertAll();
    }

    public void clicksOnNextOffersResultPage() throws InterruptedException {

        List<WebElementFacade> resultPaginationButtons =
                offersPage.findAll(OFFERS_SEARCH_RESULT_BUTTON_LIST_LOCATOR);
        int lastIndexOfList = resultPaginationButtons.size() - 1;

        try {
            ilio.clicksOn(resultPaginationButtons.get(lastIndexOfList));
        } catch (NoSuchElementException e) {
            System.out.println("Button 'Next-page' not found, no more offers to display");
        }
    }

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
}
